package com.mnu.sample.admin.controller;

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

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.service.AdminNoticeService;
import com.mnu.sample.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("Admin/Board")
public class AdminBoardController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(AdminBoardController.class);

	@Autowired
	private AdminNoticeService noticeService;
	

	//Get, Post 겸용 (검색 O, 페이징 O)
	@RequestMapping(value="board_list", method = {RequestMethod.GET, RequestMethod.POST})
	public String boardList(@RequestParam(value="page", defaultValue="1") int page, PageSearchDTO pageSearchDTO, Model model) {

		log.info("Board Call : board_list");
		
		
		int nowpage = page ; //넘어온 페이지 저장
		int maxlist = 10; //페이지당 글수
		int totpage = 1; //총 페이지수
		
		int totcount = 0;//총 글수
		if(pageSearchDTO.getKey() != null)
			totcount = noticeService.noticeSearchCount(pageSearchDTO);//총 글수
		else
			totcount = noticeService.noticeCount();
		
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
		
		List<NoticeDTO> bList = null;
		String pageSkip = null;
		if(pageSearchDTO.getKey() != null) {
			bList = noticeService.noticeList(pageSearchDTO);
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "board_list", maxlist, pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		}else {
			bList = noticeService.noticeList(pageSearchDTO);
			pageSkip = PageIndex.pageList(nowpage, totpage, "board_list", maxlist);				
		}
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("bList", bList);
		model.addAttribute("pageSkip", pageSkip);
	
		return "admin/board_list";

		
	}

	
	//글 등록 폼
	@GetMapping("board_write")
	public String boardWrite(@RequestParam(value="page", defaultValue="1") int page) {
		return "admin/board_write";
	}
	
	//글 등록처리
	@PostMapping("board_write")
	public String boardWritePro(@RequestParam(value="page", defaultValue="1") int page, NoticeDTO noticeDTO) {
		int row = noticeService.noticeWrite(noticeDTO);
		return "redirect:board_list?page=" + page;
		//return "redirect:/"; //index로 이동시
	}
	
	//상세보기(view)
	@GetMapping("board_view")
	public String boardView(@RequestParam(value="page", defaultValue="1") int page, 
							@RequestParam("idx") int idx, Model model) {
		model.addAttribute("board", noticeService.noticeSelect(idx));
		return "Admin/board_view";
	}
	
	//수정
	@GetMapping("board_modify")
	public String boardModify(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx") int idx , Model model) {
		
		model.addAttribute("board", noticeService.noticeSelect(idx));
		return "Admin/board_modify";
	}

	//수정 처리
	@PostMapping("board_modify")
	public String boardModifyPro(@RequestParam(value="page", defaultValue="1") int page, NoticeDTO noticeDTO, Model model) {
		
		model.addAttribute("row", noticeService.noticeModify(noticeDTO));
		return "Admin/board_modify_pro";
	}

	//삭제폼
	@GetMapping("board_delete")
	public String boardDelete(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx") int idx) {
		return "Admin/board_delete";
	}

	//삭제처리
	@PostMapping("board_delete")
	public String boardDeletePro(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx") int idx, Model model) {

		model.addAttribute("row", noticeService.noticeDelete(idx));
		return "Admin/board_delete_pro";
	}


}