package com.serverless;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import com.google.gson.Gson;

/**
 * This class is used to have all the elements which are sent in response
 * 
 * @author satipati
 *
 */
public class ApiGatewayIntegratedResponse {

    /**
     * This property represent HTTP response code like 2XX , 3XX etc.
     */
    private final int statusCode;

    /**
     * This property holds the response in a json format.
     */
    private final String body;

    /**
     * This property holds all the headers which are passed from UI or request.
     */
    private final Map<String, String> headers;

    /**
     * This property tells whether the request is Base64 encoded.
     */
    private final boolean isBase64Encoded;

    public ApiGatewayIntegratedResponse(int statusCode, String body, Map<String, String> headers,
            boolean isBase64Encoded) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
        this.isBase64Encoded = isBase64Encoded;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    // API Gateway expects the property to be called "isBase64Encoded" => isIs
    public boolean isIsBase64Encoded() {
        return isBase64Encoded;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * This is a builder pattern implementation to build the response object.
     * 
     * @author anangupt
     *
     */
    public static class Builder {

        private int statusCode = 200;
        private Map<String, String> headers = Collections.emptyMap();
        private String rawBody;
        private Object objectBody;
        private byte[] binaryBody;
        private boolean base64Encoded;

        public Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Builds the {@link ApiGatewayIntegratedResponse} using the passed raw
         * body string.
         */
        public Builder setRawBody(String rawBody) {
            this.rawBody = rawBody;
            return this;
        }

        /**
         * Builds the {@link ApiGatewayIntegratedResponse} using the passed
         * object body converted to JSON.
         */
        public Builder setObjectBody(Object objectBody) {
            this.objectBody = objectBody;
            return this;
        }

        /**
         * Builds the {@link ApiGatewayIntegratedResponse} using the passed
         * binary body encoded as base64. {@link #setBase64Encoded(boolean)
         * setBase64Encoded(true)} will be in invoked automatically.
         */
        public Builder setBinaryBody(byte[] binaryBody) {
            this.binaryBody = binaryBody;
            setBase64Encoded(true);
            return this;
        }

        /**
         * A binary or rather a base64encoded responses requires
         * <ol>
         * <li>"Binary Media Types" to be configured in API Gateway
         * <li>a request with an "Accept" header set to one of the
         * "Binary Media Types"
         * </ol>
         */
        public Builder setBase64Encoded(boolean base64Encoded) {
            this.base64Encoded = base64Encoded;
            return this;
        }

        /**
         * This method builds the response.
         * 
         * @return
         */
        public ApiGatewayIntegratedResponse build() {
            String body = null;
            if (rawBody != null) {
                body = rawBody;
            } else if (objectBody != null) {
                Gson gson = new Gson();
                body = gson.toJson(objectBody);
            } else if (binaryBody != null) {
                body = new String(Base64.getEncoder().encode(binaryBody), StandardCharsets.UTF_8);
            }
            return new ApiGatewayIntegratedResponse(statusCode, body, headers, base64Encoded);
        }
    }
}
