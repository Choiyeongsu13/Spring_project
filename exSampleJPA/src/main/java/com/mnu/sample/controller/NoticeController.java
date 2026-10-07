package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.dto.NoticeResponseDTO;
import com.mnu.sample.repository.NoticeRepository;
import com.mnu.sample.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Notice")
public class NoticeController {
	
	private final NoticeRepository noticeRepository;
	
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);
	private final NoticeService noticeService;
	
	
	//검색 + 페이지 처리 + get+post
	@RequestMapping(value="notice_list", method={RequestMethod.GET, RequestMethod.POST})
	public String NoticeListSearchPage(@RequestParam(value="search", required=false) String search,
										@RequestParam(value="key", required=false) String key,
											@PageableDefault(size=10) Pageable pageable, Model model) {
		log.info("Notice Call : notice_list");
		Page<NoticeResponseDTO> result = noticeService.noticeListSearchPage(search, key, pageable);
		model.addAttribute("bList", result);
		model.addAttribute("blist", result); //페이징용 (hasPrevious, number, totalPages)
		model.addAttribute("totCount", result.getTotalElements()); //전체 글 수
		model.addAttribute("page", result.getNumber() + 1); //현재 페이지 (화면용 1부터)
		model.addAttribute("totPage", result.getTotalPages()); //전체 페이지 수
		model.addAttribute("search", search);
		model.addAttribute("key", key);

		return "Notice/notice_list";
	}


	@GetMapping("notice_view")
	public String NoticeView(@RequestParam("idx") int idx, Model model) {
		log.info("Notice Call : notice_view");
		NoticeResponseDTO notice = noticeService.noticeView(idx);
		model.addAttribute("notice", notice);
		model.addAttribute("newlineChar", "\n");//게시글 내용의 <br> 처리용 (notice_view.html의 이름과 맞춤)
		model.addAttribute("page", 1);//임시
		return "Notice/notice_view";
	}
	
	
}
