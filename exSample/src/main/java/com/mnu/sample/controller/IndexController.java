package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnu.sample.service.BoardService;
import com.mnu.sample.service.NoticeService;
import com.mnu.sample.service.PdsService;

@Controller
@RequestMapping("")
public class IndexController {
	
		//로그 출력용
		private static final Logger log=
				LoggerFactory.getLogger(IndexController.class);

		//주입
		@Autowired
		private NoticeService noticeService;
		@Autowired
		private BoardService boardService;
		@Autowired
		private PdsService pdsService;

		@GetMapping("")
		public String getIndex(Model model) { //전달자 /많은양을 보낼때 model 거의 90퍼 활용
			//String이면 return이 jsp / void면 mapping이 jsp
			log.info("call : main index");
//			List<NoticeDTO> nList = 
			
			model.addAttribute("nList",noticeService.noticeTopList(3)); //최근 공지 3개 , 나중에 서비스 호출
			model.addAttribute("bList",boardService.boardTopList(3)); //최근 게시글 3개 
			model.addAttribute("pList",pdsService.PdsTopList(3)); //최근 자료실 3개
			
			
			
			return "index"; //jsp 이름
		}
		
		
		
}
