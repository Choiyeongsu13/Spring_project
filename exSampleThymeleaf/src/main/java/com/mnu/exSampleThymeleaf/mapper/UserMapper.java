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

	String userFindId(@Param("name") String name, @Param("email") String email);

	int userCheckIdEmail(@Param("userid") String userid, @Param("email") String email);

	int userUpdatePasswd(@Param("userid") String userid, @Param("passwd") String passwd);

}
