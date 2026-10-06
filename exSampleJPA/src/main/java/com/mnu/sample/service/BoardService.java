package com.mnu.sample.service;

import java.util.Comparator;
import java.util.List;

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
			BoardEntity entity = BoardEntity.builder().build();
			
			
			return boardRepository.save(board.toEntity()).getIdx(); //repo의 save, idx는 가장 마지막idx값 리턴
			//등록후 등록된 idx 반환 , entity = xml
		}
		
		// 카운트(전체 게시글 수)
		@Transactional
		public long boardCount() {
			return boardRepository.count();
		}
		
		// 전체 목록 
		@Transactional
		public List<BoardResponseDTO> boardList(){
			
			return boardRepository.findAll() //repo의 findAll
					.stream()
					//Board 엔티티의 getIdx() 를 기준으로 내림차순 정렬
					.sorted(Comparator.comparing(BoardEntity::getIdx).reversed())
					.map(BoardResponseDTO::new)
//					.collect(Collectors.toList());
					.toList();
		}
		
		// 상세보기 (view)
		@Transactional
		public BoardResponseDTO boardView(int idx) {
			BoardEntity boardEntity = boardRepository.findById(idx)
					.orElseThrow(()-> new IllegalArgumentException("idx 없음"));			//find가 select
			BoardResponseDTO board = new BoardResponseDTO(boardEntity);
			return board;
		}
		
		
		
		//삭제
		@Transactional
		public int boardDelete(int idx, String pass) {
	
			return boardRepository.boardDelete(idx,pass);

		}

		//수정 처리
		@Transactional
		public int boardModify(int idx, BoardRequestDTO board) {
			return boardRepository.boardModify(idx, board.getSubject(), board.getContents(), board.getName());
		}

		//조건에 맞는 글수 카운트
		@Transactional
		public long boardCountSearch(String search, String key) {
			switch(search) {
			case "name" :
				return boardRepository.countByNameContaining(key);
			case "subject" :
				return boardRepository.countBySubjectContaining(key);
			case "contents" :
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
				//BoardRepository 결과고 넘어온 Entity stream을 map을 통해 list로 변환
			case "subject":
				return boardRepository.findBySubjectContaining(key)
						.stream()
						.map(BoardResponseDTO::new)
						.toList();
				//BoardRepository 결과고 넘어온 Entity stream을 map을 통해 list로 변환

			case "contents":
				return boardRepository.findByContentsContaining(key)
						.stream()
						.map(BoardResponseDTO::new)
						.toList();
				//BoardRepository 결과고 넘어온 Entity stream을 map을 통해 list로 변환

			default:
				return List.of();
			}
		}

}
