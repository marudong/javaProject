import java.net.UnknownHostException;

public class Test {
	// ������IP
	

	// �������˿ں�
	public static final int SERVER_PORT = 10005;


	public static void main(String[] args) throws UnknownHostException {
		//final String SERVER_IP = InetAddress.getLocalHost().getHostAddress();//��ñ���IP
		String SERVER_IP = "127.0.0.1";
		// TODO Auto-generated method stub
		System.out.println("IP:" + SERVER_IP);
		TCPServer server = new TCPServer();
		server.startServer(SERVER_IP, SERVER_PORT);
	}

}
