package com.green.sang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.sang.dto.Academy;
import com.green.sang.dto.Buy;
import com.green.sang.dto.Member;
import com.green.sang.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberSerivce{
	@Autowired
	private MemberMapper mm;

	@Override
	public Member select(String id) {
		
		return mm.select(id);
	}

	@Override
	public int insert(Member member) {

		return mm.insert(member);
	}
	
	@Override
	public Member selectEmail(String email) {
		System.out.println("메일값 : " + email);
		return mm.selectEmail(email);
	}

	@Override
	public Member select_PW_Email(String email , String id) {
		System.out.println("이메일값 : " +email);
		System.out.println("아이디값 : " + id);
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("id", id);
		return mm.select_PW_Email(map);
	}

	@Override
	public void updatePass(Member member2) {
		mm.updatePass(member2);
		
	}

	@Override
	public List<Member> selectNameToTel(Map<String, Object> map) {
		
		return mm.selectNameToTel(map);
	}

	@Override
	public int update(Member member) {
		
		return mm.update(member);	}

	@Override
	public int delete(String id) {
		
		return mm.delete(id);
	}

	@Override
	public List<Buy> selectBuyList(String id) {
		
		return mm.selectBuyList(id);
	}

	@Override
	public Academy selectBuyInfo(int a_no) {
		
		return mm.selectBuyInfo(a_no);
	}

	@Override
	public Buy selectBuy(int b_no) {
		
		return mm.selectBuy(b_no);
	}

	@Override
	public Academy selectImg(Integer b_no) {
		
		return mm.selectImg(b_no);
	}


}
