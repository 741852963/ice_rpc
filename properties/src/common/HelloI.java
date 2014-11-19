package common;
// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import java.util.concurrent.TimeUnit;

import Demo.*;

public class HelloI extends _HelloDisp {

	/**
	 *
	 */
	private static final long serialVersionUID = 1373589218597375678L;

	public void sayHello(int delay, Ice.Current current) {
		if (delay > 0) {
			try {
				TimeUnit.MILLISECONDS.sleep(delay);
			} catch (InterruptedException ex1) {
			}
		}
		System.out.println("Hello World!");
	}

	public void shutdown(Ice.Current current) {
		System.out.println("Shutting down...");
		current.adapter.getCommunicator().shutdown();
	}
}
