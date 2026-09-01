package com.mnu.sawon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sawon.mappers.emp_mappers;

@Service
public class EmpService2 {
	
	@Autowired
	private emp_mappers mapper;
	//별도의 인터페이스없이 하는 방법 ,직접 구현체 구현
	//1. emp 테이블이 존재하는 총 사원수(tuple) 카운트
	public int EmpCount() {
		return mapper.EmpCount();
		
	}
}
