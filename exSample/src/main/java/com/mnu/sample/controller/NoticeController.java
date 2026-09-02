package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Notice")
public class NoticeController {
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);

	
	@GetMapping("notice_list") //공지사항 리스트
	public String NoticeList() {
		
		log.info("notice call : list"); 
		return"Notice/notice_list"; //view는 기본
	}
	
	@GetMapping("notice_view") //공지사항 리스트
	public String NoticeView() {
		
		log.info("notice call : view"); 
		return"Notice/notice_view"; //view는 기본
	}
	
	

	
}
