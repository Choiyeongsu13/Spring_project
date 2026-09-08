package com.mnu.sample.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.mapper.UserMapper;
import com.mnu.sample.util.UserSHA256;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;



@Service
public class UserSerivce {
	//cool sms 값 저장
	@Value("${coolsms.apikey}")
	private String apiKey;
	@Value("${coolsms.apisecret}")
	private String apiSecret;
	
	@Value("${coolsms.fromnumber}")
	private String fromNumber;
	@Value("${coolsms.url}")
	private String url;
	
	
	@Autowired
	private UserMapper userMapper;
	
	
	
	//1.id 중복검사
	public int userIdCheck(String userid) {
		return userMapper.userIdCheck(userid);
	}
	//인증번호 생성용 메소드(4~6 자리)
	private String tempRandomNumber() {
		Random r = new Random();
		StringBuffer numStr = new StringBuffer();
		for(int i = 0; i<4; i++) {
			numStr.append(r.nextInt(10));
			
		}
		return numStr.toString();
	}
	
	
	//sms 인증번호 발송
	public String sendSMS(String phoneNumber) {
		String tempNum = tempRandomNumber(); // 인증번호 생성 호출
		DefaultMessageService messageService = 
				NurigoApp.INSTANCE.initialize(apiKey, apiSecret, url);
		Message message = new Message();
		message.setFrom(fromNumber);
		message.setTo(phoneNumber);
		message.setText("인증번호 : " + tempNum);
		
		messageService.sendOne(new SingleMessageSendingRequest(message));
		
		return tempNum;
	}
	
	//2. 회원가입
	public int userWrite(UserDTO userDTO) {

		userDTO.setPasswd(UserSHA256.getSHA256(userDTO.getPasswd()));

		//이메일 가입일 경우에만 조합 (전화번호 가입시 email은 비워둠)
		if(userDTO.getEmail()!=null && !userDTO.getEmail().isEmpty()) {
			if(userDTO.getEmail2()!=null && !userDTO.getEmail2().isEmpty()) {
				userDTO.setEmail(userDTO.getEmail() +"@"+userDTO.getEmail2());
			}else {
				userDTO.setEmail(userDTO.getEmail() +"@"+userDTO.getEmail3());
			}
		}

		return userMapper.userWrite(userDTO);
	}
	
	
		
}
