package com.green.sang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.sang.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReviewController {
	@Autowired
	private ReviewService rs;
	
	@GetMapping("/review/reviewMain.do")
	public String reviewMain() {
		return "/review/reviewMain";
	}
}
