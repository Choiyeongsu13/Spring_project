package com.mnu.sample.dto;

import java.time.LocalDateTime;

import com.mnu.sample.entity.BoardEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardResponseDTO {
	private int idx;
	
	private String pass;
	private String name;
	private LocalDateTime regdate;
	private String subject;
	private String contents;
	private int readcnt;
	private LocalDateTime updatedate;
	
	//entity -> dto
	public BoardResponseDTO(BoardEntity entity) {
		this.idx=entity.getIdx();
		this.pass=entity.getPass();
		this.name=entity.getName();
		this.subject=entity.getSubject();
		this.contents=entity.getContents();
		this.readcnt=(entity.getReadcnt() == null) ? 0 : entity.getReadcnt(); //null이면 0
		this.regdate=entity.getRegdate();
		this.updatedate=entity.getUpdatedate();
	}
}
