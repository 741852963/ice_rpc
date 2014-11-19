import Demo.*;

class SessionFactoryI extends _SessionFactoryDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = -6816411230985221952L;

	SessionFactoryI(ReapThread reaper) {
		_reaper = reaper;
	}

	public synchronized SessionPrx create(String name, Ice.Current c) {
		SessionI session = new SessionI(name);
		SessionPrx proxy = SessionPrxHelper.uncheckedCast(c.adapter.addWithUUID(session));
		_reaper.add(proxy, session);
		return proxy;
	}

	public void shutdown(Ice.Current c) {
		System.out.println("Shutting down...");
		c.adapter.getCommunicator().shutdown();
	}

	private ReapThread _reaper;
}
