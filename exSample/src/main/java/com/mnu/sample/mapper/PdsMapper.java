package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.domain.PdsDTO;

@Mapper
public interface PdsMapper {
	//메소드 정의
	//1. 전체 글 카운터
	public int PdsCount();

	//2. 검색 조건에 해당하는 글수
	public int PdsCountSearch(String search, String key);

	//3. 검색조건에 맞는 목록(페이지 인덱싱)
	public List<PdsDTO> PdsList();

	//3-1. 전체목록 리스트(페이지 인덱싱)
	public List<PdsDTO> pdsListPage(PageSearchDTO pageSearchDTO);

	//4. 검색조건에 맞는 글 리스트
	public List<PdsDTO> pdsListSearch(String search, String key);

	//4-1. 검색조건 + 페이지 인덱싱 리스트
	public List<PdsDTO> pdsListSearchPage(PageSearchDTO pageSearchDTO);


	//4. 글 등록
	public int PdsWrite(PdsDTO PdsDTO);

	//6. 특정글 검색 (view, modify)
	public void PdsHits(int idx); //조회수
	public PdsDTO Pdsview(int idx); 

	//7. 수정처리
	public int PdsModifyPro(PdsDTO PdsDTO);

	//8. 삭제처리
	public int PdsDeletePro(PdsDTO pdsDTO);


}
