package com.mnu.sample.domain;

import lombok.Data;

@Data
public class UserDTO {
	private String name;
	private String userid;
	private String passwd;
	private String gubun; //인증구분 (1:전화 /2:이메일)
	private String tel;
	private String email;
	private String first_time;
	private String last_time;
	private Role role; //권한

}
