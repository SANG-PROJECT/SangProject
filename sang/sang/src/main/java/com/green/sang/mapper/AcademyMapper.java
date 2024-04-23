package com.green.sang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.sang.dto.Academy;

@Mapper
public interface AcademyMapper {

	List<Academy> list_Free();
	List<Academy> list_Hobby();
	List<Academy> list_Write();
	List<Academy> list_Book();
	List<Academy> list_Design();
	List<Academy> list_Media();
	List<Academy> list_Photo();
	Academy select(int a_no);
	List<Academy> ac_list(String id);
	String findIdByANo(int a_no);

}
