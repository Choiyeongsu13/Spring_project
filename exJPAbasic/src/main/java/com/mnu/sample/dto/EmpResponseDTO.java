package com.mnu.sample.dto;

import java.time.LocalDate;

import com.mnu.sample.entity.EmpEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmpResponseDTO {
	private int eno;
	private String ename;
	private String job;
	private Integer manager;

	private LocalDate hiredate;
	private int salary;
	private Integer commission;
	private int dno;


	public EmpResponseDTO(EmpEntity entity) {
		this.eno=entity.getEno();
		this.ename=entity.getEname();
		this.job=entity.getJob();
		this.manager=entity.getManager();
		this.hiredate=entity.getHiredate();
		this.salary=entity.getSalary();
		this.commission=entity.getCommission();
		this.dno=entity.getDno();
	}
}
