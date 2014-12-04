package common;
import java.util.Map;

import Ice.Communicator;
import Ice.Properties;

public class PropertiesUtil {

	public static void printProperties(Communicator communicator) {
		Properties properties = communicator.getProperties();
		// �����������ָ��ǰ׺��ͷ���������ԡ���ΪPropertyDict ���͵Ĵʵ䷵�ء��������Ҫ��ȡָ������ϵͳ�ĸ������ԣ��������������
		Map<String, String> map = properties.getPropertiesForPrefix("");
		for (String name : map.keySet()) {
			print(properties, name);
		}
	}

	private static void print(Properties properties, String name) {
		System.out.println(".........." + name + "..........");
		// �����������ָ�����Ե�ֵ�����������û�����ã��������ؿմ���
		System.out.println("Value          :" + properties.getProperty(name));
		// �����������ָ�����Ե�ֵ�����������û�����ã������������ṩ��ȱʡֵ��
		System.out.println("DefaultValue   :" + properties.getPropertyWithDefault(name, "none"));
		// ���������ָ�����Ե�ֵ��Ϊ�������ء��������û�����ã����߰������ǲ��ܽ����������Ĵ������������㡣
//		System.out.println("IntValue       :" + properties.getPropertyAsInt(name));
		// ���������ָ�����Ե�ֵ��Ϊ�������ء��������û�����ã����� �������ǲ��ܽ����������Ĵ��������������ṩ��ȱʡֵ��
//		System.out.println("DefaultIntValue:" + properties.getPropertyAsIntWithDefault(name, -1));

	}
}
