package com.mnu.sample.domain;

import lombok.Data;

@Data
public class NoticeDTO {
	private int idx;
	private String adminid;
	private String subject;
	private String contents;
	private String regdate;
	private String readcnt;
	

}
