package com.mnu.myBatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface testmapper {

	@Select("select sysdate from dual")
	public String getTime();
	
	public String getTime2();
	
	public int empCount();


}
