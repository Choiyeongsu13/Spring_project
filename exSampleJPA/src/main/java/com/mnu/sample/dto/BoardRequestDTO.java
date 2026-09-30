package com.mnu.sample.dto;

import com.mnu.sample.entity.BoardEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter 
public class BoardRequestDTO {
	
	private String pass;
	private String name;
	private String subject;
	private String contents;
	
	//DTO에서 필요한 부분을 entity화 시킴
	public BoardEntity toEntity() {
		return BoardEntity.builder()
				.name(name)
				.pass(pass)
				.subject(subject)
				.contents(contents)
				.build();
	}
}
