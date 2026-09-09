package com.mnu.sample.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.UserDTO;

@Mapper
public interface UserMapper {
	
	//1.id 중복검사
	public int userIdCheck(String userid);
	
	//2.회원가입
	public int userWrite(UserDTO userDTO);
	
	//3.로그인
	public UserDTO userLogin(UserDTO userDTO);
	
	//4. 로그인 한 날짜업데이트
	public void userLastTimeUpdate(String userid);
		

}
