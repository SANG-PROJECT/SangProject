package com.green.sang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.sang.dto.Academy;

@Mapper
public interface AcademyMapper {

//	강의 전체 리스트
	List<Academy> academy_list();

}
