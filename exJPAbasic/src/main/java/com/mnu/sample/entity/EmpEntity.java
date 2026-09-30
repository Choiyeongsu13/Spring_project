package com.mnu.sample.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name="emp")
@Getter
public class EmpEntity {
	@Id
	private int eno;

	private String ename;
	private String job;
	private Integer manager; // NULL 가능(사장 등) → 래퍼 타입

	private LocalDate hiredate; // DB 컬럼이 DATE 타입
	private int salary;
	private Integer commission; // NULL 가능 → 래퍼 타입
	private int dno;

	@Builder
	public EmpEntity(int eno, String ename, String job,
			LocalDate hiredate, int salary
			, Integer commission, int dno, Integer manager) {
		this.eno=eno;
		this.ename=ename;
		this.job=job;
		this.manager=manager;
		this.hiredate=hiredate;
		this.salary=salary;
		this.commission=commission;
		this.dno=dno;
	}

}
