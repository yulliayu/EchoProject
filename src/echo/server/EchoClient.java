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
	
	// 대화를 나눌수 있는 소켓
	// 말 걸때는 소켓으로 부터 출력스트림
	// 말 들을때는 소켓으로 부터 입력스트림
	Socket  socket;
	BufferedReader buffr; // 청취용
	BufferedWriter buffw; // 말걸기 용
	
	public EchoClient() {

		area = new JTextArea(15,15);
		scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension(300, 320));
		
		p_south = new JPanel();
		t_input = new JTextField(17);
		bt_connect = new JButton("접속");
		
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
				// 엔터 치면
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
	
	// 서버에 말 보내기
	public void send(){
		// 텍스트 상자의 메세지 값 얻기
		String msg = t_input.getText();
		try {
			buffw.write(msg+"\n"); // 스트림을 통해 출력. 즉, 서버측의 소켓에 데이터 전송. 
			                                     // \n 을 넣어야 입력이 끝났음을 안다.
			buffw.flush(); // 버퍼에 남아 있을지도 모를 데이터를 대상으로 모두 출력시켜 버린다.
			
			// 서버에서 날아온 메세지를 area 에 출력해 보자
			String data = buffr.readLine();
			area.append(data+"\n");			
			t_input.setText("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 서버에 접속하는 메서드
	public void connect(){
		
			try {
				socket = new Socket("localhost", 7777);
				//socket = new Socket("211.238.142.98", 7777); // 교사pc
				
				// 접속이 완료되었으니, 스트림 얻어 놓자
				// 왜? 대화를 나누려고
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
