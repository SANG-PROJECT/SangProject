package com.green.sang.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.sang.dto.Academy;
import com.green.sang.service.AcademyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AcademyController {
	@Autowired
	private AcademyService as;
	@GetMapping("/test")
	public void test() {}
	@GetMapping("/")
	public String main() {
		return "sang_main";
	}
	@GetMapping("/academy/academyList_Free")
	public String academyList_Free(HttpSession session, Model model) {
		List<Academy> list_Free = as.list_Free();
		long kk = new GregorianCalendar().getTimeInMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		for(Academy ad : list_Free) {
			long cha = kk - ad.getA_date().getTime();
			date = sdf.format(cha);
			int year = Integer.parseInt(date.substring(0,4));
			int month = Integer.parseInt(date.substring(5,7));
			int day = Integer.parseInt(date.substring(8,10));
			if (year == 1970 && month == 1 && day <= 2)
				ad.setShow("y");
		}
		model.addAttribute("list_Free",list_Free);
		return "academy/academyList_Free";
	}
	@GetMapping("/academy/academyList_Hobby")
	public String academyList_Hobby(HttpSession session, Model model) {
		List<Academy> list_Hobby = as.list_Hobby();
		long kk = new GregorianCalendar().getTimeInMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		for(Academy ad : list_Hobby) {
			long cha = kk - ad.getA_date().getTime();
			date = sdf.format(cha);
			int year = Integer.parseInt(date.substring(0,4));
			int month = Integer.parseInt(date.substring(5,7));
			int day = Integer.parseInt(date.substring(8,10));
			if (year == 1970 && month == 1 && day <= 2)
				ad.setShow("y");
		}
		model.addAttribute("list_Hobby",list_Hobby);
		return "academy/academyList_Hobby";
	}
	@GetMapping("/academy/academyList_Write")
	public String academyList_Write(HttpSession session, Model model) {
		List<Academy> list_Write = as.list_Write();
		long kk = new GregorianCalendar().getTimeInMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		for(Academy ad : list_Write) {
			long cha = kk - ad.getA_date().getTime();
			date = sdf.format(cha);
			int year = Integer.parseInt(date.substring(0,4));
			int month = Integer.parseInt(date.substring(5,7));
			int day = Integer.parseInt(date.substring(8,10));
			if (year == 1970 && month == 1 && day <= 2)
				ad.setShow("y");
		}
		model.addAttribute("list_Write",list_Write);
		return "academy/academyList_Write";
	}
	@GetMapping("/academy/academyList_Book")
	public String academyList_Book(HttpSession session, Model model) {
		List<Academy> list_Book = as.list_Book();
		long kk = new GregorianCalendar().getTimeInMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		for(Academy ad : list_Book) {
			long cha = kk - ad.getA_date().getTime();
			date = sdf.format(cha);
			int year = Integer.parseInt(date.substring(0,4));
			int month = Integer.parseInt(date.substring(5,7));
			int day = Integer.parseInt(date.substring(8,10));
			if (year == 1970 && month == 1 && day <= 2)
				ad.setShow("y");
		}
		model.addAttribute("list_Book",list_Book);
		return "academy/academyList_Book";
	}
	@GetMapping("/academy/academyList_Design")
	public String academyList_Design(HttpSession session, Model model) {
		List<Academy> list_Design = as.list_Design();
		long kk = new GregorianCalendar().getTimeInMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		for(Academy ad : list_Design) {
			long cha = kk - ad.getA_date().getTime();
			date = sdf.format(cha);
			int year = Integer.parseInt(date.substring(0,4));
			int month = Integer.parseInt(date.substring(5,7));
			int day = Integer.parseInt(date.substring(8,10));
			if (year == 1970 && month == 1 && day <= 2)
				ad.setShow("y");
		}
		model.addAttribute("list_Design",list_Design);
		return "academy/academyList_Design";
	}
	@GetMapping("/academy/academyList_Media")
	public String academyList_Media(HttpSession session, Model model) {
		List<Academy> list_Media = as.list_Media();
		long kk = new GregorianCalendar().getTimeInMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		for(Academy ad : list_Media) {
			long cha = kk - ad.getA_date().getTime();
			date = sdf.format(cha);
			int year = Integer.parseInt(date.substring(0,4));
			int month = Integer.parseInt(date.substring(5,7));
			int day = Integer.parseInt(date.substring(8,10));
			if (year == 1970 && month == 1 && day <= 2)
				ad.setShow("y");
		}
		model.addAttribute("list_Media",list_Media);
		return "academy/academyList_Media";
	}
	@GetMapping("/academy/academyList_Photo")
	public String academyList_Photo(HttpSession session, Model model) {
		List<Academy> list_Photo = as.list_Photo();
		long kk = new GregorianCalendar().getTimeInMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		for(Academy ad : list_Photo) {
			long cha = kk - ad.getA_date().getTime();
			date = sdf.format(cha);
			int year = Integer.parseInt(date.substring(0,4));
			int month = Integer.parseInt(date.substring(5,7));
			int day = Integer.parseInt(date.substring(8,10));
			if (year == 1970 && month == 1 && day <= 2)
				ad.setShow("y");
		}
		model.addAttribute("list_Photo",list_Photo);
		return "academy/academyList_Photo";
	}

	@GetMapping("/academy/academyDetail_Free")
	public void academyDetail_Free(@RequestParam("a_no") int a_no, Model model) {
		Academy ac = as.select(a_no);
		String id = as.findIdByANo(a_no);
		List<Academy> ac_list = as.ac_list(id);
		model.addAttribute("ac",ac);
		model.addAttribute("ac_list",ac_list);
	}
}
