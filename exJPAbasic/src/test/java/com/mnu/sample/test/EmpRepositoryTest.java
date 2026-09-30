package com.mnu.sample.test;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.dto.EmpResponseDTO;
import com.mnu.sample.entity.EmpEntity;
import com.mnu.sample.repository.EmpRepository;

@SpringBootTest
//@ActiveProfiles("test") //(application-test.yml)h2 db를 이용한 테스트 일경우
public class EmpRepositoryTest {
	//DeptReposity 주입
	@Autowired
	private EmpRepository empRepository;
	
//	@Test
//	public void insertCeptTest() {
//		EmpEntity entity = EmpEntity.builder()
//				.eno(110)
//				.ename("choi")
//				.manager(1000)
//				.hiredate(LocalDate.of(1980, 12, 17))
//				.salary(800)
//				.dno(20)
//				.build();
//		EmpEntity emp = empRepository.save(entity);
//		EmpResponseDTO resDTO = new EmpResponseDTO(entity);
//		System.out.println("등록된 번호 : " + resDTO.getEno());
//	}
//	
//	//dno 이용한 검색
//	
//	@Test
//	public void enoSearchTest() {
//		EmpEntity entity = empRepository.findById(110)
//				.orElseThrow(() -> new IllegalArgumentException("eno 없음"));
//		EmpResponseDTO resDTO = new EmpResponseDTO(entity);
//			System.out.println("검색된 부서명 : " + resDTO.getEname());
//	}
	
//	@Test
//	public void findAllTest() {
////		List<DeptEntity> dList = deptRepository.findAll(); //오름차순
//		List<EmpEntity> dList = empRepository.findAll(Sort.by(Sort.Direction.DESC,"eno")); //내림차순
//
//		for(EmpEntity entity: dList) {
//			EmpResponseDTO dto = new EmpResponseDTO(entity);
//			System.out.print(dto.getEno() + " 1");
//			System.out.print(dto.getEname() + "2 ");
//			System.out.println(dto.getJob() + "3 ");
//			System.out.println(dto.getManager() + "3 ");
//			System.out.println(dto.getHiredate() + "3 ");
//			System.out.println(dto.getSalary() + "3 ");
//			System.out.println(dto.getCommission() + "3 ");
//			System.out.println(dto.getDno() + "3 ");
//		}
//	}
	
//	//기본키를 이용한 삭제
//	@Test
//	public void delete() {
//		EmpEntity entity = empRepository.findById(100)
//			.orElseThrow(()-> new IllegalArgumentException("등록된 id 없음"));
//		empRepository.delete(entity);
////		empRepository.deleteById(100); // 바로삭제
//		findAllTest();
//	}
//	
//	@Test
//	public void findbyJobTest() {
////		List<DeptEntity> dList = deptRepository.findAll(); //오름차순
//		List<EmpEntity> dList = empRepository.findByJob("clerk"); //내림차순
//
//		for(EmpEntity entity: dList) {
//			EmpResponseDTO dto = new EmpResponseDTO(entity);
//			System.out.print(dto.getEno() + " 1");
//			System.out.print(dto.getEname() + "2 ");
//			System.out.println(dto.getJob() + "3 ");
//			System.out.println(dto.getManager() + "3 ");
//			System.out.println(dto.getHiredate() + "3 ");
//			System.out.println(dto.getSalary() + "3 ");
//			System.out.println(dto.getCommission() + "3 ");
//			System.out.println(dto.getDno() + "3 ");
//	}
//	}
	@Transactional // update/delete 쿼리는 트랜잭션 안에서만 실행 가능
	@Test	
	public void commissionTest() {
		int count = empRepository.empCommission(7499);
		System.out.println("수정된 행 수 : " + count); // 1이면 성공, 0이면 해당 사원 없음
	}
	
	
}
