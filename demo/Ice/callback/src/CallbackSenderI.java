import Demo.*;

public final class CallbackSenderI extends _CallbackSenderDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = 2281262756735687201L;

	public void initiateCallback(CallbackReceiverPrx proxy, Ice.Current current) {
		System.out.println("initiating callback");
		try {
			proxy.callback();
		} catch (Ice.LocalException ex) {
			ex.printStackTrace();
		}
	}

	public void shutdown(Ice.Current current) {
		System.out.println("Shutting down...");
		try {
			current.adapter.getCommunicator().shutdown();
		} catch (Ice.LocalException ex) {
			ex.printStackTrace();
		}
	}
}
