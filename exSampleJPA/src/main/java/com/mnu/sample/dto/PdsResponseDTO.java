package com.mnu.sample.dto;

import java.time.LocalDateTime;

import com.mnu.sample.entity.PdsEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Getter
public class PdsResponseDTO {
	private int idx;
	private String name;
	private String pass;
	private String subject;
	private String contents;
	private String filename;
	private int readcnt;
	private LocalDateTime regdate;
	private LocalDateTime updatedate;

	//entity -> dto
	public PdsResponseDTO(PdsEntity entity) {
		this.idx=entity.getIdx();
		this.name=entity.getName();
		this.pass=entity.getPass();
		this.subject=entity.getSubject();
		this.contents=entity.getContents();
		this.filename=entity.getFilename();
		this.readcnt=entity.getReadcnt();
		this.regdate=entity.getRegdate();
		this.updatedate=entity.getUpdatedate();
	}
}
