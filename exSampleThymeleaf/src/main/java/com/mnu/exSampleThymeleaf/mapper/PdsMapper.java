package com.mnu.exSampleThymeleaf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mnu.exSampleThymeleaf.domain.PageSearchDTO;
import com.mnu.exSampleThymeleaf.domain.PdsDTO;

@Mapper
public interface PdsMapper {

	List<PdsDTO> pdsTopList(@Param("num") int num);

	int PdsCount();

	int PdsCountSearch(@Param("search") String search, @Param("key") String key);

	List<PdsDTO> PdsList();

	List<PdsDTO> pdsListPage(PageSearchDTO pageSearchDTO);

	List<PdsDTO> pdsListSearch(@Param("search") String search, @Param("key") String key);

	List<PdsDTO> pdsListSearchPage(PageSearchDTO pageSearchDTO);

	int PdsWrite(PdsDTO pdsDTO);

	int PdsHits(@Param("idx") int idx);

	PdsDTO Pdsview(@Param("idx") int idx);

	int PdsModifyPro(PdsDTO pdsDTO);

	int PdsDeletePro(PdsDTO pdsDTO);

	String PdsSearchFile(@Param("idx") int idx);

}
