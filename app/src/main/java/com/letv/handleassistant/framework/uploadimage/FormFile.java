package com.letv.handleassistant.framework.uploadimage;

import java.io.InputStream;
import java.util.List;

/**
 * 上传文件
 */
public class FormFile {
	public String inputStreamKey;
	public InputStream inputStreamValue;
	public List<InputStream> inputStreamValueList;
	public String fileName;
	
	public FormFile(String inputStreamKey,InputStream inStream, String fileName) {
		this.fileName = fileName;
		this.inputStreamValue = inStream;
		this.inputStreamKey = inputStreamKey;
	}
	
	public FormFile(String inputStreamKey,List<InputStream> inputStreamValueList, String fileName) {
		this.fileName = fileName;
		this.inputStreamValueList = inputStreamValueList;
		this.inputStreamKey = inputStreamKey;
	}

}
