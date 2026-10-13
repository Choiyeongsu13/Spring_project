package com.mnu.sample.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//예외를 처리하고 errors.html으로 연결하는 방법
@ControllerAdvice
public class GlobalExceptionHandler {
	//CustomException 처리
	@ExceptionHandler(CustomException.class)
	public String handleCustomException(CustomException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/errorPopup";
	}



//기타 예외 처리
		@ExceptionHandler(Exception.class)
		public String handleAllException(Exception e , Model model) {
			model.addAttribute("errorMessage","시스템 오류가 발생하였습니다" + e.getMessage());
			return "error/errorPopup";
		}
		
		
}