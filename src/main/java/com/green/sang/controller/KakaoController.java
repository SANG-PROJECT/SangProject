package com.green.sang.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.green.sang.dto.Academy;
import com.green.sang.dto.Kakao;
import com.green.sang.service.AcademyService;

import jakarta.servlet.http.HttpSession;


@Controller
public class KakaoController {
	@Autowired
	private AcademyService as;
	
	@GetMapping("/kakaoCallBack")
	public String callback(@RequestParam("code") String code , Model model , HttpSession session) {
		int result = 0;
	    System.out.println("code : " + code);
	    
	    // 1. 헤더 생성
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
	    
	    // 2. 바디 생성
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "authorization_code");
	    params.add("client_id", "f2136f7403cde7c52a6e8763913c69b6"); // 카카오 애플리케이션의 클라이언트 아이디
	    params.add("redirect_uri", "http://localhost/kakaoCallBack");
	    params.add("client_secret", "4cdPvm4XVTPqP3JoH0j32ybfSDCULcZe");
	    params.add("code", code);
	    
	    // 3. 헤더 + 바디
	    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
	    
	    // 4. HTTP 요청
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> response = restTemplate.exchange(
	            "https://kauth.kakao.com/oauth/token",
	            HttpMethod.POST,
	            httpEntity,
	            Object.class);
	    
	    // 5. 응답 처리
	    if (response.getStatusCode().is2xxSuccessful()) {
	        // 요청이 성공한 경우
	        Map<String, String> responseBody = (Map<String, String>) response.getBody();
	        String accessToken = responseBody.get("access_token");
	        
	        // 사용자 정보 가져오기 API 호출
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(accessToken); // Access Token을 헤더에 추가
	        
	        HttpEntity<Void> entity = new HttpEntity<>(headers);
	        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
	                "https://kapi.kakao.com/v2/user/me",
	                HttpMethod.GET,
	                entity,
	                Map.class);

	        // 사용자 정보 처리
	        if (userInfoResponse.getStatusCode().is2xxSuccessful()) {
	            Map<String, Object> userInfo = userInfoResponse.getBody();
	            Map<String, Object> properties = (Map<String,Object>) userInfo.get("properties");
	            
	            // 카카오 사용자정보 => userInfo
	            System.out.println("User info: " + userInfo);
	            System.out.println("properties : " + properties);
	            Long userID = (Long) userInfo.get("id");
	            String nickName = (String) properties.get("nickname");
	            String profile_image = (String) properties.get("profile_image");
	            String thumbnail_image = (String) properties.get("thumbnail_image");
	            
	            Kakao kakao = new Kakao();
	            kakao.setUserID(userID);
	            kakao.setNickName(nickName);
	            kakao.setProfile_image(profile_image);
	            kakao.setThumbnail_image(thumbnail_image);
	         
	            System.out.println("userID : " +userID);
	            System.out.println("nickName : " +nickName);
	            System.out.println("profile_image : " +profile_image);
	            System.out.println("thumbnail_image : " +thumbnail_image);
	           
	            session.setAttribute("id", userID); //세션에 카카오아이디 저장
	            model.addAttribute("kakao",kakao); //카카오정보 객체전달
	            result = 1;
	        } else {
	            System.out.println("사용자 정보처리에러 - info with status code: " + userInfoResponse.getStatusCode());
	            // 실패에 대한 처리를 수행합니다.
	        }
	    } else {
	        // 요청이 실패한 경우
	        System.out.println("카카오 응답에러 - code: " + response.getStatusCode());
	        result = -1;
	    }
	    
	    model.addAttribute("result",result);
	    return "member/kakaoLogin"; 
	}
	@GetMapping("kakaoLogout")
	public String kakaoLogout(HttpSession session) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer"+session.getId());
	    HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
		"https://kapi.kakao.com/v1/user/logout",
		HttpMethod.POST,
		httpEntity,
		String.class);	

		if(response.getStatusCode()==HttpStatus.OK)
			System.out.println("카카오ID세션 삭제완료");
		
		session.removeAttribute("id");
			
		return "member/logout";
		
	}
	
	@GetMapping("/kakaomain")
	public String getMethodName(HttpSession session , Model model , Kakao kakao) {
		Long kakaoid = (Long)session.getAttribute("id"); // 카카오id는 Long형태로 전달된다.
		System.out.println("카카오로그인아이디 세션 : " + kakaoid);
		System.out.println("카카오 객체 아이디 : " + kakao.getUserID());
		System.out.println("카카오 객체 네임 : " + kakao.getNickName());
		System.out.println("카카오 객체 이미지 : " + kakao.getProfile_image());
		System.out.println("카카오 객체 섬넨일 : " + kakao.getThumbnail_image());
		
		if(kakao != null) {
			model.addAttribute("kakao",kakao);
		}
		
		List<Academy> academy_list = as.academy_list();
		model.addAttribute("academy_list", academy_list);
		model.addAttribute("id",kakaoid); // main에서 login,logout 버튼활성화에 사용
		
		return "sang_main";
	}
	
	@GetMapping("kakaoMypage")
	public String mypage(Model model , HttpSession session , Kakao kakao ) {
		Long id = (Long)session.getAttribute("id");
		System.out.println("카카오 세션id : " + id);
		System.out.println(kakao.getNickName());
		System.out.println("메뉴 : " + kakao.getMenu());
		
		if(kakao.getMenu() == null ) kakao.setMenu("order"); // 디폴트 : 신청/예약조회
		
		model.addAttribute("kakao",kakao);
		model.addAttribute("id",id);
		return "member/kakaoMypage";
	}
}
