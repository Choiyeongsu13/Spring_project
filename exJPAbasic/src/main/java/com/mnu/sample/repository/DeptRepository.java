package com.mnu.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mnu.sample.entity.DeptEntity;



public interface DeptRepository extends JpaRepository<DeptEntity, Integer> {
	//T : entity , ID : 기본키(객체)
	
	//사용자 정의 메소드 생성 (추상 메소드)
	//public int deptCount();
	//기본적은 crud(생성 조회 수정 삭제), 페이징, 정렬, 및 배치처리를 위한 다양한 메소드 즉시 사용

	//지역명을 이용한 검색
	List<DeptEntity> findByLoc(String loc);
	
	//이름을 이용한 검색
	List<DeptEntity> findByDname(String name); //쿼리 메소드
	
	// 게시판(BoardEntity) 예제 쿼리 - DeptEntity에는 readcnt/idx 필드가 없어 사용 불가
	// 게시판에서 쓸 때의 올바른 형태:
	// @Transactional
	// @Modifying
	// @Query("update BoardEntity board set board.readcnt = board.readcnt + 1 where board.idx = :idx")
	// void boardHits(@Param("idx") int idx); //조회수 증가
	
//	void boardHits(@Param("idx") int idx); //가능
	
	
	
	
}
