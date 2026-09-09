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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("User")
public class UserController {
	private static final Logger log=
			LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserSerivce userSerivce;

	@GetMapping("user_login") //로그인 폼
	public String userLogin(HttpSession session) {
		log.info("user call : login"); 
		
		if(session.getAttribute("user")==null) { 
			return"User/user_login"; //로그인 페이지로 이동
		}else {
			//로그인 한 사용자 일경우
			return "redirect:/"; //컨트롤러
		}
	}
	
	//로그인처리
	
	@PostMapping("user_login")
	public String userLoginPro(UserDTO userDTO, HttpServletRequest request) {
		log.info("user call : loginpro");
		
		UserDTO uDTO = userSerivce.userLogin(userDTO);
		
		if(uDTO != null) { //로그인 성공시
			//최근 로그인 날짜 업데이트
			userSerivce.userLastTimeUpdate(uDTO.getUserid());
			
			//세션 설정
			request.getSession().setAttribute("user", uDTO);
			//로그인 세션 유지 시간
			request.getSession().setMaxInactiveInterval(10*60); //10분
		}else {
			
		}
		return "User/user_login_ok"; //경고창
	}
	
	//로그아웃
	@GetMapping("user_logout") 
	public String userLogout(HttpSession session) {
		log.info("user call : logout"); 
		session.invalidate();
		
		return"redirect:/"; 
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
	
	//정보수정폼
	@GetMapping("user_modify")
	public String userModify() {
		// [수정] 뷰 경로가 "user/user_modify" (소문자) 로 되어 있었다.
		// 실제 폴더명은 "User" 이며, 배포 환경(리눅스 등 대소문자 구분 파일시스템)에서는
		// 해당 경로를 찾지 못해 페이지가 뜨지 않는 원인이 된다.
		return "User/user_modify";
	}
	
	//정보수정 처리
	
	//회원탈퇴(삭제)
	
	//ID찾기
	
	//비번분실시 임시번호 발송
	
	//비번분실시 id를 찾아서 임시비번 발송

	
	
}
