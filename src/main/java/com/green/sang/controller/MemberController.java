package com.green.sang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.sang.service.KakaoService;
import com.green.sang.service.MemberSerivce;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	private final KakaoService kakaoService;
	
	
	@Autowired
	private MemberSerivce ms;
	
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("main")
	public void main() {
		
	}
	
	@GetMapping("joinForm")
	public String joinForm() {
		return "member/joinForm";
	}
	
	
}
