package com.etc.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.etc.base.BaseJFrame;
import com.etc.dao.MessagesDao;
import com.etc.entity.Messages;
import com.etc.entity.Users;
import com.etc.tool.ChatManager;
import com.etc.util.DataUtil;

import java.awt.Toolkit;

/**
 * ���Ľ���
 * 
 * @author zhuzhen
 * 
 */
public class SwingSingleChat extends BaseJFrame {

	private JPanel panel_north = new JPanel();
	private JLabel ssc_jl_name;
	private JLabel ssc_jl_remark;
	private JLabel ssc_img_head;
	private JTextArea ssc_txt_content;
	private JPanel panel_east;
	private JPanel panel_floor;
	private JButton ssc_btn_send;
	private SwingSingleChat frame;
	private JTextArea ssl_txt_send;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private Users tousers;
	private MessagesDao messagesDao = new MessagesDao();
	private Socket socket;
	private ArrayList<Messages> unreadMessages;
	private JPanel panel;

	/**
	 * ��ʼ������
	 */
	public SwingSingleChat(Users u, Users own) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SwingSingleChat.class.getResource("/pic/chat.png")));
		tousers = u;
		ownuser = own;
		setTitle("��"+u.getName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 700, 560);
		setResizable(false);
		/**
		 * ���ùرյ�ǰ���ں���ӹرմ��ڵ��¼�
		 */
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Already there
		/**
		 * ��ӹرմ��ڵ��¼�����
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// �رչ����е��õķ���
				int result = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ�˳���",
						"��ʾ", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					//frame.dispose();
					frame.setVisible(false);
				}
			}
		});
		getContentPane().setLayout(null);
		panel_north.setBounds(0, 0, 649, 86);
		getContentPane().add(panel_north);
		panel_north.setLayout(null);

		/**
		 * ͷ��
		 */
		ssc_img_head = new JLabel();
		ssc_img_head.setIcon(new ImageIcon(tousers.getHeadimg()));
		ssc_img_head.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 34));
		ssc_img_head.setBounds(10, 10, 69, 66);
		panel_north.add(ssc_img_head);

		/**
		 * �ǳ�
		 */
		ssc_jl_name = new JLabel(tousers.getName());
		ssc_jl_name.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 20));
		ssc_jl_name.setBounds(97, 10, 104, 29);
		panel_north.add(ssc_jl_name);

		/**
		 * ����ǩ��
		 */
		ssc_jl_remark = new JLabel(tousers.getRemark());
		ssc_jl_remark.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 15));
		ssc_jl_remark.setBounds(97, 47, 193, 29);
		panel_north.add(ssc_jl_remark);

		/**
		 * 
		 */
		panel_east = new JPanel();
		panel_east.setBounds(487, 86, 162, 395);
		getContentPane().add(panel_east);
		panel_east.setLayout(null);

		/**
		 * ����
		 */
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("img/aa.jpg"));
		lblNewLabel.setBounds(0, 0, 162, 395);
		panel_east.add(lblNewLabel);

		panel_floor = new JPanel();
		panel_floor.setBounds(0, 338, 488, 143);
		getContentPane().add(panel_floor);
		panel_floor.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 488, 105);
		panel_floor.add(scrollPane);

		ssl_txt_send = new JTextArea();
		ssl_txt_send.setWrapStyleWord(true);
		ssl_txt_send.setLineWrap(true);
		ssl_txt_send.setFont(new Font("Monospaced", Font.PLAIN, 16));
		scrollPane.setViewportView(ssl_txt_send);
		
		/**
		 * ��Ӽ������̼���
		 */
		ssl_txt_send.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					send();
					
				}
			}
		});

		/**
		 * ���ݿ�
		 */

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 86, 488, 251);
		getContentPane().add(scrollPane_1);
		ssc_txt_content = new JTextArea();
		ssc_txt_content.setFont(new Font("Monospaced", Font.PLAIN, 16));
		ssc_txt_content.setEnabled(false);
		ssc_txt_content.setEditable(false);
		// ��ʼ�����ݿ�
		initContent();
		scrollPane_1.setViewportView(ssc_txt_content);

		/**
		 * ���Ͱ�ť
		 */
		ssc_btn_send = new JButton("\u53D1\u9001\uFF08S\uFF09");
		ssc_btn_send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				send();
			}
		});
		ssc_btn_send.setBounds(369, 110, 105, 23);
		panel_floor.add(ssc_btn_send);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 694, 532);
		getContentPane().add(panel);
	}

	private void initContent() {
		unreadMessages = messagesDao.getUnRead(tousers.getName(),
				ownuser.getName());
		for (Messages msg : unreadMessages) {
			ssc_txt_content.append("\n" + msg.getFromname() + " "
					+ msg.getSendtime() + "\n" + msg.getContent());
			messagesDao.setRead(msg.getId());
		}
	}

	/**
	 * �����������ݿ�
	 * 
	 * @param string
	 */
	@Override
	public void appendText(String in) {
		ssc_txt_content.append("\n" + in);
	}

	/**
	 * ��������ʱ���õĺ��������������ݿ�
	 * 
	 * @param string
	 */
	@Override
	public void appendTextother(String in, String fromname, String toname,
			int msgid) {
		if (toname.equals(ownuser.getName())
				&& fromname.equals(tousers.getName())) {
			MessagesDao messagesDao = new MessagesDao();
			if (messagesDao.isReadById(msgid)) {
				ssc_txt_content.append("\n" + in);
				messagesDao.setRead(msgid);
			}
		}

	}

	public SwingSingleChat getFrame() {
		return frame;
	}

	/**
	 * ��ʼ��frame�����ӷ�������SwingSingleChat����
	 * 
	 * @param frame
	 */
	public void setFrame(SwingSingleChat frame) {
		this.frame = frame;
		/**
		 * ���ӷ�����
		 */

		try {
			socket = new Socket(DataUtil.IPSTRING, 8066);
			ChatManager.getCM().connect(DataUtil.IPSTRING, ownuser.getName(), socket);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void send(){
		if (!ssl_txt_send.getText().trim().equals("")) {
			Date now = new Date();
			String nowtime = DataUtil.dateToString(now);
			// ������Ϣ�����ݿ�
			Messages newmsg = new Messages(ownuser.getName(), tousers
					.getName(), ssl_txt_send.getText(), nowtime, 1);
			int msgid = messagesDao.saveMessage(newmsg);
			String sendString = msgid + "@" + ownuser.getName() + "@"
					+ tousers.getName() + "@ " + nowtime + " " + "@"
					+ ssl_txt_send.getText();
			ChatManager.getCM().send(sendString);

			// System.out.println(textArea.getText());
			appendText(ownuser.getName() + " " + nowtime + " " + "��"
					+ "\n" + ssl_txt_send.getText());
			ssl_txt_send.setText("");
		} else {
			ssl_txt_send.setText("");
			DataUtil.showMessage("��Ϣ����Ϊ��");
			
		}
	}
}
