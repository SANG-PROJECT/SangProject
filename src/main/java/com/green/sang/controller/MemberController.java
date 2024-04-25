package com.green.sang.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties.Request;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import com.green.sang.dto.Academy;
import com.green.sang.dto.Buy;
import com.green.sang.dto.Cancle_Buy;
import com.green.sang.dto.Cart;
import com.green.sang.dto.Kakao;
import com.green.sang.dto.Member;
import com.green.sang.dto.QnA;
import com.green.sang.service.MemberSerivce;

import jakarta.mail.Multipart;
import jakarta.servlet.http.HttpSession;
import oracle.jdbc.proxy.annotation.Post;

@Controller
public class MemberController {

	@Autowired
	private MemberSerivce ms;

	@Autowired
	private BCryptPasswordEncoder bpe;

	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}

	@PostMapping("login")
	public String login(Model model, Member member, HttpSession session) {
		int result = 0;
		System.out.println("id : " + member.getId());
		System.out.println("pass : " + member.getPassword());
		Member member2 = ms.select(member.getId());

		if (member2 == null || member2.getDel().equals("y"))
			result = -1;
		else if (bpe.matches(member.getPassword(), member2.getPassword())) {
			session.setAttribute("id", member.getId());
			System.out.println("일반세션저장 id : " + session.getId());
			model.addAttribute("name", member2.getName());
			result = 1;
		}

		model.addAttribute("result", result);

		return "member/login"; // 컨트롤러의 main url형태로 전달한다.
	}

	@PostMapping("join")
	public String join(Model model, Member member, MultipartHttpServletRequest mhr, HttpSession session)
			throws IOException {

		int result = 0;
		System.out.println("id : " + member.getId());
		System.out.println("password : " + member.getPassword());
		System.out.println("name : " + member.getName());
		System.out.println("tel : " + member.getTel());
		System.out.println("email : " + member.getEmail());
		System.out.println("image : " + member.getImage());
		System.out.println("file : " + member.getFile());

		Member member2 = ms.select(member.getId());

		if (member2 == null) {
			if (member.getFile() == null || member.getFile().isEmpty()) {
				member.setImage("user_image.jpg"); // 이미지없이 가입할시 기본이미지
				String encPass = bpe.encode(member.getPassword()); // 저장될 암호를 암호화
				member.setPassword(encPass); // dto에 암호화 암호를 저장
			} else {
				String originalFileName = member.getFile().getOriginalFilename(); // 업로드용 file에담긴 file명을 가져온다.
				System.out.println("originalFileName : " + originalFileName);
				UUID uuid = UUID.randomUUID(); // 랜덤한 문자열을 가져온다
				String fileName = uuid + originalFileName.substring(originalFileName.lastIndexOf(".")); // 랜덤문자 + 파일명 ,
																										// 마지막.기준 잘라내기
				System.out.println("fileName : " + fileName);
				member.setImage(fileName); // 파일명을 image 컬럼으로 저장
				String imgSaveUrl = "src/main/resources/static/upload"; // 이미지 파일이 저장될 경로
				FileOutputStream fos = new FileOutputStream(new File(imgSaveUrl + "/" + fileName));
				System.out.println("fos : " + fos);
				fos.write(member.getFile().getBytes()); // 저장용 file dto에 저장된 이미지의 크기
				fos.close();

				String encPass = bpe.encode(member.getPassword()); // 저장될 암호를 암호화
				member.setPassword(encPass); // dto에 암호화 암호를 저장
			}
			result = ms.insert(member);
		} else
			result = -1;

		model.addAttribute("result", result);
		model.addAttribute("name", member.getName());

		return "member/join";
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {

		session.removeAttribute("id");

		return "member/logout";
	}

	@GetMapping("mypage")
	public String Mypage(Model model, HttpSession session, @RequestParam(value = "menu", defaultValue = "") String menu,
			@RequestParam(value = "b_no", required = false) Integer b_no) {
		Object idObject = session.getAttribute("id");
		String id = "";
		String kakaoChk = "";
		
		if(idObject instanceof Long) {
			id = String.valueOf(idObject);
			System.out.println("변환된 id = " + id);
			kakaoChk = "true";
			
		}else {
			id = (String) session.getAttribute("id");
			System.out.println("일반아이디 : " + id);	
			kakaoChk = "false";
		}
		
		if (menu == null || menu.equals("")) menu = "order";

		Member member = ms.select(id);
		System.out.println("조회된 사진 : " + member.getImage());

		List<Buy> buyList = ms.selectBuyList(id);
		List<Academy> academyList = new ArrayList<>();

		for (Buy buyItem : buyList) {
			int a_no = buyItem.getA_no();
			Academy academy2 = ms.selectBuyInfo(a_no);

			if (academy2 != null) {
				// 구매정보 아카데미 DTO 추가
				academy2.setC_count(buyItem.getC_count());
				academy2.setCancle(buyItem.getCancle());
				academy2.setB_no(buyItem.getB_no());
				academy2.setB_date(buyItem.getB_date());
				// 저장된 아카데미객체 리스트로추가
				academyList.add(academy2);
				System.out.println("승인여부 : " +academy2.getCancle() );
				// 취소완료 되지 않았으면 취소조회에 보이지않기위해 추가
				if(academy2.getCancle().equals("y")) {
					String cancleSuccess = "true";
					model.addAttribute("cancleSuccess",cancleSuccess);
				}
			
			}
		}

		// 구매취소 시 해당 구매내역의 b_no를 전달받아 처리
		if (b_no != null) {
			System.out.println("구매번호 : " + b_no);
			Buy buy = ms.selectBuy(b_no);
			System.out.println("buy에있는 구매번호 : " + buy.getB_no());
			System.out.println("가져온 a번호 : " + buy.getA_no());

			Academy academy3 = ms.selectImg(buy.getA_no());
			System.out.println("가져온 ac이미지 : " + academy3.getImage());
			System.out.println("개수 : " + buy.getC_count());
			System.out.println("가격 : " + academy3.getPrice());

			int buyTotal = buy.getC_count() * academy3.getPrice();
			System.out.println("구매금액 토탈 : " + buyTotal);

			model.addAttribute("buyItem", buy);
			model.addAttribute("academyItem", academy3);
			model.addAttribute("buyTotal", buyTotal);

		}
		
		//id로 위시리스트 조회 후 있으면 보여주기wish_list
		List<Cart> wishlist = ms.selectWishList(id);
		if(wishlist != null) {
			List<Academy> academyArr = new ArrayList<>();
			int count_Wish_No = ms.select_Max_Wish_No(id);
			System.out.println("위시리스트 Max : " + count_Wish_No);
			
			for(Cart wish : wishlist) {
				int wish_Ano = wish.getA_no();
				Academy academy = ms.selectBuyInfo(wish_Ano);
				academyArr.add(academy);
			}
			
			model.addAttribute("count_Wish_No",count_Wish_No);
			model.addAttribute("academyArr",academyArr);
		}
		
		//id로 문의내역 있으면 보여주기 QnA
		List<QnA> qnalist = ms.selectQnAList(id);
		if(qnalist != null) {
			 
			int count_QnA_No = ms.select_Max_QnA_No(id);
			model.addAttribute("count_QnA_No" , count_QnA_No); //sql에서 max넘버 +1하기때문에 -1해서 맞춘다.
			model.addAttribute("qnalist",qnalist);
		}
	

		model.addAttribute("academyList", academyList);
		model.addAttribute("buyList", buyList);
		model.addAttribute("member", member);
		model.addAttribute("id", id);
		model.addAttribute("menu", menu);
		model.addAttribute("kakaoChk",kakaoChk);
		return "member/mypage";
	}

	@GetMapping("cancleSelect")
	public String cancleSelect(Model model , Cancle_Buy Cancle_Buy , Buy buy) {
		int result = 0;
		
		if(Cancle_Buy.getCa_content() != null && Cancle_Buy.getCa_option() != null) {
		  
			int maxCancle_No = ms.select_Max_CaNo();
			System.out.println("Max 취소번호 : " + maxCancle_No);
			int b_no = buy.getB_no();
			System.out.println("B_no : " + b_no);
			
			Cancle_Buy.setCa_no(maxCancle_No);
			Cancle_Buy.setB_no(b_no);

			result = ms.insertCancle(Cancle_Buy); //cancle_buy테이블에 추가
			try {
				ms.updateCancle(b_no); // 취소요청 n => r (request 약자) 
			} catch (Exception e) {
				System.out.println("구매취소 업데이트 쿼리중 에러" + e.getMessage());
			}
		
		}else result = -1;
		 
		
		model.addAttribute("result" , result);
		return "member/cancle";
	}
	
	@GetMapping("cancle_Stop")
	public String cancle_Stop(Model model , Buy buy) {
		System.out.println("들어온 B넘버 : " + buy.getB_no());
		int result = 0;
		
		result = ms.updateCancle_Stop(buy.getB_no());
		
		model.addAttribute("result",result);
		
		return "member/updateCancle_Stop";
	}

	@PostMapping("update")
	public String update(Model model, Member member, MultipartHttpServletRequest mhr) throws IOException {
		int result = 0;
		System.out.println("닉네임 : " + member.getName());
		System.out.println("암호 : " + member.getPassword());
		System.out.println("전화번호 : " + member.getTel());
		
		Member member2 = ms.select(member.getId());

		if (member2 != null) {
			if (member.getFile() == null || member.getFile().isEmpty()) {
				member.setImage(member2.getImage()); // 이미지 수정없이 수정할시 기존이미지
				if(member.getPassword() != null) {
					String encPass = bpe.encode(member.getPassword()); // 저장될 암호를 암호화
					member.setPassword(encPass); // dto에 암호화 암호를 저장			
					
				}
			} else {
				String originalFileName = member.getFile().getOriginalFilename(); // 업로드용 file에담긴 file명을 가져온다.
				System.out.println("originalFileName : " + originalFileName);
				UUID uuid = UUID.randomUUID(); // 랜덤한 문자열을 가져온다
				String fileName = uuid + originalFileName.substring(originalFileName.lastIndexOf(".")); // 랜덤문자 + 파일명 ,
																										// 마지막.기준 잘라내기
				System.out.println("fileName : " + fileName);
				member.setImage(fileName); // 파일명을 image 컬럼으로 저장
				String imgSaveUrl = "src/main/resources/static/upload"; // 이미지 파일이 저장될 경로
				FileOutputStream fos = new FileOutputStream(new File(imgSaveUrl + "/" + fileName));
				System.out.println("fos : " + fos);
				fos.write(member.getFile().getBytes()); // 저장용 file dto에 저장된 이미지의 크기
				fos.close();

				String encPass = bpe.encode(member.getPassword()); // 저장될 암호를 암호화
				member.setPassword(encPass); // dto에 암호화 암호를 저장
			}
			
			System.out.println("닉네임 : " + member.getName());
			System.out.println("암호 : " + member.getPassword());
			System.out.println("전화번호 : " + member.getTel());
			System.out.println("아이디 : " + member.getId());
			
			result = ms.update(member);

		} else
			result = -1;

		model.addAttribute("result", result);
		return "member/update";
	}

	@GetMapping("delete")
	public String delete(Model model, Member member, HttpSession session) {
		System.out.println("어왔니 아이디 " + member.getId());
		int result = 0;

		result = ms.delete(member.getId());
		session.removeAttribute("id");

		model.addAttribute("result", result);
		return "member/delete";
	}
	
	@GetMapping("QnAWhiteForm")
	public String QnAWhiteForm(Model model , QnA qna) {
		System.out.println("넌뭔데 : " + qna);
		
		if(qna.getQ_no() != 0 ) { //문의작성시 qna안에 q_no 초기값이 0이라서 
			QnA qnaItem = ms.selectQnA(qna.getQ_no());
			System.out.println("qna2 제목 " + qnaItem.getQ_title());
			
			model.addAttribute("qnaItem",qnaItem);
		}
		
		return "member/QnAWhiteForm";
	}
	
	@GetMapping("QnAWhite")
	public String QnAWhite(Model model, QnA qna ,HttpSession session) {
		int result = 0;
		Object idObject = session.getAttribute("id");
		String id = "";
		
		if(idObject instanceof Long) {
			id = String.valueOf(idObject);
			System.out.println("변환된 id = " + id);

			
		}else {
			id = (String) session.getAttribute("id");
			System.out.println("일반아이디 : " + id);	
		}
		
		System.out.println("제목 : " + qna.getQ_title());
		System.out.println("내용 : " + qna.getQ_content());
		System.out.println("아이디 : " + id);
		
		int max_QnA_No = ms.select_Max_QnA_No(id);
		System.out.println("맥스 Qna넘버 : " + max_QnA_No);
		
		qna.setQ_no(max_QnA_No);
		qna.setId(id);
		
		result = ms.insertQnA(qna);
		
		model.addAttribute("result",result);
		
		return "member/QnAWhite";
	}
	
	@GetMapping("QnAUpdate")
	public String QnAUpdate(Model model , QnA qna) {
		int result = 0;
		System.out.println("qna : " + qna.getQ_no());
		if(qna.getQ_no() != 0) {
			System.out.println("문의번호 : " + qna.getQ_no());
			
			result = ms.QnAUpdate(qna);
			
			
		}
		model.addAttribute("result" , result);
		return "member/QnAUpdate";
	}
	
	
	@GetMapping("qnaDelete")
	public String qnaDelete(Model model, QnA qna) {
		int result = 0;
		System.out.println("문의번호 : " + qna.getQ_no());
		
		result = ms.deleteQnA(qna.getQ_no());
		
		model.addAttribute("result",result);
		
		return "member/QnADelete";
	}
	
	

}
