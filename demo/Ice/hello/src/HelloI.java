
import java.util.concurrent.TimeUnit;

import Demo.*;

public class HelloI extends _HelloDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void sayHello(int delay, Ice.Current current) {
		if (delay > 0) {
			try {
				TimeUnit.SECONDS.sleep(delay);
			} catch (InterruptedException ex1) {
			}
		}
		System.out.println("Hello World!");
	}

	public void shutdown(Ice.Current current) {
		System.out.println("Shutting down...");
		current.adapter.getCommunicator().shutdown();
	}
}
