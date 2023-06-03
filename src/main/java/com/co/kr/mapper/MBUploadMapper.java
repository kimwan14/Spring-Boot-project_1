package com.co.kr.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.MBTIListDomain;

@Mapper
public interface MBUploadMapper {

	// list
	public List<MBTIListDomain> MBTIList();

//	// content insert
//	public void QAcontentUpload(QAContentDomain QAContentDomain);
//
//	// file insert
//	public void QAfileUpload(QAFileDomain QAFileDomain);
//
//	// content update
//	public void QAbdContentUpdate(QAContentDomain QAContentDomain);
//
//	// file updata
//	public void QAbdFileUpdate(QAFileDomain QAFileDomain);
//
//	// select one
//	public MBTIListDomain QASelectOne(HashMap<String, Object> map);
//
//	// select one file
//	public List<QAFileDomain> QASelectOneFile(HashMap<String, Object> map);
//
//	// content delete
//	public void QAbdContentRemove(HashMap<String, Object> map);
//
//	// file delete
//	public void QAbdFileRemove(QAFileDomain QAFileDomain);
}