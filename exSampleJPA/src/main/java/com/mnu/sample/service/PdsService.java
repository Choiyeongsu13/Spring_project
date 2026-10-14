package com.mnu.sample.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.dto.PdsRequestDTO;
import com.mnu.sample.dto.PdsResponseDTO;
import com.mnu.sample.entity.PdsEntity;
import com.mnu.sample.repository.PdsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //Bean 주입
public class PdsService {
	private final PdsRepository pdsRepository;
	
	// 카운트(전체 게시글 수)
	@Transactional
	public long pdsCount() {
		return pdsRepository.count();
	}

	// 전체 목록(검색, 페이지 처리 없음)
	@Transactional
	public List<PdsResponseDTO> pdsList(){
		return pdsRepository.findAll()
				.stream()
				// Pds 엔티티의 getIdx()를 기준으로 내림차순(reversed) 정렬
	            //.sorted(Comparator.comparing(PdsEntity::getIdx).reversed())
				// Pds 엔티티의 등록일자getRegdate()를 기준으로 내림차순(reversed) 정렬
	            .sorted(Comparator.comparing(PdsEntity::getRegdate).reversed())
				.map(PdsResponseDTO::new)
				//.collect(Collectors.toList());
				.toList();//JDK 16이상
		// PdsRepository결과로 넘어온 PdsEntity의 Stream을 map을 통해 PdsReponseDto로 변환 
        //   -> List로 반환하는 메서드
	}

	// 등록 처리
	@Transactional
	public int pdsWrite(PdsRequestDTO pdsRequestDTO){
		//DTO을 Entity로 변환후 저장
		return pdsRepository.save(pdsRequestDTO.toEntity()).getIdx();
		//등록후 등록된 idx 반환
	}
	
	// 글 상세보기
	@Transactional
	public PdsResponseDTO pdsView(int idx) {
		//조회수 증가
		pdsRepository.pdsHits(idx);
		
		PdsEntity pdsEntity = pdsRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		
		PdsResponseDTO pds = new PdsResponseDTO(pdsEntity);
		return pds;
	}

/*	
	//글 삭제(무조건 삭제시 : 비밀번호 관계없이)
	@Transactional
	public void pdsDelete(int idx) {
		pdsRepository.deleteById(idx);//idx에 해다하는 글 삭제
	}
*/
	//글 삭제(idx, pass 이용)
	@Transactional
	public int pdsDelete(int idx, String pass) {
		return pdsRepository.pdsDelete(idx, pass);//idx에 해당하는 글 삭제
	}

	//수정폼(조회수 증가 없이 조회)
	@Transactional
	public PdsResponseDTO pdsModify(int idx) {
		PdsEntity pdsEntity = pdsRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		return new PdsResponseDTO(pdsEntity);
	}

	//수정처리(비밀번호가 같을때만 수정 : 성공 1, 비번오류 0)
	@Transactional
	public int pdsModifyPro(int idx, PdsRequestDTO pds) {
		PdsEntity pdsEntity = pdsRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		if(!pdsEntity.getPass().equals(pds.getPass())) {
			return 0;
		}
		//@Transactional -> 변경 감지로 update
		pdsEntity.pdsUpdate(pds.getSubject(), pds.getContents(), pds.getFilename());
		return 1;
	}


}
