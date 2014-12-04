package com.iceframework.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.iceframework.util.ClassFinder;

import Ice.Identity;
import Ice.Properties;

/**
 *
 * @author Administrator
 *
 */
public enum IceUtilService {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(IceApplicationService.class);

	private final static Ice.Communicator communicator;

	static {

		communicator = Ice.Util.initialize(new String[] {});
		try {
			logger.debug("IceUtilService static initial");
			communicator.waitForShutdown();
			communicator.destroy();
		} catch (Ice.LocalException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public void startServer(String file) {

		Properties props = loadProperties(file);
		addServants(props);

	}

	public Properties loadProperties(String file) {
		logger.info("Loading Properties");
		Properties props = communicator.getProperties();
		props.load(file);

		return props;
	}

	/**
	 * ע��Servant
	 *
	 * @param holder
	 */
	private void addServants(Properties props) {
		logger.info("Add Servants");

		String servantHome = props.getProperty("servant_home");
		for (String servant : servantHome.split(";")) {
			addServant(servant);
		}
	}

	private void addServant(String holder) {

		try {
			Ice.ObjectAdapter adapter = communicator
					.createObjectAdapterWithEndpoints("Hello", "tcp -h localhost -p " + 2020);

			List<Class> servants = ClassFinder.findClasses(holder, "I");
			for (Class clazz : servants) {
				Identity identity = communicator.stringToIdentity(clazz
						.getCanonicalName());
				adapter.add((Ice.Object) clazz.newInstance(), identity);
			}

			adapter.activate();

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
