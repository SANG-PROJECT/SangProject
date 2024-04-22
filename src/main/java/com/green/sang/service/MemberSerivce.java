package com.green.sang.service;

import java.util.List;
import java.util.Map;

import com.green.sang.dto.Academy;
import com.green.sang.dto.Buy;
import com.green.sang.dto.Cancle;
import com.green.sang.dto.Cart;
import com.green.sang.dto.Member;

public interface MemberSerivce {

	Member select(String id); // 아이디로 초회

	Member selectEmail(String email); // 이메일로 아이디찾기 

	Member select_PW_Email(String email , String id); // 비밀번호 이메일전송

	int insert(Member member);

	void updatePass(Member member2);

	List<Member> selectNameToTel(Map<String, Object> map);

	int update(Member member);

	int delete(String id);

	List<Buy> selectBuyList(String id);

	Academy selectBuyInfo(int a_no);

	Buy selectBuy(int b_no);

	Academy selectImg(Integer b_no);

	int select_Max_CaNo();

	int insertCancle(Cancle cancle);

	void updateCancle(int b_no);

	int updateCancle_Stop(int b_no);

	List<Cart> selectWishList(String id);



}
