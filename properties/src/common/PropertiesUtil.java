package common;
import java.util.Map;

import Ice.Communicator;
import Ice.Properties;

public class PropertiesUtil {

	public static void printProperties(Communicator communicator) {
		Properties properties = communicator.getProperties();
		// 这个操作把以指定前缀起头的所有属性、作为PropertyDict 类型的词典返回。如果你想要提取指定的子系统的各个属性，这个操作很有用
		Map<String, String> map = properties.getPropertiesForPrefix("");
		for (String name : map.keySet()) {
			print(properties, name);
		}
	}

	private static void print(Properties properties, String name) {
		System.out.println(".........." + name + "..........");
		// 这个操作返回指定属性的值。如果该属性没有设置，操作返回空串。
		System.out.println("Value          :" + properties.getProperty(name));
		// 这个操作返回指定属性的值。如果该属性没有设置，操作返回你提供的缺省值。
		System.out.println("DefaultValue   :" + properties.getPropertyWithDefault(name, "none"));
		// 这个操作把指定属性的值作为整数返回。如果属性没有设置，或者包含的是不能解析成整数的串，操作返回零。
//		System.out.println("IntValue       :" + properties.getPropertyAsInt(name));
		// 这个操作把指定属性的值作为整数返回。如果属性没有设置，或者 包含的是不能解析成整数的串，操作返回你提供的缺省值。
//		System.out.println("DefaultIntValue:" + properties.getPropertyAsIntWithDefault(name, -1));

	}
}
