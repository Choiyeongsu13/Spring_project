package com.mnu.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.PdsDTO;
import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.PdsMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PdsService {
	@Autowired
	private PdsMapper Pdsmapper;
	
	//메소드 정의
	//1. 전체 글 카운터
	public int PdsCount() {
		return Pdsmapper.PdsCount();
	}

	//2. 검색 조건에 해당하는 글수
	public int PdsCountSearch(String search, String key) {

		return Pdsmapper.PdsCountSearch(search, key);
	}

	//3. 검색조건에 맞는 목록
	public List<PdsDTO> PdsList(){
		return Pdsmapper.PdsList();
	}
	
	//3-1. 전체목록 리스트(페이지 인덱싱)
	public List<PdsDTO> PdsListPage(PageSearchDTO pageSearchDTO){
		return Pdsmapper.pdsListPage(pageSearchDTO);
	}
	
	//4. 검색조건에 맞는 글 리스트
	public List<PdsDTO> PdsListSearch(String search, String key){
		return Pdsmapper.pdsListSearch(search, key);
	}

	//4-1. 검색조건 + 페이지 인덱싱 리스트
	public List<PdsDTO> PdsListSearchPage(PageSearchDTO pageSearchDTO){
		return Pdsmapper.pdsListSearchPage(pageSearchDTO);
	}


	//5. 글 등록
	public int PdsWrite(PdsDTO PdsDTO) {
		return Pdsmapper.PdsWrite(PdsDTO);
	}

	//6. 특정글 검색 (view, modify)
	public PdsDTO Pdsview(int idx, HttpServletRequest request, HttpServletResponse response) {
		return Pdsmapper.Pdsview(idx);
	}
	

	//7. 수정(폼)
	
	public PdsDTO PdsModify(int idx) {
		return Pdsmapper.Pdsview(idx);
	}
	//7. 수정처리(폼)
	
	public int PdsModifyPro(PdsDTO PdsDTO) {
		return Pdsmapper.PdsModifyPro(PdsDTO);
	}

	//8. 삭제처리
	public int PdsDeletePro(PdsDTO pdsDTO) {
		return Pdsmapper.PdsDeletePro(pdsDTO);
	}

}
