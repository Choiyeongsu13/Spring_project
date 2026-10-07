package com.mnu.sample.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mnu.sample.dto.NoticeResponseDTO;
import com.mnu.sample.entity.NoticeEntity;
import com.mnu.sample.repository.NoticeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	private final NoticeRepository noticeRepository;
	
	
	// 카운트(전체 게시글 수)
	@Transactional
	public long noticeCount() {
		return noticeRepository.count();
	}
	// 전체 목록(검색 X, 페이지 처리 )
	@Transactional
	public Page<NoticeResponseDTO> noticeList(Pageable pageable){
		// 기존 pageable의 페이지 번호와 사이즈를 유지하면서, 정렬 조건만 idx 내림차순으로 덮어씁니다.
	    Pageable sortedPageable = PageRequest.of(
	        pageable.getPageNumber(), 
	        pageable.getPageSize(), 
	        Sort.by(Sort.Direction.DESC, "idx")
	    );
	    
		Page<NoticeEntity> page;
		//page = NoticeRepository.findAll(pageable);//오름차순
		page = noticeRepository.findAll(sortedPageable);
		return page.map(NoticeResponseDTO::new);
	}
	
	// 전체 목록(검색 , 페이지 처리 )
	@Transactional
	public Page<NoticeResponseDTO> noticeListSearchPage(String search, String key, Pageable pageable){
		// 기존 pageable의 페이지 번호와 사이즈를 유지하면서, 정렬 조건만 idx 내림차순으로 덮어씁니다.
	    Pageable sortedPageable = PageRequest.of(
	        pageable.getPageNumber(), 
	        pageable.getPageSize(), 
	        Sort.by(Sort.Direction.DESC, "idx")
	    );
	    
		Page<NoticeEntity> page;
		if(key != null && !key.equals("")) {
			//검색 O
			page = noticeRepository.noticeListSearchPage(search, key, pageable);
		}else {
			//검색 X
			//page = NoticeRepository.findAll(pageable);//오름차순
			page = noticeRepository.findAll(sortedPageable);
		}
		return page.map(NoticeResponseDTO::new);
	}

	
	//상세보기(View)
	@Transactional
	public NoticeResponseDTO noticeView(int idx) {
		//조회수 증가
		noticeRepository.noticeHits(idx);
		
		NoticeEntity noticeEntity = noticeRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
	
		NoticeResponseDTO notice = new NoticeResponseDTO(noticeEntity);
		return notice;
	}
	
	
	// 조건에 맞는 글수 카운트
	@Transactional
	public long noticeCountSearch(String search, String key) {
		switch(search) {
		case "name":
			return noticeRepository.countByAdidContaining(key);
		case "subject":
			return noticeRepository.countBySubjectContaining(key);
		case "contents":
			return noticeRepository.countByContentsContaining(key);
		default:
			return 0;
		}
	}
	
	//조건에 맞는 게시글 목록
			@Transactional
			public List<NoticeResponseDTO> noticeListSearch(String search, String key){
				switch(search) {
				case "name":
					return noticeRepository.findByAdidContaining(key)
							.stream()
							.map(NoticeResponseDTO::new)
							.toList();
					//NoticeRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
				case "subject":
					return noticeRepository.findBySubjectContaining(key)
							.stream()
							.map(NoticeResponseDTO::new)
							.toList();
					//NoticeRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
				case "contents":
					return noticeRepository.findByContentsContaining(key)
							.stream()
							.map(NoticeResponseDTO::new)
							.toList();
					//NoticeRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
				default:
					return null;
				}
			}
	
	
}
