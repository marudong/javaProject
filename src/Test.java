import java.net.UnknownHostException;

public class Test {
	// 服务器IP
	

	// 服务器端口号
	public static final int SERVER_PORT = 10005;


	public static void main(String[] args) throws UnknownHostException {
		//final String SERVER_IP = InetAddress.getLocalHost().getHostAddress();//获得本机IP
		String SERVER_IP = "127.0.0.1";
		// TODO Auto-generated method stub
		System.out.println("IP:" + SERVER_IP);
		TCPServer server = new TCPServer();
		server.startServer(SERVER_IP, SERVER_PORT);
	}

}
