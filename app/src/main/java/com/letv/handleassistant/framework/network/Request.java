package com.letv.handleassistant.framework.network;


import java.util.Map;

public class Request {

    private ServerInterfaceDefinition serverInterfaceDefinition;
    private Map<String, String> paramsMap;


    //  Content-Type 为 application/json
    private String requestJson;
    public Request(ServerInterfaceDefinition serverInterfaceDefinition,
                   String requestJson) {
        super();
        this.serverInterfaceDefinition = serverInterfaceDefinition;
        this.requestJson = requestJson;
    }


    public Request(ServerInterfaceDefinition serverInterfaceDefinition,
                   Map<String, String> paramsMap) {
        super();
        this.serverInterfaceDefinition = serverInterfaceDefinition;
        this.paramsMap = paramsMap;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    //  Content-Type 为 application/json
    public String getRequestJson() {
        return requestJson;
    }
    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public ServerInterfaceDefinition getServerInterfaceDefinition() {
        return serverInterfaceDefinition;
    }

    public void setServerInterfaceDefinition(
            ServerInterfaceDefinition serverInterfaceDefinition) {
        this.serverInterfaceDefinition = serverInterfaceDefinition;
    }


}
