package com.green.sang.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.green.sang.dto.Academy;
import com.green.sang.dto.Buy;
import com.green.sang.dto.Cancle;
import com.green.sang.dto.Cart;
import com.green.sang.dto.Member;

@Mapper
public interface MemberMapper {

	Member select(String id);

	Member selectEmail(String email);

	Member select_PW_Email(Map<String, Object> map);

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

	int updateCancle(int b_no);

	int updateCancle_Stop(int b_no);

	List<Cart> selectWishList(String id);



}
