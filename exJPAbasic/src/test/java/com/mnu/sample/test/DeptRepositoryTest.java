package com.mnu.sample.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.mnu.sample.dto.DeptResponseDTO;
import com.mnu.sample.entity.DeptEntity;
import com.mnu.sample.repository.DeptRepository;

@SpringBootTest
//@ActiveProfiles("test") //(application-test.yml)h2 db를 이용한 테스트 일경우
public class DeptRepositoryTest {
	//DeptReposity 주입
	@Autowired
	private DeptRepository deptRepository;
	
	@Test
	public void insertCeptTest() {
		DeptEntity entity = DeptEntity.builder()
				.dno(100)
				.dname("총무과")
				.loc("목포")
				.build();
		DeptEntity dept = deptRepository.save(entity);
		DeptResponseDTO resDTO = new DeptResponseDTO(entity);
		System.out.println("등록된 총무명 : " + resDTO.getDno());
	}
	
	//dno 이용한 검색
	
	@Test
	public void dnoSearchTest() {
		DeptEntity entity = deptRepository.findById(100)
				.orElseThrow(() -> new IllegalArgumentException("dno 없음"));
		DeptResponseDTO resDTO = new DeptResponseDTO(entity);
			System.out.println("검색된 부서명 : " + resDTO.getDname());
	}
	
	@Test
	public void findAllTest() {
//		List<DeptEntity> dList = deptRepository.findAll(); //오름차순
		List<DeptEntity> dList = deptRepository.findAll(Sort.by(Sort.Direction.DESC,"dno")); //내림차순

		for(DeptEntity entity: dList) {
			DeptResponseDTO dto = new DeptResponseDTO(entity);
			System.out.print(dto.getDno() + " 1");
			System.out.print(dto.getDname() + "2 ");
			System.out.println(dto.getLoc() + "3 ");
		}
	}
	
	//기본키를 이용한 삭제
	@Test
	public void delete() {
		DeptEntity entity = deptRepository.findById(100)
			.orElseThrow(()-> new IllegalArgumentException("등록된 id 없음"));
		deptRepository.delete(entity);
//		deptRepository.deleteById(100); // 바로삭제
		findAllTest();
	}
	
	
}
