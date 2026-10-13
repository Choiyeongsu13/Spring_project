package com.mnu.sample.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mnu.sample.entity.PdsEntity;

public interface PdsRepository extends JpaRepository<PdsEntity, Integer> {
	//count() //카운트
	//findAll() //전체목록
	//save(entity) //등록
	//findById() //기본키를 이용한 검색
	//delete() //삭제

	//idx에 해당하는 자료의 조회수 증가
	@Modifying
	@Query("update PdsEntity pds set pds.readcnt = coalesce(pds.readcnt, 0) + 1 where pds.idx = :idx")
	void pdsHits(@Param("idx") int idx);

	//검색(이름, 제목, 내용) + Page
	@Query("select pds from PdsEntity pds "
			+ " where (:search='name' and pds.name like %:key%) "
			+ " or (:search='subject' and pds.subject like %:key%) "
			+ " or (:search='contents' and pds.contents like %:key%) "
			+ " order by pds.idx desc")
	Page<PdsEntity> pdsListSearchPage(@Param("search") String search,
										@Param("key") String key, Pageable pageable);
}
