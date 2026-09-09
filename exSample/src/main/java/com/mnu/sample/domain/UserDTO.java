package com.mnu.sample.domain;

import lombok.Data;

@Data
public class UserDTO {
	private String name;
	private String userid;
	private String passwd;
	private String tel;
	private String email;
	private String email2;
	private String email3;
	
	
	private String first_time;
	private String last_time;
	private String gubun; //핸드폰인증1 , 이메일인증 2

}
