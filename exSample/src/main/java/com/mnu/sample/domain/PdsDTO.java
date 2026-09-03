package com.mnu.sample.domain;

import lombok.Data;

@Data
public class PdsDTO {
	private int idx; //고유번호(키)
	private String name; //작성자
	private String subject; //글 제목
	private String contents; //글 내용
	private String pass;  //작성자 비번
	private String regdate; //등록일자
	private int readcnt; //조회수
	private String email; //이메일
	private String filename; //첨부파일명
	
}
