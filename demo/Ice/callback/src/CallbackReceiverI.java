import Demo.*;

public final class CallbackReceiverI extends _CallbackReceiverDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = -3861656957776173590L;

	public void callback(Ice.Current current) {
		System.out.println("received callback");
	}
}
