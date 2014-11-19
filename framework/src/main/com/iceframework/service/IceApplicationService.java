package com.iceframework.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.iceframework.base.IceConstant;
import com.iceframework.util.ClassFinder;

import Ice.Application;
import Ice.Identity;
import Ice.Properties;

/**
 * ʹ��ö�ٵ���
 * �ڲ���װ��Ice.Application
 *
 * @author Administrator
 *
 */
public enum IceApplicationService {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(IceApplicationService.class);

	private final RPCApplication app = new RPCApplication();;

	private IceApplicationService() {
		initCommunicator();	// TODO ����ݳ�
	}

	private void initCommunicator() {

		if(Application.communicator() == null) {

			app.setInterruptHook();

			// ����ͨ����
			CreateThread t = new CreateThread(app);
			t.start();

			// �ȴ�ͨ�����������
			while(t.getState() != Thread.State.WAITING) {}	// TODO ����

			// ���ع����������ļ�
			Properties props = Application.communicator().getProperties();
			props.load(IceConstant.CONFIG_SERVER_FILE.toString());

		}
	}

	/**
	 * ͨ��String file�����ط������������ļ�
	 * @param file
	 */
	public void startSever() {

		logger.info("Loading Properties And Add Servants");

		// ���ط���������ļ�
		Properties props = Application.communicator().getProperties();
		props.load(IceConstant.CONFIG_ENDPOINTS_FILE.toString());

		// ע��Servant
		String servantHome = props.getProperty("servant_home");
		for (String servant : servantHome.split(";")) {
			logger.info("Servant Home : " + servant);
			app.addServantBy(servant);
		}

	}

	/**
	 * ͨ��String file�����ؿͻ��˵������ļ�
	 * @param file
	 */
	public void startClient() {

		// ���ؿͻ��������ļ�
		Properties props = Application.communicator().getProperties();
		props.load(IceConstant.CONFIG_PROXY_FILE.toString());

	}


	private static class CreateThread extends Thread {

		private Logger ctLogger = Logger.getLogger(CreateThread.class);

		private RPCApplication app ;

		CreateThread (RPCApplication app) {
			this.app = app;
		}

		@Override
		public void run() {
			ctLogger.info("Ice.Application.main()");
			// ��RPCApplication ���_communicator ��ʼ��,��֤����app��ֻ��һ��_communicator
			// ʵ�ַ���˺Ϳͻ��˶�_communicator�Ĺ���
			app.main("Service", new String[0]);
		}
	}

	/**
	 * IceRuntimeά�֣��ͻ����̳߳�, ���������̳߳�, ��������, ���󹤳�,
	 * 				   һ����־��¼������, һ��ͳ�ƶ���, һ��ȱʡ·����,
	 * 				   һ��ȱʡ��λ��, һ�����������
	 *
	 * ����ͨ��communicator() �����������ģ��
	 *
	 * @author Administrator
	 *
	 */
	private static class RPCApplication extends Ice.Application {

		private Logger rpcLogger = Logger.getLogger(RPCApplication.class);

		@Override
		public int run(String[] args) {
			/**
			 * �ڲ�������Object��wait()
			 */
			rpcLogger.info("RPCApplication waitForShutdown");
			communicator().waitForShutdown();
			return 0;
		}

		public void addServantBy(String path) {
			try {

				List<Class> servants = ClassFinder.findClasses(path, "I");
				for (Class clazz : servants) {
					String classNameI = clazz.getSimpleName();
					String className = classNameI.substring(0, classNameI.length() - 1);

					Ice.ObjectAdapter adapter = communicator().createObjectAdapter(className);
					Identity identity = communicator().stringToIdentity(className);
					adapter.add((Ice.Object) clazz.newInstance(), identity);
					rpcLogger.info("Add Servant : " + className + ".Endpoints" + " --- " + identity);
					adapter.activate();
				}


			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		public void setInterruptHook() {
			rpcLogger.info("setInterruptHook");
			setInterruptHook(new ShutdownHook());
		}

		class ShutdownHook extends Thread {
			public void run() {
				try {
					rpcLogger.info("Communicator Start Destroy");
					communicator().destroy();
					rpcLogger.info("Communicator Destroy Over");
				} catch (Ice.LocalException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
