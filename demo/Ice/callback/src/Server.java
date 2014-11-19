
public class Server extends Ice.Application {

	public int run(String[] args) {

		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Callback.Server");
		adapter.add(new CallbackSenderI(), communicator().stringToIdentity("callbackSender"));
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
