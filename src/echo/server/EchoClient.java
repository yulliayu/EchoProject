/*<2017.04.06>
 */
package echo.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EchoClient extends JFrame {
	
	JTextArea area;
	JScrollPane  scroll;
	JPanel  p_south;
	JTextField t_input;
	JButton bt_connect;
	
	// ��ȭ�� ������ �ִ� ����
	// �� �ɶ��� �������� ���� ��½�Ʈ��
	// �� �������� �������� ���� �Է½�Ʈ��
	Socket  socket;
	BufferedReader buffr; // û���
	BufferedWriter buffw; // ���ɱ� ��
	
	public EchoClient() {

		area = new JTextArea(15,15);
		scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension(300, 320));
		
		p_south = new JPanel();
		t_input = new JTextField(17);
		bt_connect = new JButton("����");
		
		p_south.add(t_input);
		p_south.add(bt_connect);		
		add(scroll);
		add(p_south, BorderLayout.SOUTH);
		
		bt_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// ���� ġ��
				int key = e.getKeyCode();
				if (key==KeyEvent.VK_ENTER){
					send();
				}
			}
		});
		
		setVisible(true);
		setSize(300, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// ������ �� ������
	public void send(){
		// �ؽ�Ʈ ������ �޼��� �� ���
		String msg = t_input.getText();
		try {
			buffw.write(msg+"\n"); // ��Ʈ���� ���� ���. ��, �������� ���Ͽ� ������ ����. 
			                                     // \n �� �־�� �Է��� �������� �ȴ�.
			buffw.flush(); // ���ۿ� ���� �������� �� �����͸� ������� ��� ��½��� ������.
			
			// �������� ���ƿ� �޼����� area �� ����� ����
			String data = buffr.readLine();
			area.append(data+"\n");			
			t_input.setText("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ������ �����ϴ� �޼���
	public void connect(){
		
			try {
				socket = new Socket("localhost", 7777);
				//socket = new Socket("211.238.142.98", 7777); // ����pc
				
				// ������ �Ϸ�Ǿ�����, ��Ʈ�� ��� ����
				// ��? ��ȭ�� ��������
				buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		new EchoClient();

	}

}
