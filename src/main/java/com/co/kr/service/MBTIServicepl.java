package com.co.kr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.kr.domain.MBTIListDomain;
import com.co.kr.mapper.MBUploadMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MBTIServicepl implements MBTIService {

	@Autowired
	private MBUploadMapper MBTIMapper;
	
	@Override
	public List<MBTIListDomain> MBTIList(){
		return MBTIMapper.MBTIList();
	}
}