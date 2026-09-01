package com.mnu.sawon.service;

public interface EmpService {
	//1. emp 테이블이 존재하는 총 사원수(tuple) 카운트
	public int EmpCount();
	//2. 부서번호가 xx인 사원수
	public int EmpCount_Dno(int dno); //where의 조건을 매개변수로

}
