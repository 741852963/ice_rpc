package server;

import servant.InitialI;
import util.ObjectFactory;

public class Server extends Ice.Application {

	public int run(String[] args) {

		if (args.length > 0) {
			System.err.println(appName() + ": too many arguments");
			return 1;
		}

		Ice.ObjectFactory factory = new ObjectFactory();
		communicator().addObjectFactory(factory, Demo.Printer.ice_staticId());

		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Value");
		Ice.Object object = new InitialI(adapter);
		adapter.add(object, communicator().stringToIdentity("initial"));
		adapter.activate();

		communicator().waitForShutdown();

		return 0;
	}

	public static void main(String[] args) {
		Server app = new Server();
		int status = app.main("Server", args, "config.server");
		System.exit(status);
	}

}
