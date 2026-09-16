package com.mnu.exSampleThymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("Gallery")
public class GalleryController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(GalleryController.class);

	//로그인 폼
	@GetMapping("gallery_list")
	public String galleryList() {
		log.info("Gallery Call : gallery_list");

	
		return "Gallery/gallery_list";
	}
	
	
}
