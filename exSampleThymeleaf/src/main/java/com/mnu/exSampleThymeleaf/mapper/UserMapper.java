package com.mnu.exSampleThymeleaf.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mnu.exSampleThymeleaf.domain.UserDTO;

@Mapper
public interface UserMapper {

	int userIdCheck(@Param("userid") String userid);

	int userWrite(UserDTO userDTO);

	UserDTO userLogin(UserDTO userDTO);

	int userLastTimeUpdate(@Param("userid") String userid);

	//userid로 회원 조회
	UserDTO userSelect(@Param("userid") String userid);

}
