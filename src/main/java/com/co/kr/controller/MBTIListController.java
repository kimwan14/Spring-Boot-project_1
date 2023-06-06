package com.co.kr.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;
import com.co.kr.domain.MBTIFileDomain;
import com.co.kr.domain.MBTIListDomain;
import com.co.kr.service.MBTIUploadService;
import com.co.kr.vo.FileListVO;
import com.co.kr.vo.MBTIListVO;

public class MBTIListController {

	@Autowired
	private MBTIUploadService mbtiUploadService;

	@PostMapping(value = "MBTIupload")
	public ModelAndView MBTIupload(MBTIListVO mbtiListVO, MultipartHttpServletRequest request,
			HttpServletRequest httpReq) throws IOException, ParseException {
		ModelAndView mav = new ModelAndView();
		int mbti_Seq = mbtiUploadService.MBTIfileProcess(mbtiListVO, request, httpReq);
		mbtiListVO.setMbti_content(""); // 초기화
		mbtiListVO.setMbti_title(""); // 초기화;

		mav = MBTISelectOneCall(mbtiListVO, String.valueOf(mbti_Seq), request);
		System.out.println(mav);
		mav.setViewName("mbti/mbtiboard.html");

		return mav;
		// 위에 fileListVo 객체는 화면단의 input name과 맵핑되어 입력 데이터를 받습니다.
	}

	// detail
	@GetMapping("detail2")
	public ModelAndView mbti_bdDetail(@ModelAttribute("MBTIListVO") MBTIListVO mbtiListVO,
			@RequestParam("mbti_bdSeq") String mbti_bdSeq, HttpServletRequest request) throws IOException {
		ModelAndView mav = new ModelAndView();
		// 하나파일 가져오기
		mav = MBTISelectOneCall(mbtiListVO, mbti_bdSeq, request);
		mav.setViewName("mbti/mbtiboard.html");
		return mav;
	}

	public ModelAndView MBTISelectOneCall(@ModelAttribute("MBTIListVO") MBTIListVO mbtiListVO, String mbti_bdSeq,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();

		map.put("mbti_bdSeq", Integer.parseInt(mbti_bdSeq));
		MBTIListDomain mbtiListDomain = mbtiUploadService.MBTISelectOne(map);
		System.out.println("MBTIListDomain" + mbtiListDomain);
		List<MBTIFileDomain> mbtifilelist = mbtiUploadService.MBTISelectOneFile(map);

		for (MBTIFileDomain list : mbtifilelist) {
			String path = list.getMbti_bd_upFilePath().replaceAll("\\\\", "/");
			list.setMbti_bd_upFilePath(path);
		}

		mav.addObject("detail2", mbtiListDomain);
		mav.addObject("mbti_files", mbtifilelist);

		return mav;

	}

}
