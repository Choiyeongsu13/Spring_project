package com.mnu.sample.dto;

import java.time.LocalDateTime;

import com.mnu.sample.entity.NoticeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NoticeResponseDTO {
	private int idx;
	private String adid;
	private String subject;
	private String contents;
	private LocalDateTime regdate;
	private int readcnt;
	
	
	public NoticeResponseDTO(NoticeEntity entity) {
		this.idx=entity.getIdx();
		this.adid=entity.getAdid();
		this.subject=entity.getSubject();
		this.contents=entity.getContents();
		
		this.regdate=entity.getRegdate();
		this.readcnt=entity.getReadcnt();
		
		
	}
	
	
}
