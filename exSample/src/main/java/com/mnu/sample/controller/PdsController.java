package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Pds")
public class PdsController {
	
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);

	
	@GetMapping("pds_list") //자료실 리스트
	public String PdsList() {

		log.info("pds call : list");
		return"Pds/pds_list"; //view는 기본
	}
	
	//등록폼
	@GetMapping("pds_write") 
	public String PdsWrite() {

		log.info("pds call : write");
		return"Pds/pds_write";
	}

	
	@GetMapping("pds_view") // 자료실 보기
	public String Pdsview() {

		log.info("pds call : view");
		return"Pds/pds_view"; //view는 기본
	}

	@GetMapping("pds_delete") //자료실 삭제
	public String PdsDelete() {

		log.info("pds call : delete");
		return"Pds/pds_delete"; //view는 기본
	}

}
