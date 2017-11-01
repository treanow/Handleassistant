package com.letv.handleassistant.framework.bean;

import java.io.Serializable;

public abstract class BaseResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public int errCode;
	public String msg;
}
