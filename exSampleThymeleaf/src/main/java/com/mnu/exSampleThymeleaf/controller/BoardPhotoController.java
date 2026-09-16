package com.mnu.exSampleThymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("BoardPhoto")
public class BoardPhotoController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(BoardPhotoController.class);

	//로그인 폼
	@GetMapping("board_list")
	public String boardList() {
		log.info("BoardPhoto Call : board_list");

	
		return "BoardPhoto/board_list";
	}
	
	
}
