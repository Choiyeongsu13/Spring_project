package com.mnu.sample.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnu.sample.controller.NoticeController;

@Controller
@RequestMapping("Admin")
public class AdminController {
	
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);

	
	@GetMapping("admin_login") //어드민 로그인
	public String AdminLogin() {
		
		log.info("admin call : Login"); 
		return"Admin/admin_login"; //view는 기본
	}
	@GetMapping("admin_List") 
	public String AdminList() {
		
		log.info("admin call : Login"); 
		return"Admin/admin_list";
	}

}
