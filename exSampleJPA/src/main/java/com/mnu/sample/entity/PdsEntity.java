package com.mnu.sample.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tbl_pds")
@NoArgsConstructor
@Getter
public class PdsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_pds_seq_idx_GENERATOR")
	@SequenceGenerator(name="tbl_pds_seq_idx_GENERATOR", sequenceName = "tbl_pds_seq_idx", initialValue=1, allocationSize=1)
	//MySQL일 경우
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;
	private String name;
	private String pass;
	private String subject;
	private String contents;
	private String filename;			
	//private String storedFileName;   // 서버에 저장된 파일명 (UUID 적용)
	private int readcnt;
	private LocalDateTime regdate = LocalDateTime.now();
	private LocalDateTime updatedate;
	
	@Builder
	public PdsEntity(String name, String pass, String subject, String contents, String filename) {
		this.name=name;
		this.pass=pass;
		this.subject=subject;
		this.contents=contents;
		this.filename=filename;
	}
}
