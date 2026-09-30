package com.mnu.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnu.sample.entity.DeptEntity;



public interface DeptRepository extends JpaRepository<DeptEntity, Integer> {
	//T : entity , ID : 기본키(객체)
	
	//사용자 정의 메소드 생성 (추상 메소드)
	//public int deptCount();
	//기본적은 crud(생성 조회 수정 삭제), 페이징, 정렬, 및 배치처리를 위한 다양한 메소드 즉시 사용
	
	
}
