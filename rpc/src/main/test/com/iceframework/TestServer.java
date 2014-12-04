package com.iceframework;

import org.junit.Ignore;
import org.junit.Test;

import com.iceframework.base.IceClient;
import com.iceframework.base.IceServer;

public class TestServer {

	@Test
//	@Ignore
	public void test_startIceApplication() {
//		IceServer.INSTANCE.startIceApplicationServer();
//		IceClient.INSTANCE.getHello().sayHello(0);
	}

	@Test
	@Ignore
	public void test_startIceUtil() {
		IceServer.INSTANCE.startIceUtilServer();
	}
}
