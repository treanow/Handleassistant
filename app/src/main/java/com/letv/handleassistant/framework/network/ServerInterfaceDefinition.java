package com.letv.handleassistant.framework.network;

public enum ServerInterfaceDefinition {


	OPT_version("/versionUpdate.do?");

	private String opt;
	private RequestMethod requestMethod = RequestMethod.POST;
	private int retryNumber = 1;

	private ServerInterfaceDefinition(String opt) {
		this.opt = opt;
	}

	private ServerInterfaceDefinition(String opt, RequestMethod requestMethod) {
		this.opt = opt;
		this.requestMethod = requestMethod;
	}

	private ServerInterfaceDefinition(String opt, RequestMethod requestMethod, int retryNumber) {
		this.opt = opt;
		this.requestMethod = requestMethod;
		this.retryNumber = retryNumber;
	}

	public String getOpt() {
		return opt;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public int getRetryNumber() {
		return retryNumber;
	}

	public enum RequestMethod {
		POST("POST"), GET("GET");
		private String requestMethodName;

		RequestMethod(String requestMethodName) {
			this.requestMethodName = requestMethodName;
		}

		public String getRequestMethodName() {
			return requestMethodName;
		}
	}
}
