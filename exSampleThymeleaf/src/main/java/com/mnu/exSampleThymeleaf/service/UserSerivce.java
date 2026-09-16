package com.mnu.exSampleThymeleaf.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import com.mnu.exSampleThymeleaf.domain.UserDTO;
import com.mnu.exSampleThymeleaf.util.UserSHA256;
import com.mnu.exSampleThymeleaf.mapper.UserMapper;

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

	// application.yml의 spring.mail.host가 설정되어 있지 않으면 빈이 생성되지 않으므로
	// required=false로 두어 이메일 인증을 아직 설정하지 않아도 앱이 정상 기동되게 함
	@Autowired(required = false)
	private JavaMailSender mailSender;

	@Value("${spring.mail.username:}")
	private String mailFrom;



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

	//이메일 인증번호 발송
	public String sendEmail(String email) {
		String tempNum = tempRandomNumber(); // 인증번호 생성 호출
		sendPlainEmail(email, "[exSampleThymeleaf] 이메일 인증번호 안내", "인증번호 : " + tempNum);
		return tempNum;
	}

	//임의의 텍스트 메일 발송(인증번호 발송 등에서 공용으로 사용)
	//한글 제목/본문이 깨지거나 Gmail이 "555 cannot decode response"로 거부하지 않도록
	//MimeMessage + UTF-8로 명시적으로 인코딩해서 보냄
	private void sendPlainEmail(String to, String subject, String text) {
		if (mailSender == null) {
			throw new IllegalStateException("spring.mail.* 설정이 되어있지 않습니다. application.yml을 확인하세요.");
		}

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			helper.setTo(to);
			if (mailFrom != null && !mailFrom.isBlank()) {
				helper.setFrom(mailFrom);
			}
			helper.setSubject(subject);
			helper.setText(text, false);

			mailSender.send(mimeMessage);
		} catch (jakarta.mail.MessagingException e) {
			throw new org.springframework.mail.MailParseException(e);
		}
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
	
	//3.로그인
	public UserDTO userLogin(UserDTO userDTO) {
		//비밀번호 암호화
		userDTO.setPasswd(UserSHA256.getSHA256(userDTO.getPasswd()));
		
		
		return userMapper.userLogin(userDTO);
	}
	
	//4. 로그인 한 날짜업데이트
	public void userLastTimeUpdate(String userid) {
		userMapper.userLastTimeUpdate(userid);
	}

	//5. 아이디 찾기(이름+이메일 일치)
	public String findId(String name, String email) {
		return userMapper.userFindId(name, email);
	}

	//6. 비밀번호 찾기(재설정) - 아이디+이메일이 일치하면 임시 비밀번호를 발급하고 반환
	//   (실제 발송은 컨트롤러에서 mailSender 유무에 따라 처리)
	public String resetPasswd(String userid, String email) {
		int matched = userMapper.userCheckIdEmail(userid, email);
		if (matched == 0) {
			return null;
		}

		String tempPasswd = tempRandomNumber() + tempRandomNumber();
		userMapper.userUpdatePasswd(userid, UserSHA256.getSHA256(tempPasswd));

		return tempPasswd;
	}

	//임시 비밀번호를 실제로 이메일 발송(메일 서버 미설정시 IllegalStateException)
	public void sendTempPasswdEmail(String email, String tempPasswd) {
		sendPlainEmail(email, "[exSampleThymeleaf] 임시 비밀번호 안내",
				"임시 비밀번호 : " + tempPasswd + "\n로그인 후 반드시 비밀번호를 변경해주세요.");
	}

	//7. 비밀번호 변경(현재 비밀번호 확인 후 변경)
	public boolean changePasswd(String userid, String currentPasswd, String newPasswd) {
		UserDTO check = new UserDTO();
		check.setUserid(userid);
		check.setPasswd(currentPasswd);

		if (userLogin(check) == null) {
			return false;
		}

		userMapper.userUpdatePasswd(userid, UserSHA256.getSHA256(newPasswd));
		return true;
	}

}
