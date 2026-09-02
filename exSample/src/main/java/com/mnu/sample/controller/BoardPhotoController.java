package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("BoardPhoto")
public class BoardPhotoController {
	
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);

	
	@GetMapping("board_list") //게시판 리스트
	public String PhotoList() {
		
		log.info("photo call : list"); 
		return"BoardPhoto/board_list"; //view는 기본
	}
	
	@GetMapping("board_write") //게시판 글쓰기
	public String BoardWrite() {
		
		log.info("board call : write"); 
		return"BoardPhoto/board_wirte"; //view는 기본
	}
	
	@GetMapping("board_view") //게시판 보기
	public String BoardView() {
		
		log.info("board call : view"); 
		return"BoardPhoto/board_view"; //view는 기본
		
	}
	@GetMapping("board_delete") //공지사항 리스트
	public String BoardDelete() {
		
		log.info("board call : delete"); 
		return"BoardPhoto/board_delete"; //view는 기본
	}

}
