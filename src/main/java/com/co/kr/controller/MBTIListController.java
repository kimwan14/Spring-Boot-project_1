package com.co.kr.controller;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.code.Code;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;
import com.co.kr.domain.MBTIFileDomain;
import com.co.kr.domain.MBTIListDomain;
import com.co.kr.exception.RequestException;
import com.co.kr.service.MBTIUploadService;
import com.co.kr.vo.FileListVO;
import com.co.kr.vo.MBTIListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MBTIListController {

	@Autowired
	private MBTIUploadService mbtiUploadService;

	@PostMapping(value = "MBTIupload")
	public ModelAndView MBTIupload(MBTIListVO mbtiListVO, MultipartHttpServletRequest request,
			HttpServletRequest httpReq) throws IOException, ParseException {
		ModelAndView mav = new ModelAndView();

		int mbti_Seq = mbtiUploadService.MBTIfileProcess(mbtiListVO, request, httpReq);

		String selectedValue = request.getParameter("mbti_list");
		mbtiListVO.setMbti_list(selectedValue); // 선택된 MBTI 유형 설정
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

	@GetMapping("MBTIedit")
	public ModelAndView edit(FileListVO fileListVO, @RequestParam("MBTI_bdSeq") String mbti_bdSeq,
			HttpServletRequest request) throws IOException {
		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();

		map.put("mbti_bdSeq", Integer.parseInt(mbti_bdSeq));
		MBTIListDomain MBTIListDomain = mbtiUploadService.MBTISelectOne(map);
		List<MBTIFileDomain> MBTIfileList = mbtiUploadService.MBTISelectOneFile(map);

		for (MBTIFileDomain list : MBTIfileList) {
			String path = list.getMbti_bd_upFilePath().replaceAll("\\\\", "/");
			list.setMbti_bd_upFilePath(path);
		}

		fileListVO.setSeq(MBTIListDomain.getMbti_bdSeq());
		fileListVO.setContent(MBTIListDomain.getMbti_bdContent());
		fileListVO.setTitle(MBTIListDomain.getMbti_bdTitle());
		fileListVO.setIsEdit("edit"); // upload 재활용하기위해서

		mav.addObject("detail", MBTIListDomain);
		mav.addObject("files", MBTIfileList);
		mav.addObject("fileLen", MBTIfileList.size());

		mav.setViewName("mbti/mbtiEditList.html");
		return mav;
	}

	@PostMapping("MBTIeditSave")
	public ModelAndView MBTIeditSave(@ModelAttribute("MBTIListVO") MBTIListVO mbtiListVO,
			MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException {
		ModelAndView mav = new ModelAndView();

		// 저장
		mbtiUploadService.MBTIfileProcess(mbtiListVO, request, httpReq);

		mav = MBTISelectOneCall(mbtiListVO, mbtiListVO.getMbti_seq(), request);
		mbtiListVO.setMbti_content(""); // 초기화
		mbtiListVO.setMbti_title(""); // 초기화
		mav.setViewName("board/boardList.html");
		return mav;
	}

	@GetMapping("MBTIremove")
	public ModelAndView MBTIRemove(@RequestParam("mbti_bdSeq") String mbti_bdSeq, HttpServletRequest request)
			throws IOException {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<MBTIFileDomain> fileList = null;
		if (session.getAttribute("files") != null) {
			fileList = (List<MBTIFileDomain>) session.getAttribute("files");
		}

		map.put("mbti_bdSeq", Integer.parseInt(mbti_bdSeq));

		// 내용삭제
		mbtiUploadService.MBTIContentRemove(map);

		for (MBTIFileDomain list : fileList) {
			list.getMbti_bd_upFilePath();
			Path filePath = Paths.get(list.getMbti_bd_upFilePath());

			try {

				// 파일 물리삭제
				Files.deleteIfExists(filePath); // notfound시 exception 발생안하고 false 처리
				// db 삭제
				mbtiUploadService.MBTIFileRemove(list);

			} catch (DirectoryNotEmptyException e) {
				throw RequestException.fire(Code.E404, "디렉토리가 존재하지 않습니다", HttpStatus.NOT_FOUND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 세션해제
		session.removeAttribute("files"); // 삭제
		mav = MBTIListCall();
		mav.setViewName("mbti/mbtiboard.html");

		return mav;
	}

	// 리스트 가져오기 따로 함수뺌
	public ModelAndView MBTIListCall() {
		ModelAndView mav = new ModelAndView();
		List<MBTIListDomain> items = mbtiUploadService.MBTIList();
		mav.addObject("items", items);
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
		// 삭제시 사용할 용도
		session.setAttribute("files", mbtifilelist);

		return mav;

	}

}
