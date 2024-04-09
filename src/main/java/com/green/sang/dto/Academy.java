package com.green.sang.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Academy")
public class Academy {
	private int a_no;		// 강의번호
	private String title; 	// 제목
	private int price; 		// 가격
	private String intro;	//소개
	private String tag;		// 키워드
	private String schedule;	//일정
	private String a_name; 	// 강사
	private String place; 	// 장소
	private String request;	//	접수
	private String onoff; 	// 온-오프라인 수업 구분
	private String image;	// 섬네일
	private String detail_img;	//	상세 이미지
	private String detail_txt;	// 상세 본문
	private int count; 		// 수량
	private Date a_date;	// 등록일자
	private String id;	
}
