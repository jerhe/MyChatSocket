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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.AbstractListModel;
import javax.swing.JScrollBar;

import com.etc.base.BaseJFrame;
import com.etc.dao.GroupMessagesDao;
import com.etc.dao.GroupsDao;
import com.etc.dao.UsersDao;
import com.etc.entity.GroupMessages;
import com.etc.entity.Groups;
import com.etc.entity.Messages;
import com.etc.entity.SendFlag;
import com.etc.entity.Users;
import com.etc.tool.ChatManager;
import com.etc.util.DataUtil;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

/**
 * 群聊界面
 * 
 * @author zhuzhen 功能的实现
 * @author yangchengrong 界面的编写
 */
public class SwingGroupChat extends BaseJFrame {
	private SwingGroupChat frame;
	private final JPanel panel_north = new JPanel();
	private JLabel sgc_img_head;
	private JLabel sgc_jl_name;
	private JButton ssc_btn_send;
	private JTextArea sgc_txt_send;
	private JTextArea sgc_txt_content;
	private JList<String> sgc_jlist_member;
	private Groups groups;
	private ArrayList<Users> groupMember = new ArrayList<>();
	private UsersDao usersDao = new UsersDao();
	private GroupsDao groupsDao = new GroupsDao();
	private GroupMessagesDao groupMessagesDao = new GroupMessagesDao();
	private Socket socket;
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	private JPanel panel;
	private JScrollPane scrollPane_2;
	private SendFlag sendFlag = new SendFlag();

	/**
	 * 构造函数
	 */
	public SwingGroupChat(Groups g, Users u) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				SwingGroupChat.class.getResource("/pic/chat.png")));
		groups = g;
		ownuser = u;
		groupMember = groupsDao.getGroupsUserByGroupid(g.getGroupid());
		setTitle(g.getGroupname());
		initialize();
	}

	/**
	 * 初始化该窗体
	 */
	private void initialize() {
		setResizable(false);
		setBounds(100, 100, 710, 561);
		// 设置关闭当前窗体
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Already there

		/**
		 * 添加关闭窗口的事件监听
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// 关闭过程中调用的方法
				int result = JOptionPane.showConfirmDialog(null, "是否要退出？",
						"提示", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					//frame.dispose();
					frame.setVisible(false);
				}
			}
		});

		getContentPane().setLayout(null);
		panel_north.setBounds(0, 0, 488, 86);
		getContentPane().add(panel_north);
		panel_north.setLayout(null);

		/**
		 * 群头像
		 */
		sgc_img_head = new JLabel("");
		sgc_img_head.setIcon(new ImageIcon(groups.getGroupimg()));
		sgc_img_head.setBackground(Color.BLACK);
		sgc_img_head.setFont(new Font("叶根友毛笔行书2.0版", Font.PLAIN, 34));
		sgc_img_head.setBounds(10, 10, 69, 66);
		panel_north.add(sgc_img_head);

		/**
		 * 群名字
		 */
		sgc_jl_name = new JLabel(groups.getGroupname());
		sgc_jl_name.setFont(new Font("叶根友毛笔行书2.0版", Font.PLAIN, 21));
		sgc_jl_name.setBounds(97, 10, 172, 66);
		panel_north.add(sgc_jl_name);

		JPanel panel_east = new JPanel();
		panel_east.setBounds(487, 0, 162, 481);
		getContentPane().add(panel_east);
		panel_east.setLayout(null);

		/**
		 * 群名字
		 */
		JLabel sgc_jl_group = new JLabel("\u7FA4\u6210\u5458");
		sgc_jl_group.setFont(new Font("叶根友毛笔行书2.0版", Font.PLAIN, 22));
		sgc_jl_group.setBounds(0, 43, 162, 42);
		panel_east.add(sgc_jl_group);

		/**
		 * 群成员列表
		 */
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 86, 162, 394);
		panel_east.add(scrollPane_1);
		sgc_jlist_member = new JList<String>();
		sgc_jlist_member.setFont(new Font("宋体", Font.PLAIN, 15));
		for (int i = 0; i < groupMember.size(); i++) {
			String username = groupMember.get(i).getName();
			dlm.addElement(username);
		}
		sgc_jlist_member.setModel(dlm);
		sgc_jlist_member.setToolTipText("\u4F60\u662F");
		scrollPane_1.setViewportView(sgc_jlist_member);

		/**
		 * 群消息内容框
		 */

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 89, 488, 245);
		scrollPane_2.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent evt) {
						if (evt.getAdjustmentType() == AdjustmentEvent.TRACK
								&& sendFlag.getIsNeedBottom() <= 3) {
							scrollPane_2.getVerticalScrollBar().setValue(
									scrollPane_2.getVerticalScrollBar()
											.getModel().getMaximum()
											- scrollPane_2
													.getVerticalScrollBar()
													.getModel().getExtent());
							sendFlag.addIsNeedBottom();
						}
					}
				});
		getContentPane().add(scrollPane_2);

		sgc_txt_content = new JTextArea();
		sgc_txt_content.setLineWrap(true);
		sgc_txt_content.setFont(new Font("Monospaced", Font.PLAIN, 16));
		sgc_txt_content.setEnabled(false);
		sgc_txt_content.setEditable(false);
		scrollPane_2.setViewportView(sgc_txt_content);

		/**
		 * 群消息发送框
		 */
		JPanel panel_floor = new JPanel();
		panel_floor.setBounds(0, 338, 488, 143);
		getContentPane().add(panel_floor);
		panel_floor.setLayout(null);

		/**
		 * 发送按钮
		 */
		ssc_btn_send = new JButton("\u53D1\u9001\uFF08S\uFF09");
		ssc_btn_send.setBounds(369, 110, 105, 23);
		ssc_btn_send.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				// 发送按钮的
				send();

			}

			/**
			 * 发送事件
			 */
			private void send() {
				if (!sgc_txt_send.getText().trim().equals("")) {
					Date now = new Date();
					String nowtime = DataUtil.dateToString(now);
					// 保存信息到数据库
					GroupMessages gmsg = new GroupMessages(ownuser.getName(),
							groups.getGroupid(), sgc_txt_send.getText(),
							nowtime, 1);
					int gmsgid = groupMessagesDao.saveGroupMessages(gmsg);

					String sendString = gmsgid + "@" + ownuser.getName() + "@"
							+ groups.getGroupid() + "@ " + nowtime + " @"
							+ sgc_txt_send.getText();
					ChatManager.getCM().send(sendString);
					sendFlag.setSendflag(true);
					appendText(ownuser.getName() + " " + nowtime + " " + " ："
							+ "\n" + sgc_txt_send.getText());
					sgc_txt_send.setText("");
				} else {
					DataUtil.showMessage("发送的消息不能为空！");
				}

			}
		});
		panel_floor.add(ssc_btn_send);

		/**
		 * 发送的文本框
		 */
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 486, 104);
		panel_floor.add(scrollPane);

		sgc_txt_send = new JTextArea();
		scrollPane.setViewportView(sgc_txt_send);
		sgc_txt_send.setFont(new Font("Monospaced", Font.PLAIN, 16));

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 704, 533);
		getContentPane().add(panel);
		/**
		 * 添加监听键盘监听
		 */
		sgc_txt_send.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					send();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					sgc_txt_send.setText("");
				}
			}
		});

	}

	/**
	 * 接受数据时调用的函数并且设置内容框
	 * 
	 * @param string
	 */
	@Override
	public void appendText(String in) {	
		//DataUtil.setContent(sgc_txt_content, in,sendFlag);
		sgc_txt_content.append("\n" + in);
		sendFlag.setSendflag(false);
	}

	@Override
	public void appendTextother(String in, String fromnname, String groupid,
			int gmsgid) {
		if (groupid.equals(groups.getGroupid())) {
			DataUtil.readGroupMessage(sgc_txt_content, in, gmsgid,sendFlag);
		}

	}

	/**
	 * 初始化界面
	 * 
	 * @param frame
	 */
	public void setFrame(SwingGroupChat frame) {
		this.frame = frame;
		/**
		 * 连接服务器
		 */
		try {
			socket = new Socket(DataUtil.IPSTRING, 8066);
			ChatManager.getCM().connect(DataUtil.IPSTRING, ownuser.getName(),
					socket);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public SwingGroupChat getFrame() {
		return frame;
	}

	private void send() {
		if (!sgc_txt_send.getText().trim().equals("")) {
			Date now = new Date();
			String nowtime = DataUtil.dateToString(now);
			// 保存信息到数据库
			GroupMessages gmsg = new GroupMessages(ownuser.getName(),
					groups.getGroupid(), sgc_txt_send.getText(), nowtime,
					groupMember.size()-1);
			int gmsgid = groupMessagesDao.saveGroupMessages(gmsg);

			String sendString = gmsgid + "@" + ownuser.getName() + "@"
					+ groups.getGroupid() + "@ " + nowtime + " @"
					+ sgc_txt_send.getText().replace("\n", "");
			ChatManager.getCM().send(sendString);

			appendText(ownuser.getName() + " " + nowtime + " " + " ：" + "\n"
					+ sgc_txt_send.getText().replace("\n", ""));
			sendFlag.setIsNeedBottom(0);
			sgc_txt_send.setText("");
		} else {
			sgc_txt_send.setText("");
			DataUtil.showMessage("发送的消息不能为空！");
		}
	}
}
