package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnu.sample.dto.BoardRequestDTO;
import com.mnu.sample.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Board")
public class BoardController {
	
	//로그 출력용
	private static final Logger log=
			LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;

	@GetMapping("board_list")
	public String boardList(Model model) {
		log.info(" Board call : board_list");
		model.addAttribute("bList", boardService.boardList());

		return "Board/board_list";
	
	}
	
	// 글쓰기 화면
	@GetMapping("board_write")
	public String boardWriteForm() {
		log.info("board call : board_write form");
		return "Board/board_write";
	}

	// 글쓰기 처리
	@PostMapping("board_write")
	public String boardWrite(BoardRequestDTO board) {
		log.info("board call : board_write");
		boardService.boardWrite(board);
		return "redirect:/Board/board_list";
	}
	
	//삭제 처리
	@PostMapping("board_delete")
	public String boardDelete(BoardRequestDTO board) {
		log.info("board call : board_delete");
		boardService.boardDelete(board);
		return "redirect:/Board/board_list";
	}
	

}