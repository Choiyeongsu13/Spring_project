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
	private int idx;

	private String name;
	private String email;
	private String pass;
	private String subject;
	private String contents;
	private String filename;	//사용자가 올린 원래 파일이름(화면 출력용)
	private String savename;	//서버에 저장된 파일이름(중복 방지용 UUID_원래이름)
	//파일 크기는 기존 tbl_pds에 컬럼이 없어서 저장된 파일에서 직접 읽음(PdsService)
	private LocalDateTime regdate = LocalDateTime.now();
	private Integer readcnt = 0;

	@Builder
	public PdsEntity(String name, String email, String pass, String subject, String contents,
						String filename, String savename) {
		this.name=name;
		this.email=email;
		this.pass=pass;
		this.subject=subject;
		this.contents=contents;
		this.filename=filename;
		this.savename=savename;
	}
}
