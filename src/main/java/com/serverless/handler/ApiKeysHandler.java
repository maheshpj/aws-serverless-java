package com.serverless.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.AmazonApiGatewayClientBuilder;
import com.amazonaws.services.apigateway.model.GetApiKeyRequest;
import com.amazonaws.services.apigateway.model.GetApiKeyResult;
import com.amazonaws.services.apigateway.model.GetUsagePlanKeysRequest;
import com.amazonaws.services.apigateway.model.GetUsagePlanKeysResult;
import com.amazonaws.services.apigateway.model.UsagePlanKey;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;

public class ApiKeysHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    public static final int HTTP_CODE_200 = 200;
    public static final int HTTP_CODE_500 = 500;

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        String usagePlanId = "z1flpw"; //Usage plan name: "test-usageplan";
        String region = "us-east-2";

        AmazonApiGateway client = AmazonApiGatewayClientBuilder.standard().withRegion(region).build();
        GetUsagePlanKeysRequest req = new GetUsagePlanKeysRequest().withUsagePlanId(usagePlanId);
        GetUsagePlanKeysResult result = client.getUsagePlanKeys(req);
        List<UsagePlanKey> usageKeys = result.getItems();
        
        Map<String, String> keyMap = new HashMap<String, String>();
        for (UsagePlanKey usagePlanKey : usageKeys) {
            String apiKeyId = usagePlanKey.getId();
            String apiKeyName = usagePlanKey.getName();
            
            GetApiKeyRequest apiKeyRequest = new GetApiKeyRequest()
                    .withIncludeValue(true)
                    .withApiKey(apiKeyId);
            GetApiKeyResult apiKeyResult = client.getApiKey(apiKeyRequest).withId(apiKeyId);

            keyMap.put(apiKeyId, "apiKeyName: " + apiKeyName + " | apiKeyVal:" + apiKeyResult.getValue());
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Powered-By", "AWS Lambda & Serverless");
        headers.put("Content-Type", "application/json");
        headers.put("access-control-allow-origin", "*");

        return ApiGatewayResponse.builder()
                .setStatusCode(HTTP_CODE_200)
                .setObjectBody(keyMap)
                .setHeaders(headers)
                .build();

    }

}
