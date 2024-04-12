package com.green.sang.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return mm.insert(member);
	}

	@Override
	public Member selectEmail(String email) {
		// TODO Auto-generated method stub
		return mm.selectEmail(email);
	}

	@Override
	public void updatePass(Member member2) {
		mm.updatePass(member2);
		
	}

	@Override
	public List<Member> selectNameToTel(Map<String, Object> map) {
		
		return mm.selectNameToTel(map);
	}

}
