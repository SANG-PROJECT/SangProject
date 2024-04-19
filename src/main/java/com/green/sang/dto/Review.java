package com.green.sang.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Review")
public class Review {
	private int r_no;			// 리뷰 번호
	private String r_title; 	// 리뷰 제목
	private String r_image;		// 리뷰 사진
	private String r_content;	// 리뷰 내용
	private int r_score;		// 리뷰 별점
	private Date r_date;		// 등록 일자
	private String id;
	private int a_no;			// 강의 번호 (fk)
	private int ct_no;
}
