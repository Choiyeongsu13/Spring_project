package com.mnu.exSampleThymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnu.exSampleThymeleaf.domain.UserDTO;
import com.mnu.exSampleThymeleaf.service.EmailService;
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

	@Autowired
	private EmailService emailService;

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

	//본인인증(SMS) - 생성된 인증번호를 그대로 반환(화면에서 사용자가 입력한 값과 비교)
	@ResponseBody
	@PostMapping("user_sms")
	public String smsSend(@RequestParam("tel") String tel) {
		String tempNum = userSerivce.sendSMS(tel);

		log.info("인증번호 : " + tempNum);
		return tempNum;
	}

	//본인인증(email) - 생성된 인증번호를 그대로 반환(화면에서 사용자가 입력한 값과 비교)
	@ResponseBody
	@PostMapping("user_email")
	public String emailSend(@RequestParam("email") String email) {
		String tempNum = emailService.sendEmail(email);

		log.info("인증번호 : " + tempNum);
		return tempNum;
	}

	//userid를 이용한 이용자 찾기(존재하지 않으면 null이 그대로 응답됨)
	@ResponseBody
	@GetMapping("user_search")
	public UserDTO userSearch(@RequestParam("userid") String userid) {
		log.info("User Call : user_search userid=" + userid);

		return userSerivce.userSelect(userid);
	}

}
