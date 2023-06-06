package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MBTIFileDomain {

	
	private Integer mbti_bdSeq;
	private String mbId;
	
	private String mbti_bd_upOriginalFileName;
	private String mbti_bd_upNewFileName; //동일 이름 업로드 될 경우
	private String mbti_bd_upFilePath;
	private Integer mbti_bd_upFileSize;
	
}