package server;

import common.PropertiesUtil;


/**
 * �ڼ̳�Ice.Application��ʹ�������ļ�
 * @author Administrator
 *
 */
public class Server extends Ice.Application {

	public int run(String[] args) {

		Ice.Properties properties = communicator().getProperties();
		properties.setProperty("proxy", "10");

		PropertiesUtil.printProperties(communicator());

		return 0;
	}

	public static void main(String[] args) {

		Server app = new Server();
		int status = app.main("Server", args, "config.server");
		System.exit(status);
	}

}
