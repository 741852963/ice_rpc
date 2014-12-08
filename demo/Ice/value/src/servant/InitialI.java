package servant;
import java.util.Date;

import Demo.*;

public class InitialI extends _InitialDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = 2937686649909086288L;

	private Simple _simple = new Simple();

	private Printer _printer = new PrinterI();
	private PrinterPrx _printerProxy;

	private DerivedPrinter _derivedPrinter = new DerivedPrinterI();

	public InitialI(Ice.ObjectAdapter adapter) {
		_simple.message = "simple message .init time: " + new Date().toLocaleString();

		_printer.message = "printer message .init time : " + new Date().toLocaleString();

		_printerProxy = PrinterPrxHelper.uncheckedCast(adapter.addWithUUID(_printer));

		_derivedPrinter.message = _printer.message;
		_derivedPrinter.derivedMessage = "derivedPrinter message .init time : " + new Date().toLocaleString();

		adapter.addWithUUID(_derivedPrinter);
	}

	public Simple getSimple(Ice.Current current) {
		return _simple;
	}

	public void getPrinter(PrinterHolder impl, PrinterPrxHolder proxy,
			Ice.Current current) {
		impl.value = _printer;
		proxy.value = _printerProxy;
	}

	public Printer getDerivedPrinter(Ice.Current current) {
		return _derivedPrinter;
	}

	public Printer updatePrinterMessage(Printer printer, Ice.Current current) {
		printer.message = "printer message updated time : " + new Date().toLocaleString();
		return printer;
	}

	public void throwDerivedPrinter(Ice.Current current)
			throws DerivedPrinterException {
		DerivedPrinterException ex = new DerivedPrinterException();
		ex.derived = _derivedPrinter;
		throw ex;
	}

	public void shutdown(Ice.Current current) {
		current.adapter.getCommunicator().shutdown();
	}

}
