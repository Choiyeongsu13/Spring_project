package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.BoardDTO;

@Mapper
public interface BoardMapper {
	//메소드 정의
	//1. 전체 글 카운터
	public int BoardCount();

	//2. 검색 조건에 해당하는 글수
	public String BoardCountSearch(String search, String key);

	//3. 전체 목록
	public List<BoardDTO> BoardList();

	//4. 검색조건에 맞는 목록
	public List<BoardDTO> BoardListSearch(String search, String key);


	//5. 글 등록
	public int BoardWrite(BoardDTO bdto);

	//6. 특정글 검색 (view, modify)

	//7. 수정처리

	//8. 삭제처리


}
