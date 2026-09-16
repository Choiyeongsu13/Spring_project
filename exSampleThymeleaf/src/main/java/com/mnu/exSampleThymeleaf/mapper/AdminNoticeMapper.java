package com.mnu.exSampleThymeleaf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mnu.exSampleThymeleaf.domain.NoticeDTO;
import com.mnu.exSampleThymeleaf.domain.PageSearchDTO;

@Mapper
public interface AdminNoticeMapper {

	int noticeCount();

	int noticeSearchCount(PageSearchDTO pageSearchDTO);

	List<NoticeDTO> noticeList(PageSearchDTO pageSearchDTO);

	NoticeDTO noticeSelect(@Param("idx") int idx);

	int noticeWrite(NoticeDTO noticeDTO);

	int noticeDelete(@Param("idx") int idx);

	int noticeModify(NoticeDTO noticeDTO);

}
