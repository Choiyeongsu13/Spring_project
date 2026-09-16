package com.mnu.exSampleThymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("AdminLogin")
public class AdminLoginController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(AdminLoginController.class);

	//로그인 폼
	@GetMapping("admin_login")
	public String adminLogin() {
		log.info("AdminLogin Call : admin_login");

	
		return "AdminLogin/admin_login";
	}
	
	
}
