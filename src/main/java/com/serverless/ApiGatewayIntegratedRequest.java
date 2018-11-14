package com.serverless;

import com.serverless.BaseRequest;

/**
 * API Gateway Request object defined [mostly] as per AWS documentation:
 * http://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-set-
 * up-simple-proxy.html#api-gateway-simple-proxy-for-lambda-input-format
 * 
 * If you require parameters from a transformed request body (e.g. POST payload)
 * then you'll need to define your POJO matching the expected deserialised
 * object here in place of the 'body' String property
 ** 
 * WARNING** The AWS docs appear to be somewhat inaccurate in the following way:
 * - message property does not exist on request ( I suspect a copy/paste error,
 * as this is a response property of the example API in the docs) - input
 * property contents is the top level object
 * 
 * @author satipati
 *
 */
public class ApiGatewayIntegratedRequest extends BaseRequest {

    private static final long serialVersionUID = 3319058407330107593L;

    /**
     * This property represent the resource which is asked in input request.
     */
    private String resource;

    /**
     * This represent the URI of the request.
     */
    private String path;

    /**
     * This property tell about the HTTP verb like GET, POST etc..
     */
    private String httpMethod;

    /**
     * This property represent the AWS request Context .
     */
    private RequestContext requestContext;

    /**
     * This property tells whether the request is Base64 encoded.
     */
    private boolean isBase64Encoded;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public boolean isBase64Encoded() {
        return isBase64Encoded;
    }

    public void setBase64Encoded(boolean base64Encoded) {
        isBase64Encoded = base64Encoded;
    }

}
