package com.co.kr.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.domain.MBTIFileDomain;
import com.co.kr.domain.MBTIListDomain;
import com.co.kr.vo.FileListVO;

public interface MBTIUploadService {
	// 인서트
		public int MBTIfileProcess(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq);

		// 전체 리스트 조회 // 지난시간 작성
		public List<MBTIListDomain> MBTIList();

		// 하나 삭제
		public void MBTIContentRemove(HashMap<String, Object> map);

		// 하나 삭제
		public void MBTIFileRemove(MBTIFileDomain MBTIFileDomain);

		// 하나 리스트 조회
		public MBTIListDomain MBTISelectOne(HashMap<String, Object> map);

		// 하나 파일 리스트 조회
		public List<MBTIFileDomain> MBTISelectOneFile(HashMap<String, Object> map);

}
