package serialize;
import demo._GreetDisp;


public class GreetI extends _GreetDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = -1146802174041098472L;

	public void sendGreeting(MyGreeting greeting, Ice.Current current) {
		if (greeting != null) {
			System.out.println(greeting.text);
		} else {
			System.out.println("Received null greeting");
		}
	}

	public void shutdown(Ice.Current current) {
		System.out.println("Shutting down...");
		current.adapter.getCommunicator().shutdown();
	}
}
