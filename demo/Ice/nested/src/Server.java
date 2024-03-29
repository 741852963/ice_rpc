import Demo.*;

public class Server extends Ice.Application {

	public int run(String[] args) {

		if (args.length > 0) {
			System.err.println(appName() + ": too many arguments");
			return 1;
		}

		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Nested.Server");
		NestedPrx self = NestedPrxHelper.uncheckedCast(adapter.createProxy(communicator().stringToIdentity("nestedServer")));
		adapter.add(new NestedI(self), communicator().stringToIdentity("nestedServer"));
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
