package com.iceframework;

import Demo._HelloDisp;
import Ice.Current;

public class HelloI extends _HelloDisp {

	/**
	 *
	 */
	private static final long serialVersionUID = -7393623644730893710L;

	@Override
	public void sayHello(int delay, Current __current) {
		System.out.println(delay);
	}

	@Override
	public void shutdown(Current __current) {
		// TODO Auto-generated method stub

	}

}
