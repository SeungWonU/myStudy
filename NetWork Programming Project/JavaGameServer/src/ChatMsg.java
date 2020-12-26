
// ChatMsg.java ä�� �޽��� ObjectStream ��.
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image, 500: Mouse Event
	public String UserName;
	public String data;
	public ImageIcon img;
	public MouseEvent mouse_e;
	public int pen_size; // pen size
	public int item;
	public int score;
	public String mission;
	public int mpoint;
	public Color color;
	public char it;
	
	public ChatMsg(String UserName, String code, String msg) {
		this.code = code;
		this.UserName = UserName;
		this.data = msg;
	}
	public ChatMsg(String UserName, String code, String msg,int score) {
		this.code = code;
		this.UserName = UserName;
		this.data = msg;
		this.score = score;
	}
}