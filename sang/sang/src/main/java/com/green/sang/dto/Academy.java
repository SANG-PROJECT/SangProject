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
	private String inwon;	// 정원
	private String turn;	// 회차
	private String request;	//	접수
	private String onoff; 	// 온-오프라인 수업 구분
	private String image;	// 섬네일
	private String detail_img;	//	상세 이미지
	private String detail_txt;	// 상세 본문
	private int count; 		// 수량
	private Date a_date;	// 등록일자
	private String id;		// 아이디
	private int ct_no;		// 카테고리 번호(fk)
	private int view_count;	// 조회수
	private String status;	//대기접수 구분
	// show
	private String show;
	private String txt_title1;
	private String text1;
	private String text1_img;
	private String txt_title2;
	private String text2;
	private String text2_img;
	private String txt_title3;
	private String text3;
	private String text3_img;
	private String txt_title4 ;
	private String text4 ;
	private String text4_img;
	private String txt_title5 ;
	private String text5 ;
	private String text5_img;
	private String txt_title6;
	private String text6 ;
	private String text6_img ;
}
