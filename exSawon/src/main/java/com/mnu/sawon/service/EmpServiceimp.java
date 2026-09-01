package com.mnu.sawon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sawon.mappers.emp_mappers;

@Service
public class EmpServiceimp implements EmpService {

	@Autowired
	private emp_mappers mapper;
	
	@Override
	public int EmpCount() {
		// TODO Auto-generated method stub
		return mapper.EmpCount();
	}
	
	@Override
	public int EmpCount_Dno(int dno) {
		// TODO Auto-generated method stub
		return 0;
	}

}
