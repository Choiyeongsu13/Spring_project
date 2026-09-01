package com.mnu.sawon.service;

import java.util.List;

import com.mnu.sawon.domain.DeptDTO;
import com.mnu.sawon.domain.EmpDTO;

public interface EmpService {
	//1. emp 테이블이 존재하는 총 사원수(tuple) 카운트
	public int EmpCount();
	//2. 부서번호가 xx인 사원수
	public int EmpCount_Dno(int dno); //where의 조건을 매개변수로
	//3. 전체 목록
		public List<EmpDTO> EmpList();
		//4. 특정 부서 목록
		public List<EmpDTO> EmpList_Dno(int dno);
		//5.특정 사원 정보 출력
		public EmpDTO empEnoList(int eno);
		//6. 등록 (resulttype 없음)
		public int deptWrite(DeptDTO dto);
		//7. 수정 
		public int deptUpdate(DeptDTO dto);
		//8. 삭제
		public int deptDelete(int dno);

}
