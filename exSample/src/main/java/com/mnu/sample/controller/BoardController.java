package com.mnu.sample.controller;

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

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.service.BoardService;
import com.mnu.sample.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("Board")
public class BoardController {
	
	private static final Logger log=
			LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService; //서비스주입
	
//	@GetMapping("board_list") //게시판 전체 리스트 (검색 x ,페이징처리 x)
//	public String BoardList(Model model) {
//		model.addAttribute("totcount",boardService.BoardCount());
//		model.addAttribute("blist",boardService.BoardList());
//
//		log.info("board call : list");
//		return"Board/board_list"; //view는 기본
//	}
	
	@GetMapping("board_list_page") //게시판 전체 리스트 (검색 x ,페이징처리 O)
	public String BoardListPage(@RequestParam("page")int page,  PageSearchDTO pagesearchDTO, Model model) {
		log.info("board call : list");
		
		int nowpage = page; //넘어온 페이지 저장
		int maxlist =10; // 페이지당 글 수
		int totpage =1; //총 페이지수
		
		int totcount= boardService.BoardCount(); //총 글수
		
		if(totcount % maxlist ==0) //총 페이지수 계산
			totpage = totcount / maxlist;
		else
			totpage = totcount /maxlist +1;
		
		//사용자가 선택한 페이지번호 체크
		
		int offset = (nowpage - 1) * maxlist;
		
		//게시글 일련번호 출력용
		int listcount = totcount - ((nowpage -1) * maxlist);
		
		pagesearchDTO.setOffset(offset);
		pagesearchDTO.setMaxlist(maxlist);
		
		String pageSkip= PageIndex.pageList(nowpage, totpage,"board_list_page", maxlist);

		log.info("페이지 스킵 : {}", pageSkip);
		model.addAttribute("totcount",totcount);
		model.addAttribute("totpage",totpage);
		model.addAttribute("listcount",listcount);
		model.addAttribute("blist", boardService.BoardListPage(pagesearchDTO));
		model.addAttribute("pageSkip",pageSkip);
		
		
		
		
		return"Board/board_list"; //view는 기본
	}
	
	@PostMapping("board_list_page") //게시판 전체 리스트 (검색 O ,페이징처리 O)
	public String BoardListSearchPage(@RequestParam("page")int page,  PageSearchDTO pagesearchDTO, Model model) {
		log.info("board call : list");
		
		int nowpage = page; //넘어온 페이지 저장
		int maxlist =10; // 페이지당 글 수
		int totpage =1; //총 페이지수
		
		int totcount= boardService.BoardCountSearch(pagesearchDTO.getSearch(),pagesearchDTO.getKey()); //총 글수
		
		if(totcount % maxlist ==0) //총 페이지수 계산
			totpage = totcount / maxlist;
		else
			totpage = totcount /maxlist +1;
		
		//사용자가 선택한 페이지번호 체크
		
		int offset = (nowpage - 1) * maxlist;
		
		//게시글 일련번호 출력용
		int listcount = totcount - ((nowpage -1) * maxlist);
		
		pagesearchDTO.setOffset(offset);
		pagesearchDTO.setMaxlist(maxlist);
		
		String pageSkip = PageIndex.pageListHan(nowpage, totpage,"board_list_page", maxlist, pagesearchDTO.getSearch(),pagesearchDTO.getKey());

		log.info("페이지 스킵 : {}", pageSkip);
		model.addAttribute("totcount",totcount);
		model.addAttribute("totpage",totpage);
		model.addAttribute("listcount",listcount);
		model.addAttribute("blist", boardService.BoardListSearchPage(pagesearchDTO));
		model.addAttribute("pageSkip",pageSkip);
		
		
		
		
		return"Board/board_list"; //view는 기본
	}


//	@PostMapping("board_list") //게시판 전체 리스트 (검색 O ,페이징처리 x) - 아래 boardList()와 매핑이 겹쳐 제거
//	public String BoardListSearch(String search, String key, Model model) { //오버로딩
//		model.addAttribute("totcount",boardService.BoardCountSearch(search, key));
//		model.addAttribute("blist",boardService.BoardListSearch(search, key));
//		model.addAttribute("search",search);
//		model.addAttribute("key", key);
//
//		log.info("board call : list {} {}", search, key);
//
//		return"Board/board_list"; //view는 기본
//	}


//	//get과 post를  같이 처리함
//	@RequestMapping(value ="board_list" , method= {RequestMethod.GET,RequestMethod.POST})
//	public String BoardListSearch(String search, String key) { //오버로딩
//
//		return""; //view는 기본
//	}

	
	//Get, Post 겸용 (검색 O, 페이징 O)
		@RequestMapping(value="board_list", method = {RequestMethod.GET, RequestMethod.POST})
		public String boardList(@RequestParam("page") int page, PageSearchDTO pageSearchDTO, Model model) {

			log.info("Board Call : board_list");
			
			int nowpage = page ; //넘어온 페이지 저장
			int maxlist = 10; //페이지당 글수
			int totpage = 1; //총 페이지수
			
			int totcount = 0;//총 글수
			if(pageSearchDTO.getKey() != null)
				totcount = boardService.BoardCountSearch(pageSearchDTO.getSearch(), pageSearchDTO.getKey());//총 글수
			else
				totcount = boardService.BoardCount();
			
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
				bList = boardService.BoardListSearchPage(pageSearchDTO);
				pageSkip = PageIndex.pageListHan(nowpage, totpage, "board_list", maxlist, pageSearchDTO.getSearch(), pageSearchDTO.getKey());
			}else {
				bList = boardService.BoardListPage(pageSearchDTO);
				pageSkip = PageIndex.pageList(nowpage, totpage, "board_list", maxlist);				
			}
			
			model.addAttribute("totcount", totcount);
			model.addAttribute("totpage", totpage);
			model.addAttribute("listcount", listcount);
			model.addAttribute("blist", bList);
			model.addAttribute("pageSkip", pageSkip);
			
			return "Board/board_list";

			
		}



	
	//게시판 글쓰기
	@GetMapping("board_write")
	public String BoardWrite(@RequestParam("page") int page, Model model) {
		model.addAttribute("page", page);
		return"Board/board_write"; //view는 기본
	}

	//글 등록처리
	@PostMapping("board_write")
	public String boardWritePro(@RequestParam("page") int page, BoardDTO boardDTO) {
		log.info("board call : write pro {}", boardDTO);
		int row = boardService.BoardWrite(boardDTO);
		return "redirect:/Board/board_list?page=" + page; //등록후 목록으로 이동
	}

	@GetMapping("board_view") //상세 보기
	public String BoardView(@RequestParam(value="page", defaultValue="1") String pageParam,
			@RequestParam("idx") int idx, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		int page;
		try {
			page = Integer.parseInt(pageParam);
		} catch (NumberFormatException e) {
			page = 1; //page 파라미터가 비어있는 경우(목록에서 넘어온 링크가 깨진 경우) 대비
		}
		model.addAttribute("page", page);
		model.addAttribute("board",boardService.boardview(idx, request, response));
		return"Board/board_view";
	}

	//삭제 확인(비밀번호 입력폼)
	@GetMapping("board_delete")
	public String BoardDelete(@RequestParam("page") int page, @RequestParam("idx") int idx, Model model) {
		log.info("board call : delete idx={}", idx);
		model.addAttribute("page", page);
		model.addAttribute("idx", idx);
		return"Board/board_delete";
	}

	//삭제처리
	@PostMapping("board_delete")
	public String BoardDeletePro(@RequestParam("page") int page, @RequestParam("idx") int idx,
			@RequestParam("pass") String pass, Model model) {
		log.info("board call : delete pro idx={}", idx);

		int row = boardService.boardDeletePro(idx, pass);
		if(row == 0) {
			model.addAttribute("page", page);
			model.addAttribute("idx", idx);
			model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "Board/board_delete";
		}
		return "redirect:/Board/board_list?page=" + page;
	}

	//수정
	@GetMapping("board_modify")
	public String BoardModify(@RequestParam("page") int page, @RequestParam("idx") int idx,Model model) {
			model.addAttribute("page", page);
			model.addAttribute("board",boardService.boardModify(idx));

		return "Board/board_modify";
	}

	//수정처리
	@PostMapping("board_modify")
	public String BoardModifyPro(@RequestParam("page") int page, BoardDTO boardDTO, Model model) {
		log.info("board call : modify pro {}", boardDTO);

		int row = boardService.boardModifyPro(boardDTO);
		if(row == 0) {
			model.addAttribute("page", page);
			model.addAttribute("board", boardDTO);
			model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "Board/board_modify";
		}

		return "redirect:/Board/board_view?page=" + page + "&idx=" + boardDTO.getIdx();
	}


}
