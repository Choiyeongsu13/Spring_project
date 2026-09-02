package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Gallery")
public class GalleryController {
	
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);

	
	@GetMapping("gallery_list") //갤러리 리스트
	public String GalleryList() {
		
		log.info("gallery call : list"); 
		return"Gallery/gallery_list"; //view는 기본
	}
	@GetMapping("gallery_view") //갤러리 보기
	public String GalleryView() {
		
		log.info("gallery call : view"); 
		return"Gallery/gallery_view"; //view는 기본
	}
	@GetMapping("gallery_write") //갤러리 글쓰기
	public String GalleryWrite() {
		
		log.info("gallery call : write"); 
		return"Gallery/gallery_write"; //view는 기본
	}
	

}
