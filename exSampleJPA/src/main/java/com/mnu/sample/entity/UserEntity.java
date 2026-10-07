package com.mnu.sample.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mnu.sample.dto.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="tbl_user")
public class UserEntity {
	
	@Id
	private String userid;
	private String name;
	private String passwd;
	private String tel;
	private String email;
	private String gubun;
	
	@CreationTimestamp //insert시 자동 입력
	private LocalDateTime first_time = LocalDateTime.now();
	@UpdateTimestamp //업데이트 시 자동 입력
	private LocalDateTime last_time = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public UserEntity(String userid, String name, String passwd, String email, String tel,String gubun) {
		this.userid=userid;
		this.name=name;
		this.passwd=passwd;
		this.email=email;
		this.gubun=gubun;
		this.tel=tel;
		this.role=Role.ROLE_USER;
	}
}
