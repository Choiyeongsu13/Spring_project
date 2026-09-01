package com.mun.Maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MavenController {
	//jsp 동작 텍스트
	@GetMapping("/Test")
	public String test() {
		return "test"; //jsp 파리명
	}
	//jstl 테스트
	@GetMapping("/Exam")
	public String exam() {
		return "exam"; //jsp 파일명
 	}

}
