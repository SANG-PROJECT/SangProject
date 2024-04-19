package com.green.sang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.sang.dto.Academy;
import com.green.sang.mapper.AcademyMapper;

@Service
public class AcademyServiceImpl implements AcademyService {
	@Autowired
	private AcademyMapper am;

	
//	강의 전체 리스트
	public List<Academy> academy_list() {
		return am.academy_list();
	}
	
}
