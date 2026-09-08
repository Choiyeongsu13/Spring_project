package com.mnu.sample.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.UserDTO;

@Mapper
public interface UserMapper {
	
	//1.id 중복검사
	public int userIdCheck(String userid);
	
	//2.회원가입
	public int userWrite(UserDTO userDTO);
		

}
