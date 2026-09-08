package com.mnu.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.AdminNoticeMapper;

@Service
public class AdminNoticeService {
	@Autowired
	private AdminNoticeMapper noticeMapper;
	
	//1. 전체 공지사항  리스트
	public int noticeCount() {
		
		return noticeMapper.noticeCount();
		
	}
	//2. 검색조건에 맞는 공지사항  리스트
	public int noticeSearchCount(PageSearchDTO pageSearchDTO) {
		
		return noticeMapper.noticeSearchCount(pageSearchDTO);
		
	}
	//3. 공지사항 목록(검색+페이지) 겸용
	public List<NoticeDTO> noticeList(PageSearchDTO pageSearchDTO){
		
		return noticeMapper.noticeList(pageSearchDTO);
	}
	//4. idx에 해당하는 해당하는 글 목록(view, modify) 사용
	public NoticeDTO noticeSelect(int idx) {
		
		return noticeMapper.noticeSelect(idx);
	}
	//5. 공지사항 등록(write)//실패시 0 성공시 1 반환을위해 int
	public int noticeWrite(NoticeDTO noticeDTO) {
		
		return noticeMapper.noticeWrite(noticeDTO);
	}
	//6. 공지사항 삭제(delete) /관리자권한이기에 비밀번호 의미 x
	public int noticeDelete(int idx) {
		
		return noticeMapper.noticeDelete(idx);
	}
	//7. 공지사항 수정(modify) 처리
	public int noticeModify(NoticeDTO noticeDTO) {
		
		return noticeMapper.noticeModify(noticeDTO);
	}

}
