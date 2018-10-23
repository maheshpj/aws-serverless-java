package com.serverless.auth;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.okta.jwt.JoseException;
import com.okta.jwt.JwtVerifier;

public class APITokenAuthorizer implements RequestHandler<Map<String, Object>, AuthPolicy> {

	@Override
	public AuthPolicy handleRequest(Map<String, Object> event, Context context) {

		context.getLogger().log("Input APITokenAuthorizer: " + event);
		String authToken = event.get("authorizationToken").toString();
		String principalId = "XXXX";
		context.getLogger().log("AUTH_TOKEN: " + authToken);

		if (authToken.toLowerCase().startsWith(AuthorizerConstants.BEARER_TOKEN.val().toLowerCase())) {
			principalId = parseJWT(authToken.substring(AuthorizerConstants.BEARER_TOKEN.val().length()).trim());
			context.getLogger().log("principalId parsed " + principalId);
			if (principalId != "") {
				return new AuthPolicy(principalId,
						AuthPolicy.PolicyDocument.getAllowAllPolicy("us-east-2", "*", "*", "dev"));
			}
		}
		return new AuthPolicy("123456", AuthPolicy.PolicyDocument.getDenyAllPolicy("us-east-2", "*", "*", "dev"));
	}

	// Sample method to validate and read the JWT
	private String parseJWT(String token) {
		String principalId = "";
		try {
			DecodedJWT jwt = JWT.decode(token);
			principalId = jwt.getSubject();
		} catch (JWTDecodeException exception) {
			System.out.println("ERROR DECODING TOKEN");
		}
		return principalId;
	}

	/**
	 * This method validates the jwt token received from OKTA authorization
	 * server
	 * 
	 * @param token
	 */
	private boolean validateToken(String token) {
		boolean isAuthorizationSuccess = false;
		JwtVerifier jwtVerifier = null;
		try {
			// jwtVerifier = new
			// JwtHelper().setIssuerUrl("").setAudience("").build();
			jwtVerifier.decodeAccessToken(token);
			isAuthorizationSuccess = true;
		} catch (JoseException e) {
			System.out.println("Exception while validating JWT token" + e);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return isAuthorizationSuccess;
	}
}
