package com.mnu.exSampleThymeleaf.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnu.exSampleThymeleaf.domain.UserDTO;
import com.mnu.exSampleThymeleaf.service.UserSerivce;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("User")
public class UserController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserSerivce userSerivce;

	//로그인 폼
	@GetMapping("user_login")
	public String userLogin() {
		log.info("User Call : user_login");
		return "User/user_login";
	}

	//로그인 처리
	@PostMapping("user_login")
	public String userLoginPro(UserDTO userDTO, HttpSession session, Model model) {
		log.info("User Call : user_login_pro");

		UserDTO user = userSerivce.userLogin(userDTO);

		if (user == null) {
			model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "User/user_login";
		}

		session.setAttribute("user", user);
		userSerivce.userLastTimeUpdate(user.getUserid());

		return "redirect:/";
	}

	//로그 아웃
	@GetMapping("user_logout")
	public String userLogout(HttpSession session) {
		log.info("User Call : user_logout");

		session.invalidate();

		return "redirect:/";
	}

	//회원가입 폼
	@GetMapping("user_insert")
	public String userInsert() {
		log.info("User Call : userInsert");

		return "User/user_insert";
	}

	//회원가입 처리
	@PostMapping("user_insert")
	public String userInsertPro(UserDTO userDTO, Model model) {
		log.info("User Call : userInsertPro");

		if (userDTO.getUserid() == null || userDTO.getUserid().isBlank()
				|| userDTO.getPasswd() == null || userDTO.getPasswd().isBlank()
				|| userDTO.getName() == null || userDTO.getName().isBlank()) {
			model.addAttribute("error", "필수 항목을 모두 입력해주세요.");
			return "User/user_insert";
		}

		if (userSerivce.userIdCheck(userDTO.getUserid()) > 0) {
			model.addAttribute("error", "이미 사용중인 아이디입니다.");
			return "User/user_insert";
		}

		userSerivce.userWrite(userDTO);

		return "redirect:/User/user_login";
	}

	//마이페이지
	@GetMapping("user_mypage")
	public String userMyPage(HttpSession session, Model model) {
		log.info("User Call : userMyPage");

		UserDTO user = (UserDTO) session.getAttribute("user");
		if (user == null) {
			return "redirect:/User/user_login";
		}

		model.addAttribute("user", user);

		return "User/user_mypage";
	}

	//비밀번호 변경
	@PostMapping("change_password_ajax")
	@ResponseBody
	public Map<String, Object> changePasswordAjax(@RequestParam("currentPasswd") String currentPasswd,
			@RequestParam("newPasswd") String newPasswd, HttpSession session) {
		Map<String, Object> result = new HashMap<>();

		UserDTO user = (UserDTO) session.getAttribute("user");
		if (user == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다.");
			return result;
		}

		boolean changed = userSerivce.changePasswd(user.getUserid(), currentPasswd, newPasswd);
		result.put("success", changed);
		result.put("message", changed ? "비밀번호가 변경되었습니다." : "현재 비밀번호가 일치하지 않습니다.");

		return result;
	}

	//아이디 찾기
	@PostMapping("find_id_action")
	@ResponseBody
	public Map<String, Object> findIdAction(@RequestParam("userName") String userName,
			@RequestParam("userEmail") String userEmail) {
		Map<String, Object> result = new HashMap<>();

		String userid = userSerivce.findId(userName, userEmail);
		result.put("success", userid != null);
		if (userid != null) {
			result.put("userid", userid);
		}

		return result;
	}

	//비밀번호 찾기(재설정)
	@PostMapping("find_pw_action")
	@ResponseBody
	public Map<String, Object> findPwAction(@RequestParam("userid") String userid,
			@RequestParam("userEmail") String userEmail) {
		Map<String, Object> result = new HashMap<>();

		String tempPasswd = userSerivce.resetPasswd(userid, userEmail);
		if (tempPasswd == null) {
			result.put("success", false);
			return result;
		}

		try {
			userSerivce.sendTempPasswdEmail(userEmail, tempPasswd);
			result.put("success", true);
			result.put("message", "입력하신 이메일로 임시 비밀번호를 발송했습니다.");
		} catch (IllegalStateException e) {
			// 메일 서버가 아직 설정되지 않은 개발 환경 - 화면에 바로 노출
			result.put("success", true);
			result.put("message", "메일 설정이 되어있지 않아 임시 비밀번호를 여기에 표시합니다 : " + tempPasswd);
		} catch (MailException e) {
			log.warn("임시 비밀번호 메일 발송 실패", e);
			// 비밀번호는 이미 재설정되었으므로, 발송 실패 시에도 화면에 노출해 로그인 가능하게 함
			result.put("success", true);
			result.put("message", "메일 발송에 실패해 임시 비밀번호를 여기에 표시합니다 : " + tempPasswd);
		}

		return result;
	}

	//휴대폰 인증번호 발송
	@PostMapping("send_sms")
	@ResponseBody
	public Map<String, Object> sendSms(@RequestParam("tel") String tel, HttpSession session) {
		Map<String, Object> result = new HashMap<>();

		String code = userSerivce.sendSMS(tel);
		session.setAttribute("smsCode", code);

		result.put("success", true);
		result.put("message", "인증번호를 발송했습니다.");
		return result;
	}

	//휴대폰 인증번호 확인
	@PostMapping("verify_sms")
	@ResponseBody
	public Map<String, Object> verifySms(@RequestParam("code") String code, HttpSession session) {
		Map<String, Object> result = new HashMap<>();

		Object saved = session.getAttribute("smsCode");
		boolean matched = saved != null && saved.equals(code);
		if (matched) {
			session.setAttribute("phoneVerified", true);
		}

		result.put("success", matched);
		result.put("message", matched ? "인증되었습니다." : "인증번호가 일치하지 않습니다.");
		return result;
	}

	//이메일 인증번호 발송
	@PostMapping("send_email")
	@ResponseBody
	public Map<String, Object> sendEmailCode(@RequestParam("email") String email, HttpSession session) {
		Map<String, Object> result = new HashMap<>();

		try {
			String code = userSerivce.sendEmail(email);
			session.setAttribute("emailCode", code);
			result.put("success", true);
			result.put("message", "인증번호를 발송했습니다.");
		} catch (IllegalStateException e) {
			result.put("success", false);
			result.put("message", "메일 설정이 되어있지 않습니다. 관리자에게 문의하세요.");
		} catch (MailException e) {
			log.warn("이메일 발송 실패", e);
			result.put("success", false);
			result.put("message", "메일 발송에 실패했습니다. 메일 서버 설정(계정/비밀번호)을 확인해주세요.");
		}

		return result;
	}

	//이메일 인증번호 확인
	@PostMapping("verify_email")
	@ResponseBody
	public Map<String, Object> verifyEmail(@RequestParam("code") String code, HttpSession session) {
		Map<String, Object> result = new HashMap<>();

		Object saved = session.getAttribute("emailCode");
		boolean matched = saved != null && saved.equals(code);
		if (matched) {
			session.setAttribute("emailVerified", true);
		}

		result.put("success", matched);
		result.put("message", matched ? "인증되었습니다." : "인증번호가 일치하지 않습니다.");
		return result;
	}

}
