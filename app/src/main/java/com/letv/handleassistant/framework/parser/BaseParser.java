package com.letv.handleassistant.framework.parser;


import com.letv.handleassistant.framework.bean.BaseResponse;
import com.letv.handleassistant.framework.bean.DataHull;

public abstract class BaseParser<T extends BaseResponse> {

	public DataHull<T> getParseResult(String paramString){
		DataHull dataHull = new DataHull();
		dataHull.dataEntry = parse(paramString);
		return dataHull;
	}

	public abstract  T parse(String paramString);
}
