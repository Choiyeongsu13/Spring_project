package com.mnu.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mnu.sample.entity.BoardEntity;

import jakarta.transaction.Transactional;

//JpaRepository 인터페이스 상속해서 사용자 인터페이스 생성
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	//count() //카운트
	//findAll() //전체목록
	//save(entity) //등록
	//findById() //기본키를 이용한 검색
	//delete() //삭제
	
	//사용자 정의 메소드(1. 쿼리메소드 / 2. @query 어노테이션)
	//1: 삭제(id,pass)
	@Transactional
	@Modifying
	@Query("delete from BoardEntity board where board.idx = :idx and board.pass= :pass")
	int boardDelete(@Param("idx")int idx,@Param("pass") String pass);
	
	
	
	
}
