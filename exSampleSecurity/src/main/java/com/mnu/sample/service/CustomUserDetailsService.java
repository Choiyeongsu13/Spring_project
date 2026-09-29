package com.mnu.sample.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.CustomUserDetails;
import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.mapper.UserMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserMapper userMapper;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO userDTO = userMapper.selectById(username); //id을 이용한 사용자 정보 조회
		if(userDTO == null) {
			throw new UsernameNotFoundException(username + "가 존재하지 않습니다");
		}
		
		return new CustomUserDetails(userDTO);
	}

}