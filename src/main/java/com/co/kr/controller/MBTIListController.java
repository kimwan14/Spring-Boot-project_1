package com.co.kr.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.MBTIFileDomain;
import com.co.kr.domain.MBTIListDomain;
import com.co.kr.service.MBTIUploadService;
import com.co.kr.vo.FileListVO;

public class MBTIListController {
	
	@Autowired
	private MBTIUploadService mbtiUploadService;
	
	@PostMapping(value = "MBTIupload")
	public ModelAndView MBTIupload(FileListVO fileListVO, MultipartHttpServletRequest request,HttpServletRequest httpReq) throws IOException,ParseException{
		ModelAndView mav = new ModelAndView();
		int mbti_Seq = mbtiUploadService.MBTIfileProcess(fileListVO,request,httpReq);
		fileListVO.setContent(""); //초기화
		fileListVO.setTitle(""); //초기화;
		
		mav = MBTISelectOneCall(fileListVO,String.valueOf(mbti_Seq),request);
		System.out.println(mav);
		return mav;
		//위에 fileListVo 객체는 화면단의 input name과 맵핑되어 입력 데이터를 받습니다. 
	}
	
	public ModelAndView MBTISelectOneCall(@ModelAttribute("fileListVO") MBTIListVO,String mbti_bdSeq,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		
		map.put("mbti_bdSeq", Integer.parseInt(mbti_bdSeq));
		MBTIListDomain mbtiListDomain = mbtiUploadService.MBTISelectOne(map);
		System.out.println("MBTIListDomain" + mbtiListDomain);
		List<MBTIFileDomain> mbtifilelist = mbtiUploadService.MBTISelectOneFile(map);
		
		for (MBTIFileDomain list : fileList) {
			String path = list.getmbti_bd_UpFilePath().replaceAll("\\\\","/");
			list.setMbti_bd_upFilePath(path);
		}
		
		mav.addObject("detail2",mbtiListDomain);
		mav.addObject("mbti_files",fileList);
		
		return mav;
		
	}
		
}
