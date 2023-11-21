package com.kyungiljava4.board.user.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kyungiljava4.board.user.domain.User;
import com.kyungiljava4.board.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/regist")
	public String registPage() {
		return "/basic/regist";
	}

	@PostMapping("/regist")
	public String regist(@RequestParam Map<String, String> data) throws ParseException {

	    String userId = data.get("userId");
	    String password = data.get("password");
	    String name = data.get("name");
	    String phone = data.get("phone");
	    String address = data.get("address");
	    String email = data.get("email");
	    String gitAddress = data.get("gitAddress");
	    String birthString = data.get("birth");
	    boolean gender = Boolean.parseBoolean(data.get("gender"));

	    java.util.Date birthDate = null;

	    if (birthString != null && !birthString.isEmpty()) {
	        birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthString);
	    }

	    User newUser = new User(0, userId, password, name, phone, address, email, gitAddress, gender, birthDate, null);

	    userService.regist(newUser);

	    return "redirect:/";
	}

	@PostMapping("/login")
	public String userLogin(@RequestParam Map<String, String> map, HttpSession session, Model model) {
	    User user = new User();
	    user.setUserId(map.get("userId"));
	    user.setPassword(map.get("password"));
	    user = userService.login(user);

	    if (user != null) {
	        session.setAttribute("userName", user.getName());
	        session.setAttribute("userId", user.getUserId());
	        session.setAttribute("user", user.getId());
	        return "redirect:/";
	    } else {
	        model.addAttribute("loginError", true);
	        return "redirect:/";
	    }
	}


	@PostMapping("/logout")
	public String userLogout(@RequestParam Map<String, String> map, HttpSession session) {
		session.setAttribute("userName", null);
		session.setAttribute("userId", null);
		return "redirect:/";
	}

	@GetMapping("/user/regist")
	public String regists(Model model) {
		model.addAttribute("title", "회원가입");
		model.addAttribute("path", "/user/regist");
		model.addAttribute("content", "registFragment");
		model.addAttribute("contentHead", "registFragmentHead");
		return "basic/layout";
	}

	@PostMapping("/user/regist")
	public String postRegist(@RequestParam Map<String, String> map, Model model) {
		User tempUser = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("phone"),
				 map.get("email"));
		if(map.get("address") != "") {
			tempUser.setAddress(map.get("address"));
		}
		if(map.get("git-address") != "") {
			tempUser.setGitAddress(map.get("git-address"));
		}
		if(map.get("gender") != null) {
			tempUser.setGender(Boolean.parseBoolean(map.get("gender")));
		}
		if(map.get("birth") != "") {
			tempUser.setBirth(Date.valueOf(map.get("birth")));
		}
		userService.regist(tempUser);
		
		return "redirect:/";
	}
}
