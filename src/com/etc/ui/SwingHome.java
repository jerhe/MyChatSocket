package com.etc.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;

import com.etc.base.BaseJFrame;
import com.etc.dao.FriendsDao;
import com.etc.dao.GroupsDao;
import com.etc.dao.MessagesDao;
import com.etc.dao.UsersDao;
import com.etc.entity.Groups;
import com.etc.entity.Messages;
import com.etc.entity.Users;
import com.etc.tool.ChatManager;
import com.etc.util.DataUtil;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SwingHome extends BaseJFrame {

	private JPanel contentPane;
	private JLabel sh_img_head;
	private JLabel sh_jl_name;
	private JLabel sh_jl_remark;
	private JLabel sh_jl_recent;
	private JLabel sh_jl_friend;
	private JLabel sh_jl_group;
	private Object[][] obj;
	private JPanel panel;
	private JScrollPane scrollPane;
	private FriendsDao friendsDao;
	private GroupsDao groupsDao;
	private MessagesDao messagesDao;
	private UsersDao usersDao = new UsersDao();
	private ArrayList<Messages> unreadMessages;
	private JButton sh_btn_menu;
	private JPopupMenu m_popupMenu;
	private JButton sl_btn_del;
	private JButton sh_btn_add;
	private SwingLogin loginFrame;
	private SwingHome homeFrame;
	
	// private ArrayList<Users>

	/**
	 * ͨ�����ź�������Users����
	 * 
	 * @param us
	 */
	public SwingHome(Users us) {
		setTitle("\u4E3B\u754C\u9762");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SwingHome.class.getResource("/pic/chat.png")));

		/**
		 * ��ʼ���û�����������б������б�Ⱥ�б� ������ö񺺵ķ�ʽ��ʼ�� �ɸ��ĳ����� Ĭ����ʾ�ҵĺ���
		 */
		ownuser = us;
		// System.out.println(ownuser);
		messagesDao = new MessagesDao();
		friendsDao = new FriendsDao();
		groupsDao = new GroupsDao();
		recentsList = messagesDao.getRecentUsersByUserName(ownuser.getName());
		friendsList = friendsDao.getFriends(ownuser.getName());
		groupsList = groupsDao.getGroupsByUsername(ownuser.getName());

		setResizable(false);
		/**
		 * �����˳������¼�,��������Ϊ������
		 */
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// �˳�������ʾ
				int result = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ�˳�����",
						"��ʾ", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					UsersDao usersDao = new UsersDao();
					usersDao.setUnOnlineById(ownuser.getId());
					System.exit(0);
				}
			}
		});

		setBounds(100, 100, 373, 693);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		sh_img_head = new JLabel("");
		sh_img_head.setIcon(new ImageIcon(ownuser.getHeadimg()));
		sh_img_head.setBounds(20, 10, 80, 80);
		contentPane.add(sh_img_head);

		sh_jl_name = new JLabel(ownuser.getName());
		sh_jl_name.setHorizontalAlignment(SwingConstants.CENTER);
		sh_jl_name.setFont(new Font("Ҷ����ë������2.0��", Font.PLAIN, 25));
		sh_jl_name.setBounds(110, 18, 91, 37);
		contentPane.add(sh_jl_name);

		sh_jl_remark = new JLabel(ownuser.getRemark());
		sh_jl_remark.setFont(new Font("����", Font.PLAIN, 14));
		sh_jl_remark.setBounds(110, 65, 146, 25);	
		contentPane.add(sh_jl_remark);

		sh_jl_recent = new JLabel("\u6700\u8FD1");
		/**
		 * ������찴ť,�����ʾ������찴ť
		 */
		sh_jl_recent.setHorizontalAlignment(SwingConstants.CENTER);
		sh_jl_recent.setForeground(SystemColor.red);
		sh_jl_recent.setFont(new Font("��������", Font.PLAIN, 16));
		sh_jl_recent.setBounds(-2, 100, 102, 42);
		sh_jl_recent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ���õ����
				// Ĭ�ϡ����ˡ�Ϊ��ɫ
				initMyRecentJPanel();
			}
		});
		contentPane.add(sh_jl_recent);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(-2, 152, 318, 416);

		contentPane.add(scrollPane);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		scrollPane.setViewportView(panel);
		panel.setBounds(0, 187, 318, 416);
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));

		
		/**
		 * ���Ѱ�ť,��ʾ�����б��б�ĵ�¼����
		 */
		sh_jl_friend = new JLabel("\u597D\u53CB");
		sh_jl_friend.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// �����¼�
				
				initMyFriendPanel();
			}
		});
		sh_jl_friend.setHorizontalAlignment(SwingConstants.CENTER);
		sh_jl_friend.setFont(new Font("��������", Font.PLAIN, 16));
		sh_jl_friend.setBounds(110, 100, 91, 42);
		contentPane.add(sh_jl_friend);

		/**
		 * Ⱥ�б�ť����ʾȺ�б�
		 */
		sh_jl_group = new JLabel("\u7FA4");
		sh_jl_group.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				// �����¼�
				initMyGroupJPanel();
			}
		});
		sh_jl_group.setHorizontalAlignment(SwingConstants.CENTER);
		sh_jl_group.setFont(new Font("��������", Font.PLAIN, 16));
		sh_jl_group.setBounds(211, 100, 108, 42);
		contentPane.add(sh_jl_group);

		/**
		 * �˵���
		 */
		sh_btn_menu = new JButton("\u83DC\u5355");
		sh_btn_menu.setBounds(1, 578, 106, 25);
		createPopupMenu();
		sh_btn_menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				m_popupMenu.show(sh_btn_menu, -80, -90);
			}
		});
		contentPane.add(sh_btn_menu);

		/**
		 * ���Һ���
		 */
		sh_btn_add = new JButton("\u67E5\u627E");
		sh_btn_add.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
//				setVisible(false);
				SwingSearch ssframe = new SwingSearch(homeFrame,ownuser);
				ssframe.setVisible(true);

			}
		});
		sh_btn_add.setBounds(112, 578, 91, 24);
		contentPane.add(sh_btn_add);

		/**
		 * ɾ������
		 */
		sl_btn_del = new JButton("\u5220\u9664\u597D\u53CB");
		sl_btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingDelFriend delframe= new SwingDelFriend(ownuser);
				delframe.setHomeframe(homeFrame);
				delframe.setVisible(true);
			}
		});
		sl_btn_del.setBounds(211, 578, 105, 23);
		contentPane.add(sl_btn_del);
		setLocation(900, 40);
		// Ĭ����������б�
		initMyRecentJPanel();
	}

	/**
	 * ��ʼ��Ⱥ�б�
	 */
	public void initMyGroupJPanel() {
		panel.removeAll();
		// ��ʼ��Ⱥ�������
		sh_jl_recent.setForeground(SystemColor.black);
		sh_jl_friend.setForeground(SystemColor.black);
		sh_jl_group.setForeground(SystemColor.red);
		groupsList = groupsDao.getGroupsByUsername(ownuser.getName());
		for (int i = 0; i < groupsList.size(); i++) {
			Groups g = groupsList.get(i);
			JLabel btn1 = new JLabel("");
			btn1.setIcon(new ImageIcon(g.getGroupimg()));
			JLabel jl2 = new JLabel(g.getGroupname()
					+ getFixedLength(g.getGroupname().length()));
			jl2.setFont(new Font("��������", Font.PLAIN, 25));
			// �Զ���Ⱥ����
			MyJPanelGroup p = new MyJPanelGroup(g, ownuser);
			p.setBounds(0, 0, 318, 50);
			p.add(btn1);
			p.add(jl2);
			panel.add(p);
			panel.setPreferredSize(new Dimension(50, 50 + i * 50 * 2));
		}
		panel.updateUI();
	}

	/**
	 * ��ʼ����������б�
	 */
	public void initMyRecentJPanel() {
		panel.removeAll();
		// ��ʼ���������Ľ���
		sh_jl_recent.setForeground(SystemColor.red);
		sh_jl_friend.setForeground(SystemColor.black);
		sh_jl_group.setForeground(SystemColor.black);
		recentsList = messagesDao.getRecentUsersByUserName(ownuser.getName());
		for (int i = 0; i < recentsList.size(); i++) {
			Users u = recentsList.get(i);
			unreadMessages = messagesDao.getUnRead(u.getName(),
					ownuser.getName());
			JLabel btn1;
			if (unreadMessages.size() == 0) {
				btn1 = new JLabel("");
			} else {
				btn1 = new JLabel("" + unreadMessages.size());
			}
			btn1.setForeground(Color.RED);
			btn1.setIcon(new ImageIcon(u.getHeadimg()));
			JLabel jl2 = new JLabel(u.getName()
					+ getFixedLength(u.getName().length()));
			jl2.setFont(new Font("��������", Font.PLAIN, 25));
			// �Զ��嵥������ touser���� fromuser����˭
			MyJPanelSingle p = new MyJPanelSingle(u, ownuser);
			p.setBounds(0, 0, 318, 50);
			p.add(btn1);
			p.add(jl2);
			panel.add(p);
			panel.setPreferredSize(new Dimension(50, 50 + i * 50 * 2));
		}
		panel.updateUI();
	}

	/**
	 * ��ʼ�������б�
	 */
	public void initMyFriendPanel() {
		panel.removeAll();
		// ��ʼ�������б�
		sh_jl_recent.setForeground(SystemColor.black);
		sh_jl_friend.setForeground(SystemColor.red);
		sh_jl_group.setForeground(SystemColor.black);
		friendsList = friendsDao.getFriends(ownuser.getName());
		for (int i = 0; i < friendsList.size(); i++) {
			Users u = friendsList.get(i);
			unreadMessages = messagesDao.getUnRead(u.getName(),
					ownuser.getName());
			JLabel btn1;
			if (unreadMessages.size() == 0) {
				btn1 = new JLabel("");
			} else {
				btn1 = new JLabel("" + unreadMessages.size());
			}
			btn1.setForeground(Color.RED);
			btn1.setIcon(new ImageIcon(u.getHeadimg().trim()));
			JLabel jl2 = new JLabel(u.getName()
					+ getFixedLength(u.getName().length()));
			jl2.setFont(new Font("��������", Font.PLAIN, 25));
			// �Զ�������
			MyJPanelSingle p = new MyJPanelSingle(u, ownuser);
			p.setBounds(0, 0, 318, 50);
			p.add(btn1);
			p.add(jl2);
			panel.add(p);
			panel.setPreferredSize(new Dimension(50, 50+i * 50*2));
		}
		panel.updateUI();

	}

	/**
	 * ���ݳ��ȵĴ�С��������Ҫ��ӵĿո�
	 * 
	 * @param size
	 * @return
	 */
	private String getFixedLength(int size) {
		StringBuffer str = new StringBuffer();
		for (int i = size; i < 40; i++) {
			str.append(" ");
		}
		return str.toString();
	}

	/**
	 * �˵�
	 */
	private void createPopupMenu() {
		m_popupMenu = new JPopupMenu();
		
		JMenuItem MenItem1 = new JMenuItem();
		MenItem1.setText("  ����Ⱥ   ");
		MenItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// �ò�����Ҫ������
				//����Ⱥ
				
			}
		});
		
		JMenuItem MenItem2 = new JMenuItem();
		MenItem2.setText("�޸����� ");
		MenItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// �ò�����Ҫ������
				SwingUpdate swingUpdate = new SwingUpdate(homeFrame, ownuser);
				swingUpdate.setVisible(true);

			}
		});
		JMenuItem MenItem3 = new JMenuItem();
		MenItem3.setText("�л��û� ");
		MenItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// �ò�����Ҫ������
				usersDao.setUnOnlineById(ownuser.getId());
				loginFrame.clear();
				loginFrame.setVisible(true);
				dispose();

			}
		});

		JMenuItem MenItem4 = new JMenuItem();
		MenItem4.setText("�˳� ");
		MenItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// �ò�����Ҫ������
				int result = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ�˳�����",
						"��ʾ", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					UsersDao usersDao = new UsersDao();
					usersDao.setUnOnlineById(ownuser.getId());
					System.exit(0);
				}
			}
		});
		m_popupMenu.add(MenItem1);
		m_popupMenu.add(MenItem2);
		m_popupMenu.add(MenItem3);
		m_popupMenu.add(MenItem4);
	}

	public void setLoginFrame(SwingLogin f) {
		this.loginFrame = f;
	}

	public void setHomeFrame(SwingHome h) {
		this.homeFrame = h;
	}
	
	public SwingLogin getLoginFrame() {
		return loginFrame;
	}
	
	public void UpdateUI(Users u){
		ownuser=u;
		sh_jl_remark.setText(u.getRemark());
		
	}
}
