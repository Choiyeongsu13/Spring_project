package com.mnu.sample.dto;

import com.mnu.sample.entity.DeptEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeptResponseDTO {
	private int dno;
	
	private String dname;
	private String loc;
	
	
	
	//DTO에서 필요한 부분을 Entity화
	//빌더패턴
	public DeptResponseDTO(DeptEntity entity) {
		this.dno=entity.getDno();
		this.dname=entity.getDname();
		this.loc=entity.getLoc();
	}
}
