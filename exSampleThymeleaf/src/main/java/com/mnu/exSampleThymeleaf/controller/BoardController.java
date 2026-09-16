package com.mnu.exSampleThymeleaf.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.exSampleThymeleaf.domain.BoardDTO;
import com.mnu.exSampleThymeleaf.domain.PageSearchDTO;
import com.mnu.exSampleThymeleaf.service.BoardService;
import com.mnu.exSampleThymeleaf.util.PageIndex;

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

	//게시판 리스트(검색 O, 페이징 O)
	@RequestMapping(value = "board_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String boardList(@RequestParam(value = "page", defaultValue = "1") int page,
			PageSearchDTO pageSearchDTO, Model model) {
		log.info("Board Call : board_list");

		int nowpage = page;
		int maxlist = 10;
		int totpage = 1;

		boolean searching = pageSearchDTO.getKey() != null && !pageSearchDTO.getKey().isEmpty();

		int totcount = searching
				? boardService.boardCountSearch(pageSearchDTO.getSearch(), pageSearchDTO.getKey())
				: boardService.boardCount();

		if (totcount > 0) {
			totpage = (totcount % maxlist == 0) ? totcount / maxlist : totcount / maxlist + 1;
		}

		int offset = (nowpage - 1) * maxlist;
		pageSearchDTO.setOffset(offset);
		pageSearchDTO.setMaxlist(maxlist);

		List<BoardDTO> pList = searching
				? boardService.boardListSearchPage(pageSearchDTO)
				: boardService.boardListPage(pageSearchDTO);

		String pageSkip;
		if (searching) {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "board_list", maxlist,
					pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		} else {
			pageSkip = PageIndex.pageList(nowpage, totpage, "board_list", maxlist);
		}

		model.addAttribute("totcount", totcount);
		model.addAttribute("nowpage", nowpage);
		model.addAttribute("totpage", totpage);
		model.addAttribute("pList", pList);
		model.addAttribute("pageSkip", pageSkip);
		model.addAttribute("search", pageSearchDTO.getSearch());
		model.addAttribute("key", pageSearchDTO.getKey());

		return "Board/board_list";
	}

	//게시글 보기(조회수 증가)
	@GetMapping("board_view")
	public String boardView(@RequestParam("idx") int idx,
			@RequestParam(value = "page", defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		log.info("Board Call : board_view");

		BoardDTO board = boardService.boardView(idx, request, response);

		model.addAttribute("board", board);
		model.addAttribute("page", page);

		return "Board/board_view";
	}

	//글쓰기 폼
	@GetMapping("board_write")
	public String boardWrite() {
		log.info("Board Call : board_write");
		return "Board/board_write";
	}

	//글쓰기 처리
	@PostMapping("board_write")
	public String boardWritePro(BoardDTO boardDTO) {
		log.info("Board Call : board_write_pro");
		boardService.boardWrite(boardDTO);
		return "redirect:/Board/board_list?page=1";
	}

	//수정 폼(비밀번호 재입력 필요)
	@GetMapping("board_modify")
	public String boardModify(@RequestParam("idx") int idx,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		log.info("Board Call : board_modify");

		model.addAttribute("board", boardService.boardModify(idx));
		model.addAttribute("page", page);

		return "Board/board_modify";
	}

	//수정 처리(비밀번호가 일치할 때만 수정)
	@PostMapping("board_modify")
	public String boardModifyPro(BoardDTO boardDTO,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		log.info("Board Call : board_modify_pro");

		int result = boardService.boardModifyPro(boardDTO);

		if (result == 0) {
			model.addAttribute("board", boardDTO);
			model.addAttribute("page", page);
			model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "Board/board_modify";
		}

		return "redirect:/Board/board_view?idx=" + boardDTO.getIdx() + "&page=" + page;
	}

	//삭제 폼(비밀번호 입력)
	@GetMapping("board_delete")
	public String boardDeleteForm(@RequestParam("idx") int idx,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		log.info("Board Call : board_delete form");

		model.addAttribute("idx", idx);
		model.addAttribute("page", page);

		return "Board/board_delete";
	}

	//삭제 처리(비밀번호가 일치할 때만 삭제)
	@PostMapping("board_delete")
	public String boardDeletePro(BoardDTO boardDTO,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		log.info("Board Call : board_delete pro");

		int result = boardService.boardDelete(boardDTO);

		if (result == 0) {
			model.addAttribute("idx", boardDTO.getIdx());
			model.addAttribute("page", page);
			model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "Board/board_delete";
		}

		return "redirect:/Board/board_list?page=" + page;
	}

}
