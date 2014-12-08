package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import servant.ClientPrinterI;
import util.ObjectFactory;
import Demo.*;

public class Client extends Ice.Application {

	class ShutdownHook extends Thread {
		public void run() {
			try {
				communicator().destroy();
			} catch (Ice.LocalException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void readline(BufferedReader in) {
		try {
			System.out.println("[press enter]");
			in.readLine();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public int run(String[] args) {

		setInterruptHook(new ShutdownHook());

		InitialPrx initial = InitialPrxHelper.checkedCast(communicator().propertyToProxy("Initial.Proxy"));
		if (initial == null) {
			System.err.println("invalid object reference");
			return 1;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("************测试Simple*************");
		System.out.println("首先我们从服务端获得一个不含有任何操作的Simple对象, 然后打印它的内容. 我们并没有对Simple对象配置Factory.");

		Simple simple = initial.getSimple();
		System.out.println("print simple message content ==> " + simple.message);

		readline(in);
		System.out.println("************测试Printer*************");
		System.out.println("\n在Printer不配置工厂的情况下,调用InitialPrx#getPrinter(), 随后会输出一个`no factory' exception.");

		PrinterHolder printer = new PrinterHolder();
		PrinterPrxHolder printerProxy = new PrinterPrxHolder();

		try {
			initial.getPrinter(printer, printerProxy);
			System.err.println("Did not get the expected NoObjectFactoryException!");
			System.exit(1);
		} catch (Ice.NoObjectFactoryException ex) {
			System.out.println("==> " + ex);
		}

		readline(in);
		System.out.println("现在对Printer配置上ObjectFactory, 再调用InitialPrx#getPrinter一次.");

		Ice.ObjectFactory factory = new ObjectFactory();
		communicator().addObjectFactory(factory, Demo.Printer.ice_staticId());

		initial.getPrinter(printer, printerProxy);

		System.out.println("输出printer.value.messag ==> " + printer.value.message);
		System.out.print("输出printBackwards ==> ");
		printer.value.printBackwards();

		System.out.println("接着我们在远程对象(PrinterPrxHolder)上调用相同的方法,注意看服务端的通知.");
		printerProxy.value.printBackwards();

		readline(in);
		System.out.println("************测试Printer 衍生*************");
		System.out.println("\n接下来我们传输一个基于服务端对象衍生出来的一个对象\n "
						+ "由于我们还没有对衍生类(Demo.DerivedPrinter)配置工厂, 它会被分配到它的基类(Demo.Printer).");

		Printer derivedAsBase = initial.getDerivedPrinter();
		System.out.println("==> The type ID of the received object is \"" + derivedAsBase.ice_id() + "\"");
		assert (derivedAsBase.ice_id().equals(Demo.Printer.ice_staticId()));

		readline(in);
		System.out.println("\n现在我们对衍生类配置工厂,然后再试一次.因为我们在接受衍生对象时候是作为基类对象接受的,"
						+ "\n因此我们需要手动地将其从基类转型到衍生类型.");

		communicator().addObjectFactory(factory, Demo.DerivedPrinter.ice_staticId());

		derivedAsBase = initial.getDerivedPrinter();
		DerivedPrinter derived = (Demo.DerivedPrinter) derivedAsBase;

		readline(in);
		System.out.println("==> class cast to derived object succeeded");
		System.out.println("==> The type ID of the received object is \"" + derived.ice_id() + "\"");

		System.out.println("现在我们打印出衍生对象里的message 同时在本地调用衍生对象 printUppercase() 方法");

		readline(in);

		System.out.println("==> " + derived.derivedMessage + "\n==> ");
		derived.printUppercase();

		System.out.println("现在我们来确认一下slice已经被[\"preserve-slice\"] 元数据隐藏了. "
				+ "\n我们在客户端创建一个衍生类型并且将它传递给服务端, 同时该衍生类型没有配置工厂."
				+ "\n我们会做一个类型强转以便确保我们仍能接受从服务端返回的衍生类型.");

		readline(in);

		ClientPrinter clientp = new ClientPrinterI();
		clientp.message = "a message 4 u"  + " - " + new Date().toLocaleString();
		communicator().addObjectFactory(factory, Demo.ClientPrinter.ice_staticId());

		derivedAsBase = initial.updatePrinterMessage(clientp);
		clientp = (Demo.ClientPrinter) derivedAsBase;
		System.out.println("==> " + clientp.message);

		System.out.println("Finally, we try the same again, but instead of returning the derived object, "
						+ "\n we throw an exception containing the derived object.");

		readline(in);

		try {
			initial.throwDerivedPrinter();
		} catch (DerivedPrinterException ex) {
			derived = ex.derived;
			assert (derived != null);
		}

		System.out.println("==> " + derived.derivedMessage + "\n==> ");
		derived.printUppercase();

		System.out.println("That's it for this demo. Have fun with Ice!");

		initial.shutdown();

		return 0;
	}

	public static void main(String[] args) {
		Client app = new Client();
		int status = app.main("Client", args, "config.client");
		System.exit(status);
	}
}
