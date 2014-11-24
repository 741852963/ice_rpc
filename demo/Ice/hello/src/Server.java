
/**
 * ICE���ŵ�
 *
 * 1����ƽ̨��֧�ֶ�������
 * 2�����������
 * 3��Ϊ�ֲ�ʽӦ�÷����ṩ��һ���� ǿ������Ժ͹���֧�֣����縺��ƽ�⡢����ַ�������ͬ���ȣ���
 * 4������������ڴ�ʹ�ú�CPU���������Ѿ������˸�Ч��ʵ�֡�
 * 5�������˰�ȫ������ʵ�֣��ɿ�Խ����ȫ�����磨��������ʹ�á�
 * 6�������˸����ԣ�����ѧϰ��ʹ�á�
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

		// Adapter �������൱�ڷ���λ ��Servant���Ĺ����ߣ���������ÿ������÷������һ������λ��
		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Hello");
		// HelloI(Servant) ���Ƿ������С��λ��һ���Ǿ��嵽 ĳ���ӿڵ�ʵ��
		boolean isAddFacet = false;
		if(isAddFacet)
			// ICE�ڲ����ɷǳ��õİ汾 ���ƹ��ܣ�Facet������ÿһ������Ԫ��Servant����ʵ����һ��Facet��֮���棬�����û����Ϊָ������ô���Ĭ��Facet��ֵ��Ϊ��
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
