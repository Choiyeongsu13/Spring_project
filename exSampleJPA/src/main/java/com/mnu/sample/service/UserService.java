package com.mnu.sample.service;

import org.springframework.stereotype.Service;

import com.mnu.sample.dto.UserRequestDTO;
import com.mnu.sample.repository.UserRepository;
import com.mnu.sample.util.UserSHA256;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;
	

//	//id 중복검사 -1
//	public int userIdCheck1(String userid) {
//		if(userRepository.existsByUserid(userid))
//			return 1;
//		else {
//			return 0;
//		}
//	}
	
	//id 중복검사 -2
	public boolean userIdCheck2(String userid) {
		return userRepository.existsByUserid(userid);
	}
	
	//등록처리
	public int userInsert(UserRequestDTO userRequestDTO) {
		//비번 암호화
		userRequestDTO.setPasswd(UserSHA256.getSHA256(userRequestDTO.getPasswd()));
		try {
			 userRepository.save(userRequestDTO.toEntity());
				return 10;
		}catch(Exception e) {
			return 0;
		}
		
	}
	//등록처리
	
	
}
