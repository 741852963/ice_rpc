package com.iceframework.base;

import com.iceframework.service.IceApplicationService;

public enum IceClient {

	INSTANCE;

	private IceClient() {
		start();
	}

	private void start() {
		IceApplicationService.INSTANCE.startClient(IceConstant.CONFIG_PROXY_FILE.toString());
	}



}
