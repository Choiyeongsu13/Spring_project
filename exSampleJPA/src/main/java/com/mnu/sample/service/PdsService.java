package com.mnu.sample.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mnu.sample.dto.PdsRequestDTO;
import com.mnu.sample.dto.PdsResponseDTO;
import com.mnu.sample.entity.PdsEntity;
import com.mnu.sample.repository.PdsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PdsService {
	private final PdsRepository pdsRepository;

	//업로드 파일 저장 폴더 (기본: 프로젝트폴더/upload/pds)
	@Value("${pds.upload-dir:${user.dir}/upload/pds}")
	private String uploadDir;

	//목록(검색 + 페이지)
	public Page<PdsResponseDTO> pdsListSearchPage(String search, String key, Pageable pageable) {
		Page<PdsEntity> page;
		if(key != null && !key.equals("")) {
			//검색 O
			page = pdsRepository.pdsListSearchPage(search, key, pageable);
		}else {
			//검색 X -> idx 내림차순
			Pageable sortedPageable = PageRequest.of(
					pageable.getPageNumber(),
					pageable.getPageSize(),
					Sort.by(Sort.Direction.DESC, "idx"));
			page = pdsRepository.findAll(sortedPageable);
		}
		return page.map(this::toResponse);
	}

	//entity -> dto (저장된 파일의 크기 포함)
	private PdsResponseDTO toResponse(PdsEntity pds) {
		long filesize = -1;
		if(pds.getSavename() != null) {
			File file = new File(uploadDir, pds.getSavename());
			if(file.exists()) filesize = file.length();
		}
		return new PdsResponseDTO(pds, filesize);
	}

	//등록처리(파일 저장 + DB 저장)
	public int pdsWrite(PdsRequestDTO pds, MultipartFile file) throws IOException {
		String filename = null;
		String savename = null;

		//첨부파일이 있으면 서버 폴더에 저장
		if(file != null && !file.isEmpty()) {
			File dir = new File(uploadDir);
			if(!dir.exists()) dir.mkdirs();

			filename = new File(file.getOriginalFilename()).getName(); //경로 제거한 원래 이름
			savename = UUID.randomUUID() + "_" + filename;			//같은 이름 파일 덮어쓰기 방지
			file.transferTo(new File(dir, savename));
		}
		return pdsRepository.save(pds.toEntity(filename, savename)).getIdx();
	}

	//상세보기(조회수 증가)
	public PdsResponseDTO pdsView(int idx) {
		pdsRepository.pdsHits(idx);
		PdsEntity pdsEntity = pdsRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		return toResponse(pdsEntity);
	}

	//다운로드할 파일
	public PdsEntity pdsFile(int idx) {
		return pdsRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
	}
	public Resource pdsResource(PdsEntity pds) {
		if(pds.getSavename() == null) return null;
		Resource resource = new FileSystemResource(new File(uploadDir, pds.getSavename()));
		return resource.exists() ? resource : null;
	}

	//삭제(비번이 맞으면 DB + 파일 삭제) : 성공 1, 비번오류 0
	public int pdsDelete(int idx, String pass) {
		PdsEntity pds = pdsRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		if(!pds.getPass().equals(pass)) {
			return 0;
		}
		pdsRepository.delete(pds);
		if(pds.getSavename() != null) {
			new File(uploadDir, pds.getSavename()).delete();
		}
		return 1;
	}
}
