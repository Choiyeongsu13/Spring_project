package com.mnu.exSampleThymeleaf.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.exSampleThymeleaf.domain.NoticeDTO;
import com.mnu.exSampleThymeleaf.domain.PageSearchDTO;
import com.mnu.exSampleThymeleaf.service.NoticeService;
import com.mnu.exSampleThymeleaf.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("Notice")
public class NoticeController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;

	//공지사항 리스트(검색 O, 페이징 O)
	@RequestMapping(value = "notice_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String noticeList(@RequestParam(value = "page", defaultValue = "1") int page,
			PageSearchDTO pageSearchDTO, Model model) {
		log.info("Notice Call : notice_list");

		int nowpage = page;
		int maxlist = 10;
		int totpage = 1;

		boolean searching = pageSearchDTO.getKey() != null && !pageSearchDTO.getKey().isEmpty();

		int totcount = searching ? noticeService.noticeSearchCount(pageSearchDTO) : noticeService.noticeCount();

		if (totcount > 0) {
			totpage = (totcount % maxlist == 0) ? totcount / maxlist : totcount / maxlist + 1;
		}

		int offset = (nowpage - 1) * maxlist;
		pageSearchDTO.setOffset(offset);
		pageSearchDTO.setMaxlist(maxlist);

		List<NoticeDTO> pList = noticeService.noticeList(pageSearchDTO);

		String pageSkip;
		if (searching) {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "notice_list", maxlist,
					pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		} else {
			pageSkip = PageIndex.pageList(nowpage, totpage, "notice_list", maxlist);
		}

		model.addAttribute("totcount", totcount);
		model.addAttribute("nowpage", nowpage);
		model.addAttribute("totpage", totpage);
		model.addAttribute("pList", pList);
		model.addAttribute("pageSkip", pageSkip);
		model.addAttribute("search", pageSearchDTO.getSearch());
		model.addAttribute("key", pageSearchDTO.getKey());

		return "Notice/notice_list";
	}

	//공지사항 보기(조회수 증가)
	@GetMapping("notice_view")
	public String noticeView(@RequestParam("idx") int idx,
			@RequestParam(value = "page", defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		log.info("Notice Call : notice_view");

		NoticeDTO notice = noticeService.noticeSelect(idx, request, response);

		model.addAttribute("notice", notice);
		model.addAttribute("page", page);

		return "Notice/notice_view";
	}

}
