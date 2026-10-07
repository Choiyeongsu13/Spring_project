//package com.mnu.sample.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.mnu.sample.dto.UserRequestDTO;
//import com.mnu.sample.service.UserService;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("User")
//public class User_vaildController {
//	private final UserService userService;
//	
//	//로그 출력용
//	private static final Logger log=
//			LoggerFactory.getLogger(IndexContoller.class);
//	
//	//회원가입 폼
//	@GetMapping("user_insert")
//	public String userInsert(Model model) {
//		log.info("User call : user_insert");
//		model.addAttribute("userRequestDTO", new UserRequestDTO());
//		return "User/user_insert";
//	}
//	
//	//id 중복체크 폼
//	
//	//본인인증(핸드폰)
//	
//	//본인인증(이메일)
//	
//	//회원가입 처리
//	@PostMapping("user_insert")
//	public String userInsertPro(@Valid UserRequestDTO userRequestDTO, BindingResult result , Model model) {
//		log.info("User call : user_insert_pro");
//		if(result.hasErrors()) {
//			return "User/user_insert";
//		}
//		
//		return "/";
//	}
//}
