package com.mnu.sample.config;

import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mnu.sample.domain.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
//id 비밀번호 비교
	
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 로그인 정보 받기
		String userid = authentication.getName();
		String password = (String)authentication.getCredentials();
		UserDetails userDetails = (CustomUserDetails)userDetailsService.loadUserByUsername(userid);
		
		//시큐리티에서 지원하는 암호화 객체를 통해 비번 비교
		if(!passwordEncoder.matches(password,userDetails.getPassword())) {
			//일치하지 않을 경우 예외처리
			throw new BadCredentialsException("사용자 정보가 일치하지 않습니다");
		}
		//일치하는 사용자가 있을  경우
		return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
		//												사용자 정보 , 비번 , 권한(role)
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// provider의 동작 여부를 결정한다 false 가 리턴되면 authenticate 메서드는 호출되지 않는다
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
