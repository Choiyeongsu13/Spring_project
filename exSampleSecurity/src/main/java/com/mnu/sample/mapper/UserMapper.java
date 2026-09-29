package com.mnu.sample.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.UserDTO;

@Mapper
public interface UserMapper {
	//1. id 중복검사
	public int userIdCheck(String userid);
	//2. 회원가입
	public int userWrite(UserDTO userDTO);
	//3. 아이디를 이용한 사용자 검색(Security Login)
	public UserDTO selectById(String userid);
	//4. 로그인 날짜 업데이트 
	public void userLastTimeUpdate(String userid);
}
