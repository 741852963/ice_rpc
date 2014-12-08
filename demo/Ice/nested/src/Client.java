import java.io.BufferedReader;
import java.io.InputStreamReader;

import Demo.*;
import Ice.Properties;

public class Client extends Ice.Application {
	class ShutdownHook extends Thread {
		public void run() {
			/*
			 * For this demo we won't destroy the communicator since it has to
			 * wait for any outstanding invocations to complete which may take
			 * some time if the nesting level is exceeded.
			 *
			 * try { communicator().destroy(); } catch(Ice.LocalException ex) {
			 * ex.printStackTrace(); }
			 */
		}
	}

	public int run(String[] args) {
		if (args.length > 0) {
			System.err.println(appName() + ": too many arguments");
			return 1;
		}

		setInterruptHook(new ShutdownHook());

		NestedPrx nested = NestedPrxHelper.checkedCast(communicator().propertyToProxy("Nested.Proxy"));
		if (nested == null) {
			System.err.println("invalid proxy");
			return 1;
		}

		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Nested.Client");
		NestedPrx self = NestedPrxHelper.uncheckedCast(adapter.createProxy(communicator().stringToIdentity("nestedClient")));
		adapter.add(new NestedI(self), communicator().stringToIdentity("nestedClient"));
		adapter.activate();

		Properties properties = communicator().getProperties();
		System.out.println("Ice.ThreadPool.Server.Size : " + properties.getProperty("Ice.ThreadPool.Server.Size"));
		System.out.println("Ice.ThreadPool.Server.SizeWarn : " + properties.getProperty("Ice.ThreadPool.Server.SizeWarn"));
		System.out.println("Ice.ThreadPool.Server.SizeMax : " + properties.getProperty("Ice.ThreadPool.Server.SizeMax"));

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String s = null;
		do {
			try {
				System.out.print("enter nesting level or 'x' for exit: ");
				System.out.flush();
				s = in.readLine();
				if (s == null) {
					break;
				}
				int level = Integer.parseInt(s);
				if (level > 0) {
					nested.nestedCall(level, self);
				}
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		} while (!s.equals("x"));

		return 0;
	}

	public static void main(String[] args) {
		Client app = new Client();
		int status = app.main("Client", args, "config.client");
		System.exit(status);
	}
}
