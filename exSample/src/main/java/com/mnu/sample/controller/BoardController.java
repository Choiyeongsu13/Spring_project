package com.mnu.sample.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.service.BoardService;
import com.mnu.sample.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("Board")
public class BoardController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;
	
/*	
	//게시판 전체 리스트(검색 X, 페이징처리 X)
	@GetMapping("board_list")
	public String boardList(Model model) {
		log.info("Board Call : board_list");
		model.addAttribute("totcount", boardService.boardCount());
		model.addAttribute("bList", boardService.boardList());
		
		return "Board/board_list";
	}
*/	
	//게시판 전체 리스트(검색 X, 페이징처리 O)
	@GetMapping("board_list_page")
	public String boardListPage(@ModelAttribute("page") int page, PageSearchDTO pageSearchDTO, Model model) {
		log.info("Board Call : board_list");
		
		int nowpage = page ; //넘어온 페이지 저장
		int maxlist = 10; //페이지당 글수
		int totpage = 1; //총 페이지수
		
		int totcount = boardService.boardCount();//총 글수
		// 총 페이지수 계산
		if(totcount % maxlist ==0)
			totpage = totcount / maxlist;
		else
			totpage = totcount / maxlist + 1;
				
		int offset = (nowpage - 1) * maxlist;
		
		//게시글 일련번호 출력용
		int listcount = totcount - ((nowpage-1) * maxlist);
		
		pageSearchDTO.setOffset(offset);
		pageSearchDTO.setMaxlist(maxlist);
		
		String pageSkip = PageIndex.pageList(nowpage, totpage, "board_list_page", maxlist);
		
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("bList", boardService.boardListPage(pageSearchDTO));
		model.addAttribute("pageSkip", pageSkip);
		
		return "Board/board_list";
	}

/*
	//게시판 전체 리스트(검색 O, 페이징처리 X)
	@PostMapping("board_list")
	public String boardList(String search, String key, Model model) {
		log.info("Board Call : board_list");
		model.addAttribute("totcount", boardService.boardCountSearch(search, key));
		model.addAttribute("bList", boardService.boardListSearch(search, key));
		model.addAttribute("search", search);
		model.addAttribute("key", key);
		return "Board/board_list";
	}
*/	
	//게시판 전체 리스트(검색 X, 페이징처리 O)
	@PostMapping("board_list_page")
	public String boardListSearchPage(@ModelAttribute("page") int page, PageSearchDTO pageSearchDTO, Model model) {
		log.info("Board Call : board_list");
		
		int nowpage = page ; //넘어온 페이지 저장
		int maxlist = 10; //페이지당 글수
		int totpage = 1; //총 페이지수
		
		int totcount = boardService.boardCountSearch(pageSearchDTO.getSearch(), pageSearchDTO.getKey());//총 글수
		// 총 페이지수 계산
		if(totcount % maxlist ==0)
			totpage = totcount / maxlist;
		else
			totpage = totcount / maxlist + 1;
				
		int offset = (nowpage - 1) * maxlist;
		
		//게시글 일련번호 출력용
		int listcount = totcount - ((nowpage-1) * maxlist);
		
		pageSearchDTO.setOffset(offset);
		pageSearchDTO.setMaxlist(maxlist);
		
		String pageSkip = PageIndex.pageListHan(nowpage, totpage, "board_list_page", maxlist, pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("bList", boardService.boardListSearchPage(pageSearchDTO));
		model.addAttribute("pageSkip", pageSkip);
		
		return "Board/board_list";
	}

	//Get, Post 겸용 (검색 O, 페이징 O)
	@RequestMapping(value="board_list", method = {RequestMethod.GET, RequestMethod.POST})
	public String boardList(@ModelAttribute("page") int page, PageSearchDTO pageSearchDTO, Model model) {

		log.info("Board Call : board_list");
		
		int nowpage = page ; //넘어온 페이지 저장
		int maxlist = 10; //페이지당 글수
		int totpage = 1; //총 페이지수
		
		int totcount = 0;//총 글수
		if(pageSearchDTO.getKey() != null)
			totcount = boardService.boardCountSearch(pageSearchDTO.getSearch(), pageSearchDTO.getKey());//총 글수
		else
			totcount = boardService.boardCount();
		
		// 총 페이지수 계산
		if(totcount % maxlist ==0)
			totpage = totcount / maxlist;
		else
			totpage = totcount / maxlist + 1;
				
		int offset = (nowpage - 1) * maxlist;
		
		//게시글 일련번호 출력용
		int listcount = totcount - ((nowpage-1) * maxlist);
		
		pageSearchDTO.setOffset(offset);
		pageSearchDTO.setMaxlist(maxlist);
		
		List<BoardDTO> bList = null;
		String pageSkip = null;
		if(pageSearchDTO.getKey() != null) {
			bList = boardService.boardListSearchPage(pageSearchDTO);
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "board_list", maxlist, pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		}else {
			bList = boardService.boardListPage(pageSearchDTO);
			pageSkip = PageIndex.pageList(nowpage, totpage, "board_list", maxlist);				
		}
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("bList", bList);
		model.addAttribute("pageSkip", pageSkip);
		
		return "Board/board_list";

		
	}

	
	//글 등록 폼
	@GetMapping("board_write")
	public String boardWrite(@ModelAttribute("page") int page) {
		return "Board/board_write";
	}
	
	//글 등록처리
	@PostMapping("board_write")
	public String boardWritePro(@ModelAttribute("page") int page, BoardDTO boardDTO) {
		int row = boardService.boardWrite(boardDTO);
		return "redirect:board_list?page=" + page;
		//return "redirect:/"; //index로 이동시
	}
	
	//상세보기(view)
	@GetMapping("board_view")
	public String boardView(@ModelAttribute("page") int page, 
										@RequestParam("idx") int idx, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		model.addAttribute("board", boardService.boardView(idx, request, response));
		return "Board/board_view";
	}
	
	//수정
	@GetMapping("board_modify")
	public String boardModify(@ModelAttribute("page") int page, @RequestParam("idx") int idx , Model model) {
		
		model.addAttribute("board", boardService.boardModify(idx));
		return "Board/board_modify";
	}

	//수정 처리
	@PostMapping("board_modify")
	public String boardModifyPro(@ModelAttribute("page") int page, BoardDTO boardDTO, Model model) {
		
		model.addAttribute("row", boardService.boardModifyPro(boardDTO));
		return "Board/board_modify_pro";
	}

	//삭제폼
	@GetMapping("board_delete")
	public String boardDelete(@ModelAttribute("page") int page, @ModelAttribute("idx") int idx) {
		return "/Board/board_delete";
	}

	//삭제처리
	@PostMapping("board_delete")
	public String boardDeletePro(@ModelAttribute("page") int page, BoardDTO boardDTO, Model model) {
		
		model.addAttribute("row", boardService.boardDelete(boardDTO));
		return "/Board/board_delete_pro";
	}


}