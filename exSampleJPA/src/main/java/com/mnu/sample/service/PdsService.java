package com.mnu.sample.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

/*
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
*/
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
	
	//글 수정
	@Transactional
	public int pdsModify(int idx, PdsRequestDTO pdsRequestDTO) {
		return pdsRepository.pdsModify(idx, pdsRequestDTO.getSubject(), 
				pdsRequestDTO.getContents(), pdsRequestDTO.getFilename(), 
				pdsRequestDTO.getPass());
	}
	

	// 전체 목록(검색 X, 페이지 처리 )
	@Transactional
	public Page<PdsResponseDTO> pdsList(Pageable pageable){
		// 기존 pageable의 페이지 번호와 사이즈를 유지하면서, 정렬 조건만 idx 내림차순으로 덮어씁니다.
	    Pageable sortedPageable = PageRequest.of(
	        pageable.getPageNumber(), 
	        pageable.getPageSize(), 
	        Sort.by(Sort.Direction.DESC, "idx")
	    );
	    
		Page<PdsEntity> page;
		//page = pdsRepository.findAll(pageable);//오름차순
		page = pdsRepository.findAll(sortedPageable);
		return page.map(PdsResponseDTO::new);
	}
	
	// 전체 목록(검색 , 페이지 처리 )
	@Transactional
	public Page<PdsResponseDTO> pdsListSearchPage(String search, String key, Pageable pageable){
		// 기존 pageable의 페이지 번호와 사이즈를 유지하면서, 정렬 조건만 idx 내림차순으로 덮어씁니다.
	    Pageable sortedPageable = PageRequest.of(
	        pageable.getPageNumber(), 
	        pageable.getPageSize(), 
	        Sort.by(Sort.Direction.DESC, "idx")
	    );
	    
		Page<PdsEntity> page;
		if(key != null && !key.equals("")) {
			//검색 O
			page = pdsRepository.pdsListSearchPage(search, key, pageable);
		}else {
			//검색 X
			//page = pdsRepository.findAll(pageable);//오름차순
			page = pdsRepository.findAll(sortedPageable);
		}
		return page.map(PdsResponseDTO::new);
	}

	

	//수정폼(modify)
	@Transactional
	public PdsResponseDTO pdsModify(int idx) {
		PdsEntity pdsEntity = pdsRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
	          //.orElseThrow(() -> new CustomException("존재하지 idx입니다."));
		      // 위로 던지기(비스에서는 예외를 try-catch로 잡아서 해결하려고 하지 말고, 
		      //상황에 맞는 CustomException으로 전환하여 컨트롤러 방향으로 던집니다.
		PdsResponseDTO pds = new PdsResponseDTO(pdsEntity);
		return pds;
	}
	
	
	// 조건에 맞는 글수 카운트
	@Transactional
	public long pdsCountSearch(String search, String key) {
		switch(search) {
		case "name":
			return pdsRepository.countByNameContaining(key);
		case "subject":
			return pdsRepository.countBySubjectContaining(key);
		case "contents":
			return pdsRepository.countByContentsContaining(key);
		default:
			return 0;
		}
	}
	
	//조건에 맞는 게시글 목록
	@Transactional
	public List<PdsResponseDTO> pdsListSearch(String search, String key){
		switch(search) {
		case "name":
			return pdsRepository.findByNameContaining(key)
					.stream()
					.map(PdsResponseDTO::new)
					.toList();
			//PdsRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
		case "subject":
			return pdsRepository.findBySubjectContaining(key)
					.stream()
					.map(PdsResponseDTO::new)
					.toList();
			//PdsRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
		case "contents":
			return pdsRepository.findByContentsContaining(key)
					.stream()
					.map(PdsResponseDTO::new)
					.toList();
			//PdsRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
		default:
			return null;
		}
	}

	
}
