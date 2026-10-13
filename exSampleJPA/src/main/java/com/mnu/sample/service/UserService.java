package com.mnu.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.dto.UserRequestDTO;
import com.mnu.sample.dto.UserResponseDTO;
import com.mnu.sample.entity.UserEntity;
import com.mnu.sample.exception.CustomException;
import com.mnu.sample.repository.UserRepository;
import com.mnu.sample.util.UserSHA256;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	@Autowired
	private final UserRepository userRepository;

	//id중복 검사 - 1
	public int userIdCheck1(String userid) {
		if(userRepository.existsByUserid(userid))
			return 1;
		else
			return 0;
	}

	//id중복 검사 - 2
	public boolean userIdCheck2(String userid) {
		return userRepository.existsByUserid(userid);
	}

	//등록처리
	public int userInsert(UserRequestDTO userRequestDTO) {
		//비번 암호화
		userRequestDTO.setPasswd(UserSHA256.getSHA256(userRequestDTO.getPasswd()));
		try {
			userRepository.save(userRequestDTO.toEntity());
			return 1;
		}catch(Exception e) {
			return 0;
		}

	}

	//로그인 처리
	public UserResponseDTO userLogin(UserRequestDTO userRequestDTO){
		UserEntity userEntity = userRepository.findByUserid(userRequestDTO.getUserid())
				.orElseThrow(()->new IllegalArgumentException("id없음"));
		//id가 존재하지 않으면 예외발생

		if(!UserSHA256.getSHA256(userRequestDTO.getPasswd()).equals(userEntity.getPasswd())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
		}
		return new UserResponseDTO(userEntity);
	}
	//throw(예외발생), throws(예외처리)

}
