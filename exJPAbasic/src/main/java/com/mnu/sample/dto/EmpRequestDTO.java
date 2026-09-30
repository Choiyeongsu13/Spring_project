package com.mnu.sample.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmpRequestDTO {
	private int eno;
	private String ename;
	private String job;
	private Integer manager;

	private LocalDate hiredate;
	private int salary;
	private Integer commission;
	private int dno;
}
