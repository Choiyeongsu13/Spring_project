package com.mnu.sample.service.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sample.domain.PdsDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.PdsMapper;
import com.mnu.sample.service.PdsService;

@SpringBootTest
public class PdsServiceTest {
	private static final Logger log=
			LoggerFactory.getLogger(PdsServiceTest.class);
	
	@Autowired
	private PdsService PdsService;
	
	@Test
	public void PdsCount() {
		log.info("총 게시글수 : " + PdsService.PdsCount());
	}
	


}
