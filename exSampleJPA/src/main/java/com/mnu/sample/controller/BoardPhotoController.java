package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("BoardPhoto")
public class BoardPhotoController {
	
	//로그 출력용
	private static final Logger log=
			LoggerFactory.getLogger(IndexContoller.class);
	
	@GetMapping("board_list")
	public String boardList() {
		log.info(" BoardPhoto call : board_list");

		return "BoardPhoto/board_list";
	
	}

}