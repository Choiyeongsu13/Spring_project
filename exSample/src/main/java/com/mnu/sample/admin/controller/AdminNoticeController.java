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

@Controller
@RequestMapping("Admin/Notice")
public class AdminNoticeController {

	private static final Logger log=
			LoggerFactory.getLogger(AdminNoticeController.class);

	@Autowired
	private AdminNoticeService noticeService;

	@GetMapping("") //기본 경로 접근시 목록으로 이동
	public String AdminNoticeHome() {
		return "redirect:/Admin/Notice/notice_list?page=1";
	}

	@RequestMapping(value="notice_list", method = {RequestMethod.GET, RequestMethod.POST}) //공지사항 리스트(검색 O, 페이징 O)
	public String AdminNoticeList(@RequestParam(value="page", defaultValue="1") int page, PageSearchDTO pageSearchDTO, Model model) {

		log.info("admin call : noticelist");

		int nowpage = page; //넘어온 페이지 저장
		int maxlist = 10; //페이지당 글수
		int totpage = 1; //총 페이지수

		int totcount = 0; //총 글수
		if(pageSearchDTO.getKey() != null)
			totcount = noticeService.noticeSearchCount(pageSearchDTO);
		else
			totcount = noticeService.noticeCount();

		// 총 페이지수 계산
		if(totcount % maxlist == 0)
			totpage = totcount / maxlist;
		else
			totpage = totcount / maxlist + 1;

		int offset = (nowpage - 1) * maxlist;

		int max = totcount;
		int min = (totcount+1)-totcount;
		//게시글 일련번호 출력용
		int listcount = totcount - ((nowpage - 1) * maxlist);

		pageSearchDTO.setOffset(offset);
		pageSearchDTO.setMaxlist(maxlist);

		List<NoticeDTO> pList = noticeService.noticeList(pageSearchDTO);

		String pageSkip = null;
		if(pageSearchDTO.getKey() != null)
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "notice_list", maxlist, pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		else
			pageSkip = PageIndex.pageList(nowpage, totpage, "notice_list", maxlist);

		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("pList", pList);
		model.addAttribute("pageSkip", pageSkip);
		model.addAttribute("max",max);
		model.addAttribute("max",min);
		model.addAttribute("search", pageSearchDTO.getSearch());
		model.addAttribute("key", pageSearchDTO.getKey());

		return "Admin/notice_list";
	}

	@GetMapping("notice_write") //공지사항 등록폼
	public String AdminNoticeWrite(@RequestParam(value="page", defaultValue="1") int page) {

		log.info("admin call : write");
		return "Admin/notice_write";
	}

	@PostMapping("notice_write") //공지사항 등록처리
	public String AdminNoticeWritePro(@RequestParam(value="page", defaultValue="1") int page, NoticeDTO noticeDTO) {

		log.info("admin call : write pro");
		noticeService.noticeWrite(noticeDTO);
		return "redirect:/Admin/Notice/notice_list?page=1";
	}

	@GetMapping("notice_view") //공지사항 뷰
	public String NoticeView(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx") int idx, Model model) {

		log.info("admin call : noticeview");
		model.addAttribute("notice", noticeService.noticeSelect(idx));
		return "Admin/notice_view";
	}

	@GetMapping("notice_modify") //공지사항 수정폼
	public String NoticeModify(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx") int idx, Model model) {

		log.info("admin call : notice modify");
		model.addAttribute("notice", noticeService.noticeSelect(idx));
		return "Admin/notice_modify";
	}

	@PostMapping("notice_modify") //공지사항 수정처리
	public String NoticeModifyPro(@RequestParam(value="page", defaultValue="1") int page, NoticeDTO noticeDTO) {

		log.info("admin call : notice modify pro");
		noticeService.noticeModify(noticeDTO);
		return "redirect:/Admin/Notice/notice_view?page=" + page + "&idx=" + noticeDTO.getIdx();
	}

	@GetMapping("notice_delete") //공지사항 삭제처리 (관리자권한이기에 별도 확인폼 없이 처리)
	public String NoticeDelete(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx") int idx) {

		log.info("admin call : notice delete");
		noticeService.noticeDelete(idx);
		return "redirect:/Admin/Notice/notice_list?page=" + page;
	}

}
