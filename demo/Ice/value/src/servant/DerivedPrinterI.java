package servant;
import java.util.Date;

import Demo.*;

public class DerivedPrinterI extends DerivedPrinter {
	/**
	 *
	 */
	private static final long serialVersionUID = -8023562201068084502L;

	public void printBackwards(Ice.Current current) {

		char[] arr = message.toCharArray();

//		for (int i = 0; i < arr.length / 2; i++) {
//			char tmp = arr[arr.length - i - 1];
//			arr[arr.length - i - 1] = arr[i];
//			arr[i] = tmp;
//		}

		System.out.println("DerivedPrinterI : " + new String(arr) + ". invoke time: " + new Date().toLocaleString());
	}

	public void printUppercase(Ice.Current current) {
		System.out.println(derivedMessage.toUpperCase());
	}
}
