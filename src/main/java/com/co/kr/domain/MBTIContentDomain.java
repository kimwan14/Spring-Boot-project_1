package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MBTIContentDomain {

	private Integer mbti_bdSeq;
	private String mbId;
	private String mbti_bd_Title;
	private String mbti_bd_Content;

}