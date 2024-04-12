package com.green.sang.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import com.green.sang.dto.Member;
import com.green.sang.service.KakaoService;
import com.green.sang.service.MemberSerivce;

import jakarta.mail.Multipart;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	@Autowired
	private final KakaoService kakaoService;
	
	@Autowired
	private MemberSerivce ms;
	
	@Autowired
	private BCryptPasswordEncoder bpe;
	
	@GetMapping("main")
	public String main() {
		
		return "member/main";
	}
	
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@PostMapping("join")
	public String join(Model model , Member member , MultipartHttpServletRequest mhr , HttpSession session) throws IOException {
		
		int result = 0;
		System.out.println("id : " + member.getId());
		System.out.println("password : " + member.getPassword());
		System.out.println("name : " + member.getName());
		System.out.println("tel : " + member.getTel());
		System.out.println("email : " + member.getEmail());
		System.out.println("image : " + member.getImage());
		System.out.println("file : " + member.getFile());
		
		Member member2 = ms.select(member.getId());
		
		if(member2 == null) {
			String originalFileName = member.getFile().getOriginalFilename(); // 업로드용 file에담긴 file명을 가져온다.
			System.out.println("originalFileName : " + originalFileName);
			UUID uuid = UUID.randomUUID(); // 랜덤한 문자열을 가져온다
			String fileName = uuid+originalFileName.substring(originalFileName.lastIndexOf(".")); // 랜덤문자 + 파일명 , 마지막.기준 잘라내기
			System.out.println("fileName : " +fileName);
			member.setImage(fileName); // 파일명을 image 컬럼으로 저장
			String imgSaveUrl = "src/main/resources/static/upload"; // 이미지 파일이 저장될 경로
			FileOutputStream fos = new FileOutputStream(new File(imgSaveUrl+"/"+fileName));
			System.out.println("fos : " + fos);
			fos.write(member.getFile().getBytes()); // 저장용 file dto에 저장된 이미지의 크기
			fos.close();
			
			String encPass = bpe.encode(member.getPassword()); //저장될 암호를 암호화
			member.setPassword(encPass); // dto에 암호화 암호를 저장
			
			result = ms.insert(member);
		}else result = -1;
		
		model.addAttribute("result",result);
		model.addAttribute("name",member.getName());
		
		return "member/join";
	}
	
	@PostMapping("login")
	public String login(Model model , Member member , HttpSession session) {
		int result = 0;
		System.out.println("id : " + member.getId());
		System.out.println("pass : " + member.getPassword());
		Member member2 = ms.select(member.getId());
		
		if(member2 == null || member2.getDel().equals("y")) result = -1;
		else if(bpe.matches(member.getPassword(), member2.getPassword())) {
			result = 1;
			session.setAttribute("id",member.getId());
			model.addAttribute("name",member2.getName());
		}
		
		model.addAttribute("result",result);
	
		return "member/login";
	}
	
	

}
