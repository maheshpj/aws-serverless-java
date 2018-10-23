package com.serverless.auth;

public enum AuthorizerConstants {

	BEARER_TOKEN("Bearer");

	private String value;

	AuthorizerConstants(String property) {
		this.value = property;
	}

	public String val() {
		return value;
	}
}
