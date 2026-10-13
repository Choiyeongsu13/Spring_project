package com.mnu.sample.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mnu.sample.dto.PdsRequestDTO;
import com.mnu.sample.dto.PdsResponseDTO;
import com.mnu.sample.entity.PdsEntity;
import com.mnu.sample.exception.CustomException;
import com.mnu.sample.service.PdsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Pds")
public class PdsController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(PdsController.class);

	private final PdsService pdsService;

	//목록(검색 + 페이지 처리 + get+post)
	@RequestMapping(value="pds_list", method={RequestMethod.GET, RequestMethod.POST})
	public String pdsList(@RequestParam(value="search", required=false) String search,
							@RequestParam(value="key", required=false) String key,
							@PageableDefault(size=10) Pageable pageable, Model model) {
		log.info("Pds Call : pds_list");
		Page<PdsResponseDTO> result = pdsService.pdsListSearchPage(search, key, pageable);
		model.addAttribute("pList", result);
		model.addAttribute("totCount", result.getTotalElements()); //전체 자료 수
		model.addAttribute("page", result.getNumber());			//현재 페이지(0부터)
		model.addAttribute("search", search);
		model.addAttribute("key", key);
		return "Pds/pds_list";
	}

	//등록 폼
	@GetMapping("pds_write")
	public String pdsWrite() {
		log.info("Pds Call : pds_write");
		return "Pds/pds_write";
	}

	//등록처리(파일 업로드)
	@PostMapping("pds_write")
	public String pdsWritePro(PdsRequestDTO pds,
								@RequestParam(value="filename", required=false) MultipartFile file)
								throws CustomException {
		log.info("Pds Call : pds_write_pro");
		try {
			pdsService.pdsWrite(pds, file);
		}catch(IOException e) {
			throw new CustomException("파일 업로드에 실패했습니다", e);
		}
		return "redirect:/Pds/pds_list";
	}

	//상세보기
	@GetMapping("pds_view")
	public String pdsView(@RequestParam("idx") int idx,
							@RequestParam(value="page", defaultValue="0") int page, Model model) {
		log.info("Pds Call : pds_view");
		model.addAttribute("pds", pdsService.pdsView(idx));
		model.addAttribute("newlineChar", "\n");//내용의 <br> 처리용
		model.addAttribute("page", page);
		return "Pds/pds_view";
	}

	//파일 다운로드
	@GetMapping("pds_download")
	public ResponseEntity<Resource> pdsDownload(@RequestParam("idx") int idx) throws CustomException {
		log.info("Pds Call : pds_download");
		PdsEntity pds = pdsService.pdsFile(idx);
		Resource resource = pdsService.pdsResource(pds);
		if(resource == null) {
			throw new CustomException("첨부파일이 존재하지 않습니다");
		}
		//한글 파일이름이 깨지지 않도록 UTF-8로 지정
		ContentDisposition disposition = ContentDisposition.attachment()
				.filename(pds.getFilename(), StandardCharsets.UTF_8)
				.build();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}

	//삭제 폼(팝업)
	@GetMapping("pds_delete")
	public String pdsDelete(@ModelAttribute("idx") int idx, @ModelAttribute("page") int page) {
		log.info("Pds Call : pds_delete");
		return "Pds/pds_delete";
	}

	//삭제처리
	@PostMapping("pds_delete")
	public String pdsDeletePro(@RequestParam("idx") int idx, @ModelAttribute("page") int page,
								@RequestParam("pass") String pass, Model model) {
		log.info("Pds Call : pds_delete_pro");
		model.addAttribute("row", pdsService.pdsDelete(idx, pass));
		return "Pds/pds_delete_pro";
	}
}
