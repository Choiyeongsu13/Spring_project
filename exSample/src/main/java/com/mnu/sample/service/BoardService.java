package com.mnu.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.mapper.BoardMapper;

@Service
public class BoardService {
	@Autowired
	private BoardMapper Boardmapper;
	
	//메소드 정의
	//1. 전체 글 카운터
	public int BoardCount() {
		return Boardmapper.BoardCount();
	}

	//2. 검색 조건에 해당하는 글수
	public String BoardCountSearch(String search, String key) {
		
		return Boardmapper.BoardCountSearch(search, key);
	}

	//3. 전체 목록
	public List<BoardDTO> BoardList(){
		return Boardmapper.BoardList();
		
	}

	//4. 검색조건에 맞는 목록
	public List<BoardDTO> BoardListSearch(String search, String key){
		return Boardmapper.BoardListSearch(search, key);
	}


	//5. 글 등록
	public int BoardWrite(BoardDTO boardDTO) {
		return Boardmapper.BoardWrite(boardDTO);
	}

	//6. 특정글 검색 (view, modify)

	//7. 수정처리

	//8. 삭제처리

}
