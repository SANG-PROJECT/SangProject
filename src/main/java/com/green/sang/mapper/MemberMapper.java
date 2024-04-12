package com.green.sang.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.green.sang.dto.Member;

@Mapper
public interface MemberMapper {

	Member select(String id);

	Member selectEmail(String email);

	int insert(Member member);

	void updatePass(Member member2);

	List<Member> selectNameToTel(Map<String, Object> map);


}
