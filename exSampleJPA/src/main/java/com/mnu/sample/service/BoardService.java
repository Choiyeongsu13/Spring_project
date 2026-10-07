package com.mnu.sample.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mnu.sample.dto.BoardRequestDTO;
import com.mnu.sample.dto.BoardResponseDTO;
import com.mnu.sample.entity.BoardEntity;
import com.mnu.sample.repository.BoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //bean 주입
public class BoardService {
		private final BoardRepository boardRepository; //repo 주입 -> service 
		
		// 등록 처리
		@Transactional
		public int boardWrite(BoardRequestDTO board) {
			return boardRepository.save(board.toEntity()).getIdx();
			//등록후 등록된 idx 반환
		}
		
		// 카운트(전체 게시글 수)
		@Transactional
		public long boardCount() {
			return boardRepository.count();
		}
	/*	
		// 전체 목록(페이지 처리 없음)
		@Transactional
		public List<BoardResponseDTO> boardList(){
			return boardRepository.findAll()
					.stream()
					// Board 엔티티의 getIdx()를 기준으로 내림차순(reversed) 정렬
		            .sorted(Comparator.comparing(BoardEntity::getIdx).reversed()) 
					.map(BoardResponseDTO::new)
					//.collect(Collectors.toList());
					.toList();//JDK 16이상
			// BoardRepository결과로 넘어온 BoardEntity의 Stream을 map을 통해 BoardReponseDto로 변환 
	        //   -> List로 반환하는 메서드
		}
	*/
		// 전체 목록(검색 X, 페이지 처리 )
		@Transactional
		public Page<BoardResponseDTO> boardList(Pageable pageable){
			// 기존 pageable의 페이지 번호와 사이즈를 유지하면서, 정렬 조건만 idx 내림차순으로 덮어씁니다.
		    Pageable sortedPageable = PageRequest.of(
		        pageable.getPageNumber(), 
		        pageable.getPageSize(), 
		        Sort.by(Sort.Direction.DESC, "idx")
		    );
		    
			Page<BoardEntity> page;
			//page = boardRepository.findAll(pageable);//오름차순
			page = boardRepository.findAll(sortedPageable);
			return page.map(BoardResponseDTO::new);
		}
		
		// 전체 목록(검색 , 페이지 처리 )
		@Transactional
		public Page<BoardResponseDTO> boardListSearchPage(String search, String key, Pageable pageable){
			// 기존 pageable의 페이지 번호와 사이즈를 유지하면서, 정렬 조건만 idx 내림차순으로 덮어씁니다.
		    Pageable sortedPageable = PageRequest.of(
		        pageable.getPageNumber(), 
		        pageable.getPageSize(), 
		        Sort.by(Sort.Direction.DESC, "idx")
		    );
		    
			Page<BoardEntity> page;
			if(key != null && !key.equals("")) {
				//검색 O
				page = boardRepository.boardListSearchPage(search, key, pageable);
			}else {
				//검색 X
				//page = boardRepository.findAll(pageable);//오름차순
				page = boardRepository.findAll(sortedPageable);
			}
			return page.map(BoardResponseDTO::new);
		}

		
		//상세보기(View)
		@Transactional
		public BoardResponseDTO boardView(int idx) {
			//조회수 증가
			boardRepository.boardHits(idx);
			
			BoardEntity boardEntity = boardRepository.findById(idx)
					.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		
			BoardResponseDTO board = new BoardResponseDTO(boardEntity);
			return board;
		}
		
		//삭제
		@Transactional
		public int boardDelete(int idx, String pass) {
			return boardRepository.boardDelete(idx, pass);
		}

		//수정폼(modify)
		@Transactional
		public BoardResponseDTO boardModify(int idx) {
			BoardEntity boardEntity = boardRepository.findById(idx)
					.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		
			BoardResponseDTO board = new BoardResponseDTO(boardEntity);
			return board;
		}
		
		//수정처리
		@Transactional
		public int boardModifyPro(int idx, BoardRequestDTO board) {
			return boardRepository.boardModify(idx, board.getSubject(), board.getContents(), board.getPass());
		}
		
		// 조건에 맞는 글수 카운트
		@Transactional
		public long boardCountSearch(String search, String key) {
			switch(search) {
			case "name":
				return boardRepository.countByNameContaining(key);
			case "subject":
				return boardRepository.countBySubjectContaining(key);
			case "contents":
				return boardRepository.countByContentsContaining(key);
			default:
				return 0;
			}
		}
		
		//조건에 맞는 게시글 목록
		@Transactional
		public List<BoardResponseDTO> boardListSearch(String search, String key){
			switch(search) {
			case "name":
				return boardRepository.findByNameContaining(key)
						.stream()
						.map(BoardResponseDTO::new)
						.toList();
				//BoardRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
			case "subject":
				return boardRepository.findBySubjectContaining(key)
						.stream()
						.map(BoardResponseDTO::new)
						.toList();
				//BoardRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
			case "contents":
				return boardRepository.findByContentsContaining(key)
						.stream()
						.map(BoardResponseDTO::new)
						.toList();
				//BoardRepository결과로 넘어온 Entity의 stream을 map을 통해 list로 변환
			default:
				return null;
			}
		}

}
