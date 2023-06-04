package com.co.kr.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileListVO {

	private String isEdit; //수정내용
	private String seq;   // 게시글 번호
	private String title; //제목
	private String content; //내용
	
}