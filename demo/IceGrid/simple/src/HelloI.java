import Demo.*;

public class HelloI extends _HelloDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = 6206652573123749175L;

	public HelloI(String name) {
		_name = name;
	}

	public void sayHello(Ice.Current current) {
		System.out.println(_name + " says Hello World!");
	}

	public void shutdown(Ice.Current current) {
		System.out.println(_name + " shutting down...");
		current.adapter.getCommunicator().shutdown();
	}

	private final String _name;
}
