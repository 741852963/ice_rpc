package ice_server;

import com.iceframework.base.IceServer;

public class Server {

	public static void main(String[] args) {
		IceServer.INSTANCE.startIceApplicationServer("servant_home", "Hello");
	}
}
