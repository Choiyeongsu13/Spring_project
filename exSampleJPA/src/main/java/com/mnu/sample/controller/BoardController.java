package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.dto.BoardRequestDTO;
import com.mnu.sample.dto.BoardResponseDTO;
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
		model.addAttribute("totCount", boardService.boardCount());
		
		return "Board/board_list";
	
	}
	
	// 글쓰기 화면
	@GetMapping("board_write")
	public String boardWriteForm() {
		log.info("board call : board_write");
		return "Board/board_write";
	}

	// 글쓰기 처리
	@PostMapping("board_write")
	public String boardWrite(BoardRequestDTO board) {
		log.info("board call : board_write_pro");
		boardService.boardWrite(board);
		return "redirect:/Board/board_list";
	}
	//뷰 처리
	@GetMapping("board_view")
	public String boardView(@RequestParam("idx") int idx, Model model) {
		BoardResponseDTO board = boardService.boardView(idx);
		
		model.addAttribute("board",board);
		model.addAttribute("newlineChar","\n");
		model.addAttribute("page",1); //임시
		
		
		log.info("board call : board_view");
		return "Board/board_view";
	}
	
	//삭제 폼
	@GetMapping("board_delete")
	public String boardDelete(BoardRequestDTO board) {
		log.info("board call : board_delete");
//		boardService.boardDelete(board);
		return "Board/board_delete";
	}
	
	//삭제 처리
	@PostMapping("board_delete")
	public String boardDeletePro(@RequestParam("idx")int idx, @RequestParam("pass") String pass,Model model) {
		log.info("board call : board_deletePro");
		int row = boardService.boardDelete(idx, pass);
		model.addAttribute("row",row);
		return "Board/board_delete_pro"; //경고 출력용
	}
	

}