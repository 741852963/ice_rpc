import Demo.*;

public class HelloI extends _HelloDisp {

	public void sayHello(Ice.Current current) {
		System.out.println("Hello World!");
	}
}
