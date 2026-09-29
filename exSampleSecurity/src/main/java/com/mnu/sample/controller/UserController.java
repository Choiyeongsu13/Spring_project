package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	//로그 출력용
	private static final Logger log=
			LoggerFactory.getLogger(IndexContoller.class);
	
	private final UserService userService;
	
	//로그인 폼
	@GetMapping("/Join/user_login")
	public String userLogin() {
		log.info("user call : user_login");

		return "/Join/user_login";
	}
	
	//로그인 에러(id,pass오류)
	@GetMapping("/Join/user_error")
	public String userError() {
		log.info("user call : user_error");

		return "/Join/user_error";
	}
	
	//회원가입 폼
	@GetMapping("/Join/user_insert")
	public String userInsert() {
		log.info("user insert : user_insert");

		return "/Join/user_insert";
	}
	// 회원 가입처리
	@PostMapping("/Join/user_insert")
	public String insertPro(UserDTO userDTO) {
		log.info("user call : user_insert_pro");
		userService.userWrite(userDTO);
		return "redirect:/"; //회원가입 인덱스로 이동
	}
	
	//ID 중복 검사
	
	
	//인증
	//회원 가입처리(db 저장)
	
	
	//MyPage
	
	@GetMapping("/User/user_mypage")
	public String userMypage() {
		log.info("user call : user_mypage");
		return "User/user_mypage";
	}
	
	//로그아웃 처리
	@GetMapping("/Join/user_logout")
	public String userLogout(HttpServletRequest request) {
	 log.info("user call : user_logout");
	 HttpSession session = request.getSession();
	 if(session != null) { 
			 session.invalidate();
	}
	 return "/Join/user_logout";
}
}


