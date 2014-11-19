package com.iceframework.base;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.iceframework.util.ClassFinder;

import Ice.Application;
import Ice.ObjectPrx;
import Ice.Properties;

/**
 * rpc ���Ի�����
 * @author Administrator
 *
 */
public enum IceCache {

	INSTANCE;

	private final Logger logger = Logger.getLogger(IceCache.class);

	// value�ڷ���֮����ΪObject����,������get֮����ҪObjectPrx ����ȥ���䷺�Ͳ���
	private final Map cache = new HashMap();

	private IceCache() {
		Properties props = Application.communicator().getProperties();
		// ��ʼ��������
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

			if(arrays.length >= 1)
				logger.info("Loaded PrxHelper : " + prxHelper.getSimpleName());
			else
				throw new RuntimeException("PrxHelper cant found. " + arrays.length);

			ObjectPrx op = Application.communicator()
					.propertyToProxy(arrays[0] + ".Proxy").ice_twoway()
					.ice_timeout(-1).ice_secure(false);

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
					Object twoway = checkedCastMethod.invoke(prxHelper, op);
					logger.debug("class info : " + arrays[0] + "..." + twoway.getClass().getSimpleName());

					cache.put(arrays[0], twoway);
				} catch (final Exception e) {
					e.printStackTrace();
				}
				break;
			}
			logger.info("Loaded Proxy : " + arrays[0] + "... " + op);

		}

	}

	public ObjectPrx getObjectPrx(String key) {
		logger.debug("get : key -- " + key + "..." + cache.get(key).getClass().getSimpleName());
		return (ObjectPrx)cache.get(key);
	}
}
