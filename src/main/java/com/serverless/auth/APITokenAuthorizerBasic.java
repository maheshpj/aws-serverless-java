package com.serverless.auth;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class APITokenAuthorizerBasic implements RequestHandler<Object, AuthPolicy> {
	@Override
	public AuthPolicy handleRequest(Object event, Context context) {
		context.getLogger().log("Input APITokenAuthorizer: " + event);
		String principalId = "123456";
		return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowAllPolicy("us-east-2", "*", "*", "dev"));
	}
}
