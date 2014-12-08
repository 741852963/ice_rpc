package servant;
import java.util.Date;

import Demo.*;

public class PrinterI extends Printer {
	/**
	 *
	 */
	private static final long serialVersionUID = -4161653266866867411L;

	public void printBackwards(Ice.Current current) {
		char[] arr = message.toCharArray();

//		for (int i = 0; i < arr.length / 2; i++) {
//			char tmp = arr[arr.length - i - 1];
//			arr[arr.length - i - 1] = arr[i];
//			arr[i] = tmp;
//		}

		System.out.println("PrinterI : " + new String(arr) + ". invoke time: " + new Date().toLocaleString());
	}
}
