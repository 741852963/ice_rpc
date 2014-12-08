package util;

import servant.ClientPrinterI;
import servant.DerivedPrinterI;
import servant.PrinterI;

public class ObjectFactory implements Ice.ObjectFactory {

	@Override
	public Ice.Object create(String type) {
		if (type.equals(Demo.Printer.ice_staticId())) {
			return new PrinterI();
		}

		if (type.equals(Demo.DerivedPrinter.ice_staticId())) {
			return new DerivedPrinterI();
		}

		if (type.equals(Demo.ClientPrinter.ice_staticId())) {
			return new ClientPrinterI();
		}

		assert (false);
		return null;
	}

	@Override
	public void destroy() {
		// Nothing to do
	}
}
