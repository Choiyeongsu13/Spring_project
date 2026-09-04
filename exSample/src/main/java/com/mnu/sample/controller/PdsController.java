package com.mnu.sample.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.domain.PdsDTO;
import com.mnu.sample.service.PdsService;
import com.mnu.sample.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("Pds")
public class PdsController {
	
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private PdsService pdsService; //주입
	
/*
	//자료실 리스트(검색 X, 페이징처리 X)
	@GetMapping("pds_list")
	public String PdsList() {

		log.info("pds call : list");
		return"Pds/pds_list"; //view는 기본
	}
*/
	//등록폼
	@GetMapping("pds_write") 
	public String PdsWrite() {

		log.info("pds call : write");
		return"Pds/pds_write";
	}
	//등록처리
	@PostMapping("pds_writePro") 
	public String PdsWritePro(MultipartHttpServletRequest request) { //파일첨부 방식
		log.info("pds call : pds_write_pro");
		PdsDTO pdsDTO = new PdsDTO();
		pdsDTO.setName(request.getParameter("name"));
		pdsDTO.setEmail(request.getParameter("email"));
		pdsDTO.setSubject(request.getParameter("subject"));
		pdsDTO.setContents(request.getParameter("contents"));
		pdsDTO.setPass(request.getParameter("pass"));
				
		MultipartFile mf = request.getFile("filename"); //첨부파일 이름받기

		if (mf != null && !mf.isEmpty()) {
			//파일이름 추출
			String fileName = mf.getOriginalFilename();
			pdsDTO.setFilename(fileName);

			//저장경로 설정
			String path = request.getServletContext().getRealPath("/WEB-INF/views/Pds/upload/");

			//실제 파일 저장
			File file = new File(path, fileName); //파일 객체 생성
			try {
				mf.transferTo(file);
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			pdsDTO.setFilename(null);
		}

		pdsService.PdsWrite(pdsDTO);
		return"redirect:/Pds/pds_list?page=1"; //컨트롤러
	}
	
	//게시판 전체 리스트 (검색 x, 페이징처리O)
	@GetMapping("pds_list_page")
	public String pdsListPage(@ModelAttribute("page")int page , PageSearchDTO pagesearchDTO,Model model) {
		
		int nowpage= page;
		int maxlist = 10;
		int totpage = 1;
		
		int totcount = pdsService.PdsCount();
		
		if(totcount % maxlist ==0)
			totpage = totcount /maxlist;
		else
			totpage = totcount / maxlist +1;
		
		int offset = (nowpage -1) * maxlist;
		
		int listcount = totcount - ((nowpage-1) * maxlist);
		
		pagesearchDTO.setOffset(offset);
		pagesearchDTO.setMaxlist(maxlist);
		
		String pageSkip = PageIndex.pageList(nowpage, totpage, "pds_list", maxlist);
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("pList", pdsService.PdsListPage(pagesearchDTO));
		model.addAttribute("pageSkip", pageSkip);
		
		return "Pds/pds_list";
	
	}
	
	//게시판 전체 리스트(검색 X, 페이징처리 O)
	@PostMapping("pds_list_page")
	public String pdsListSearchPage(@ModelAttribute("page") int page, PageSearchDTO pageSearchDTO, Model model) {
		log.info("pds Call : pds_list");
		
		int nowpage = page ; //넘어온 페이지 저장
		int maxlist = 10; //페이지당 글수
		int totpage = 1; //총 페이지수
		
		int totcount = pdsService.PdsCountSearch(pageSearchDTO.getSearch(), pageSearchDTO.getKey());//총 글수
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
		
		String pageSkip = PageIndex.pageListHan(nowpage, totpage, "pds_list_page", maxlist, pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("pList", pdsService.PdsListSearchPage(pageSearchDTO));
		model.addAttribute("pageSkip", pageSkip);
		
		return "Pds/pds_list";
	}

	
	//게시판 전체 리스트 Get,Post 겸용
	@RequestMapping(value="pds_list", method = {RequestMethod.GET, RequestMethod.POST})
	public String pdsList(@ModelAttribute("page")int page, PageSearchDTO pagesearchDTO, Model model) {
		int nowpage=page;
		int maxlist = 10;
		int totpage= 1;
		
		int totcount = 0;
		if(pagesearchDTO.getKey() != null)
			totcount = pdsService.PdsCountSearch(pagesearchDTO.getSearch(),pagesearchDTO.getKey());
		else
			totcount = pdsService.PdsCount();
		
		if(totcount % maxlist ==0)
			totpage = totcount / maxlist;
		else
			totpage = totcount / maxlist +1;
		
		int offset = (nowpage -1) * maxlist;
		
		int listcount = totcount - ((nowpage-1) * maxlist);
		
		pagesearchDTO.setOffset(offset);
		pagesearchDTO.setMaxlist(maxlist);
		
		List<PdsDTO> pList = null;
		String pageSkip = null;
		if(pagesearchDTO.getKey() != null) {
			pList = pdsService.PdsListSearchPage(pagesearchDTO);
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "pds_list", maxlist,pagesearchDTO.getSearch(),pagesearchDTO.getKey());
		}
		else {
			pList = pdsService.PdsListPage(pagesearchDTO);
			pageSkip = PageIndex.pageList(nowpage, totpage, "pds_list", maxlist);
		}
			
		model.addAttribute("totcount",totcount);
		model.addAttribute("totpage",totpage);
		model.addAttribute("listcount",listcount);
		model.addAttribute("pList",pList);
		model.addAttribute("pageSkip",pageSkip);
		
		return "Pds/pds_list";
	}
	
	@GetMapping("pds_view") // 자료실 보기
	public String Pdsview(@ModelAttribute("page")int idx, Model model, HttpServletRequest request, HttpServletResponse response){
		model.addAttribute("pds",pdsService.Pdsview(idx,request,response));
		return"Pds/pds_view"; //view는 기본
	}
	
	//수정
	@GetMapping("pds_modify")
	public String pdsModify(@ModelAttribute("page") int page, @RequestParam("idx") int idx , Model model) {
		
		model.addAttribute("pds", pdsService.PdsModify(idx));
		return "Pds/pds_modify";
	}

	//수정 처리
	@PostMapping("pds_modify")
	public String pdsModifyPro(@ModelAttribute("page") int page, PdsDTO pdsDTO, Model model) {
		
		model.addAttribute("row", pdsService.PdsModifyPro(pdsDTO));
		return "pds/pds_modify_pro";
	}
//	@GetMapping("down_load")
//	public Respons


	@GetMapping("pds_delete") //삭제폼
	public String PdsDelete(@ModelAttribute("page")int page, @ModelAttribute("idx") int idx) {
		return"Pds/pds_delete"; //view는 기본
	}
	@PostMapping("pds_delete")
	public String pdsDeletePro(@ModelAttribute("page")int page, PdsDTO pdsDTO, Model model) {
		model.addAttribute("row",pdsService.PdsDeletePro(pdsDTO));
		return "Pds/pds_delete_pro";
	}

}
