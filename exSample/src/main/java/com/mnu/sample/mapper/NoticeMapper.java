package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;

@Mapper
public interface NoticeMapper {
	//1. 전체 공지사항리스트
	public int noticeCount();
	
	//2. 검색 조건에 맞는 리스트
	public int noticeSearchCount(PageSearchDTO pageSearchDTO);
	
	//3. 공지사항 목록 (검색 페이지 겸용)
	public List<NoticeDTO> noticeList(PageSearchDTO pageSearchDTO);
	
	//4. idx에 해당하는 글 목록 (view,modify)
	public NoticeDTO noticeSelect(int idx);

}
