package com.mnu.exSampleThymeleaf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mnu.exSampleThymeleaf.domain.BoardDTO;
import com.mnu.exSampleThymeleaf.domain.PageSearchDTO;

@Mapper
public interface BoardMapper {

	List<BoardDTO> boardTopList(@Param("num") int num);

	int boardCount();

	int boardCountSearch(@Param("search") String search, @Param("key") String key);

	List<BoardDTO> boardList();

	List<BoardDTO> boardListPage(PageSearchDTO pageSearchDTO);

	List<BoardDTO> boardListSearch(@Param("search") String search, @Param("key") String key);

	List<BoardDTO> boardListSearchPage(PageSearchDTO pageSearchDTO);

	int boardWrite(BoardDTO boardDTO);

	int boardHits(@Param("idx") int idx);

	BoardDTO boardView(@Param("idx") int idx);

	int boardModifyPro(BoardDTO boardDTO);

	int boardDelete(BoardDTO boardDTO);

}
