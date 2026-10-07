package com.mnu.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnu.sample.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	
	//1. userid이용한 사용자 검색
	UserEntity findByUserid(String userid);
	
	
	//2. id 중복검사
	boolean existsByUserid(String userid); //userid 존재 유무

	//3. 회원가입
	//save()
	//save(entity);
	
	

}
