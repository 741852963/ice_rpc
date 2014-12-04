package server;

import common.PropertiesUtil;


/**
 * 在继承Ice.Application中使用配置文件
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
