package com.mnu.exSampleThymeleaf.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	// application.yml의 spring.mail.host가 설정되어 있지 않으면 빈이 생성되지 않으므로
	// required=false로 두어 이메일 인증을 아직 설정하지 않아도 앱이 정상 기동되게 함
	@Autowired(required = false)
	private JavaMailSender emailSender;

	// From을 명시적으로 지정하지 않으면 JavaMail이 로컬 계정명(이 PC는 한글 "컴퓨터")으로
	// 발신 주소를 자동 생성하려다 깨진 값이 되어 Gmail이 "555 Syntax error"로 거부함
	@Value("${spring.mail.username:}")
	private String mailFrom;

	// 인증 번호 6자리 생성 메서드
	public String createCode() {
		Random random = new Random();
		StringBuilder key = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			key.append(random.nextInt(10));
		}
		return key.toString();
	}

	// 회원가입 인증 메일 발송 - 사용자가 입력한 값과 비교하기 위해 인증번호를 그대로 리턴
	public String sendEmail(String toEmail) {
		String authCode = createCode();
		send(toEmail, "회원가입 인증 번호", "인증 번호는 " + authCode + " 입니다.");
		return authCode;
	}

	// 한글 제목/본문이 깨지거나 Gmail이 "555 cannot decode response"로 거부하지 않도록
	// MimeMessage + UTF-8로 명시적으로 인코딩해서 보냄
	private void send(String to, String subject, String text) {
		if (emailSender == null) {
			throw new IllegalStateException("spring.mail.* 설정이 되어있지 않습니다. application.yml을 확인하세요.");
		}

		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			helper.setTo(to);
			if (mailFrom != null && !mailFrom.isBlank()) {
				helper.setFrom(mailFrom);
			}
			helper.setSubject(subject);
			helper.setText(text, false);

			emailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
	}
}
