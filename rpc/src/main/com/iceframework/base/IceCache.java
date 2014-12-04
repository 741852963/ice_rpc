package com.iceframework.base;

import Ice.Application;
import Ice.ObjectPrx;
import Ice.Properties;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.iceframework.util.ClassFinder;

/**
 * rpc ���Ի�����
 * @author Administrator
 *
 */
public enum IceCache {

	INSTANCE;

	private final Logger logger = Logger.getLogger(IceCache.class);

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	// value�ڷ���֮����ΪObject����,������get֮����ҪObjectPrx ����ȥ���䷺�Ͳ���
	private final Table<String, String, ObjectPrx> proxyCache =  HashBasedTable.create();
	private final Map<String, Class> prxHelperCache = new ConcurrentHashMap<>();

	private IceCache() {
		Properties props = Application.communicator().getProperties();
	//  ��ʼ��������
		String proxypaths = props.getProperty(IceConstant.PROXY_PATH.toString());
		for (String proxypath : proxypaths.split(";")) {
			logger.info("Proxy Path :" + proxypath);
			init(proxypath);
		}
	}

	private void init(String path) {

		// �ҵ����е�PrxHelper
		List<Class> prxHelpers = ClassFinder.findClasses(path, "PrxHelper");

		for (Class prxHelper : prxHelpers) {

			String[] arrays = prxHelper.getSimpleName().split("PrxHelper");

			if(arrays.length < 1)
				throw new RuntimeException("PrxHelper cant found. " + arrays.length);

			String name = arrays[0].split("PrxHelper")[0];

			prxHelperCache.put(name, prxHelper);
		}

	}

	private void load(String name, String property) {

		ObjectPrx op = null;
		Class prxHelper = prxHelperCache.get(name);
		try {
			op = Application.communicator()
					.propertyToProxy(name + ".Proxy").ice_twoway()
					.ice_timeout(-1).ice_secure(false);
			logger.debug("ProxyHelper:" + prxHelper.getSimpleName() + " -- Proxy : " + op);

		}catch(final Exception e) {
			logger.debug("Load Proxy error :" + name);
			e.printStackTrace();
			return;
		}

		for (Method checkedCastMethod : prxHelper.getMethods()) {

			// �ҵ�checkedCast����,��op ת���� XxxPrx
			if(!checkedCastMethod.getName().equals("checkedCast")) {
				continue;
			}
			Type[] pars = checkedCastMethod.getParameterTypes();
			if(pars.length != 1)
				continue;

			try {
				// ����opת���� XxxPrx
				ObjectPrx twoway = (ObjectPrx)checkedCastMethod.invoke(prxHelper, op);

				lock.writeLock().lock();
				proxyCache.put(property, name, twoway);
				lock.writeLock().unlock();
			} catch (final Exception e) {
				logger.info("checkedCast error:\n" + prxHelper.getCanonicalName() + " -- " + name + "\n" + op);
				e.printStackTrace();
			}
			break;
		}

	}

	public ObjectPrx getObjectPrx(String name, String...targets) {

		String target = name;
		if(targets.length == 1) {
			target += ":" + targets[0];
			Properties props = Application.communicator().getProperties();
			props.setProperty(name + ".Proxy", target);
		}

		lock.readLock().lock();
		ObjectPrx prx = proxyCache.get(target, name);
		lock.readLock().unlock();
		if(prx == null) {
			load(name, target);
		}

		lock.readLock().lock();
		prx = proxyCache.get(target, name);
		lock.readLock().unlock();

		return prx;
	}
}
