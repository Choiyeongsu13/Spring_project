package com.mnu.exMVC.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mnu.exMVC.domain.DeptDTO;

@Controller
@RequestMapping("") //기본경로
public class ExamController {
		//로그 출력
	private static final Logger log =
			LoggerFactory.getLogger(ExamController.class);
		
	//반환타입은 기본적으로 void 아니면 String
	@GetMapping("index") //변수가 jsp와 연결됨
	public void mainIndex() {
		log.info("call : index" );
	}
	
	@GetMapping("index1") //views 폴더에서만 인식
	public void mainIndex1() {
		log.info("call : index1" );
	}
	
	
	@GetMapping("index2") //url만 의미
	public String mainIndex2() {
		log.info("call : index2" );
		return "/Exam/test"; //return이 있을땐 return값이 jsp 파일
		//경로 지정으로 특정 폴더에 있는 폴더를 지정
	}
	
	//파라미터 수집 (//넘어온값을 받음) 파라미터로 사용된 번수와 전달되는 변수가 같을 경우
	@GetMapping("ex01")
	public void ex01(String name,int idx) { //매개변수와 같아야 받음)
		log.info("name : " + name); //http://localhost:8070/ex01?name=aaa&idx=4
		log.info("idx : " + idx);
	}
	
	//파라미터 수집 (//넘어온값을 받음) 파라미터로 사용된 번수와 전달되는 변수가 다른 경우
	@GetMapping("ex02")
	public void ex02(@RequestParam("name") String na, @RequestParam("idx") int no) { //매개변수와 같아야 받음)
		log.info("name : " + na); //http://localhost:8070/ex01?name=aaa&idx=4
		log.info("idx : " + no);
	}
	
	//파라미터 자동 수집(DTO,VO)
	@GetMapping("ex03")
	public void ex03(DeptDTO dto) { //이름이 똑같은것만있어도 자동 수집
		log.info("dto : " + dto.toString()); //전체 한번에 수집
		log.info("dno : " + dto.getDno());
		log.info("dname : " + dto.getDname());
		log.info("loc : " + dto.getLoc());
	}
	
	//리스트
	@GetMapping("ex04")
	public void ex04(@RequestParam("data") ArrayList<String> list) {
		log.info("list : " + list );
		
	}
/*---------------------------------------*/
//	전달(view) -- 스프링에서 전달자(Model) =request.setAttribute
	@GetMapping("trans01")
	public String trans01(Model model) {
		model.addAttribute("name", "김씨");
			
		return "exam01";
	}
	
	@GetMapping("trans02")
	public String trans02(Model model) {
		List<String> list = new ArrayList<>();
		list.add("김학생");
		list.add("저학생");
		list.add("이학생");
		list.add("대학생");
		
		model.addAttribute("list",list);
		
		return "exam02";
	}
	
	//@ModelAttribute 전달자 /DTO 전달자 -> 전달되면 view 에서는 첫글자를 소문자로 사용
	
	@GetMapping("trans03")
	public String trans03(DeptDTO dto, int page) {
		
		
		
		return "exam03"; //dto가 exam03으로 전달됨 page는 안됌
	}
	
	@GetMapping("trans04")
	public String trans04(DeptDTO dto, @ModelAttribute("page") int page) {
		
		
		
		return "exam04"; //dto,page가 exam03으로 전달됨
	}
	
	//response.sendRedirect ==> RedirectAttribute 1회성(새로고침 의미 x)
	//ReadirectAttribute -> 1. addAttribute() : 화면에 보임(url)
	//                   -> 2. FlashAttribute() : 안보임(세션)
	
	@GetMapping("trans05")
	public String exam05(RedirectAttributes rttr) {
//		rttr.addAttribute("page",10);
		rttr.addFlashAttribute("page",10);
		
//		return "redirect:trans04?page=10";
//		return "redirect:trans04?page="+10;
		return "redirect:trans04";
		
	}
	
	//컨트롤러의 반환타입이 void, String = jsp 이동
	//VO,DTO가 반환타입인경우 주로 JSON타입의 데이터를 만들어 반환
	
	@GetMapping("trans06")
	
		public @ResponseBody DeptDTO trans06() { //json 타입
			DeptDTO dto = new DeptDTO();
			dto.setDno(10);
			dto.setDname("영업부");
			dto.setLoc("목포");
			
			return dto;
			

		}
		
	
	
	
//	@PostMapping()

}
