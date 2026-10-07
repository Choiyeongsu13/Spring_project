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
@Table(name="tbl_board")
@NoArgsConstructor
@Getter
public class BoardEntity {
	
		@Id
	   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_board_seq_idx_GENERATOR")
	   @SequenceGenerator(name="tbl_board_seq_idx_GENERATOR", sequenceName = "tbl_board_seq_idx", initialValue=1, allocationSize=1)
		private int idx;
		
		private String pass;
		private String name;
		private LocalDateTime regdate = LocalDateTime.now(); //날짜시간
		private String subject;
		private String contents;
		private Integer readcnt = 0; //조회수 (기존 글 중 null인 행이 있어서 Integer, 새 글은 0으로 저장)
		private LocalDateTime updatedate;
		
		
		@Builder
		public BoardEntity(String name,String pass, String subject,String contents) {
			this.name=name;
			this.pass=pass;
			this.subject=subject;
			this.contents=contents;
		}
		
		//수정 메소드(비번과 상관없이 수정할 경우)
		public void boardUpdate(String subject, String contents) {
			this.subject=subject;
			this.contents=contents;
			this.updatedate=LocalDateTime.now();
		}
}
