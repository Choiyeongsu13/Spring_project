package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnu.sample.dto.UserRequestDTO;
import com.mnu.sample.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("User")
public class UserController {
	private final UserService userService;
	
	//로그 출력용
	private static final Logger log=
			LoggerFactory.getLogger(IndexContoller.class);
	
	//회원가입 폼
	@GetMapping("user_insert")
	public String userInsert(Model model) {
		log.info("User call : user_insert");
		model.addAttribute("userRequestDTO", new UserRequestDTO());
		return "User/user_insert";
	}
//	//회원가입 폼
//	@GetMapping("user_insert")
//	public String userInsert(Model model) {
//		log.info("User call : user_insert");
//		model.addAttribute("userRequestDTO", new UserRequestDTO());
//		return "User/user_insert";
//	}
	
	//id 중복체크 폼
	@ResponseBody
	@PostMapping("user_idCheck")
	public String userIdCheck(@RequestParam("userid") String userid) {
		boolean bool = userService.userIdCheck2(userid);
		return String.valueOf(bool);
	}
	//본인인증(핸드폰)
	
	//본인인증(이메일)
	
	//회원가입 처리
	@PostMapping("user_insert")
	public String userInsertPro(UserRequestDTO userRequestDTO, Model model) {
		log.info("User call : user_insert_pro");

		
		return "/";
	}
}
