package com.mnu.myBatis.mappertest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.myBatis.domain.DeptDTO;
import com.mnu.myBatis.mapper.emp_mapper;

@SpringBootTest
public class EmpMapper_test {
	//로그 출력용 클래스 생성

	private static final Logger log =
			LoggerFactory.getLogger(EmpMapper_test.class);

	//주입
	@Autowired
	private emp_mapper mapper;


	//1.테스트 (empcount)
//	@Test
//	public void EmpCount() {
//		log.info("사원수 : " + mapper.EmpCount());
//	}

	//2.테스트 (empcount_Dno
//	@Test
//	public void EmpCount_Dno() {
//		int count =  mapper.EmpCount_Dno(10);
//		log.info("조건 사원수 : " +  count);
//
//		
//		log.info("조건 사원수 : " +  mapper.EmpCount_Dno(10));
//
//	}
//	
//	//3.테스트 (EmpList)
//	@Test
//	public void EmpListTest() {
//		//람다식
//		mapper.EmpList().forEach(emp->log.info(emp.toString()));
//	}
//	
	//4. 테스트(EmpListDNO)
//	@Test
//	public void EmpListTest2() {
//		//람다식
//		mapper.EmpList_Dno(10).forEach(emp->log.info(emp.toString()));
//	}
	
//	@Test
//	public void EmpListTest3() {
//		log.info("특정 사원의 목록 : " + mapper.empEnoList(7782).toString());
//	}
	
//	//6. 등록
//	@Test
//	public void deptWriteTest() {
//		DeptDTO dto = new DeptDTO();
//		dto.setDno(50);
//		dto.setDname("자재부");
//		dto.setLoc("서울");
//		
//		int row = mapper.deptWrite(dto);
//		log.info("등록 결과 : " +  row);
//	}
//	7. 수정
//	@Test
//	public void deptUpdate() {
//		DeptDTO dto = new DeptDTO();
//		dto.setDname("인사부");
//		dto.setDno(50);
//		dto.setLoc("대전");
//		
//		int row =mapper.deptUpdate(dto);
//		log.info("수정 결과 : " + row);
//	}
	
	@Test
	public void deptDeleteTest() {
		log.info("삭제 결과 : "+ mapper.deptDelete(50));
	}
	
	
//	
//	@Test
//	public void EmpListTest2() {
//		List<EmpDTO> list = mapper.EmpList();
//	}
//	
	
}
