package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Admin")
public class AdminController {
	
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);

	
	@GetMapping("admin_list") //어드민 리스트
	public String AdminList() {
		
		log.info("admin call : list"); 
		return"Admin/admin_list"; //view는 기본
	}
	@GetMapping("admin_login") //어드민 로그인
	public String AdminLogin() {
		
		log.info("admin call : Login"); 
		return"Admin/admin_login"; //view는 기본
	}

}
