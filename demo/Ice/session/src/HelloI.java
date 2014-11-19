import Demo.*;

public class HelloI extends _HelloDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = -2904008622963307024L;

	public HelloI(String name, int id) {
		_name = name;
		_id = id;
	}

	public void sayHello(Ice.Current current) {
		System.out.println("Hello object #" + _id + " for session `" + _name
				+ "' says:\n" + "Hello " + _name + "!");
	}

	final private String _name;
	final private int _id;

}
