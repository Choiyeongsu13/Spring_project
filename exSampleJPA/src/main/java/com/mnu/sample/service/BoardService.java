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
		private final BoardRepository boardRepository;
		
		// 등록 처리
		@Transactional
		public int boardWrite(BoardRequestDTO board) {
			BoardEntity entity = BoardEntity.builder().build();
			
			
			return boardRepository.save(board.toEntity()).getIdx();
			//등록후 등록된 idx 반환
		}
		
		// 전체 목록 
		@Transactional
		public List<BoardResponseDTO> boardList(){
			
			
			return boardRepository.findAll()
					.stream()
					//Board 엔티티의 getIdx() 를 기준으로 내림차순 정렬
					.sorted(Comparator.comparing(BoardEntity::getIdx).reversed())
					.map(BoardResponseDTO::new)
//					.collect(Collectors.toList());
					.toList();
			
		}
		
		//삭제 처리
		@Transactional
		public int boardDelete(BoardRequestDTO board) {
			BoardEntity entity = BoardEntity.builder().build();
			
			return boardRepository.delete(entity);
			
		}
}
