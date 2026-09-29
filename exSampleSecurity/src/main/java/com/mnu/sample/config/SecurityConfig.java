package com.mnu.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers(new RegexRequestMatcher("^/User/.*", null, true)).hasAnyRole("USER") //대소문자 구분 없이 매칭
				.requestMatchers(new RegexRequestMatcher("^/Admin/.*", null, true)).hasAnyRole("ADMIN") //admin  폴더는 ADMIN 역할만
				.anyRequest().permitAll() //그외 모든곳은 모두가 접근가능
				)
		.formLogin(login -> login
			.loginPage("/Join/user_login")
			.loginProcessingUrl("/Join/user_login_ok") //로그인 form의 action 주소와 일치
			.usernameParameter("userid")
			.passwordParameter("passwd") //로그인 form의 비밀번호 input name과 일치
			.failureUrl("/Join/user_error")
		);
			
		return http.build();
	}
		@Bean
		public BCryptPasswordEncoder bCryptPasswordEncoder() { //비밀번호 암호화
			return new BCryptPasswordEncoder();
		}
	
}
