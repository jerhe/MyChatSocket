package com.etc.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bsb.tool.ChatManager;
import com.etc.base.BaseJFrame;
import com.etc.entity.Users;

/**
 * ���Ľ���
 * @author zhuzhen
 *
 */
public class SwingSingleChat extends BaseJFrame{

	private JPanel panel_north = new JPanel();
	private JLabel ssc_jl_name;
	private JLabel ssc_jl_remark;
	private JLabel ssc_img_head;
	private JTextArea ssc_txt_content;
	private JButton ssc_btn_close;
	private JPanel panel_east;
	private JPanel panel_floor;
	private JButton ssc_btn_send;
	private JButton ssc_btn_record;
	private SwingSingleChat frame;
	private JTextArea ssl_txt_send;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	/**
	 * ��ʼ������
	 */
	public SwingSingleChat(Users u) {
		ownuser=u;
		initialize();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 665, 519);
		setResizable(false);
		/**
		 * ���ùرյ�ǰ����
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
		getContentPane().setLayout(null);
		panel_north.setBounds(0, 5, 649, 86);
		getContentPane().add(panel_north);
		panel_north.setLayout(null);
		
		/**
		 * ͷ��
		 */
		ssc_img_head = new JLabel();
		ssc_img_head.setIcon(new ImageIcon(ownuser.getHeadimg()));
		ssc_img_head.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 34));
		ssc_img_head.setBounds(10, 10, 69, 66);
		panel_north.add(ssc_img_head);
		
		/**
		 * �ǳ�
		 */
		ssc_jl_name = new JLabel(ownuser.getName());
		ssc_jl_name.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 20));
		ssc_jl_name.setBounds(97, 10, 104, 29);
		panel_north.add(ssc_jl_name);
		
		/**
		 * ����ǩ��
		 */
		ssc_jl_remark = new JLabel(ownuser.getRemark());
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
		

		
		/**
		 * �رհ�ť
		 */
		ssc_btn_close = new JButton("\u5173\u95ED\uFF08C\uFF09");
		ssc_btn_close.setBounds(257, 110, 102, 23);
		panel_floor.add(ssc_btn_close);
		
		
		/**
		 * ��Ϣ��¼��ť
		 */
		ssc_btn_record = new JButton("\u6D88\u606F\u8BB0\u5F55");
		ssc_btn_record.setBounds(145, 110, 102, 23);
		panel_floor.add(ssc_btn_record);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 5, 488, 100);
		panel_floor.add(scrollPane);
		
		ssl_txt_send = new JTextArea();
		scrollPane.setViewportView(ssl_txt_send);
		
		/**
		 * ���ݿ�
		 */
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 86, 488, 251);
		getContentPane().add(scrollPane_1);
		ssc_txt_content = new JTextArea();
		scrollPane_1.setViewportView(ssc_txt_content);
//		frame.setUndecorated(true);
		
		/**
		 * ���Ͱ�ť
		 */
		ssc_btn_send = new JButton("\u53D1\u9001\uFF08S\uFF09");
		ssc_btn_send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");// ���Է�����޸����ڸ�ʽ
				String hehe = dateFormat.format(now);
				
				ChatManager.getCM().send(
						ownuser.getName() + "(" + hehe + ")" + "@"
								+ ssl_txt_send.getText());
				//System.out.println(textArea.getText());
				appendText(ownuser.getName() + "(" + hehe + ")" + "˵��" + "\n"
						+ ssl_txt_send.getText());
				ssl_txt_send.setText("");
			}
		});
		ssc_btn_send.setBounds(369, 110, 105, 23);
		panel_floor.add(ssc_btn_send);
	}
	
	/**
	 * ��������ʱ���õĺ��������������ݿ�
	 * @param string
	 */
	@Override
	public void appendText(String in) {
		ssc_txt_content.append("\n" + in);
		
	}
	
	@Override
	public void appendTextother(String in) {
		ssc_txt_content.append("\n" + in);
	}
	
	public SwingSingleChat getFrame() {
		return frame;
	}

	/**
	 * ��ʼ��frame�����ӷ�������SwingSingleChat����
	 * @param frame
	 */
	public void setFrame(SwingSingleChat frame) {
		this.frame = frame;
		/**
		 * ���ӷ�����
		 */
		String ipString = "127.0.0.1";
		ChatManager.getCM().connect(ipString, ownuser.getName());
		
	}
}
