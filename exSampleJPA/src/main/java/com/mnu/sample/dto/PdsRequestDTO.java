package com.mnu.sample.dto;

import com.mnu.sample.entity.PdsEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PdsRequestDTO {

	private String name;
	private String email;
	private String pass;
	private String subject;
	private String contents;

	//DTO + 업로드된 파일 정보 -> entity
	public PdsEntity toEntity(String filename, String savename) {
		return PdsEntity.builder()
				.name(name)
				.email(email)
				.pass(pass)
				.subject(subject)
				.contents(contents)
				.filename(filename)
				.savename(savename)
				.build();
	}
}
