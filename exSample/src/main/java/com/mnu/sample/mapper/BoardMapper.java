package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;

@Mapper
public interface BoardMapper {
	//메소드 정의
	//1. 전체 글 카운터
	public int BoardCount();

	//2. 검색 조건에 해당하는 글수
	public int BoardCountSearch(String search, String key);

	//3. 전체 목록
	public List<BoardDTO> BoardList();
	
	//3-1. 전체 목록 (패이지 인덱싱)
	public List<BoardDTO> BoardListPage(PageSearchDTO pagesearchDTO); //건너뛸것 / 한페이지에 몇개나 보여줄건지

	//4. 검색조건에 맞는 목록
	public List<BoardDTO> BoardListSearch(String search, String key);
	
	//4-1. 검색조건에 맞는 목록(페이지 인덱싱)
	public List<BoardDTO> BoardListSearchPage(PageSearchDTO pagesearchDTO);


	//5. 글 등록
	public int BoardWrite(BoardDTO boardDTO);

	//6. 특정글 검색 (view, modify)
	public void BoardHits(int idx); //조회수
	public BoardDTO boardview(int idx); 

	//7. 수정처리
	public int boardModifyPro(BoardDTO boardDTO);

	//8. 삭제처리
	public int boardDeletePro(@Param("idx") int idx, @Param("pass") String pass);


}
