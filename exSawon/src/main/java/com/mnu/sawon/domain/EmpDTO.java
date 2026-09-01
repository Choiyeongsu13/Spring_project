package com.mnu.sawon.domain;

import lombok.Data;

@Data
public class EmpDTO {
	private int eno;
	private String ename;
	private String job;
	private int manager;
	private String hiredate;
	private int salary;
	private int commission;
	private int dno;

}
