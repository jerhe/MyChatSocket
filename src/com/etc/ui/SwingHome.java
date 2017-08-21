package com.etc.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;

import com.bsb.tool.ChatManager;
import com.etc.base.BaseJFrame;
import com.etc.dao.FriendsDao;
import com.etc.dao.GroupsDao;
import com.etc.dao.MessagesDao;
import com.etc.entity.Groups;
import com.etc.entity.Users;
import com.etc.util.DataUtil;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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

	/**
	 * 通过够着函数传递Users对象
	 * 
	 * @param us
	 */
	public SwingHome(Users us) {

		/**
		 * 初始化用户的最近聊天列表，好友列表，群列表 这里采用恶汉的方式初始化 可更改成懒汉 默认显示我的好友
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 334, 642);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		sh_img_head = new JLabel("");
		sh_img_head.setIcon(new ImageIcon(ownuser.getHeadimg()));
		sh_img_head.setBounds(26, 21, 80, 80);
		contentPane.add(sh_img_head);

		sh_jl_name = new JLabel(ownuser.getName());
		sh_jl_name.setHorizontalAlignment(SwingConstants.LEFT);
		sh_jl_name.setBounds(116, 21, 91, 25);
		contentPane.add(sh_jl_name);

		sh_jl_remark = new JLabel(ownuser.getRemark());
		sh_jl_remark.setBounds(116, 68, 146, 33);
		contentPane.add(sh_jl_remark);

		sh_jl_recent = new JLabel("\u6700\u8FD1");
		/**
		 * 最近聊天按钮,点击显示最近聊天按钮
		 */
		sh_jl_recent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 设置点击事件
				MyRecentJPanel();
			}
		});
		sh_jl_recent.setHorizontalAlignment(SwingConstants.CENTER);
		sh_jl_recent.setFont(new Font("华文隶书", Font.PLAIN, 16));
		sh_jl_recent.setBounds(0, 124, 102, 53);
		contentPane.add(sh_jl_recent);

		/**
		 * 群列表按钮，显示群列表
		 */
		sh_jl_group = new JLabel("\u7FA4");
		sh_jl_group.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MyGroupJPanel();
				super.mousePressed(e);
			}
		});
		sh_jl_group.setHorizontalAlignment(SwingConstants.CENTER);
		sh_jl_group.setFont(new Font("华文隶书", Font.PLAIN, 16));
		sh_jl_group.setBounds(213, 124, 108, 52);
		contentPane.add(sh_jl_group);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 187, 318, 416);

		contentPane.add(scrollPane);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		scrollPane.setViewportView(panel);
		panel.setBounds(0, 187, 318, 416);
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));

		// 默认显示好友列表
		MyFriendPanel();
		/**
		 * 好友按钮,显示好友列表列表的登录界面
		 */
		sh_jl_friend = new JLabel("\u597D\u53CB");
		sh_jl_friend.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 单击事件
				super.mousePressed(e);
				MyFriendPanel();
			}
		});
		sh_jl_friend.setHorizontalAlignment(SwingConstants.CENTER);
		sh_jl_friend.setFont(new Font("华文隶书", Font.PLAIN, 16));
		sh_jl_friend.setBounds(112, 124, 91, 53);
		contentPane.add(sh_jl_friend);

		setLocation(900, 40);
	}

	/**
	 * 初始话群列表
	 */
	public void MyGroupJPanel() {
		panel.removeAll();
		// 初始化最近聊天的界面
		for (int i = 0; i < groupsList.size(); i++) {
			Groups g = groupsList.get(i);
			// System.out.println(u);
			JLabel btn1 = new JLabel("");
			btn1.setIcon(new ImageIcon("/img/aa.jpg"));
			JLabel jl2 = new JLabel(g.getGroupname()
					+ getFixedLength(g.getGroupname().length()));
			jl2.setFont(new Font("华文隶书", Font.PLAIN, 25));
			// 自定义容器
			//MyJPanelSingle p = new MyJPanelSingle(g, ownuser);
//			p.setBounds(0, 0, 318, 50);
//			p.add(btn1);
//			p.add(jl2);
//			panel.add(p);
			panel.setPreferredSize(new Dimension(50, 50 + i * 50 * 2));
		}
		panel.updateUI();
	}

	/**
	 * 初始化最近聊天列表
	 */
	public void MyRecentJPanel() {
		panel.removeAll();
		// 初始化最近聊天的界面
		for (int i = 0; i < recentsList.size(); i++) {
			Users u = recentsList.get(i);
			// System.out.println(u);
			JLabel btn1 = new JLabel("");
			btn1.setIcon(new ImageIcon(u.getHeadimg().trim()));
			JLabel jl2 = new JLabel(u.getName()
					+ getFixedLength(u.getName().length()));
			jl2.setFont(new Font("华文隶书", Font.PLAIN, 25));
			// 自定义容器
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
	 * 初始化好友列表
	 */
	public void MyFriendPanel() {
		panel.removeAll();
		// 初始化好友列表
		for (int i = 0; i < friendsList.size(); i++) {
			Users u = friendsList.get(i);
			// System.out.println(u);
			JLabel btn1 = new JLabel("");
			btn1.setIcon(new ImageIcon(u.getHeadimg().trim()));
			JLabel jl2 = new JLabel(u.getName()
					+ getFixedLength(u.getName().length()));
			jl2.setFont(new Font("华文隶书", Font.PLAIN, 25));
			// 自定义容器
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
	 * 传递长度的大小，返回需要添加的空格
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
}
