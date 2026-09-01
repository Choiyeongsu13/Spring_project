package com.mnu.sawon.serviceTest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sawon.mappers.emp_mappers;
import com.mnu.sawon.service.EmpService;

@SpringBootTest
public class EmpServiceTest {
	
	//로그 출력용 클래스
	private static final Logger log=
			LoggerFactory.getLogger(EmpServiceTest.class);
	
	@Autowired
	private EmpService service;
	
	//1.empTest
	@Test
	public void empCountTest() {
		log.info("service : " +  service.EmpCount());
	}
	
	
}
