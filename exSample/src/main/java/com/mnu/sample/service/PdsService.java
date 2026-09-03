package com.mnu.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.PdsDTO;
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
	public List<PdsDTO> PdsList(PageSearchDTO pageSearchDTO){
		return Pdsmapper.PdsList(pageSearchDTO);
	}




	//5. 글 등록
	public int PdsWrite(PdsDTO PdsDTO) {
		return Pdsmapper.PdsWrite(PdsDTO);
	}

	//6. 특정글 검색 (view, modify)
	public PdsDTO Pdsview(int idx) {
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
