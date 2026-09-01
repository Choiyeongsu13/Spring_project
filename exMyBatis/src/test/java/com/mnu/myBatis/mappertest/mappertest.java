package com.mnu.myBatis.mappertest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.myBatis.mapper.testmapper;

@SpringBootTest
public class mappertest{
	private static final Logger log =
			LoggerFactory.getLogger(mappertest.class);

	@Autowired
	private testmapper mapper;

//	@Test
//	public void testGetTime() {
//		log.info(mapper.getClass().getName());
//		log.info("오늘 날짜는 : " + mapper.getTime());
//
//	}
//	@Test
//	public void testGetTime2() {
//		log.info("오늘날짜는 : " + mapper.getTime2());
//		
//	}
//	

	@Test
	public void empCounted() {
		log.info("카운트는 : " + mapper.empCount());
	}
	

	
}
