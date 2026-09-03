package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.service.BoardService;

@Controller
@RequestMapping("Board")
public class BoardController {
	
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private BoardService boardService; //서비스주입
	
	@GetMapping("board_list") //게시판 전체 리스트 (검색 x ,페이징처리 x)
	public String BoardList(Model model) {
		model.addAttribute("totcount",boardService.BoardCount());
		model.addAttribute("blist",boardService.BoardList());

		log.info("board call : list");
		return"Board/board_list"; //view는 기본
	}

	@PostMapping("board_list") //게시판 전체 리스트 (검색 O ,페이징처리 x)
	public String BoardListSearch(String search, String key, Model model) { //오버로딩
		model.addAttribute("totcount",boardService.BoardCountSearch(search, key));
		model.addAttribute("blist",boardService.BoardListSearch(search, key));
		model.addAttribute(search);
		model.addAttribute("key", key);

		log.info("board call : list search={} key={}", search, key);

		return"Board/board_list"; //view는 기본
	}
	
//	//get과 post를  같이 처리함
//	@RequestMapping(value ="board_list" , method= {RequestMethod.GET,RequestMethod.POST})
//	public String BoardListSearch(String search, String key) { //오버로딩
//
//		return""; //view는 기본
//	}

	//게시판 글쓰기
	@GetMapping("board_write") 
	public String BoardWrite() {
		return"Board/board_write"; //view는 기본
	}
	
	//글 등록폼
	@PostMapping("board_write")
	public String boardWritePro(BoardDTO boardDTO) {
		int row = boardService.BoardWrite(boardDTO);
		
		return "redirect:board_list";
	}
	
	
	
	@GetMapping("board_view") //게시판 보기
	public String BoardView() {
		
		log.info("board call : view"); 
		return"Board/board_view"; //view는 기본
	}
	@GetMapping("board_delete") //공지사항 리스트
	public String BoardDelete() {
		
		log.info("board call : delete"); 
		return"Board/board_delete"; //view는 기본
	}

}
