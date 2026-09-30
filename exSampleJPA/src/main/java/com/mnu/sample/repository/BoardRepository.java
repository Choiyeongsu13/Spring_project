package com.mnu.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnu.sample.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	//count() //카운트
	//findAll() //전체목록
	//save(entity) //등록
	//findById() //기본키를 이용한 검색
	//delete() //삭제
	
	//사용자 정의 메소드(1. 쿼리메소드 / 2. @query 어노테이션)
	
	
}
