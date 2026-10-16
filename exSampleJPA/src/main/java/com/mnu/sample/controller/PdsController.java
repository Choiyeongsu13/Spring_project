package com.mnu.sample.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

import com.mnu.sample.dto.PdsRequestDTO;
import com.mnu.sample.dto.PdsResponseDTO;
import com.mnu.sample.service.PdsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Pds")
public class PdsController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(PdsController.class);
	
	@Value("${file.dir}") // application.yml 등에 설정된 경로
	private String fileDir;
	
	private final PdsService pdsService;//서비스 주입
/*	
	//전체(검색 X, 페이지 X)
	@GetMapping("pds_list")
	public String pdsList(@ModelAttribute("page") int page, Model model) {
		log.info("Pds Call : pds_list");
		model.addAttribute("pList", pdsService.pdsList());
		model.addAttribute("totcount", pdsService.pdsCount());
		
		return "/Pds/pds_list";
	}
*/
	/*
	//전체(검색 X, 페이지 O)
	@GetMapping("pds_list")
	public String pdsList(Model model, @PageableDefault(size=10) Pageable pageable) {
		log.info("Pds Call : pds_list");
		model.addAttribute("pList", pdsService.pdsList(pageable));
		model.addAttribute("totcount", pdsService.pdsCount());
		
		return "/Pds/pds_list";
	}

	
	//검색
	@PostMapping("pds_list")
	public String pdsListSearch(@RequestParam("search") String search, 
									@RequestParam("key") String key, Model model) {
		log.info("Pds Call : pds_list_search");
		
		model.addAttribute("pList", pdsService.pdsListSearch(search, key));
		model.addAttribute("totcount", pdsService.pdsCountSearch(search, key));
		
		model.addAttribute("search", search);
		model.addAttribute("key", key);
		return "/Pds/pds_list";
	}
*/
	//검색 + 페이지 처리 + get+post
	@GetMapping("pds_list")
	public String pdsListSearchPage(@ModelAttribute("page") int page, @RequestParam(value="search", required=false) String search, 
										@RequestParam(value="key", required=false) String key, 
											@PageableDefault(size=10) Pageable pageable, Model model) {

		Page<PdsResponseDTO> result = pdsService.pdsListSearchPage(search, key, pageable);
		model.addAttribute("pList", result);
		model.addAttribute("search", search);
		model.addAttribute("key", key);
		
		return "/Pds/pds_list";
	}
	
	//등록 폼
	@GetMapping("pds_write")
	//public String pdsWrite(@ModelAttribute("page") int page) {
	public String pdsWrite(@RequestParam("page") int page, Model model) {
		log.info("Pds Call : pds_write : " + page);
		model.addAttribute("page", page);
		return "/Pds/pds_write";
		//return "/Pds/pds_write?page=" + page; // 불가함
	}
	
	@PostMapping("pds_write")
	public String pdsWritePro(@RequestParam("page") int page, MultipartHttpServletRequest request, Model model) {
		log.info("Pds Call : pds_write_pro : ");
		
		PdsRequestDTO pdsDTO = new PdsRequestDTO();
		pdsDTO.setName(request.getParameter("name"));
		pdsDTO.setSubject(request.getParameter("subject"));
		pdsDTO.setContents(request.getParameter("contents"));
		pdsDTO.setPass(request.getParameter("pass"));
		
		//파일 저장경로 설정
		//String path = request.getServletContext().getRealPath("/upload/");
		MultipartFile mf = request.getFile("filename");
		String fileName = mf.getOriginalFilename();

		if(fileName == null || fileName.equals("")) {
			fileName = null;//첨부파일 선택 안함 -> 파일 저장 안하고 filename은 null
		}else {
			File file = new File(fileDir+fileName);
			try {
				mf.transferTo(file);//파일저장(로컬 폴더 C:/Users/websnet/upload/xxxx.xxx 저장)
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		pdsDTO.setFilename(fileName);//db에 파일 이름만 저장
		int row = pdsService.pdsWrite(pdsDTO);
		//return "redirect:pds_list?page=1";// 경고없이 리스트로 이동(controller)
		return "redirect:pds_list?page=" + page;// 경고없이 리스트로 이동(controller)
/*
		model.addAttribute("row", row);
		return "/Pds/pds_write_pro";
*/		
	}
	
	//글 상세보기
	@GetMapping("pds_view")
	public String pdsView(@RequestParam("idx") int idx, 
						@ModelAttribute("page") int page, Model model) {
		
		log.info("Pds Call : pds_view : ");
		
		PdsResponseDTO pds = pdsService.pdsView(idx);
		model.addAttribute("pds", pds);
		model.addAttribute("newLineChar", "\n");//글 상세보기에서 <br> 처리용
		
		return "/Pds/pds_view";
	}

	// 기존 업로드와 동일한 경로를 얻기 위해 request 주입
	// 파일다운로드
	@GetMapping("down_load")
	public ResponseEntity<Resource> downloadFile( 
			  @RequestParam("filename") String filename,
                                              HttpServletRequest request) {        
        try {
            // 1. 업로드 때와 동일한 서블릿 컨텍스트 상의 실제 물리 경로 획득
        		//@Value("${file.dir}") // application.yml 등에 설정된 경로
        		//private String fileDir;
           
            // 2. 보안을 위해 상위 디렉토리 접근 차단(.normalize()) 및 경로 병합
            Path base = Paths.get(fileDir).toAbsolutePath().normalize();
            Path path = base.resolve(filename).normalize();
            //normalize() 후에도 업로드 폴더 밖이면(예: ../../) 차단
            if (!path.startsWith(base)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 파일 경로입니다");
            }
            Resource resource = new UrlResource(path.toUri());

            // 3. 파일 존재 및 읽기 가능 여부 체크
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다: " + filename);
            }

            // 4. 한글 파일명 깨짐 방지 인코딩
            String encodedFilename = UriUtils.encode(filename, StandardCharsets.UTF_8);
            
            // 5. 다운로드 창을 띄우기 위한 Content-Disposition 설정
            String contentDisposition = "attachment; filename=\"" + encodedFilename + "\"";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 경로 오류가 발생했습니다.");
        }
    }	

	//삭제 폼
	@GetMapping("pds_delete")
	public String pdsDelete(@ModelAttribute("page") int page, @ModelAttribute("idx") int idx) {
		log.info("Call  :  pds_delete (삭제 폼)" );
		return "/Pds/pds_delete";
	}
	
	//삭제처리
	@PostMapping("pds_delete")
	public String pdsDeletePro(@RequestParam("idx") int idx, @RequestParam("pass") String pass, 
			@ModelAttribute("page") int page, Model model) {
		log.info("Call  :  pds_delete (삭제 처리)" );
		
		PdsResponseDTO pdsResponseDTO = pdsService.pdsModify(idx);//조회수 증가 없이 조회

		int row = pdsService.pdsDelete(idx, pass);
		//비밀번호가 맞아서 글이 삭제된 경우에만 첨부파일 삭제
		if(row == 1 && pdsResponseDTO.getFilename() != null) {
			File file = new File(fileDir + pdsResponseDTO.getFilename());
			file.delete();
		}
		model.addAttribute("row", row);
		
		return "/Pds/pds_delete_pro";
	}
	
	//수정 폼
	//삭제 폼
	@GetMapping("pds_modify")
	public String pdsModify(@ModelAttribute("page") int page, 
			@ModelAttribute("idx") int idx, Model model) {
		log.info("Call  :  pds_modify (수정 폼)" );
		
		model.addAttribute("pds", pdsService.pdsView(idx));
		return "/Pds/pds_modify";
	}

	//수정처리
	@PostMapping("pds_modify")
	public String pdsModifyPro(@RequestParam("page") int page, MultipartHttpServletRequest request, Model model) {
		log.info("Pds Call : pds_modify_pro : ");
		
		PdsRequestDTO pdsDTO = new PdsRequestDTO();
		pdsDTO.setName(request.getParameter("name"));
		pdsDTO.setSubject(request.getParameter("subject"));
		pdsDTO.setContents(request.getParameter("contents"));
		pdsDTO.setPass(request.getParameter("pass"));
		String oldfilename = request.getParameter("oldfilename");//구파일
		int idx = Integer.parseInt(request.getParameter("idx"));//기본키
		
		//파일 저장경로 설정
		//String path = request.getServletContext().getRealPath("/upload/");
		MultipartFile mf = request.getFile("filename");
		String fileName = mf.getOriginalFilename();
		
		if(oldfilename != null && oldfilename.equals("")) {
			oldfilename = null;//기존 첨부파일 없음
		}

		if(fileName.equals("")) {
			pdsDTO.setFilename(oldfilename);
		}else {
			File newFile=new File(fileDir+fileName);
			try {
				mf.transferTo(newFile);
			}catch(Exception e) {
				e.printStackTrace();
			}
			pdsDTO.setFilename(fileName);
		}

		int row = pdsService.pdsModify(idx, pdsDTO);
		//수정성공시 1 / 실패시 0
		//구파일 삭제는 수정 성공(비번 일치)후에만, 비번이 틀리면 방금 올린 새 파일을 삭제
		if(!fileName.equals("") && !fileName.equals(oldfilename)) {
			String delName = (row == 1) ? oldfilename : fileName;
			if(delName != null) {
				new File(fileDir+delName).delete();// 파일삭제
			}
		}
		model.addAttribute("row", row);
		model.addAttribute("page", page);
		return "/Pds/pds_modify_pro";
	}
	
	
	
}
