package com.letv.handleassistant.framework.network;


import com.letv.handleassistant.application.SoftApplication;

import java.util.HashMap;
import java.util.Map;


public class RequestMaker {

    private static final String AUTH = "auth";
    private static final String SIGN = "sign";
    private static final String INFO = "info";
    private SoftApplication softApplication;

    private RequestMaker() {
        softApplication = SoftApplication.softApplication;
    }

    private static RequestMaker requestMaker = null;

    /**
     * 得到RequestMaker的实例
     *
     * @return
     */
    public static RequestMaker getInstance() {
        if (requestMaker == null) {
            requestMaker = new RequestMaker();
            return requestMaker;
        } else {
            return requestMaker;
        }
    }


    /**
     * 测试
     *
     * @param type
     * @return
     */
    public Request getVersionRequest(int type) {
        Request request = null;
        try {

            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("type", type + "");

            request = new Request(ServerInterfaceDefinition.OPT_version, paramsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }



}
