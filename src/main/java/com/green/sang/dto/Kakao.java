package com.green.sang.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("kakao")
public class Kakao {
	private Long userID;
	private String nickName;
	private String profile_image;
	private String thumbnail_image;
	
	//마이페이지 구현중필요
	private String menu;
}
