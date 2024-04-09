package com.green.sang.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Attend")
public class Attend {
	private int at_no;			// 참여 번호
	private String at_title;	// 참여 제목
	private String at_image;	// 참여 사진
	private String at_content; 	// 참여 내용
	private Date at_date;		// 등록 일자
	private String id;
}
