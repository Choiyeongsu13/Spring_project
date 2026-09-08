package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;

@Mapper
public interface AdminNoticeMapper {
	//1. 전체 공지사항  리스트
	public int noticeCount(); 	
	//2. 검색조건에 맞는 공지사항  리스트
	public int noticeSearchCount(PageSearchDTO pageSearchDTO);
	//3. 공지사항 목록(검색+페이지) 겸용
	public List<NoticeDTO> noticeList(PageSearchDTO pageSearchDTO);
	//4. idx에 해당하는 해당하는 글 목록(view, modify) 사용
	public NoticeDTO noticeSelect(int idx);
	//5. 공지사항 등록(write)
	public int noticeWrite(NoticeDTO noticeDTO); //실패시 0 성공시 1 반환을위해 int
	//6. 공지사항 삭제(delete)
	public int noticeDelete(int idx); //관리자권한이기에 비밀번호 의미 x
	//7. 공지사항 수정(modify) 처리
	public int noticeModify(NoticeDTO noticeDTO);
	

}
