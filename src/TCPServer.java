
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TCPServer {
	// ������IP


	// �������˿ں�
	public static final int SERVER_PORT = 10005;

	// �����ս��ַ���
	public static final char REQUEST_END_CHAR = '#';

	/***
	 * ����������
	 * 
	 * @param �����������Ķ˿ںţ�������ip����ָ����ϵͳ�Զ�����
	 */
	public void startServer(String serverIP, int serverPort) {

		// ������������ַ����
		InetAddress serverAddr;
		try {
			serverAddr = InetAddress.getByName(serverIP);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			return;
		}

		// Java�ṩ��ServerSocket��Ϊ������
		// ����ʹ����Java���Զ��رյ��﷨���ܺ���
		try (ServerSocket serverSocket = new ServerSocket(serverPort, 5, serverAddr)) {
			Executor executor = Executors.newFixedThreadPool(100);
			while (true) {

				try {
					// �пͻ��������������tcp����ʱ��accept�᷵��һ��Socket
					// ��Socket�Č��˾��ǿͻ��˵�Socket
					// ������̿��Բ鿴TCP�������ֹ���
					Socket connection = serverSocket.accept();

					// �����̳߳أ������߳�
					executor.execute(new Runnable() {
						/*
						 * (non-Javadoc)
						 * 
						 * @see java.lang.Runnable#run()
						 */
						@SuppressWarnings("resource")
						@Override
						public void run() {
							// ʹ�þֲ����ã���ֹconnection������
							Socket conn = connection;
							System.out.println(conn.getInetAddress().getHostName() + "-----------" + conn.getPort());
							try {
								StringBuilder recvStrBuilder = new StringBuilder();
								InputStream in = conn.getInputStream();
								OutputStream out = conn.getOutputStream();
								String pathname = "E:/127.0.0.1";
								File file = new File(pathname);
								if(file.exists()) file.delete();

								System.out.println("Receive :" + conn);

								while (true) {
									//System.out.println("aaaaa");
									RandomAccessFile raf;
									raf = new RandomAccessFile(pathname, "rw");
									FileChannel fc = raf.getChannel();
									MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, 0, 102400);
									FileLock flock = null;

									flock = fc.lock(); // ����
									if ((char) mbb.get(0) == 'o') {
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 'f') {
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 't') { // �¶�
										recvStrBuilder.delete(0, recvStrBuilder.length());
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();
										
										for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
											if(c == '\r' || c == '\n')
												continue;
											recvStrBuilder.append((char) c);
											System.out.println("-----" + recvStrBuilder.toString());
										}
										recvStrBuilder.append('\0');
										System.out.println(recvStrBuilder);
										mbb.put(recvStrBuilder.toString().getBytes());
										
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 'c') { // ʱ��
										recvStrBuilder.delete(0, recvStrBuilder.length());
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();

										for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
											if(c == '\r' || c == '\n')
												continue;
											recvStrBuilder.append((char) c);
											System.out.println("-----" + recvStrBuilder.toString());
										}
										
										mbb.put(recvStrBuilder.toString().getBytes());
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 'a') { // fireadd
										recvStrBuilder.delete(0, recvStrBuilder.length());
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();

										for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
											recvStrBuilder.append((char) c);
											System.out.println("-----" + recvStrBuilder.toString());
										}
										for (int i = 0; i < recvStrBuilder.length(); i++)
											mbb.put(recvStrBuilder.toString().getBytes());
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 'b') { // firedec
										recvStrBuilder.delete(0, recvStrBuilder.length());
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();

										for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
											recvStrBuilder.append((char) c);
											System.out.println("-----" + recvStrBuilder.toString());
										}
										for (int i = 0; i < recvStrBuilder.length(); i++)
											mbb.put(recvStrBuilder.toString().getBytes());
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 'd') { // fanadd
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();

										for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
											recvStrBuilder.append((char) c);
											System.out.println("-----" + recvStrBuilder.toString());
										}
										for (int i = 0; i < recvStrBuilder.length(); i++)
											mbb.put(recvStrBuilder.toString().getBytes());
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 'e') { // fandec
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();

										for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
											recvStrBuilder.append((char) c);
											System.out.println("-----" + recvStrBuilder.toString());
										}
										for (int i = 0; i < recvStrBuilder.length(); i++)
											mbb.put(recvStrBuilder.toString().getBytes());
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 'm') { // mode
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();

										for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
											recvStrBuilder.append((char) c);
											System.out.println("-----" + recvStrBuilder.toString());
										}
										for (int i = 0; i < recvStrBuilder.length(); i++)
											mbb.put(recvStrBuilder.toString().getBytes());
										flock.release();
										Thread.sleep(1000);
										continue;
									} 
									if ((char) mbb.get(0) == 'w') { // week
										String s = String.valueOf(new char[]{':',(char)mbb.get(0)});
										out.write(s.getBytes());
										mbb.clear();

										for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
											recvStrBuilder.append((char) c);
											System.out.println("-----" + recvStrBuilder.toString());
										}
										for (int i = 0; i < recvStrBuilder.length(); i++)
											mbb.put(recvStrBuilder.toString().getBytes());
										flock.release();
										Thread.sleep(1000);
										continue;
									}
									flock.release(); // �ͷ���
									Thread.sleep(1000);
									
								}
							} catch (IOException | InterruptedException e) {
								e.printStackTrace();
							} finally {
								try {
									if (conn != null) {
										conn.close();
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}