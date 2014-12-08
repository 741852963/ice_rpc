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

		System.out.println("************����Simple*************");
		System.out.println("�������Ǵӷ���˻��һ���������κβ�����Simple����, Ȼ���ӡ��������. ���ǲ�û�ж�Simple��������Factory.");

		Simple simple = initial.getSimple();
		System.out.println("print simple message content ==> " + simple.message);

		readline(in);
		System.out.println("************����Printer*************");
		System.out.println("\n��Printer�����ù����������,����InitialPrx#getPrinter(), �������һ��`no factory' exception.");

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
		System.out.println("���ڶ�Printer������ObjectFactory, �ٵ���InitialPrx#getPrinterһ��.");

		Ice.ObjectFactory factory = new ObjectFactory();
		communicator().addObjectFactory(factory, Demo.Printer.ice_staticId());

		initial.getPrinter(printer, printerProxy);

		System.out.println("���printer.value.messag ==> " + printer.value.message);
		System.out.print("���printBackwards ==> ");
		printer.value.printBackwards();

		System.out.println("����������Զ�̶���(PrinterPrxHolder)�ϵ�����ͬ�ķ���,ע�⿴����˵�֪ͨ.");
		printerProxy.value.printBackwards();

		readline(in);
		System.out.println("************����Printer ����*************");
		System.out.println("\n���������Ǵ���һ�����ڷ���˶�������������һ������\n "
						+ "�������ǻ�û�ж�������(Demo.DerivedPrinter)���ù���, ���ᱻ���䵽���Ļ���(Demo.Printer).");

		Printer derivedAsBase = initial.getDerivedPrinter();
		System.out.println("==> The type ID of the received object is \"" + derivedAsBase.ice_id() + "\"");
		assert (derivedAsBase.ice_id().equals(Demo.Printer.ice_staticId()));

		readline(in);
		System.out.println("\n�������Ƕ����������ù���,Ȼ������һ��.��Ϊ�����ڽ�����������ʱ������Ϊ���������ܵ�,"
						+ "\n���������Ҫ�ֶ��ؽ���ӻ���ת�͵���������.");

		communicator().addObjectFactory(factory, Demo.DerivedPrinter.ice_staticId());

		derivedAsBase = initial.getDerivedPrinter();
		DerivedPrinter derived = (Demo.DerivedPrinter) derivedAsBase;

		readline(in);
		System.out.println("==> class cast to derived object succeeded");
		System.out.println("==> The type ID of the received object is \"" + derived.ice_id() + "\"");

		System.out.println("�������Ǵ�ӡ�������������message ͬʱ�ڱ��ص����������� printUppercase() ����");

		readline(in);

		System.out.println("==> " + derived.derivedMessage + "\n==> ");
		derived.printUppercase();

		System.out.println("����������ȷ��һ��slice�Ѿ���[\"preserve-slice\"] Ԫ����������. "
				+ "\n�����ڿͻ��˴���һ���������Ͳ��ҽ������ݸ������, ͬʱ����������û�����ù���."
				+ "\n���ǻ���һ������ǿת�Ա�ȷ���������ܽ��ܴӷ���˷��ص���������.");

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
