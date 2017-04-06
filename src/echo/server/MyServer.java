/* 2017.04.06
 * �ڹٸ� �̿��Ͽ� ���� �� ���α׷��� �ۼ��Ѵ�.
 * ��ȭ�� �޴¿�.
 */
package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	// ��ȭ�� ���������� ������ �˷��ֱ� ���� ��ü
	// �� ���� ��ȭ�� �� ������.
	// ������ Ŭ���̾�Ʈ�� ã�ƿ��� ��ٸ��Ƿ� Ŭ���̾�Ʈ�� ����� ��Ʈ��ȣ�� �����ϸ� �ȴ�.
	// ��Ģ�� ��Ʈ��ȣ�� �����Ӱ� ���ϸ� �ȴ�.
	// ����) 0~1023 �� �ý����� �����ϰ� �ִ� ��Ʈ
	// ����) ������ ���α׷����� ������ (����Ŭ 1521, MySql 3306, �������� 80..)
	ServerSocket  server;
	int port=8888;
	Socket  socket;
	
	public MyServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("���� ����");
			
			// Ŭ���̾�Ʈ�� ������ ��ٸ���.
			// ������ ���������� ���Ѵ�� 
			// ��, ������ ������. ��ġ ��Ʈ���� read �迭�� ���� ����.
			while (true){
				socket = server.accept();
				System.out.println("������ �߰�");
				
				// ������ �̿��Ͽ� �����͸� �ް����ϴ� ��쿣 �Է½�Ʈ���� 
				// �����͸� ���������� �Ѵ� ��쿣 ��½�Ʈ��
				InputStream is =socket.getInputStream();
				InputStreamReader reader=null;
				reader = new InputStreamReader(is); // �ѱ� �ȱ�����
				
				int data;
				while(true){
					data=reader.read();
					System.out.print((char)data);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MyServer();
	}
}
