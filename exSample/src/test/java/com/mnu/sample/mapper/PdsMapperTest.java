package com.mnu.sample.mapper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sample.controller.NoticeController;
import com.mnu.sample.domain.PageSearchDTO;

@SpringBootTest
public class PdsMapperTest {
	private static final Logger log=
			LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private PdsMapper Pdsmapper;
	
	
	@Test
	public void PdsCount() {
		log.info("총 게시글수 : " + Pdsmapper.PdsCount());
	}
	@Test
	public void PdsCountSearch() {
		String search ="name";
		String key ="최씨";
		log.info("총 검색 게시글수 : " + Pdsmapper.PdsCountSearch(search,key));
	}
//	@Test
//	public void PdsList() {
//		Pdsmapper.PdsList().forEach(Pds->log.info(Pds.toString()));
//	
//	}
//	@Test
//	public void PdsListSearch() {
//		String name = "name";
//		String key= "최";
//		Pdsmapper.PdsListSearch(name,key).forEach(Pds->log.info(Pds.toString()));
//		
//	}
//	@Test
//	public void PdsListPageTest() {
//		PageSearchDTO dto = new  PageSearchDTO();
//		dto.setOffset(0);
//		dto.setMaxlist(10);
//		
//		Pdsmapper.PdsListPage(dto).forEach(Pds->log.info(Pds.toString()));
//	
//	}
//	@Test
//	public void PdsListSearchPageTest() {
//		PageSearchDTO dto = new PageSearchDTO();
//		dto.setOffset(0);
//		dto.setMaxlist(10);
//		dto.setSearch("name");
//		dto.setKey("최씨");
//		Pdsmapper.PdsListSearchPage(dto).forEach(Pds->log.info(Pds.toString()));
//	}
	
	

}
