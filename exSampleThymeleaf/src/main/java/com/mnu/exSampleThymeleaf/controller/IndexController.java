package com.mnu.exSampleThymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnu.exSampleThymeleaf.service.BoardService;
import com.mnu.exSampleThymeleaf.service.NoticeService;
import com.mnu.exSampleThymeleaf.service.PdsService;

@Controller
@RequestMapping("/")
public class IndexController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private PdsService pdsService;

	@GetMapping("")
	public String getIndex(Model model) {
		log.info("Call : main index");

		model.addAttribute("noticeTopList", noticeService.noticeTopList(3));
		model.addAttribute("boardTopList", boardService.boardTopList(3));
		model.addAttribute("pdsTopList", pdsService.PdsTopList(3));

		return "index";
	}
}
