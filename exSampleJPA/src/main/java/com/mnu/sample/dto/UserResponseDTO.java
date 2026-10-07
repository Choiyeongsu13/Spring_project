package com.mnu.sample.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mnu.sample.entity.UserEntity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class UserResponseDTO {
	
	private String userid;
	private String name;
	private String passwd;
	private String tel;
	private String gubun;
	private String email;
	private Role role;
	
	public UserResponseDTO(UserEntity entity) {
		this.userid=entity.getUserid();
		this.name=entity.getName();
		this.gubun=entity.getGubun();
		this.passwd=entity.getPasswd();
		this.tel=entity.getTel();
		this.email=entity.getEmail();
		this.role=entity.getRole();
	}
}
