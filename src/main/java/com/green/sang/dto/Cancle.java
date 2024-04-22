package com.green.sang.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Cancle")
public class Cancle {
	private int ca_no;
	private String ca_option;
	private String ca_content;
	private int b_no;
}
