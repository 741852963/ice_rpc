package server;

import common.PropertiesUtil;

/**
 * 取代配置文件,用程序生成ice配置
 * @author Administrator
 *
 */
public class PropertiesServer {

	public static void main(String[] args) {

		try {

			Ice.StringSeqHolder argsH = new Ice.StringSeqHolder(args);
			Ice.Properties properties = Ice.Util.createProperties(argsH);
			properties.setProperty("proxy", "10");

			Ice.InitializationData id = new Ice.InitializationData();
			id.properties = properties;

			Ice.Communicator communicator = Ice.Util.initialize(id);
			PropertiesUtil.printProperties(communicator);
			communicator.destroy();

		} catch (Ice.LocalException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}
}
