package com.green.sang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AcademyController {

	@GetMapping("/")
	public String main() {
		return "sang_main";
	}
}
