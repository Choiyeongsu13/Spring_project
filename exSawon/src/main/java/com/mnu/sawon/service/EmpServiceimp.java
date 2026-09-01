package com.mnu.sawon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sawon.domain.DeptDTO;
import com.mnu.sawon.domain.EmpDTO;
import com.mnu.sawon.mappers.emp_mappers;

@Service
public class EmpServiceimp implements EmpService {

	@Autowired
	private emp_mappers mapper;
	
	//1
	@Override
	public int EmpCount() {
		// TODO Auto-generated method stub
		return mapper.EmpCount();
	}
	//2
	@Override
	public int EmpCount_Dno(int dno) {
		// TODO Auto-generated method stub
		return mapper.EmpCount_Dno(10);
		
	}
	//3
	@Override
	public List<EmpDTO> EmpList() {
		// TODO Auto-generated method stub
		return mapper.EmpList();
		}
	//4
	@Override
	public List<EmpDTO> EmpList_Dno(int dno) {
		// TODO Auto-generated method stub
		return mapper.EmpList_Dno(10);
	}
	//5
	@Override
	public int deptWrite(DeptDTO dto) {
		// TODO Auto-generated method stub
		DeptDTO dDto = new DeptDTO();
		return mapper.deptWrite(dDto);
	}
	//6
	@Override
	public int deptUpdate(DeptDTO dto) {
		// TODO Auto-generated method stub
		DeptDTO dDto = new DeptDTO();
		return mapper.deptUpdate(dDto);
	}
	//7
	@Override
	public int deptDelete(int dno) {
		// TODO Auto-generated method stub
	
		return mapper.deptDelete(50);
	}
	//8
	@Override
	public EmpDTO empEnoList(int eno) {
		// TODO Auto-generated method stub
		return mapper.empEnoList(7784);
	}

}
