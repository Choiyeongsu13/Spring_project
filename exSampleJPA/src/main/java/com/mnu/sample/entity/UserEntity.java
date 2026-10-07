package com.mnu.sample.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mnu.sample.dto.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {
	
	@Id
	private String userid;
	private String name;
	private String passwd;
	private String tel;
	private String email;
	
	@CreationTimestamp //insert시 자동 입력
	private LocalDateTime first_time = LocalDateTime.now();
	@UpdateTimestamp //업데이트 시 자동 입력
	private LocalDateTime last_time = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public UserEntity(String userid, String name, String passwd, String email, String tel) {
		this.userid=userid;
		this.name=name;
		this.passwd=passwd;
		this.email=email;
		this.tel=tel;
		this.role=Role.ROLE_USER;
	}
}
