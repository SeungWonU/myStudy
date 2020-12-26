// JavaObjClient.java
// ObjecStream 사용하는 채팅 Client

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.awt.event.ActionEvent;

public class JavaGameClientMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;
	private JButton btnPink,btnYellow,btnBlue,btnGray;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaGameClientMain frame = new JavaGameClientMain();
					frame.setTitle("캐치 마인드 게임");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JavaGameClientMain() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 297, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Name : ");
		lblNewLabel.setBounds(22, 22, 82, 33);
		contentPane.setBackground(new Color(0x91acdb));
		contentPane.add(lblNewLabel);
		
		txtUserName = new JTextField();
		txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserName.setBounds(126, 22, 133, 33);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblIpAddress = new JLabel("Profile Image 선택");
		lblIpAddress.setBounds(22, 65, 143, 33);
		contentPane.add(lblIpAddress);
		
		txtIpAddress = new JTextField();
		txtIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
		txtIpAddress.setText("127.0.0.1");
		txtIpAddress.setColumns(10);
		txtIpAddress.setBounds(101, 100, 116, 33);
	//	contentPane.add(txtIpAddress);
		
//		JLabel lblPortNumber = new JLabel("Port Number");
//		lblPortNumber.setBounds(12, 163, 82, 33);
//		contentPane.add(lblPortNumber);
//		
		txtPortNumber = new JTextField();
		txtPortNumber.setText("30000");
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setColumns(10);
		txtPortNumber.setBounds(101, 163, 116, 33);
	//	contentPane.add(txtPortNumber);
		
		JButton btnConnect = new JButton("게임 입장하기");
		btnConnect.setBounds(22, 324, 237, 41);
		btnConnect.setFont(new Font("굴림", Font.BOLD, 16));
		btnConnect.setForeground(Color.white);
		//btnConnect.setBackground(new Color(88, 88, 88));
		
		btnConnect.setBackground(new Color(0x50669f));
		contentPane.add(btnConnect);
		
		ImageIcon iconPink = new ImageIcon("src/1234.PNG");
		ImageIcon iconYellow = new ImageIcon("src/8.PNG");
		ImageIcon iconBlue = new ImageIcon("src/j.PNG");
		ImageIcon iconGray = new ImageIcon("src/123.PNG");

		btnPink= new JButton(iconPink);
		btnPink.setHorizontalTextPosition(JButton.CENTER);
		btnPink.setBounds(22, 108, 105, 97);
		contentPane.add(btnPink);
		
		btnYellow = new JButton( iconYellow);
		btnYellow.setHorizontalTextPosition(JButton.CENTER);
		btnYellow.setBounds(154, 108, 105, 97);
		contentPane.add(btnYellow);
		
		btnBlue = new JButton( iconBlue);
		btnBlue.setHorizontalTextPosition(JButton.CENTER);
		btnBlue.setBounds(22, 216, 105, 97);
		contentPane.add(btnBlue);
		
		btnGray = new JButton(iconGray);
		btnGray.setHorizontalTextPosition(JButton.CENTER);
		btnGray.setBounds(154, 216,105, 97);
		contentPane.add(btnGray);
		
		Myaction action = new Myaction();
		btnConnect.addActionListener(action);
		txtUserName.addActionListener(action);
		txtIpAddress.addActionListener(action);
		txtPortNumber.addActionListener(action);
	//	btnRed.addActionListener(action);
		btnPink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPink.setText("Clicked");
				btnPink.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다	
				}
			});
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnYellow.setText("Clicked");
				btnYellow.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다	
				}
			});
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBlue.setText("Clicked");
				btnBlue.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다	
				}
			});
		btnGray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGray.setText("Clicked");
				btnGray.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다	
				}
			});

	}

	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = txtUserName.getText().trim();
			String ip_addr = txtIpAddress.getText().trim();
			String port_no = txtPortNumber.getText().trim();
			int sc =0;
			String profile = "";
	
			if(btnPink.getText().equals("Clicked"))
				profile ="Pink";
			else if(btnBlue.getText().equals("Clicked"))
				profile ="Blue";
			else if(btnYellow.getText().equals("Clicked"))
				profile ="Yellow";
			else if(btnGray.getText().equals("Clicked"))
				profile ="Gray";
			
			//들어온 값이 Clicked이면 넘겨주기
			JavaGameClientView view = new JavaGameClientView(username, ip_addr, port_no,profile,sc);
			setVisible(false);
		}
	}
}
