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
		System.out.println("usage:\n" + "t: send callback\n"
				+ "s: shutdown server\n" + "x: exit\n" + "?: help\n");
	}

	public int run(String[] args) {

		setInterruptHook(new ShutdownHook());

		CallbackSenderPrx sender = CallbackSenderPrxHelper.checkedCast(
						communicator().propertyToProxy("CallbackSender.Proxy")
						.ice_twoway().ice_timeout(-1).ice_secure(false));

		if (sender == null) {
			System.err.println("invalid proxy");
			return 1;
		}

		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Callback.Client");
		adapter.add(new CallbackReceiverI(), communicator().stringToIdentity("callbackReceiver"));
		adapter.activate();

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
					CallbackReceiverPrx receiver = CallbackReceiverPrxHelper
							.uncheckedCast(adapter.createProxy(communicator()
									.stringToIdentity("callbackReceiver")));
					sender.initiateCallback(receiver);
				} else if (line.equals("s")) {
					sender.shutdown();
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
