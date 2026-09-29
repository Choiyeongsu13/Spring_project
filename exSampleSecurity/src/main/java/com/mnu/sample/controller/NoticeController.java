package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Notice")
public class NoticeController {
	
	//로그 출력용
	private static final Logger log=
			LoggerFactory.getLogger(IndexContoller.class);
	
	@GetMapping("notice_list")
	public String noticeList() {
		log.info(" Notice call : notice_list");

		return "/Notice/notice_list";
	
	}

}