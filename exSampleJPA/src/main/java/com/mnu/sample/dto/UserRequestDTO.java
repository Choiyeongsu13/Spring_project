package com.mnu.sample.dto;

import com.mnu.sample.entity.UserEntity;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserRequestDTO {
//	@NotNull(message="아이디는 필수 입니다") -- null값만 허용하지 않음(""," ") <- 허용
	@NotEmpty(message="아이디는 필수 입력사항입니다") 
	private String userid;
	@NotEmpty(message="이름은 필수 입력사항입니다") 
	private String name;
	@NotEmpty(message="비밀번호는 필수 입력사항입니다") 
	private String passwd;
	private String gubun;
	private String tel;
	@Email(message="이메일 형식으로 입력하세요")
	private String email;
	
	
	@AssertTrue(message = "이메일과 전화번호 중 하나는 필수 입력값입니다")
	public boolean isEmailOrTelPresent() {
		//둘 다 비어있거나 null 인경우 false 반환하여 검즘실패 처리
		if((email == null || email.trim().isEmpty()) &&
			(tel == null || tel.trim().isEmpty())) {
			return false;
		}
		return true;
	}
	
	
	//DTO 부분에서 필요한 부분을 entity화
	public UserEntity toEntity() {
		return UserEntity.builder()
				.name(name)
				.userid(userid)
				.passwd(passwd)
				.email(email)
				.tel(tel)
				.build();		
	}
	
}
