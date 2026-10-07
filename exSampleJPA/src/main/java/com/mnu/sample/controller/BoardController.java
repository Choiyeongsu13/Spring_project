package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.dto.BoardRequestDTO;
import com.mnu.sample.dto.BoardResponseDTO;
import com.mnu.sample.repository.BoardRepository;
import com.mnu.sample.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Board")
public class BoardController {
	
	private final BoardRepository boardRepository;
	//로그 출력용
	private static final Logger log=
			LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
	// 생성자는 @RequiredArgsConstructor가 (boardRepository, boardService) 둘 다 주입하도록 자동 생성

/*
	//전체(검색 X, 페이지 X) → 아래 boardListSearchPage와 주소가 겹쳐서 주석 처리
	@GetMapping("board_list")
	public String boardList(Model model) {
		log.info("Board Call : board_list");
		model.addAttribute("bList", boardService.boardList());
		model.addAttribute("totcount", boardService.boardCount());

		return "/Board/board_list";
	}


	//전체(검색 X, 페이지 O)
	@GetMapping("board_list")
	public String boardList(Model model, @PageableDefault(size=10) Pageable pageable) {
		log.info("Board Call : board_list");
		model.addAttribute("bList", boardService.boardList(pageable));
		model.addAttribute("totcount", boardService.boardCount());
		
		return "/Board/board_list";
	}

	
	//검색
	@PostMapping("board_list")
	public String boardListSearch(@RequestParam("search") String search, 
									@RequestParam("key") String key, Model model) {
		log.info("Board Call : board_list_search");
		
		model.addAttribute("bList", boardService.boardListSearch(search, key));
		model.addAttribute("totcount", boardService.boardCountSearch(search, key));
		
		model.addAttribute("search", search);
		model.addAttribute("key", key);
		return "/Board/board_list";
	}
*/
	//검색 + 페이지 처리 + get+post
	@RequestMapping(value="board_list", method={RequestMethod.GET, RequestMethod.POST})
	public String boardListSearchPage(@RequestParam(value="search", required=false) String search,
										@RequestParam(value="key", required=false) String key,
											@PageableDefault(size=10) Pageable pageable, Model model) {
		log.info("Board Call : board_list");
		Page<BoardResponseDTO> result = boardService.boardListSearchPage(search, key, pageable);
		model.addAttribute("bList", result);
		model.addAttribute("blist", result); //페이징용 (hasPrevious, number, totalPages)
		model.addAttribute("totCount", result.getTotalElements()); //전체 글 수
		model.addAttribute("page", result.getNumber() + 1); //현재 페이지 (화면용 1부터)
		model.addAttribute("totPage", result.getTotalPages()); //전체 페이지 수
		model.addAttribute("search", search);
		model.addAttribute("key", key);

		return "Board/board_list";
	}

	//등록 폼
	@GetMapping("board_write")
	public String boardWrite() {
		log.info("Board Call : board_write");
		return "Board/board_write";
		
	}
	//등록처리
	@PostMapping("board_write")
	public String boardWritePro(BoardRequestDTO board) {
		log.info("Board Call : board_write_pro");
		int row = boardService.boardWrite(board);
		if(row==0) {
			return "Board/board_write";
		}else {
			return "redirect:board_list";
		}
		
	}
	
	//리스트에서 제목 선택시 idx을 이용한 상세보기(view)
	@GetMapping("board_view")
	public String boardView(@RequestParam("idx") int idx, Model model) {
		log.info("Board Call : board_view");
		BoardResponseDTO board = boardService.boardView(idx);
		model.addAttribute("board", board);
		model.addAttribute("newlineChar", "\n");//게시글 내용의 <br> 처리용 (board_view.html의 이름과 맞춤)
		model.addAttribute("page", 1);//임시
		return "Board/board_view";
	}
	
	//삭제 폼
	@GetMapping("board_delete")
	public String boardDelete(@ModelAttribute("idx") int idx, @ModelAttribute("page") int page) {
		log.info("Board Call : board_delete");
		
		return "Board/board_delete";
	}
	
	//삭제처리
	@PostMapping("board_delete")
	public String boardDeletePro(@RequestParam("idx") int idx, @RequestParam("pass") String pass, Model model) {
		log.info("Board Call : board_delete_pro");
		int row = boardService.boardDelete(idx, pass);
		model.addAttribute("row", row);
		return "Board/board_delete_pro";// 경고 출력용
	}
	
	//수정 폼
	@GetMapping("board_modify")
	public String boardModify(@RequestParam("idx") int idx, @ModelAttribute("page") int page, Model model) {
		log.info("Board Call : board_modify");
		BoardResponseDTO board = boardService.boardModify(idx);
		model.addAttribute("board", board);

		return "Board/board_modify";
	}
	
	//수정처리
	@PostMapping("board_modify")
	public String boardModifyPro(@RequestParam("idx") int idx, @ModelAttribute("page") int page, BoardRequestDTO board, Model model) {
		log.info("Board Call : board_modify_pro");
		
		model.addAttribute("row", boardService.boardModifyPro(idx, board));
		return "Board/board_modify_pro";
	}



}