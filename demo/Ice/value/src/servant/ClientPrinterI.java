package servant;
import java.util.Date;

import Demo.*;

public class ClientPrinterI extends ClientPrinter {
	/**
	 *
	 */
	private static final long serialVersionUID = 3889287973669067211L;

	public void printBackwards(Ice.Current current) {
		char[] arr = message.toCharArray();

//		for (int i = 0; i < arr.length / 2; i++) {
//			char tmp = arr[arr.length - i - 1];
//			arr[arr.length - i - 1] = arr[i];
//			arr[i] = tmp;
//		}

		System.out.println("DerivedPrinterI : " + new String(arr) + ". invoke time: " + new Date().toLocaleString());
	}
}
