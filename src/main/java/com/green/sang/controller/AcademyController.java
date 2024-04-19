package com.green.sang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.green.sang.dto.Academy;
import com.green.sang.dto.Kakao;
import com.green.sang.service.AcademyService;

import jakarta.servlet.http.HttpSession;



@Controller
public class AcademyController {
	@Autowired
	private AcademyService as;
	
	@GetMapping("/main")
	public String main(HttpSession session, Model model) {
		System.out.println("어디서에러야 1");
		Object idobj = session.getAttribute("id"); // 카카오 id타입이 Long이라서 검사위해 추가
		String id = null;
		
		if(idobj instanceof Long) { // id 데이터타입이 Long이라면
			Long idLong = (Long)idobj;
			id = String.valueOf(idLong); // string 형식으로 변환하여 id에 저장
		}else if(idobj instanceof String) {
			id=(String)idobj; // id데이터타입이 String이라면 id에 그대로저장
		}
		System.out.println("id값 : " + id);
		
		List<Academy> academy_list = as.academy_list();
		
		model.addAttribute("academy_list", academy_list);
		model.addAttribute("id",id); // main에서 login,logout 버튼활성화에 사용
		return "sang_main";
	}
	

	
}