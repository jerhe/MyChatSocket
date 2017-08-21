package com.etc.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JList;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.AbstractListModel;
import javax.swing.JScrollBar;

import com.bsb.tool.ChatManager;
import com.etc.base.BaseJFrame;
import com.etc.dao.GroupsDao;
import com.etc.dao.UsersDao;
import com.etc.entity.Groups;
import com.etc.entity.Users;
import com.etc.util.DataUtil;

/**
 * Ⱥ�Ľ���
 * @author zhuzhen ���ܵ�ʵ��
 * @author yangchengrong ����ı�д
 */
public class SwingGroupChat  extends BaseJFrame{
	private SwingGroupChat frame;
	private final JPanel panel_north = new JPanel();
	private JTextField sgc_txt_smb;
	private JLabel sgc_img_head;
	private JLabel sgc_jl_name;
	private JLabel sgc_jl_remark;
	private JPanel sgc_jp_member;
	private JButton ssc_btn_send;
	private JButton ssc_btn_close;
	private JTextArea sgc_txt_send;
	private JButton ssc_btn_record;
	private JTextArea sgc_txt_content;
	private Groups groups;
	private ArrayList<Users> groupMember = new ArrayList<>();
	private UsersDao usersDao = new UsersDao();
	private GroupsDao groupsDao = new GroupsDao();
	
	/**
	 * ���캯��
	 */
	public SwingGroupChat(Groups g,Users u) {
		groups=g;
		ownuser=u;
		groupMember=groupsDao.getGroupsByGroupid(g.getGroupid());
		
		initialize();
	}

	/**
	 * ��ʼ���ô���
	 */
	private void initialize() {
		setBounds(100, 100, 665, 519);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
		getContentPane().setLayout(null);
		panel_north.setBounds(0, 0, 488, 86);
		getContentPane().add(panel_north);
		panel_north.setLayout(null);
		
		/**
		 * Ⱥͷ��
		 */
		sgc_img_head = new JLabel("");
		sgc_img_head.setIcon(new ImageIcon(groups.getGroupimg()));
		sgc_img_head.setBackground(Color.BLACK);
		sgc_img_head.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 34));
		sgc_img_head.setBounds(10, 10, 69, 66);
		panel_north.add(sgc_img_head);
		
		/**
		 * Ⱥ����
		 */
		sgc_jl_name = new JLabel("name");
		sgc_jl_name.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 20));
		sgc_jl_name.setBounds(97, 10, 85, 29);
		panel_north.add(sgc_jl_name);
		
		/**
		 * Ⱥ���
		 */
		sgc_jl_remark = new JLabel("remark");
		sgc_jl_remark.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 15));
		sgc_jl_remark.setBounds(97, 47, 174, 29);
		panel_north.add(sgc_jl_remark);
		
		JPanel panel_east = new JPanel();
		panel_east.setBounds(487, 0, 162, 481);
		getContentPane().add(panel_east);
		panel_east.setLayout(null);
		
		/**
		 * Ⱥ����
		 */
		JLabel sgc_jl_group = new JLabel(groups.getGroupname());
		sgc_jl_group.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 20));
		sgc_jl_group.setBounds(0, 0, 162, 36);
		panel_east.add(sgc_jl_group);
		
		/**
		 * ������
		 */
		sgc_txt_smb = new JTextField();
		sgc_txt_smb.setText("\u641C\u7D22\u7FA4\u6210\u5458");
		sgc_txt_smb.setBounds(0, 36, 162, 30);
		panel_east.add(sgc_txt_smb);
		sgc_txt_smb.setColumns(10);
		
		/**
		 * Ⱥ��Ա
		 */
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 69, 162, 412);
		panel_east.add(scrollPane_1);
		
		sgc_jp_member = new JPanel();
		sgc_jp_member.setBackground(Color.WHITE);
		scrollPane_1.setViewportView(sgc_jp_member);
		
		/**
		 * Ⱥ��Ϣ���ݿ�
		 */
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 89, 488, 245);
		getContentPane().add(scrollPane_2);
		
		sgc_txt_content = new JTextArea();
		scrollPane_2.setViewportView(sgc_txt_content);
		
		/**
		 * Ⱥ��Ϣ���Ϳ�
		 */
		JPanel panel_floor = new JPanel();
		panel_floor.setBounds(0, 338, 488, 143);
		getContentPane().add(panel_floor);
		panel_floor.setLayout(null);
		
		/**
		 * ���Ͱ�ť
		 */
		ssc_btn_send = new JButton("\u53D1\u9001\uFF08S\uFF09");
		ssc_btn_send.setBounds(369, 110, 105, 23);
		ssc_btn_send.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				//���Ͱ�ť��
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");// ���Է�����޸����ڸ�ʽ
				String hehe = dateFormat.format(now);
				
				ChatManager.getCM().send(
						ownuser.getName() + "(" + hehe + ")" + "@"
								+ sgc_txt_send.getText());
				//System.out.println(textArea.getText());
				appendText(ownuser.getName() + "(" + hehe + ")" + "˵��" + "\n"
						+ sgc_txt_send.getText());
				sgc_txt_send.setText("");
				
			}
		});
		panel_floor.add(ssc_btn_send);
		
		/**
		 *�رհ�ť
		 */
		ssc_btn_close = new JButton("\u5173\u95ED\uFF08C\uFF09");
		ssc_btn_close.setBounds(257, 110, 102, 23);
		panel_floor.add(ssc_btn_close);

		/**
		 * ���͵��ı���
		 */
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 486, 104);
		panel_floor.add(scrollPane);
		
		sgc_txt_send = new JTextArea();
		scrollPane.setViewportView(sgc_txt_send);
		sgc_txt_send.setFont(new Font("Monospaced", Font.PLAIN, 45));
		
		/**
		 * ��Ϣ��¼
		 */
		ssc_btn_record = new JButton("\u6D88\u606F\u8BB0\u5F55");
		ssc_btn_record.setBounds(145, 110, 102, 23);
		panel_floor.add(ssc_btn_record);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 86, 488, 3);
		getContentPane().add(panel);
		
		
		

	}
	
	
	/**
	 * ��������ʱ���õĺ��������������ݿ�
	 * @param string
	 */
	@Override
	public void appendText(String in) {
		sgc_txt_content.append("\n" + in);
		
	}
	
	@Override
	public void appendTextother(String in) {
		sgc_txt_content.append("\n" + in);
	}
	
	
	/**
	 * ��ʼ������
	 * @param frame
	 */
	public void setFrame(SwingGroupChat frame){
		this.frame=frame;
		/**
		 * ���ӷ�����
		 */
		String ipString = "127.0.0.1";
		ChatManager.getCM().connect(ipString, ownuser.getName());
	}
	public SwingGroupChat getFrame(){
		return frame;
	}
}
