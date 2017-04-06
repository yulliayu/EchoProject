/*<2017.04.06>
 * ���� ���α׷��̶� ?
 * Ŭ���̾�Ʈ�� �޼����� �״�� �ٽ� �����ϴ� ����� ����
 * ä�� ���� 1�ܰ�
 */
package echo.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	ServerSocket  server;
	int port = 7777;
		
	public EchoServer() {
		try {
			// ���� ����
			server = new ServerSocket(port);
			System.out.println("���� ����");			
			
			Socket socket = server.accept(); // �����ڰ� ���� ������ ���� ���
			InetAddress  inet = socket.getInetAddress();
			String ip = inet.getHostAddress();
			
			System.out.println( ip+" �� ������ �߰�");
			
			// Ŭ���̾�Ʈ�� �����͸� �ޱ� ���� �Է½�Ʈ�� ���
			// ����Ʈ --> ���� --> ����
			
			// ��� ��
			BufferedReader buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));			
			// ���ϱ� ��
			BufferedWriter buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			// Ŭ���̾�Ʈ�� �� ���
			String msg;
			
			// ��ȭ�� ��� ����������, ����ΰ� �Ʒ��� while �� �Ʒ��� ���� �����Ƿ�,
			// ���̻� �߰� �����ڿ� ���� ���� ����� �Ұ��ϴ�.
			// ��� : ���� ���� ���� ���� ����� ����
			while(true){
				// ���
				msg= buffr.readLine();
				System.out.println("Ŭ���̾�Ʈ�� ���� �� : "+msg);
				
				// �޼��� �ٽ� ������
				buffw.write(msg+"\n"); // �� �ٺ�����
				buffw.flush(); // ���� ����
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoServer();
	}

}
