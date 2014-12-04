package com.iceframework.base;

public enum IceConstant {

	CONFIG_SERVER_FILE("config.server"),
	CONFIG_ENDPOINTS_FILE("config.endpoints"),
	CONFIG_PROXY_FILE("config.proxy"),
	PROXY_PATH("proxypath"),
	;

	private IceConstant(String str) {
		this.value = str;
	}

	private String value;

	@Override
	public String toString() {
		return value;
	}
}
