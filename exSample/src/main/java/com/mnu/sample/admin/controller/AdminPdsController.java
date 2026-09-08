package com.mnu.sample.admin.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.domain.PdsDTO;
import com.mnu.sample.service.PdsService;
import com.mnu.sample.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("Admin/Pds")
public class AdminPdsController {

	private static final Logger log=
			LoggerFactory.getLogger(AdminPdsController.class);

	@Autowired
	private PdsService pdsService; //주입

	//등록폼
	@GetMapping("pds_write")
	public String PdsWrite() {

		log.info("pds call : write");
		return"Admin/pds_write";
	}
	//등록처리
	@PostMapping("pds_writePro")
	public String PdsWritePro(MultipartHttpServletRequest request) { //파일첨부 방식
		log.info("Admin pds call : pds_write_pro");
		PdsDTO pdsDTO = new PdsDTO();
		pdsDTO.setName(request.getParameter("name"));
		pdsDTO.setEmail(request.getParameter("email"));
		pdsDTO.setSubject(request.getParameter("subject"));
		pdsDTO.setContents(request.getParameter("contents"));
		pdsDTO.setPass(request.getParameter("pass"));

		MultipartFile mf = request.getFile("filename"); //첨부파일 이름받기

		if (mf != null && !mf.isEmpty()) {
			String fileName = mf.getOriginalFilename();
			pdsDTO.setFilename(fileName);

			String path = request.getServletContext().getRealPath("/WEB-INF/views/Pds/upload/");

			File file = new File(path, fileName);
			try {
				mf.transferTo(file);
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			pdsDTO.setFilename(null);
		}

		pdsService.PdsWrite(pdsDTO);
		return"redirect:/Admin/Pds/pds_list?page=1"; //컨트롤러
	}



	//게시판 전체 리스트 Get,Post 겸용
	@RequestMapping(value="pds_list", method = {RequestMethod.GET, RequestMethod.POST})
	public String pdsList(@RequestParam(value="page", defaultValue="1") int page, PageSearchDTO pagesearchDTO, Model model) {
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

		return "Admin/pds_list";
	}

	@GetMapping("pds_view") // 자료실 보기
	public String Pdsview(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx")int idx, Model model, HttpServletRequest request, HttpServletResponse response){
		model.addAttribute("pds",pdsService.Pdsview(idx,request,response));
		return"Admin/pds_view"; //view는 기본
	}

	//수정
	@GetMapping("pds_modify")
	public String pdsModify(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx") int idx , Model model) {

		model.addAttribute("pds", pdsService.PdsModify(idx));
		return "Admin/pds_modify";
	}

	//수정 처리
	@PostMapping("pds_modify")
	public String pdsModifyPro(@RequestParam(value="page", defaultValue="1") int page, MultipartHttpServletRequest request) {
		PdsDTO dto = new PdsDTO();
		dto.setIdx(Integer.parseInt(request.getParameter("idx")));
		dto.setName(request.getParameter("name"));
		dto.setPass(request.getParameter("pass"));
		dto.setEmail(request.getParameter("email"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContents(request.getParameter("contents"));
		String oldfilename = request.getParameter("oldfilename");

		MultipartFile mf = request.getFile("upfile");

		String path = request.getServletContext().getRealPath("/WEB-INF/views/Pds/upload/");
		String filename = (mf != null) ? mf.getOriginalFilename() : "";
		if(mf == null || filename.equals("")) {
			dto.setFilename(oldfilename);
		}
		else {
			File newFile=new File(path+filename);
			File oldFile=new File(path+oldfilename);
			try {
				if(oldFile.exists()) {
					oldFile.delete();
				}
				mf.transferTo(newFile);
			}catch(Exception e) {
				e.printStackTrace();
			}
			dto.setFilename(filename);
		}
		pdsService.PdsModifyPro(dto);
		return "redirect:/Admin/Pds/pds_list?page="+page;
	}

	@GetMapping("pds_delete") //삭제폼
	public String PdsDelete(@RequestParam(value="page", defaultValue="1") int page, @RequestParam("idx") int idx) {
		return"Admin/pds_delete"; //view는 기본
	}
	@PostMapping("pds_delete")
	public String pdsDeletePro(@RequestParam(value="page", defaultValue="1") int page, PdsDTO dto, Model model, HttpServletRequest request) {
		log.info("Call  :  pds_delete (삭제 처리)" );
		String filename = pdsService.PdsSearchFile(dto.getIdx());//파일검색
		int row=pdsService.PdsDeletePro(dto);

		model.addAttribute("row", row);
		if(row==1) {
			if(filename != null) {
				File file = new File(request.getServletContext().getRealPath("/WEB-INF/views/Pds/upload/") + filename);
				file.delete();
			}
		}

		return "Admin/pds_delete_pro";
	}

}
