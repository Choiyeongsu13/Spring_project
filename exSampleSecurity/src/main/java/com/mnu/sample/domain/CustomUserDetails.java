package com.mnu.sample.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	private final UserDTO userDTO;
	//가입된 회원정보를 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		ArrayList<GrantedAuthority> rollList = new ArrayList<>();
		rollList.add(new SimpleGrantedAuthority(userDTO.getRole().toString()));
		return rollList;
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return userDTO.getPasswd();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userDTO.getUserid();
	}

}
