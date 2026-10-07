package com.mnu.sample.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
@Table(name="tbl_notice")
@NoArgsConstructor
@Getter
public class NoticeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_notice_seq_idx_GENERATOR")
	@SequenceGenerator(name="tbl_notice_seq_idx_GENERATOR", sequenceName = "tbl_notice_seq_idx", initialValue=1, allocationSize=1)
	private int idx;
	@Column(length = 20)   //tbl_notice.adid varchar2(20)
	private String adid;
	private String subject;
	@Column(length = 2000) //tbl_notice.contents varchar2(2000) (지정 안 하면 255로 줄어듦)
	private String contents;
	private LocalDateTime regdate = LocalDateTime.now();
	private int readcnt; //조회수 (DB number, readcnt + 1 계산하려면 숫자여야 함)
	

	@Builder
	public NoticeEntity(String subject, String contents) {
		this.subject=subject;
		this.contents=contents;
		
	}
	
	
}
