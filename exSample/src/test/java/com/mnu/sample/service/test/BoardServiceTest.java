package com.mnu.sample.service.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import com.mnu.sample.service.BoardService;

@SpringBootTest
public class BoardServiceTest {
	private static final Logger log=
			LoggerFactory.getLogger(BoardServiceTest.class);
	
	@Autowired
	private BoardService BoardService;
	
	@Test
	public void BoardCount() {
		log.info("총 게시글수 : " + BoardService.BoardCount());
	}
	


}
