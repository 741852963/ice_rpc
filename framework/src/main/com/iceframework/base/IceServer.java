package com.iceframework.base;

import org.apache.log4j.Logger;

import com.iceframework.service.IceApplicationService;
import com.iceframework.service.IceUtilService;

/**
 * ����RPC������
 * ���ط���������
 * @author Administrator
 *
 */
public enum IceServer {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(IceServer.class);

	public void startIceApplicationServer() {

		logger.info("Start IceApplication Server");

		IceApplicationService.INSTANCE.startSever();
	}

	public void startIceUtilServer() {

		logger.info("Start IceUtil Server");

		IceUtilService.INSTANCE.startServer("config.server");
	}

}
