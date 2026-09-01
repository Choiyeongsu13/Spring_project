package com.mnu.myBatis.dbtest;


import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class datasourcetest {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(datasourcetest.class);

	@Autowired // 자동 주입
	private SqlSessionFactory sqlSessionFactory;


	@Test
	public void udTest() throws Exception{
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		log.info("SqlSession : " + session);
		log.info("Connection : " + conn);



	}

}
