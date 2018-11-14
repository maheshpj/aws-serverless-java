package com.serverless;

import java.io.Serializable;
import java.util.Map;

/**
 * This class will be implemented by all Lambda request objects
 * 
 * @author satipati
 *
 */
public class BaseRequest implements Serializable {
    private static final long serialVersionUID = 3054991753233970885L;
    private Map<String, String> headers;
    private Map<String, String> queryStringParameters;
    private Map<String, String> pathParameters;
    private Map<String, String> stageVariables;
    private String body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryStringParameters() {
        return queryStringParameters;
    }

    public void setQueryStringParameters(Map<String, String> queryStringParameters) {
        this.queryStringParameters = queryStringParameters;
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(Map<String, String> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public Map<String, String> getStageVariables() {
        return stageVariables;
    }

    public void setStageVariables(Map<String, String> stageVariables) {
        this.stageVariables = stageVariables;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
