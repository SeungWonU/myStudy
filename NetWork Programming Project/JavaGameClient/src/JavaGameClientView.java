
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.JList;
import java.awt.Canvas;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class JavaGameClientView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInput;
	private String UserName;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private JLabel lblUserName;
	// private JTextArea textArea;
	private JTextPane textArea;

	private Frame frame;
	private FileDialog fd;
	private JButton imgBtn;

	JPanel panel;
	private JLabel lblMouseEvent;
	private Graphics gc;
	private int pen_size = 2; // minimum 2
	// 그려진 Image를 보관하는 용도, paint() 함수에서 이용한다.
	private Image panelImage = null; 
	private Graphics gc2 = null;
	private JButton btnPink,btnYellow,btnBlue,btnGray;
	private JButton btnRedc,btnBluec,btnPurplec,btnOrangec,btnGreenc,btnYellowc,btnErase,btnBlackc,btnBrownc;
	private JLabel lblUserProfile;
	private JButton btnMission,lblTimer;
	private JButton btnFinish;
	
	private String mission = "";
	private int n=0; 
	ArrayList<String> list = new ArrayList<String>();
	private int item=3;
	private int score=0;
	private JLabel lblItem;
	private JLabel lblScore;
	private int[] uscore= new int[4];
	/**
	 * Create the frame.
	 * @throws BadLocationException 
	 */
	public JavaGameClientView(String username, String ip_addr, String port_no,String profile,int sc)  {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 899, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		score =sc;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 148, 252, 328);
		contentPane.setBackground(new Color(0x6357ca));
		contentPane.add(scrollPane);

		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setFont(new Font("굴림체", Font.PLAIN, 14));
		scrollPane.setViewportView(textArea);

		txtInput = new JTextField();
		txtInput.setBounds(12, 479, 185, 40);
		contentPane.add(txtInput);
		txtInput.setColumns(10);

		btnSend = new JButton("Send");
		btnSend.setFont(new Font("굴림", Font.PLAIN, 14));
		btnSend.setBounds(195, 478, 69, 40);
		contentPane.add(btnSend);

		lblUserName = new JLabel("Name");
		lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUserName.setOpaque(true);
		lblUserName.setBackground(new Color(0x365979));
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setFont(new Font("굴림", Font.BOLD, 20));
		
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(12, 20, 146, 47);
		contentPane.add(lblUserName);
		setVisible(true);
		
		//AppendText("User " + username + " connecting " + ip_addr + " " + port_no);
		UserName = username;
		lblUserName.setText(username);

		lblItem = new JLabel("Item");
		lblItem.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblItem.setOpaque(true);
		lblItem.setBackground(new Color(0x4a8dc5));
		lblItem.setForeground(Color.black);
		
		lblItem.setFont(new Font("굴림", Font.BOLD, 14));
		lblItem.setHorizontalAlignment(SwingConstants.CENTER);
		lblItem.setBounds(12, 67, 146, 40);
		contentPane.add(lblItem);
		
		setVisible(true);
		lblItem.setText("ITEM : "+item);
		
		imgBtn = new JButton("+");
		imgBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		imgBtn.setBounds(12, 489, 50, 40);
		//contentPane.add(imgBtn);
		
		ImageIcon imageRedc = new ImageIcon("src/redc.PNG");
		ImageIcon imageBluec = new ImageIcon("src/bluec.PNG");
		ImageIcon imageGreenc = new ImageIcon("src/greenc.PNG");
		ImageIcon imageOrangec = new ImageIcon("src/orangec.PNG");
		ImageIcon imagePurplec = new ImageIcon("src/purplec.PNG");
		ImageIcon imageYellowc = new ImageIcon("src/ye.PNG");
		ImageIcon imageBlackc = new ImageIcon("src/blackc.PNG");
		ImageIcon imageBrownc = new ImageIcon("src/brownc.PNG");
		
		ImageIcon imageErase = new ImageIcon("src/nu1.PNG");
		
		btnErase= new JButton(imageErase);
		btnErase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(Color.WHITE);
			}
		});
		btnErase.setHorizontalTextPosition(JButton.CENTER);
		btnErase.setBounds(543,20,50,48);
		contentPane.add(btnErase);
	
		btnBlackc = new JButton(imageBlackc);
		btnBlackc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(Color.BLACK);
			}
		});
		btnBlackc.setHorizontalTextPosition(JButton.CENTER);
		btnBlackc.setBounds(447, 20,24, 25);
		contentPane.add(btnBlackc);
		
		btnRedc = new JButton(imageRedc);
		btnRedc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(Color.RED);
			}
		});
		btnRedc.setHorizontalTextPosition(JButton.CENTER);
		btnRedc.setBounds(447, 44,24, 25);
		contentPane.add(btnRedc);
		
		btnGreenc = new JButton(imageGreenc);
		btnGreenc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(Color.GREEN);
			}
		});
		btnGreenc.setHorizontalTextPosition(JButton.CENTER);
		btnGreenc.setBounds(471, 20,24, 25);
		contentPane.add(btnGreenc);
		
		btnOrangec = new JButton(imageOrangec);
		btnOrangec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(Color.ORANGE);
			}
		});
		btnOrangec.setHorizontalTextPosition(JButton.CENTER);
		btnOrangec.setBounds(471, 44,24, 25);
		contentPane.add(btnOrangec);
		
		btnPurplec = new JButton(imagePurplec);
		btnPurplec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(new Color(102, 0, 153));
			}
		});
		btnPurplec.setHorizontalTextPosition(JButton.CENTER);
		btnPurplec.setBounds(495, 20,24, 25);
		contentPane.add(btnPurplec);
		
		btnYellowc = new JButton(imageYellowc);
		btnYellowc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(Color.YELLOW);
			}
		});
		btnYellowc.setHorizontalTextPosition(JButton.CENTER);
		btnYellowc.setBounds(519, 20,24, 25);
		contentPane.add(btnYellowc);
		
		btnBluec = new JButton(imageBluec);
		btnBluec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(Color.BLUE);
			}
		});
		btnBluec.setHorizontalTextPosition(JButton.CENTER);
		btnBluec.setBounds(519, 44,24, 25);
		contentPane.add(btnBluec);
		
		btnBrownc = new JButton(imageBrownc);
		btnBrownc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc2.setColor(new Color(102, 51, 0));
			}
		});
		btnBrownc.setHorizontalTextPosition(JButton.CENTER);
		btnBrownc.setBounds(495, 44,24, 25);
		contentPane.add(btnBrownc);
		
		ImageIcon iconPink = new ImageIcon("src/1234.PNG");
		ImageIcon iconYellow = new ImageIcon("src/8.PNG");
		ImageIcon iconBlue = new ImageIcon("src/j.PNG");
		ImageIcon iconGray = new ImageIcon("src/123.PNG");

		if(profile.equals("Pink")) {
			btnPink = new JButton(iconPink);
			btnPink.setHorizontalTextPosition(JButton.CENTER);
			btnPink.setBounds(158, 49, 105, 97);
			contentPane.add(btnPink);
			}
		else if(profile.equals("Yellow")) {
			btnYellow = new JButton( iconYellow);
			btnYellow.setHorizontalTextPosition(JButton.CENTER);
			btnYellow.setBounds(158, 49, 105, 97);
			contentPane.add(btnYellow);
			}
		else if(profile.equals("Blue")) {
			btnBlue = new JButton( iconBlue);
			btnBlue.setHorizontalTextPosition(JButton.CENTER);
			btnBlue.setBounds(158, 49, 105, 97);
			contentPane.add(btnBlue);
			}
		else if(profile.equals("Gray")) {
			btnGray = new JButton( iconGray);
			btnGray.setHorizontalTextPosition(JButton.CENTER);
			btnGray.setBounds(158, 49, 105, 97);
			contentPane.add(btnGray);
			}
		
		
		list.add("사과");
		list.add("포도");
		list.add("수박");
		list.add("바나나");
		
		//Collections.shuffle(list);	
		
		btnMission= new JButton("게임 시작!");
		btnMission.setHorizontalTextPosition(JButton.CENTER);
		btnMission.setFont(new Font("굴림", Font.BOLD, 18));
		btnMission.setBounds(315, 21, 131, 47);
		btnMission.setBackground(Color.orange);
		btnMission.setForeground(Color.black);
		contentPane.add(btnMission);
		btnMission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//btnMission.setBackground(new Color(255,128,0));
				//ChatMsg cm = new ChatMsg(UserName, "500", "MOUSE");
				
					if(UserName.contains("1")) {
						btnMission.setText(list.get(0));
						mission=list.get(0);					
						}
					else if(UserName.contains("3")) {
						btnMission.setText(list.get(1));
						mission=list.get(1);
						}
					else if(UserName.contains("4")) {
						btnMission.setText(list.get(2));
						mission=list.get(2);
					}
				SendMission("OK");
				
			//btnMission.setFont(new Font("굴림", Font.PLAIN, 14));	
			//	mission=list.get(1);// mission에 문제 적어서 넘겨줌
				n++;
				
			}
		});		
		
		JButton btnNewButton = new JButton("아이템");
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
//				SendObject(msg);
//				System.exit(0);
				if(UserName.contains("4")) {
					lblItem.setText("ITEM : 2");
					btnMission.setText("첫글자 : 포");
				}
				
				//SendItem("ok");				
			}
		});
		btnNewButton.setBounds(675, 21, 82, 47);
		btnNewButton.setForeground(Color.white);
		btnNewButton.setBackground(new Color(0x5d3e2f));
		contentPane.add(btnNewButton);
		
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(315, 69, 524, 452);
		contentPane.add(panel);
		gc = panel.getGraphics();
		
		// Image 영역 보관용. paint() 에서 이용한다.
		panelImage = createImage(panel.getWidth(), panel.getHeight());
		gc2 = panelImage.getGraphics();
		gc2.setColor(panel.getBackground());
		gc2.fillRect(0,0, panel.getWidth(),  panel.getHeight());
		gc2.setColor(Color.BLACK);
		gc2.drawRect(0,0, panel.getWidth()-1,  panel.getHeight()-1);
		
		lblMouseEvent = new JLabel("<dynamic>");
		lblMouseEvent.setHorizontalAlignment(SwingConstants.CENTER);
		lblMouseEvent.setFont(new Font("굴림", Font.BOLD, 14));
		lblMouseEvent.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblMouseEvent.setBackground(Color.WHITE);
		lblMouseEvent.setBounds(259, 478, 400, 40);
		//contentPane.add(lblMouseEvent);
		
		lblUserProfile = new JLabel("\uD504\uB85C\uD544");
		lblUserProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserProfile.setOpaque(true);
		lblUserProfile.setBackground(new Color(0xb7e09f));
		lblUserProfile.setForeground(Color.black);
		lblUserProfile.setFont(new Font("굴림", Font.BOLD, 14));
		//lblUserProfile.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUserProfile.setBounds(159, 20, 105, 32);
		contentPane.add(lblUserProfile);
		
		lblScore = new JLabel("SCORE : 0");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setOpaque(true);
		lblScore.setBackground(new Color(0x4a8dc5));
		lblScore.setForeground(Color.red);
		lblScore.setFont(new Font("굴림", Font.BOLD, 14));
		lblScore.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblScore.setBounds(12, 106, 146, 40);
		contentPane.add(lblScore);
		
		lblTimer = new JButton("밑 그림");
		lblTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(UserName.contains("1")) {
					Image image = new ImageIcon("src/appleill.PNG").getImage();
					panelImage = createImage(panel.getWidth(), panel.getHeight());	
					gc2 = panelImage.getGraphics();
					gc2.setColor(panel.getBackground());
					gc2.fillRect(0,0, panel.getWidth(),  panel.getHeight());
					gc2.setColor(Color.BLACK);
			
					gc2.drawRect(0,0, panel.getWidth()-1,  panel.getHeight()-1);
					gc2.drawImage(image,  0,  0, panel.getWidth(), panel.getHeight(), panel);
					gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
				
					}
				else if(UserName.contains("3")) {
					Image image = new ImageIcon("src/grapeill.PNG").getImage();
					panelImage = createImage(panel.getWidth(), panel.getHeight());	
					gc2 = panelImage.getGraphics();
					gc2.setColor(panel.getBackground());
					gc2.fillRect(0,0, panel.getWidth(),  panel.getHeight());
					gc2.setColor(Color.BLACK);
					gc2.drawRect(0,0, panel.getWidth()-1,  panel.getHeight()-1);
					gc2.drawImage(image,  0,  0, panel.getWidth(), panel.getHeight(), panel);
					gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
				
				}
				else if(UserName.contains("4")) {
					Image image = new ImageIcon("src/watermelonill.PNG").getImage();
					panelImage = createImage(panel.getWidth(), panel.getHeight());	
					gc2 = panelImage.getGraphics();
					gc2.setColor(panel.getBackground());
					gc2.fillRect(0,0, panel.getWidth(),  panel.getHeight());
					gc2.setColor(Color.BLACK);
					gc2.drawRect(0,0, panel.getWidth()-1,  panel.getHeight()-1);
					gc2.drawImage(image,  0,  0, panel.getWidth(), panel.getHeight(), panel);
					gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
				}
			}
		});
		lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimer.setFont(new Font("굴림", Font.BOLD, 14));
		//lblTimer.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTimer.setBounds(593, 21, 82, 47);
		lblTimer.setBackground(new Color(0xf29886));
		contentPane.add(lblTimer);
		
		btnFinish= new JButton("client 승리 ");
		btnFinish.setBounds(290, 240, 320, 60);
		contentPane.add(btnFinish);
		btnFinish.setHorizontalTextPosition(JButton.CENTER);
		btnFinish.setFont(new Font("굴림", Font.BOLD, 30));
		btnFinish.setBackground(new Color(88, 88, 88));
		btnFinish.setForeground(Color.WHITE);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
				SendObject(msg);
				System.exit(0);
			}
		});
		btnFinish.setVisible(false);
		
		contentPane.add(btnFinish);
		
		btnNewButton_1 = new JButton("나가기");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
				SendObject(msg);
				System.exit(0);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 14));
		btnNewButton_1.setBackground(new Color(88, 88, 88));
		btnNewButton_1.setBounds(757, 21, 82, 47);
		contentPane.add(btnNewButton_1);
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			// SendMessage("/login " + UserName);
			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
			SendObject(obcm);

			ListenNetwork net = new ListenNetwork();
			net.start();
			TextSendAction action = new TextSendAction();
			btnSend.addActionListener(action);
			txtInput.addActionListener(action);
			txtInput.requestFocus();
			ImageSendAction action2 = new ImageSendAction();
			imgBtn.addActionListener(action2);
			MyMouseEvent mouse = new MyMouseEvent();
			panel.addMouseMotionListener(mouse);
			panel.addMouseListener(mouse);
			MyMouseWheelEvent wheel = new MyMouseWheelEvent();
			panel.addMouseWheelListener(wheel);


		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppendText("connect error");
		}

	}

	public void paint(Graphics g) {
		super.paint(g);
		// Image 영역이 가려졌다 다시 나타날 때 그려준다.
		gc.drawImage(panelImage, 0, 0, this);
	}
	
	// Server Message를 수신해서 화면에 표시
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {

					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("<%s>\n%s", cm.UserName, cm.data);
					} else
						continue;
					switch (cm.code) {
					case "200": // chat message
						if(lblScore.getText().contains("3")) {
							Dofinish(cm);
						}
						//if (cm.UserName.equals(UserName)) 
					//		AppendTextR(msg); // 내 메세지는 우측에
							
					//	else {
							AppendText(msg);
							
//							DoAnswer(cm);
						//	{
//							cm.score++;// cm.item으로 바꿔서 올려줘야됨
//							lblScore.setText("SCORE : "+cm.score);
//							AppendText(cm.UserName+"가 맞췄습니다!!");
//							if(cm.UserName.contains("1"))
//							{uscore[0]++;
//							lblScore.setText("SCORE : "+uscore[0]);
//							AppendText(cm.UserName+"가 맞췄습니다!!");
//							System.out.println("111");
//							}
//							else if(cm.UserName.contains("2"))
//							{uscore[1]++;
//							lblScore.setText("SCORE : "+uscore[1]);
//							AppendText(cm.UserName+"가 맞췄습니다!!");
//							System.out.println("222");
//							}
							
							//}
								
//						if (cm.UserName.equals(UserName))
//							if(cm.data.matches(mission)) { // 입력한 값이 Mission과 같다면 
//								DoAnswer(cm);
//							cm.score++;// cm.item으로 바꿔서 올려줘야됨
//							lblScore.setText("SCORE : "+cm.score);
//							AppendText(cm.UserName+"가 맞췄습니다!!");
//							}
//						
							if(cm.data.matches(mission)) 						
								DoAnswer(cm);
								
						//}
						
						break;
						
					case "300": // Image 첨부
						if (cm.UserName.equals(UserName))
							AppendTextR("<" + cm.UserName + ">");
						else
							AppendText("<" + cm.UserName + ">");
						AppendImage(cm.img);
						break;
					case "500": // Mouse Event 수신
						DoMouseEvent(cm);
						
						break;
					case "600":
						DoMission(cm);
						break;
					case "700":
							DoPanel(cm);
							GetAnswer(cm);				
						break;
					case "800":
						Havefinish(cm);
						break;
					case "1000":
						DoItem(cm);
						break;
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						socket.close();
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}

	public void Dofinish(ChatMsg cm) {
		
		cm = new ChatMsg(UserName, "800", "MISSION");
		SendObject(cm);
	}
	public void Havefinish(ChatMsg cm) {
		btnFinish.setText(cm.UserName+"가 최종 승리");
		panel.setVisible(false);
		btnFinish.setVisible(true);
	}
	public void SendMission(String s) {
		ChatMsg cm = new ChatMsg(UserName, "600", "MISSION");
		//mission=list.get(0);
		//cm.mission=mission;

		//cm.score =0;
		SendObject(cm);
	}
	public void SendItem(String s) {
		ChatMsg cm = new ChatMsg(UserName, "1000", "MISSION");
		//mission=list.get(0);
		cm.mission=mission;
		cm.it= cm.mission.charAt(0);
		//cm.score =0;
		SendObject(cm);
	}
	public void DoItem(ChatMsg cm) {
		if (UserName.contains("4")) {
			
			btnMission.setText("첫글자: "+cm.it);
			lblItem.setText("ITEM : 2");
		}		
	}
	
	public void DoMission(ChatMsg cm) {
		if (cm.UserName.matches(UserName))
			return;
		btnMission.setText("맞춰보시오");
		//panel.setVisible(false);
		//btnFinish.setVisible(true);
	}
	public void DoAnswer(ChatMsg cm) {
		cm = new ChatMsg(UserName, "700", "ANSWER");

		SendObject(cm);
	
	}
	public void DoPanel(ChatMsg cm) {
		btnMission.setText("게임 시작!");
//		if (UserName.contains("1")) {
//		Image image = new ImageIcon("src/pink.PNG").getImage();
//		panelImage = createImage(panel.getWidth(), panel.getHeight());	
//		gc2 = panelImage.getGraphics();
//		gc2.setColor(panel.getBackground());
//		gc2.fillRect(0,0, panel.getWidth(),  panel.getHeight());
//		gc2.setColor(Color.BLACK);
//		gc2.drawRect(0,0, panel.getWidth()-1,  panel.getHeight()-1);
//		//gc.drawImage(panelImage, 0, 0, panel);
//		//gc.drawImage(image, 0, 0, panel);
//		gc2.drawImage(image,  0,  0, panel.getWidth(), panel.getHeight(), panel);
//		gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
//		}
		//else	{}
		panelImage = createImage(panel.getWidth(), panel.getHeight());	
		gc2 = panelImage.getGraphics();
		gc2.setColor(panel.getBackground());
		gc2.fillRect(0,0, panel.getWidth(),  panel.getHeight());
		gc2.setColor(Color.BLACK);
		gc2.drawRect(0,0, panel.getWidth()-1,  panel.getHeight()-1);
		gc.drawImage(panelImage, 0, 0, panel);
		
		//cm.score++;// cm.item으로 바꿔서 올려줘야됨
	}
	public void GetAnswer(ChatMsg cm) {
		if (UserName.contains("2")) {
		// cm.item으로 바꿔서 올려줘야됨
		lblScore.setText("SCORE : "+cm.score);
		//panel.setBackground(Color.white);
		//gc2.setColor(panel.getBackground());
		//ImageIcon panelImage = new ImageIcon("src/pink.PNG");
		
		
		if(cm.score==3) {
		//	panel.setVisible(false);
		//	btnFinish.setVisible(true);
			cm = new ChatMsg(UserName, "200", "OK");
			cm.data=UserName+" 승리!";
		}
		//AppendText(UserName+"가 맞췄습니다!!");
		else {
		cm = new ChatMsg(UserName, "200", "OK");
		cm.data=UserName+"가 맞췄습니다 !!";
		}
		SendObject(cm);
		}
					
	}
	private int x2=0,y2=0,mpoint;
	// Mouse Event 수신 처리
	public void DoMouseEvent(ChatMsg cm) {
		//Color c;
		gc2.setColor(cm.color);
		if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다.
			return;
		if(x2!=0)
			if(x2-cm.mouse_e.getX()<10 && x2-cm.mouse_e.getX()>-10)
				gc2.drawLine(x2,y2,cm.mouse_e.getX(),cm.mouse_e.getY());
		x2=cm.mouse_e.getX();
		y2=cm.mouse_e.getY();
		
	//gc2.fillOval(cm.mouse_e.getX() - pen_size/2, cm.mouse_e.getY() - cm.pen_size/2, cm.pen_size, cm.pen_size);
	gc.drawImage(panelImage, 0, 0, panel);
	}

	public void SendMouseEvent(MouseEvent e) {
		ChatMsg cm = new ChatMsg(UserName, "500", "MOUSE");
		cm.mouse_e = e;
		cm.pen_size = pen_size;
		cm.color=gc2.getColor();
		SendObject(cm);
	}

	class MyMouseWheelEvent implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if (e.getWheelRotation() < 0) { // 위로 올리는 경우 pen_size 증가
				if (pen_size < 20)
					pen_size++;
			} else {
				if (pen_size > 2)
					pen_size--;
			}
		}
		
	}
	// Mouse Event Handler
	class MyMouseEvent implements MouseListener, MouseMotionListener {
		private int x,y,x1,y1;

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			//Color c = new Color(0,0,255);
			//gc2.setColor(c);
			//gc2.fillOval(e.getX()-pen_size/2, e.getY()-pen_size/2, pen_size, pen_size);
			//gc.drawImage(panelImage, 0, 0, panel);
			//SendMouseEvent(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			x= e.getX();
			y= e.getY();
			SendMouseEvent(e);

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			x1= e.getX();
			y1= e.getY();
		//	gc2.drawLine(x,y,x1,y1);
		//	gc.drawImage(panelImage, 0, 0, panel);
			mpoint=1;
			SendMouseEvent(e);
		}
		
		
		@Override
		public void mouseDragged(MouseEvent e) {
			x1= e.getX();
			y1= e.getY();
			gc2.drawLine(x,y,x1,y1);
			x=x1;
			y=y1;
			gc.drawImage(panelImage, 0, 0, panel);
			SendMouseEvent(e);

		}
	}


	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = txtInput.getText();
				SendMessage(msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}

	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			if (e.getSource() == imgBtn) {
				frame = new Frame("이미지첨부");
				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
				// frame.setVisible(true);
				// fd.setDirectory(".\\");
				fd. setVisible(true);
				// System.out.println(fd.getDirectory() + fd.getFile());
				if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
					ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
					ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
					obcm.img = img;
					SendObject(obcm);
				}
			}
		}
	}

	ImageIcon icon1 = new ImageIcon("src/icon1.jpg");
	private JButton btnNewButton_1;
	
	public void AppendIcon(ImageIcon icon) {
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		textArea.setCaretPosition(len);
		textArea.insertIcon(icon);
	}

	// 화면에 출력
	public void AppendText(String msg) {
		// textArea.append(msg + "\n");
		// AppendIcon(icon1);
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		//textArea.setCaretPosition(len);
		//textArea.replaceSelection(msg + "\n");
		
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.BLACK);
	    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		try {
			doc.insertString(doc.getLength(), msg+"\n", left );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// 화면 우측에 출력
	
	public void AppendTextR(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.	
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLUE);	
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
		try {
			doc.insertString(doc.getLength(),msg+"\n", right );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AppendImage(ImageIcon ori_icon) {
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		Image new_img;
		ImageIcon new_icon;
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			new_icon = new ImageIcon(new_img);
			textArea.insertIcon(new_icon);
		} else {
			textArea.insertIcon(ori_icon);
			new_img = ori_img;
		}
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
		// ImageViewAction viewaction = new ImageViewAction();
		// new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
		// panelImage = ori_img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_DEFAULT);

		gc2.drawImage(ori_img,  0,  0, panel.getWidth(), panel.getHeight(), panel);
		gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
	}

	// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		try {
			// dos.writeUTF(msg);
//			byte[] bb;
//			bb = MakePacket(msg);
//			dos.write(bb, 0, bb.length);
			ChatMsg obcm = new ChatMsg(UserName, "200", msg,1);
			oos.writeObject(obcm);
		} catch (IOException e) {
			// AppendText("dos.write() error");
			AppendText("oos.writeObject() error");
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			AppendText("SendObject Error");
		}
	}
}
