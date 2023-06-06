package com.co.kr.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.MBTIContentDomain;
import com.co.kr.domain.MBTIFileDomain;
import com.co.kr.domain.MBTIListDomain;

@Mapper
public interface MBTIUploadMapper {

	// list
	public List<MBTIListDomain> MBTIList();

	// content insert
	public void MBTIcontentUpload(MBTIContentDomain MBTIContentDomain);

	// file insert
	public void MBTIfileUpload(MBTIFileDomain MBTIFileDomain);

	// content update
	public void MBTIContentUpdate(MBTIContentDomain MBTIContentDomain);

	// file updata
	public void MBTIFileUpdate(MBTIFileDomain MBTIFileDomain);

	// select one
	public MBTIListDomain MBTISelectOne(HashMap<String, Object> map);

	// select one file
	public List<MBTIFileDomain> MBTISelectOneFile(HashMap<String, Object> map);

	// content delete
	public void MBTIContentRemove(HashMap<String, Object> map);

	// file delete
	public void MBTIFileRemove(MBTIFileDomain MBTIFileDomain);
}