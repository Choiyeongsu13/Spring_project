package com.mnu.sample.exception;

//사용자 정의 예외 클래스
public class CustomException extends Exception{
	//생성자(예외 메시지를 이용한 생성)
	public CustomException(String message) {
		super(message);
	}
	
	//new CustomException("ID가 존재하지 않습니다");
	
	
	public CustomException(String message, Throwable cause) {
		super(message,cause);
	}

}
