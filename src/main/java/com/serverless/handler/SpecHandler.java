package com.serverless.handler;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.PredefinedClientConfigurations;
import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.AmazonApiGatewayClientBuilder;
import com.amazonaws.services.apigateway.model.GetExportRequest;
import com.amazonaws.services.apigateway.model.GetExportResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.CommonUtils;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class SpecHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    public static final Logger LOGGER = LoggerFactory.getLogger(SpecHandler.class);
    public static final String EXPORT_TYPE = "swagger";
    public static final int HTTP_CODE_200 = 200;
    public static final int HTTP_CODE_500 = 500;

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        String stage = "dev";
        String restApiId = "3c1enr5j5g";
        String region = "us-east-2";

        LOGGER.debug("SpecHandler.doProcess: restApiId:: {}", restApiId);
        LOGGER.debug("SpecHandler.doProcess: stagename:: {}", stage);
        LOGGER.debug("SpecHandler.doProcess: region:: {}", region);

        AmazonApiGateway ag = AmazonApiGatewayClientBuilder.standard()
                .withClientConfiguration(PredefinedClientConfigurations.defaultConfig())
                .withRegion(region)
                .build();

        GetExportRequest exportRequest = new GetExportRequest()
                .withExportType(EXPORT_TYPE)
                .withRestApiId(restApiId)
                .withStageName(stage);

        GetExportResult result = ag.getExport(exportRequest);

        ByteBuffer buffer = result.getBody();
        String jsonString = "";
        if (buffer != null) {
            jsonString = new String(buffer.array());
        }
        
        JSONParser parser = new JSONParser(); 
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Powered-By", "AWS Lambda & Serverless");
        headers.put("Content-Type", "application/json");
        headers.put("access-control-allow-origin", "*");

        if (CommonUtils.isNotBlank(jsonString)) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(HTTP_CODE_200)
                    .setRawBody(jsonString)
                    .setHeaders(headers)
                    .build();
        }
        return ApiGatewayResponse.builder()
                .setStatusCode(HTTP_CODE_500)
                .setHeaders(headers)
                .build();
    }
}
