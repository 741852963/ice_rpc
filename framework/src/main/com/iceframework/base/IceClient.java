package com.iceframework.base;

import Demo.HelloPrx;

import com.iceframework.service.IceApplicationService;

public enum IceClient {

	INSTANCE;

	private IceClient() {
		start();
	}

	private void start() {
		IceApplicationService.INSTANCE.startClient();
	}

	public HelloPrx getHello() {
		return (HelloPrx)IceCache.INSTANCE.getObjectPrx("Hello");
	}

}
