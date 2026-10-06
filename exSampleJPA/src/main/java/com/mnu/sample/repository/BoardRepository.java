package com.mnu.sample.repository;

import java.util.List;

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

	//2: 수정(idx)
	@Transactional
	@Modifying
	@Query("update BoardEntity board set board.subject = :subject, board.contents = :contents, board.name = :name, board.updatedate = CURRENT_TIMESTAMP where board.idx = :idx")
	int boardModify(@Param("idx")int idx, @Param("subject") String subject, @Param("contents") String contents, @Param("name") String name);

	//검색 (이름,제목,내용)카운트
	long countByNameContaining(String keyword);
	//(name like '%keyword%') 쿼리문 이라면
	long countBySubjectContaining(String keyword);

	long countByContentsContaining(String keyword);

	//검색 목록
	List<BoardEntity> findByNameContaining(String keyword);

	List<BoardEntity> findBySubjectContaining(String keyword);

	List<BoardEntity> findByContentsContaining(String keyword);

}
