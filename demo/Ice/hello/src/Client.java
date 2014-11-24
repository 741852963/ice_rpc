// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import java.io.BufferedReader;
import java.io.InputStreamReader;

import Demo.*;

public class Client extends Ice.Application {

	class ShutdownHook extends Thread {
		public void run() {
			try {
				communicator().destroy();
			} catch (Ice.LocalException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void menu() {
		System.out.println("usage:\n" + "t: send greeting as twoway\n"
				+ "o: send greeting as oneway\n"
				+ "O: send greeting as batch oneway\n"
				+ "d: send greeting as datagram\n"
				+ "D: send greeting as batch datagram\n"
				+ "f: flush all batch requests\n" + "T: set a timeout\n"
				+ "P: set a server delay\n" + "S: switch secure mode on/off\n"
				+ "s: shutdown server\n" + "x: exit\n" + "?: help\n");
	}

	public int run(String[] args) {
		if (args.length > 0) {
			System.err.println(appName() + ": too many arguments");
			return 1;
		}

		//
		// Since this is an interactive demo we want to clear the
		// Application installed interrupt callback and install our
		// own shutdown hook.
		//
		setInterruptHook(new ShutdownHook());

		/*
		 * 代理实际上就是远程服务驻在本地的一个代表,创建 它时首先会和远程服务经行握手和状态确认等操作.
		 * Client所有的操作都是从过Proxy来办理的。
		 * 代理又分为直接代理,已经知道服务端的位置及其他信 息)和间接代理,不知道服务器在哪里,由Registry注册器告诉它地址等信息)。
		 */
		HelloPrx twoway = HelloPrxHelper.checkedCast(	// checkedCast 会联系服务器
				communicator()
				.propertyToProxy("Hello.Proxy")
				.ice_twoway()
				.ice_timeout(-1)
				.ice_secure(false));

		System.out.println("Client : checkedCast");
		if (twoway == null) {
			System.err.println("invalid proxy");
			return 1;
		}

		/*
		 * ICE调用模式
		 * ICE采用的网络协议有TCP、UDP以及SSL三 种,不同于WebService,ICE在调用模式上有好几种选择方案,
		 * 并且每种方案正对不同的网络协议的特性做了相应的选择。
		 *
		 * 不同的调用模式其实对应着不动的业务,对于大部分的有返回值的或需要实时响应的方法,我们可能都采用Twoway方式调用,
		 * 对于一些无需返回值或 者不依赖返回值的业务,我们可以用Oneway或者BatchOneway方式,例如消息通知；
		 * 剩下的Datagram和BatchDatagram方式 一般用在无返回值且不做可靠性检查的业务上,例如日志。
		 */
		// Oneway(单 向调用)：客户端只需将调用注册到本地传输缓冲区,Local Transport Buffers)后就立即返回,并不对调用结果负责。
		HelloPrx oneway = (HelloPrx) twoway.ice_oneway();
		// Twoway,双 向调用)：最通用的模式,同步方法调用模式,只能用TCP或SSL协议。
		HelloPrx batchOneway = (HelloPrx) twoway.ice_batchOneway();
		// Datagram,数据报)：类似于Oneway调用,不同的是 Datagram调用只能采用UDP协议而且只能调用无返回值和无输出参数的方法。
		HelloPrx datagram = (HelloPrx) twoway.ice_datagram();
		// BatchDatagram,批量数据报同BatchOneway批量单向调用类似)：先将调用存 在调用缓冲区里面,到达一定限额后自动批量发送所有请求,也可手动刷除缓冲区)。
		HelloPrx batchDatagram = (HelloPrx) twoway.ice_batchDatagram();


		boolean secure = false;
		int timeout = -1;
		int delay = 0;

		menu();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String line = null;
		do {
			try {
				System.out.print("==> ");
				System.out.flush();
				line = in.readLine();
				if (line == null) {
					break;
				}
				if (line.equals("t")) {
					twoway.sayHello(delay);
				} else if (line.equals("o")) {
					oneway.sayHello(delay);
				} else if (line.equals("O")) {
					batchOneway.sayHello(delay);
				} else if (line.equals("d")) {
					if (secure) {
						System.out.println("secure datagrams are not supported");
					} else {
						datagram.sayHello(delay);
					}
				} else if (line.equals("D")) {
					if (secure) {
						System.out.println("secure datagrams are not supported");
					} else {
						batchDatagram.sayHello(delay);
					}
				} else if (line.equals("f")) {
					communicator().flushBatchRequests();
				} else if (line.equals("T")) {
					if (timeout == -1) {
						timeout = 2000;
					} else {
						timeout = -1;
					}

					twoway = (HelloPrx) twoway.ice_timeout(timeout);
					oneway = (HelloPrx) oneway.ice_timeout(timeout);
					batchOneway = (HelloPrx) batchOneway.ice_timeout(timeout);

					if (timeout == -1) {
						System.out.println("timeout is now switched off");
					} else {
						System.out.println("timeout is now set to 2000ms");
					}
				} else if (line.equals("P")) {
					if (delay == 0) {
						delay = 2500;
					} else {
						delay = 0;
					}

					if (delay == 0) {
						System.out.println("server delay is now deactivated");
					} else {
						System.out.println("server delay is now set to 2500ms");
					}
				} else if (line.equals("S")) {
					secure = !secure;

					twoway = (HelloPrx) twoway.ice_secure(secure);
					oneway = (HelloPrx) oneway.ice_secure(secure);
					batchOneway = (HelloPrx) batchOneway.ice_secure(secure);
					datagram = (HelloPrx) datagram.ice_secure(secure);
					batchDatagram = (HelloPrx) batchDatagram.ice_secure(secure);

					if (secure) {
						System.out.println("secure mode is now on");
					} else {
						System.out.println("secure mode is now off");
					}
				} else if (line.equals("s")) {
					twoway.shutdown();
				} else if (line.equals("x")) {
					// Nothing to do
				} else if (line.equals("?")) {
					menu();
				} else {
					System.out.println("unknown command `" + line + "'");
					menu();
				}
			} catch (java.io.IOException ex) {
				ex.printStackTrace();
			} catch (Ice.LocalException ex) {
				ex.printStackTrace();
			}
		} while (!line.equals("x"));

		return 0;
	}

	public static void main(String[] args) {
		Client app = new Client();
		int status = app.main("Client", args, "config.client");
		System.exit(status);
	}
}
