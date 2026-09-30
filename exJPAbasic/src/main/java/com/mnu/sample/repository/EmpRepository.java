package com.mnu.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.entity.DeptEntity;
import com.mnu.sample.entity.EmpEntity;

public interface EmpRepository extends JpaRepository<EmpEntity, Integer> {
	
	
	//지역명을 이용한 검색
	List<EmpEntity> findByJob(String job);
	
	//이름을 이용한 검색
	List<EmpEntity> findByEname(String name); //쿼리 메소드

	
	
	@Modifying
	@Query("update EmpEntity emp set emp.commission = emp.commission + 100 where emp.eno = :eno")
	int empCommission(@Param("eno") int eno); //특정 사원 커미션 + 100
}
