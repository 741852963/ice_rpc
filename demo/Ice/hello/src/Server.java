
/**
 * ICE的优点
 *
 * 1、跨平台，支持多种语言
 * 2、面向对象编程
 * 3、为分布式应用方面提供了一整套 强大的特性和功能支持（例如负载平衡、软件分发、数据同步等）。
 * 4、在网络带宽、内存使用和CPU开销方面已经内置了高效的实现。
 * 5、内置了安全特征的实现，可跨越不安全的网络（广域网）使用。
 * 6、降低了复杂性，易于学习和使用。
 *
 * @author Administrator
 *
 */
public class Server extends Ice.Application {

	public int run(String[] args) {
		if (args.length > 0) {
			System.err.println(appName() + ": too many arguments");
			return 1;
		}

		// Adapter 是配置相当于服务单位 （Servant）的管理者，它管理着每个请求该分配给哪一个服务单位。
		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Hello");
		// HelloI(Servant) 它是服务的最小单位，一般是具体到 某个接口的实现
		boolean isAddFacet = false;
		if(isAddFacet)
			// ICE内部集成非常好的版本 控制功能（Facet），在每一个服务单元（Servant）其实都有一个Facet与之并存，如果你没有认为指定，那么这个默认Facet的值就为空
			adapter.addFacet(new HelloI(), communicator().stringToIdentity("hello"), "revision");
		else
			adapter.add(new HelloI(), communicator().stringToIdentity("hello"));


		adapter.activate();

		communicator().waitForShutdown();
		return 0;
	}

	public static void main(String[] args) {
		Server app = new Server();
		int status = app.main("Server", args, "config.server");
		System.exit(status);
	}
}
