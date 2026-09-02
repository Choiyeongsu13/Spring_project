package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("User")
public class UserController {
	private static final Logger log=
			LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("user_login") //로그인
	public String userLogin() {
		
		log.info("user call : login"); 
		return"User/user_login"; //view는 기본
	}
	
	
	//로그아웃
	@GetMapping("user_logout") 
	public String userLogout() {
		
		log.info("user call : logout"); 
		return"redirect:"; //index이동
		}
	
	
	// 회원가입
	@GetMapping("user_insert") 
	public String userInsert() {
		
		log.info("user call : insert"); 
		return"User/user_insert"; //
		}
	
	
	// 정보수정

	
	
}
