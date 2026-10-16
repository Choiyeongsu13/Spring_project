package com.mnu.sample.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.entity.PdsEntity;

//JpaRepository 인터페이스를 상속해서 사용자 인터페이스 생성
public interface PdsRepository extends JpaRepository<PdsEntity, Integer> {
	//count() // 카운트
	//findAll()	//전체목록
	//save(entity)// 등록, 수정(update)
	//findById()//기본키를 이용한 검색
	//delete()//삭제
	//deleteById(idx)//무조건 삭제
	
	//조회수 증가
	@Transactional
	@Modifying
	@Query("update PdsEntity pds set pds.readcnt=pds.readcnt+1 where pds.idx=:idx")
	void pdsHits(@Param("idx") int idx);
	
	//1. 삭제(id, pass)
	@Transactional
	@Modifying
	@Query("delete from PdsEntity pds where pds.idx= :idx and pds.pass= :pass")
	int pdsDelete(@Param("idx") int idx, @Param("pass") String pass);
	
	//수정처리
	@Transactional
	@Modifying
	@Query("update PdsEntity pds set pds.subject = :subject, pds.contents= :contents, "
			+ " pds.filename= :filename  where pds.idx = :idx and pds.pass= :pass")
	int pdsModify(@Param("idx") int idx, @Param("subject") String subject,
							@Param("contents") String contents, 
							@Param("filename") String filename, 
							@Param("pass") String pass);
	
	
	//검색(이름, 제목, 내용) 카운트
	long countByNameContaining(String keyword);
	//(name like '%keyword%')
	long countBySubjectContaining(String keyword);
	long countByContentsContaining(String keyword);
	
	
	//검색 목록
	//검색(이름, 제목, 내용) 목록
	List<PdsEntity> findByNameContaining(String keyword);
	//(name like '%keyword%')
	List<PdsEntity> findBySubjectContaining(String keyword);
	List<PdsEntity> findByContentsContaining(String keyword);
	
	//검색(이름, 제목, 내용)-> idx기준 내림차순
	List<PdsEntity> findByNameContainingOrderByIdxDesc(String keyword);
	List<PdsEntity> findBySubjectContainingOrderByIdxDesc(String keyword);
	List<PdsEntity> findByContentsContainingOrderByIdxDesc(String keyword);

	//페이지 인덱싱
	//검색(이름,제목,내용) + PageIndexing
	Page<PdsEntity> findByNameContainingOrderByIdxDesc(String keyword, Pageable pageable);	
	Page<PdsEntity> findBySubjectContainingOrderByIdxDesc(String keyword, Pageable pageable);	
	Page<PdsEntity> findByContentsContainingOrderByIdxDesc(String keyword, Pageable pageable);	
	
	//@Query 이용한 검색 + Page
	@Query("select pds from PdsEntity pds "
			+ " where (:search='name' and pds.name like %:key%) "
			+ " or (:search='subject' and pds.subject like %:key%) "
			+ " or (:search='contents' and pds.contents like %:key%) "
			+ " order by pds.idx desc")
	Page<PdsEntity> pdsListSearchPage(@Param("search") String search, 
										@Param("key") String key, Pageable pageable);
	
}
