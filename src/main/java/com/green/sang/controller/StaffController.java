package com.green.sang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.sang.dto.PagingBean;
import com.green.sang.dto.Staff;
import com.green.sang.service.StaffService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StaffController {

	@Autowired
	private StaffService ss;
	
	//강사 소개
	@GetMapping("staff")
	public String staff(Model model, HttpSession session, @RequestParam(value = "pageNum" , defaultValue = "1") String pageNum) {
		// 페이징
		int rowPerPage = 12;
		if (pageNum == null || pageNum.equals(""))
			pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		int total = ss.getTotal(); // 20개
		int startRow = (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;

		PagingBean pb = new PagingBean(currentPage, rowPerPage, total);
		System.out.println("스타트 : " + startRow);
		System.out.println("엔드 : " + endRow);

		List<Staff> stafflist = ss.staffList(startRow , endRow);

		for (Staff staff : stafflist) {
			System.out.println("스태프명 : " + staff.getSt_name());
		}

		model.addAttribute("pb", pb);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("total", total);
		model.addAttribute("stafflist", stafflist);

		return "staff/staffList";
	}
	
	//기관소개
	@GetMapping("agency")
	public String agency() {
		
		return "staff/agency";
	}
	
	//강사 세부사항
	@GetMapping("staff_Detail")
	public String staff_Detail(Model model , Staff staff) {
		System.out.println("넘어온 번호 : " + staff.getSt_no());
		
		Staff staff2 = ss.selectStaff(staff.getSt_no());
		System.out.println("강사이름 : " + staff2.getSt_name());
		System.out.println("강사이미지 : " + staff2.getSt_image());
		
		if(staff2 != null) 
			model.addAttribute("staff" , staff2);
		
		return "staff/staffDetail";
	}
	
	

}