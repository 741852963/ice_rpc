import Demo.*;

public class Client {
	public static void main(String[] args) {

		try {

			Ice.Communicator communicator = Ice.Util.initialize(args);

			HelloPrx hello = HelloPrxHelper.checkedCast(communicator
					.stringToProxy("hello:tcp -h localhost -p 10000"));

			hello.sayHello();

			communicator.destroy();
		} catch (Ice.LocalException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
