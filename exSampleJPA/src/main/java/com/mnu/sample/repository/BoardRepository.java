package com.mnu.sample.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	//사용자 정의 메소드(1. 쿼리 메소드 /  2. @Query 어노테이션)
		//1. 삭제(id, pass)
		@Transactional
		@Modifying
		@Query("delete from BoardEntity board where board.idx= :idx and board.pass= :pass")
		int boardDelete(@Param("idx") int idx, @Param("pass") String pass);
		
		//idx에 해당하는 글의 조회수 증가
		@Transactional
		@Modifying
		@Query("update BoardEntity board set board.readcnt = coalesce(board.readcnt, 0) + 1 where board.idx = :idx")
		void boardHits(@Param("idx") int idx);
		
		//수정처리
		@Transactional
		@Modifying
		@Query("update BoardEntity board set board.subject = :subject, board.contents= :contents  where board.idx = :idx and board.pass= :pass")
		int boardModify(@Param("idx") int idx, @Param("subject") String subject,
							@Param("contents") String contents, @Param("pass") String pass);
		
		//검색(이름, 제목, 내용) 카운트
		long countByNameContaining(String keyword);
		//(name like '%keyword%')
		long countBySubjectContaining(String keyword);
		long countByContentsContaining(String keyword);
		
		
		//검색 목록
		//검색(이름, 제목, 내용) 목록
		List<BoardEntity> findByNameContaining(String keyword);
		//(name like '%keyword%')
		List<BoardEntity> findBySubjectContaining(String keyword);
		List<BoardEntity> findByContentsContaining(String keyword);
		
		//검색(이름, 제목, 내용)-> idx기준 내림차순
		List<BoardEntity> findByNameContainingOrderByIdxDesc(String keyword);
		List<BoardEntity> findBySubjectContainingOrderByIdxDesc(String keyword);
		List<BoardEntity> findByContentsContainingOrderByIdxDesc(String keyword);

		//페이지 인덱싱
		//검색(이름,제목,내용) + PageIndexing
		Page<BoardEntity> findByNameContainingOrderByIdxDesc(String keyword, Pageable pageable);	
		Page<BoardEntity> findBySubjectContainingOrderByIdxDesc(String keyword, Pageable pageable);	
		Page<BoardEntity> findByContentsContainingOrderByIdxDesc(String keyword, Pageable pageable);	
		
		//@Query 이용한 검색 + Page
		@Query("select board from BoardEntity board "
				+ " where (:search='name' and board.name like %:key%) "
				+ " or (:search='subject' and board.subject like %:key%) "
				+ " or (:search='contents' and board.contents like %:key%) "
				+ " order by board.idx desc")
		Page<BoardEntity> boardListSearchPage(@Param("search") String search, 
											@Param("key") String key, Pageable pageable);
	}
	


