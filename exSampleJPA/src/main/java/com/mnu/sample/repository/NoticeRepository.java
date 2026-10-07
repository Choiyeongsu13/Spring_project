package com.mnu.sample.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mnu.sample.entity.NoticeEntity;

import jakarta.transaction.Transactional;

//JpaRepository 인터페이스 상속 (count, findAll, findById 등 기본 제공)
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {

	@Transactional
	@Modifying
	@Query("update NoticeEntity notice set notice.readcnt = notice.readcnt + 1 where notice.idx = :idx")
	void noticeHits(@Param("idx") int idx);
	
	//검색(작성자, 제목, 내용) 카운트 - 공지사항은 name 대신 adid(관리자 아이디)가 작성자
	long countByAdidContaining(String keyword);
	//(adid like '%keyword%')
	long countBySubjectContaining(String keyword);
	long countByContentsContaining(String keyword);


	//검색 목록
	//검색(작성자, 제목, 내용) 목록
	List<NoticeEntity> findByAdidContaining(String keyword);
	//(adid like '%keyword%')
	List<NoticeEntity> findBySubjectContaining(String keyword);
	List<NoticeEntity> findByContentsContaining(String keyword);

	//검색(작성자, 제목, 내용)-> idx기준 내림차순
	List<NoticeEntity> findByAdidContainingOrderByIdxDesc(String keyword);
	List<NoticeEntity> findBySubjectContainingOrderByIdxDesc(String keyword);
	List<NoticeEntity> findByContentsContainingOrderByIdxDesc(String keyword);

	//페이지 인덱싱
	//검색(작성자,제목,내용) + PageIndexing
	Page<NoticeEntity> findByAdidContainingOrderByIdxDesc(String keyword, Pageable pageable);
	Page<NoticeEntity> findBySubjectContainingOrderByIdxDesc(String keyword, Pageable pageable);
	Page<NoticeEntity> findByContentsContainingOrderByIdxDesc(String keyword, Pageable pageable);

	//@Query 이용한 검색 + Page
	@Query("select notice from NoticeEntity notice "
			+ " where (:search='name' and notice.adid like %:key%) "
			+ " or (:search='subject' and notice.subject like %:key%) "
			+ " or (:search='contents' and notice.contents like %:key%) "
			+ " order by notice.idx desc")
	Page<NoticeEntity> noticeListSearchPage(@Param("search") String search, 
										@Param("key") String key, Pageable pageable);

	
	
	
	
}
