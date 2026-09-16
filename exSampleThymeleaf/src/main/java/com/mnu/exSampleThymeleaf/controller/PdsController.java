package com.mnu.exSampleThymeleaf.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mnu.exSampleThymeleaf.domain.PageSearchDTO;
import com.mnu.exSampleThymeleaf.domain.PdsDTO;
import com.mnu.exSampleThymeleaf.service.PdsService;
import com.mnu.exSampleThymeleaf.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("Pds")
public class PdsController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(PdsController.class);

	@Autowired
	private PdsService pdsService;

	@Value("${app.upload-dir:./uploads}")
	private String uploadDir;

	private Path pdsUploadPath() throws IOException {
		Path dir = Paths.get(uploadDir, "pds").toAbsolutePath().normalize();
		Files.createDirectories(dir);
		return dir;
	}

	//업로드 파일을 서버에 저장하고, 저장된(충돌 방지용 접두사가 붙은) 파일명을 반환
	private String saveUploadFile(MultipartFile file) throws IOException {
		String original = Paths.get(file.getOriginalFilename()).getFileName().toString();
		if (original.isBlank()) {
			original = "file";
		}
		String stored = System.currentTimeMillis() + "_" + original;
		Path target = pdsUploadPath().resolve(stored);
		file.transferTo(target);
		return stored;
	}

	//업로드 당시 붙인 시간 접두사를 제거하고 원래 파일명만 보여주기 위한 헬퍼
	private String displayName(String storedFilename) {
		if (storedFilename == null) {
			return null;
		}
		return storedFilename.replaceFirst("^\\d+_", "");
	}

	//자료실 리스트(검색 O, 페이징 O)
	@RequestMapping(value = "pds_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String pdsList(@RequestParam(value = "page", defaultValue = "1") int page,
			PageSearchDTO pageSearchDTO, Model model) {
		log.info("Pds Call : pds_list");

		int nowpage = page;
		int maxlist = 10;
		int totpage = 1;

		boolean searching = pageSearchDTO.getKey() != null && !pageSearchDTO.getKey().isEmpty();

		int totcount = searching
				? pdsService.PdsCountSearch(pageSearchDTO.getSearch(), pageSearchDTO.getKey())
				: pdsService.PdsCount();

		if (totcount > 0) {
			totpage = (totcount % maxlist == 0) ? totcount / maxlist : totcount / maxlist + 1;
		}

		int offset = (nowpage - 1) * maxlist;
		pageSearchDTO.setOffset(offset);
		pageSearchDTO.setMaxlist(maxlist);

		List<PdsDTO> pList = searching
				? pdsService.PdsListSearchPage(pageSearchDTO)
				: pdsService.PdsListPage(pageSearchDTO);

		String pageSkip;
		if (searching) {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "pds_list", maxlist,
					pageSearchDTO.getSearch(), pageSearchDTO.getKey());
		} else {
			pageSkip = PageIndex.pageList(nowpage, totpage, "pds_list", maxlist);
		}

		model.addAttribute("totcount", totcount);
		model.addAttribute("nowpage", nowpage);
		model.addAttribute("totpage", totpage);
		model.addAttribute("pList", pList);
		model.addAttribute("pageSkip", pageSkip);
		model.addAttribute("search", pageSearchDTO.getSearch());
		model.addAttribute("key", pageSearchDTO.getKey());

		return "Pds/pds_list";
	}

	//자료 보기(조회수 증가)
	@GetMapping("pds_view")
	public String pdsView(@RequestParam("idx") int idx,
			@RequestParam(value = "page", defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		log.info("Pds Call : pds_view");

		pdsService.pdsHits(idx);
		PdsDTO pds = pdsService.Pdsview(idx, request, response);

		model.addAttribute("pds", pds);
		model.addAttribute("displayFilename", displayName(pds.getFilename()));
		model.addAttribute("page", page);

		return "Pds/pds_view";
	}

	//첨부파일 다운로드
	@GetMapping("pds_download")
	public ResponseEntity<Resource> pdsDownload(@RequestParam("idx") int idx) throws IOException {
		PdsDTO pds = pdsService.PdsModify(idx);
		if (pds == null || pds.getFilename() == null || pds.getFilename().isBlank()) {
			return ResponseEntity.notFound().build();
		}

		Path file = pdsUploadPath().resolve(pds.getFilename()).normalize();
		Resource resource = new UrlResource(file.toUri());
		if (!resource.exists()) {
			return ResponseEntity.notFound().build();
		}

		String downloadName = displayName(pds.getFilename());
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadName + "\"")
				.body(resource);
	}

	//자료 올리기 폼
	@GetMapping("pds_write")
	public String pdsWriteForm() {
		log.info("Pds Call : pds_write");
		return "Pds/pds_write";
	}

	//자료 올리기 처리
	@PostMapping("pds_write")
	public String pdsWritePro(PdsDTO pdsDTO, @RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {
		log.info("Pds Call : pds_write_pro");

		if (file != null && !file.isEmpty()) {
			pdsDTO.setFilename(saveUploadFile(file));
		}

		pdsService.PdsWrite(pdsDTO);

		return "redirect:/Pds/pds_list?page=1";
	}

	//수정 폼(비밀번호 재입력 필요)
	@GetMapping("pds_modify")
	public String pdsModifyForm(@RequestParam("idx") int idx,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		log.info("Pds Call : pds_modify");

		PdsDTO pds = pdsService.PdsModify(idx);
		model.addAttribute("pds", pds);
		model.addAttribute("displayFilename", displayName(pds.getFilename()));
		model.addAttribute("page", page);

		return "Pds/pds_modify";
	}

	//수정 처리(비밀번호가 일치할 때만 수정, 새 파일이 있으면 기존 파일 교체)
	@PostMapping("pds_modify")
	public String pdsModifyPro(PdsDTO pdsDTO, @RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws IOException {
		log.info("Pds Call : pds_modify_pro");

		String oldFilename = pdsService.PdsSearchFile(pdsDTO.getIdx());

		if (file != null && !file.isEmpty()) {
			pdsDTO.setFilename(saveUploadFile(file));
		} else {
			pdsDTO.setFilename(oldFilename);
		}

		int result = pdsService.PdsModifyPro(pdsDTO);

		if (result == 0) {
			model.addAttribute("pds", pdsDTO);
			model.addAttribute("displayFilename", displayName(oldFilename));
			model.addAttribute("page", page);
			model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "Pds/pds_modify";
		}

		if (file != null && !file.isEmpty() && oldFilename != null && !oldFilename.isBlank()) {
			Files.deleteIfExists(pdsUploadPath().resolve(oldFilename));
		}

		return "redirect:/Pds/pds_view?idx=" + pdsDTO.getIdx() + "&page=" + page;
	}

	//삭제 폼(비밀번호 입력)
	@GetMapping("pds_delete")
	public String pdsDeleteForm(@RequestParam("idx") int idx,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		log.info("Pds Call : pds_delete form");

		model.addAttribute("idx", idx);
		model.addAttribute("page", page);

		return "Pds/pds_delete";
	}

	//삭제 처리(비밀번호가 일치할 때만 삭제, 첨부파일도 함께 제거)
	@PostMapping("pds_delete")
	public String pdsDeletePro(PdsDTO pdsDTO,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws IOException {
		log.info("Pds Call : pds_delete pro");

		String filename = pdsService.PdsSearchFile(pdsDTO.getIdx());

		int result = pdsService.PdsDeletePro(pdsDTO);

		if (result == 0) {
			model.addAttribute("idx", pdsDTO.getIdx());
			model.addAttribute("page", page);
			model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "Pds/pds_delete";
		}

		if (filename != null && !filename.isBlank()) {
			Files.deleteIfExists(pdsUploadPath().resolve(filename));
		}

		return "redirect:/Pds/pds_list?page=" + page;
	}

}
