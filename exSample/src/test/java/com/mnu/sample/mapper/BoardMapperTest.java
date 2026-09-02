package com.mnu.sample.mapper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sample.controller.NoticeController;

@SpringBootTest
public class BoardMapperTest {
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private BoardMapper boardmapper;
	
	
	@Test
	public void BoardCount() {
		log.info("총 게시글수 : " + boardmapper.BoardCount());
	}
	@Test
	public void BoardCountSearch() {
		String search ="name";
		String key ="최";
		log.info("총 검색 게시글수 : " + boardmapper.BoardCountSearch(search,key));
	}
	@Test
	public void BoardList() {
		boardmapper.BoardList().forEach(board->log.info(board.toString()));
	
	}
	@Test
	public void BoardListSearch() {
		String name = "name";
		String key= "최";
		boardmapper.BoardListSearch(name,key).forEach(board->log.info(board.toString()));
		
	}

}
