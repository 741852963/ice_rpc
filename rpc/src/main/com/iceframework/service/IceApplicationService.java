package com.iceframework.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.iceframework.base.IceConstant;
import com.iceframework.util.ClassFinder;

import Ice.Application;
import Ice.Identity;
import Ice.Properties;

/**
 * 使用枚举单例
 * 内部封装了Ice.Application
 *
 * @author Administrator
 *
 */
public enum IceApplicationService {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(IceApplicationService.class);

	private final RPCApplication app = new RPCApplication();;

	private IceApplicationService() {
		initCommunicator();	// TODO 检查逸出
	}

	private void initCommunicator() {

		if(Application.communicator() == null) {

			app.setInterruptHook();

			// 构建通信器
			CreateThread t = new CreateThread(app);
			t.start();

			// 等待通信器构造完成
			while(t.getState() != Thread.State.WAITING) {}		// TODO 性能

			// 加载公共的配置文件
			Properties props = Application.communicator().getProperties();
			props.load(IceConstant.CONFIG_SERVER_FILE.toString());

		}
	}

	/**
	 * 通过String file来加载服务器的配置文件
	 * @param file
	 */
	public void startSever(String servantHomeStr, String endpoints) {

		logger.info("Loading Properties -- " + IceConstant.CONFIG_ENDPOINTS_FILE.toString());

		// 加载服务端配置文件
		Properties props = Application.communicator().getProperties();
		props.load(IceConstant.CONFIG_ENDPOINTS_FILE.toString());

		// 注册Servant
		String servantHome = props.getProperty(servantHomeStr);
		for (String servant : servantHome.split(";")) {
			logger.info("Servant Home : " + servant);
			app.addServantBy(servant, endpoints);
		}

	}




	/**
	 * ͨ 通过String file来加载客户端的配置文件
	 * @param file
	 */
	public void startClient(String proxyPath) {

		// 加载客户端配置文件
		Properties props = Application.communicator().getProperties();
		props.load(proxyPath);

		logger.debug("Client init config ");
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
			// 对RPCApplication 里的_communicator 初始化,保证整个app中只有一个_communicator
			// 实现服务端和客户端对_communicator的共享
			app.main("Service", new String[0]);
		}
	}

	/**
	 * IceRuntime维持：客户端线程池, 服务器端线程池, 配置属性, 对象工厂,
	 * 				   一个日志记录器对象, 一个统计对象, 一个缺省路由器,
	 * 				   一个缺省定位器, 一个插件管理器
	 *
	 * 可以通过communicator() 获得上述部分模块
	 *
	 * @author Administrator
	 *
	 */
	private static class RPCApplication extends Ice.Application {

		private Logger rpcLogger = Logger.getLogger(RPCApplication.class);
		private Ice.ObjectAdapter adapter = null;
		@Override
		public int run(String[] args) {
			/**
			 * 内部调用了Object的wait()
			 */
			rpcLogger.info("RPCApplication waitForShutdown");
			communicator().waitForShutdown();
			return 0;
		}

		public void addServantBy(String path, String endpoints) {
			try {

				if(adapter == null)
					adapter = communicator().createObjectAdapter(endpoints);

				List<Class> servants = ClassFinder.findClasses(path, "I");
				for (Class clazz : servants) {
					String classNameI = clazz.getSimpleName();
					String className = classNameI.substring(0, classNameI.length() - 1);

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
