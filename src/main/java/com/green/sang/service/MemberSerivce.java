package com.green.sang.service;

import java.util.List;
import java.util.Map;

import com.green.sang.dto.Member;

public interface MemberSerivce {

	Member select(String id); // 아이디로 초회

	Member selectEmail(String email); // 이메일로 아이디찾기

	int insert(Member member);

	void updatePass(Member member2);

	List<Member> selectNameToTel(Map<String, Object> map);


}
