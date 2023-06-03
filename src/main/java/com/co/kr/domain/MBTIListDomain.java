package com.co.kr.domain;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName="builder")
public class MBTIListDomain {

	private String mbti_bdSeq;
	private String mbId;
	private String mbti_bdTitle;
	private String mbti_bdContent;
	private String mbti_bdCreateAt;
	private String mbti_bdUpdateAt;

}