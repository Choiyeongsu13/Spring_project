package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.service.UserSerivce;

@Controller
@RequestMapping("User")
public class UserController {
	private static final Logger log=
			LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserSerivce userSerivce;

	@GetMapping("user_login") //로그인
	public String userLogin() {
		
		log.info("user call : login"); 
		return"User/user_login"; 
	}
	
	
	//로그아웃
	@GetMapping("user_logout") 
	public String userLogout() {
		
		log.info("user call : logout"); 
		return"redirect:"; //index이동
		}
	
	
	// 회원가입 폼
	@GetMapping("user_insert") 
	public String userInsert() {
		
		log.info("user call : insert"); 
		return"User/user_insert"; //
		}
	
	@PostMapping("user_write")
	public String userWrite(UserDTO userDTO, Model model) {
		
		log.info("user call : insert");
		model.addAttribute("row", userSerivce.userWrite(userDTO));
		return"User/user_insert_pro";
	}
	
	
	// ID 중복검사
	@ResponseBody
	@PostMapping("user_idCheck")
	public String userIdCheck(@RequestParam("userid") String userid) {
		log.info("user call : user id check");
		int row = userSerivce.userIdCheck(userid);
		
		return String.valueOf(row);
	}
	//본인인증(sms)
	@ResponseBody
	@PostMapping("user_sms")
	public String smsSend(@RequestParam("tel") String tel) {
		String tempNum = userSerivce.sendSMS(tel);
		
		log.info("인증번호 : " +  tempNum);
		return tempNum;
		
	}
	
	
	//본인인증(email)
	
	//정보수정폼'
	@GetMapping("user_modify")
	public String userModify() {
		return "user/user_modify";
	}
	
	//정보수정 처리
	
	//회원탈퇴(삭제)
	
	//ID찾기
	
	//비번분실시 임시번호 발송
	
	//비번분실시 id를 찾아서 임시비번 발송

	
	
}
