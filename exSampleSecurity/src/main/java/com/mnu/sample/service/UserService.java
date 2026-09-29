package com.mnu.sample.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.Role;
import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //autowire 자동 주입
public class UserService {
	//Mapper 주입
	private final UserMapper userMapper;
	// 비밀번호 암호화 bean
	private final BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	//1. id 중복검사
	public int userIdCheck(String userid) {
		
		return userMapper.userIdCheck(userid);
	}
	//2. 회원가입
	public int userWrite(UserDTO userDTO) {
		userDTO.setRole(Role.ROLE_USER); //기본 권한은 사용자
		//비번 암호화
		userDTO.setPasswd(bCryptPasswordEncoder.encode(userDTO.getPasswd()));
		
		return userMapper.userWrite(userDTO);
	}
	//3. 아이디를 이용한 사용자 검색(Security Login)
	//시큐리티가 처리함
	
	
	//4. 로그인 날짜 업데이트 
	public void userLastTimeUpdate(String userid) {
		userMapper.userLastTimeUpdate(userid);
	}

}
