package com.mnu.sample.controller;

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

import com.mnu.sample.dto.UserRequestDTO;
import com.mnu.sample.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("User")
public class UserController {
	//로그 출력용
	private static final Logger log =
			LoggerFactory.getLogger(UserController.class);

	@Autowired
	private final UserService userService;

	//회원가입 폼
	@GetMapping("user_insert")
	public String userInsert() {
		log.info("User Call : user_insert");

		return "User/user_insert";
	}

/*
	//회원가입 폼
	@GetMapping("user_insert_valid")
	public String userInsert(Model model) {
		log.info("User Call : user_insert_valid");

		model.addAttribute("userRequestDTO", new UserRequestDTO());
		return "User/user_insert";
	}
*/
	//ID 중복검사
	@ResponseBody
	@PostMapping("user_idCheck")
	public String userIdCheck(@RequestParam("userid") String userid) {
		log.info("User Call : user_idCheck");
		boolean bool = userService.userIdCheck2(userid);
		return String.valueOf(bool);
	}
	//보인인증(핸드폰)

	//보인인증(이메일)

	//회원가입처리
	@PostMapping("user_insert")
	public String userInsertPro(UserRequestDTO userRequestDTO, Model model) {
		log.info("User Call : user_insert_pro");

		int row = userService.userInsert(userRequestDTO);
		if(row == 1) {
			return "redirect:/User/user_login";
		}
		model.addAttribute("errorMessage", "회원가입에 실패했습니다");
		return "User/user_insert";
	}

	/*
	//회원가입처리
	@PostMapping("user_insert_valid")
	public String userInsertValidPro(@Valid UserRequestDTO userRequestDTO,
						BindingResult result, Model model) {
		log.info("User Call : user_insert_valid_pro");

		if(result.hasErrors()) {
			return "User/user_insert_valid";
		}

		return "/";
	}
*/

	//로그인 폼
	@GetMapping("user_login")
	public String userLogin() {
		log.info("User Call : user_login");
		return "User/user_login";
	}
/*
	//로그인 처리
	@PostMapping("user_login")
	public String userLoginPro(@RequestParam("userid") String userid,
								@RequestParam("passwd") String passwd,
								HttpSession session, Model model) {
		log.info("User Call : user_login_pro");
		try {
			UserResponseDTO user = userService.userLogin(userid, passwd);
			session.setAttribute("user", user);
		}catch(IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "User/user_login";
		}
		return "redirect:/";
	}
*/
	//로그아웃
	@GetMapping("user_logout")
	public String userLogout(HttpSession session) {
		log.info("User Call : user_logout");
		session.invalidate();
		return "redirect:/";
	}
	
	//마이페이지
	@GetMapping("user_mypage")
	public String userMypage() {
		log.info("User Call : user_mypage");
		return "User/user_mypage";
	}
	
	
	//로그인 처리
	@PostMapping("user_login")
	public String userLoginPro(UserRequestDTO userrequestDTO,HttpServletRequest request, Model model) {
		log.info("User Call : user_login_pro");
		try {
			request.getSession().setAttribute("user", userService.userLogin(userrequestDTO));
			request.getSession().setMaxInactiveInterval(1800); //세션 유호기간
			return "redirect:/";
		}catch(IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "User/user_login";
		}
	}
}
