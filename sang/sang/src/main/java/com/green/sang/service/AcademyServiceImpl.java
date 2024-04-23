package com.green.sang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.sang.dto.Academy;
import com.green.sang.mapper.AcademyMapper;

@Service
public class AcademyServiceImpl implements AcademyService{
	@Autowired
	private AcademyMapper am;

	@Override
	public List<Academy> list_Free() {
		return am.list_Free();
	}
	@Override
	public List<Academy> list_Hobby() {
		return am.list_Hobby();
	}
	@Override
	public List<Academy> list_Write() {
		return am.list_Write();
	}
	@Override
	public List<Academy> list_Book() {
		return am.list_Book();
	}
	@Override
	public List<Academy> list_Design() {
		return am.list_Design();
	}
	@Override
	public List<Academy> list_Media() {
		return am.list_Media();
	}
	@Override
	public List<Academy> list_Photo() {
		return am.list_Photo();
	}
	@Override
	public Academy select(int a_no) {
		return am.select(a_no);
	}
	@Override
	public List<Academy> ac_list(String id) {
		return am.ac_list(id);
	}
	@Override
	public String findIdByANo(int a_no) {
		return am.findIdByANo(a_no);
	}
}
