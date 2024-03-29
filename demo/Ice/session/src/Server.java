
public class Server extends Ice.Application {

	public int run(String[] args) {

		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("SessionFactory");

		ReapThread reaper = new ReapThread();
		reaper.start();

		adapter.add(new SessionFactoryI(reaper), communicator().stringToIdentity("SessionFactory"));
		adapter.activate();
		communicator().waitForShutdown();

		reaper.terminate();
		try {
			reaper.join();
		} catch (InterruptedException e) {
		}

		return 0;
	}

	public static void main(String[] args) {
		Server app = new Server();
		int status = app.main("Server", args, "config.server");
		System.exit(status);
	}
}
