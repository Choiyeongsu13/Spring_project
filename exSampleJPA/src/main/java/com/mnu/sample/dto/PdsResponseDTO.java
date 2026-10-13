package com.mnu.sample.dto;

import java.time.LocalDateTime;

import com.mnu.sample.entity.PdsEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PdsResponseDTO {
	private int idx;
	private String name;
	private String email;
	private String subject;
	private String contents;
	private String filename;
	private long filesize = -1;	//-1 : 서버에 파일 없음
	private LocalDateTime regdate;
	private int readcnt;

	//entity -> dto (비밀번호, 저장파일명은 화면에 내보내지 않음)
	public PdsResponseDTO(PdsEntity entity) {
		this.idx=entity.getIdx();
		this.name=entity.getName();
		this.email=entity.getEmail();
		this.subject=entity.getSubject();
		this.contents=entity.getContents();
		this.filename=entity.getFilename();
		this.regdate=entity.getRegdate();
		this.readcnt=(entity.getReadcnt() == null) ? 0 : entity.getReadcnt();
	}

	//entity + 실제 파일 크기 (PdsService에서 파일을 확인해서 넘겨줌)
	public PdsResponseDTO(PdsEntity entity, long filesize) {
		this(entity);
		this.filesize=filesize;
	}

	//파일 크기 화면 출력용 (예: 1.2 KB)
	public String getFilesizeText() {
		if(filename == null || filesize < 0) return "";
		if(filesize < 1024) return filesize + " B";
		if(filesize < 1024 * 1024) return String.format("%.1f KB", filesize / 1024.0);
		return String.format("%.1f MB", filesize / (1024.0 * 1024));
	}
}
