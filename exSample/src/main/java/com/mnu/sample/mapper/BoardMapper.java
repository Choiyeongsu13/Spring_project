package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;

@Mapper
public interface BoardMapper {
	//1. 전체 글수 카운트
	public int boardCount();
	
	//2. 검색조건에 해당하는 글수
	public int boardCountSearch(String search, String key);
	
	//3. 전체목록 리스트
	public List<BoardDTO> boardList();
	//3-1. 전체목록 리스트(페이지 인덱싱)
	public List<BoardDTO> boardListPage(PageSearchDTO pageSearchDTO);

	//4. 검색조건에 맞는 글 리스트
	public List<BoardDTO> boardListSearch(String search, String key);

	//4-1. 검색조건 + 페이지 인덱싱 리스트
	public List<BoardDTO> boardListSearchPage(PageSearchDTO pageSearchDTO);

	//5. 글 등록 
	public int boardWrite(BoardDTO boardDTO);
	
	//6. 특정글 검색(view, 수정)
	public void boardHits(int idx);//조회수 증가
	
	public BoardDTO boardView(int idx);
	
	//7. 수정처리 처리
	public int boardModifyPro(BoardDTO boardDTO);
	
	//8. 삭제처리
	public int boardDelete(BoardDTO boardDTO);
	
}