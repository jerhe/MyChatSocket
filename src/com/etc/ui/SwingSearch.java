package com.etc.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.etc.base.BaseJFrame;
import com.etc.dao.FriendsDao;
import com.etc.dao.GroupsDao;
import com.etc.dao.UsersDao;
import com.etc.entity.Friends;
import com.etc.entity.Groups;
import com.etc.entity.Users;
import com.etc.util.DataUtil;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

/**
 * 此类实现查找好友查找群功能
 * 
 * @author Administrator
 * 
 */

public class SwingSearch extends BaseJFrame {

	private UsersDao usersDao = new UsersDao();
	private GroupsDao groupsDao = new GroupsDao();
	private JTextField ss_txt_s;// 搜索框
	private JLabel ss_JL_sp; // “找人”
	private JLabel ss_JL_sgid; // “通过群号查找群”
	private JLabel ss_JL_sgname;// “通过群名查找群”
	private JButton ss_btn_s; // 搜索按钮
	int flag = 0;// flag为进行查找操作的标识符。flag=0为找人。flag=1为通过群号查找群。flag=2为通过群名查找群。
	private JTable table;
	private String[] headtable = new String[] { "昵称" };
	private final int STRIP = 5;
	private int maxPage = 1;
	private int nowPage = 1;
	private Object[][] obj;
	private SwingHome homeFrame;
	private JScrollPane scrollPane;
	private JButton jumpPage;
	private JButton afterPage;
	private JTextField textPage;
	private JButton frontPage;
	private JLabel pageNumber;
	private ArrayList<Users> userlist;
	private ArrayList<String> grouplist;
	private JPopupMenu m_popupMenu;
	private JMenuItem menuItem1;
	private JMenuItem menuItem2;
	private JMenuItem menuItem3;
	private JPanel panel;

	/**
	 * 无参构造调用界面初始化函数
	 */
	public SwingSearch(SwingHome h, Users u) {
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setForeground(Color.WHITE);
		ownuser = u;
		homeFrame = h;
		initialize();
	}

	/**
	 * 将搜索框中的背景字封装成setss_txt_s方法
	 * 
	 * @param flag
	 */
	public void setss_txt_s(int flag) {
		if (flag == 0) {
			ss_txt_s.setText("请输入您要查找的好友名称");
		} else if (flag == 1) {
			ss_txt_s.setText("请输入您要查找的群号");
		} else if (flag == 2) {
			ss_txt_s.setText("请输入您要查找的群名称");
		}
		ss_txt_s.setCaretPosition(0);
		ss_txt_s.setForeground(SystemColor.gray);
	}

	/**
	 * 界面初始化
	 */
	private void initialize() {
		// frame为查找好友及群函数的主窗口界面
		setTitle("\u67E5\u627E");
		setLocationRelativeTo(this);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						SwingSearch.class
								.getResource("/com/sun/java/swing/plaf/windows/icons/Question.gif")));
		setBounds(100, 100, 569, 454);
		// 界面关闭则结束进程并设置关闭事件
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// 窗口关闭事件
				homeFrame.setVisible(true);
				if (flag == 0) {
					homeFrame.initMyFriendPanel();
				} else {
					homeFrame.initMyGroupJPanel();
				}

				dispose();

			}
		});
		getContentPane().setLayout(null);
		/**
		 * 给table添加右击事件
		 */
		createPopupMenu();

		/**
		 * 下一页
		 */

		/**
		 * 上一页
		 */
		// ss_txt_s为搜索框
		ss_txt_s = new JTextField();
		ss_txt_s.setBounds(75, 100, 223, 29);
		ss_txt_s.addKeyListener(new KeyAdapter() {
			/**
			 * 键盘打字事件，开始打字时，字体变为黑色
			 */
			@Override
			public void keyTyped(KeyEvent e) {
				ss_txt_s.setForeground(SystemColor.BLACK);
			}

			/**
			 * 键盘按下事件，当开始打字，文本框中字消失
			 */
			@Override
			public void keyPressed(KeyEvent e) {

				if (ss_txt_s.getText().equals("请输入您要查找的好友名称")) {
					ss_txt_s.setText("");
				} else if (ss_txt_s.getText().equals("请输入您要查找的群号")) {
					ss_txt_s.setText("");
				} else if (ss_txt_s.getText().equals("请输入您要查找的群名称")) {
					ss_txt_s.setText("");
				}
			}
		});
		ss_txt_s.addMouseListener(new MouseAdapter() {
			/**
			 * 鼠标点击事件，当鼠标点击进文本框时，文本为空
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				ss_txt_s.setText("");
			}
		});
		getContentPane().add(ss_txt_s);
		ss_txt_s.setColumns(10);

		// ss_btn_s为搜索按钮
		ss_btn_s = new JButton("\u641C\u7D22\uFF08S\uFF09");
		ss_btn_s.setBounds(322, 100, 103, 29);
		ss_btn_s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// flag==0时为找人
				if (flag == 0) {
					System.out.println("正在进行找人操作");
					String finduser = ss_txt_s.getText();

					userlist = usersDao.geteVagueUsersByName(finduser);
					obj = new Object[STRIP][1];
					nowPage = 1;
					maxPage = (int) Math.ceil(((double) userlist.size())
							/ STRIP);
					reslultTable(nowPage);

				}
				// flag==1时为通过群号找群
				else if (flag == 1) {
					System.out.println("正在通过群号找群");
					String findgroupbyid = ss_txt_s.getText();
					if (findgroupbyid.matches("^\\d+$")) {
						grouplist = groupsDao.getGroupsByGroupid(findgroupbyid);
						obj = new Object[STRIP][1];

						nowPage = 1;
						maxPage = (int) Math.ceil(((double) grouplist.size())
								/ STRIP);
						reslultTable(nowPage);

					} else {
						JOptionPane.showMessageDialog(null, "您输入的格式不正确");
					}

				}
				// flag==2时为通过群名找群
				else if (flag == 2) {
					System.out.println("正在通过群名找群");
					String findgroupbyname = ss_txt_s.getText();
					grouplist = groupsDao.getGroupsByGroupname(findgroupbyname);
					obj = new Object[STRIP][1];
					nowPage = 1;
					maxPage = (int) Math.ceil(((double) grouplist.size())
							/ STRIP);
					reslultTable(nowPage);

				}

			}
		});
		getContentPane().add(ss_btn_s);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 139, 350, 170);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setEnabled(false);
		table.setRowHeight(25);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					// 通过点击位置找到点击为表格中的行
					int focusedRowIndex = table.rowAtPoint(e.getPoint());
					if (focusedRowIndex == -1) {
						return;
					}
					// 将表格所选项设为当前右键点击的行
					table.setRowSelectionInterval(focusedRowIndex,
							focusedRowIndex);
					// 弹出菜单
					if (flag == 0) {
						m_popupMenu.removeAll();
						m_popupMenu.add(menuItem1);
						m_popupMenu.add(menuItem2);
						m_popupMenu.updateUI();
						m_popupMenu.show(table, e.getX(), e.getY());
					} else {
						m_popupMenu.removeAll();
						m_popupMenu.add(menuItem3);
						m_popupMenu.updateUI();
						m_popupMenu.show(table, e.getX(), e.getY());
					}

				}
			}
		});
		scrollPane.setViewportView(table);

		jumpPage = new JButton("\u8DF3\u9875");
		jumpPage.setBounds(257, 318, 67, 23);
		jumpPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int p = Integer.valueOf(textPage.getText());
				if (p <= maxPage && p >= 1) {
					reslultTable(p);
					nowPage = p;

				} else {
					JOptionPane
							.showMessageDialog(getContentPane(), "输入的页数超出范围");
				}
			}
		});
		getContentPane().add(jumpPage);
		afterPage = new JButton("\u4E0B\u4E00\u9875");
		afterPage.setBounds(334, 318, 91, 23);
		getContentPane().add(afterPage);

		textPage = new JTextField();
		textPage.setBounds(216, 319, 31, 21);
		textPage.setText("1");
		textPage.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(textPage);
		textPage.setColumns(10);
		frontPage = new JButton("\u4E0A\u4E00\u9875");
		frontPage.setBounds(115, 318, 91, 23);
		frontPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nowPage != 1) {
					nowPage--;
					reslultTable(nowPage);
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "已经最前页");
				}
			}
		});
		getContentPane().add(frontPage);

		pageNumber = new JLabel("\u7B2C1\u9875");
		pageNumber.setBounds(53, 322, 53, 15);
		pageNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(pageNumber);

		// ss_JL_sp为“找人”的JLabel控件
		ss_JL_sp = new JLabel("\u627E\u4EBA");
		ss_JL_sp.setBounds(65, 34, 54, 29);
		getContentPane().add(ss_JL_sp);
		// 默认“找人”为红色
		ss_JL_sp.setForeground(SystemColor.red);
		// 为ss_JL_sp添加单击事件
		ss_JL_sp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 当点击“找人”控件时，该控件变为红色字体，其余颜色变灰
				ss_JL_sp.setForeground(SystemColor.red);
				ss_JL_sgid.setForeground(SystemColor.BLACK);
				ss_JL_sgname.setForeground(SystemColor.BLACK);

				// flag=0,默认没选择时进行找人操作
				flag = 0;
				setss_txt_s(flag);
			}
		});

		ss_JL_sp.setFont(new Font("楷体", Font.PLAIN, 20));
		// ss_JL_sgid为“通过群号查找群”的JLabel控件
		ss_JL_sgid = new JLabel("\u901A\u8FC7\u7FA4\u53F7\u627E\u7FA4");
		ss_JL_sgid.setBounds(133, 34, 144, 29);
		getContentPane().add(ss_JL_sgid);
		// 为ss_JL_sgid添加单击事件
		ss_JL_sgid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ss_JL_sgname为“通过群号查找群”的JLabel控件
				ss_JL_sgid.setForeground(SystemColor.red);
				ss_JL_sgname.setForeground(SystemColor.BLACK);
				ss_JL_sp.setForeground(SystemColor.BLACK);

				// flag=1,通过群号进行找群操作
				flag = 1;
				setss_txt_s(flag);
			}
		});
		ss_JL_sgid.setFont(new Font("楷体", Font.PLAIN, 20));
		// ss_JL_sgname为“通过群名查找群”的JLabel控件
		ss_JL_sgname = new JLabel("\u901A\u8FC7\u7FA4\u540D\u627E\u7FA4");
		ss_JL_sgname.setBounds(293, 31, 144, 29);
		getContentPane().add(ss_JL_sgname);
		// 为ss_JL_sgname添加单击事件
		ss_JL_sgname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 当点击“通过群名查找群”控件时，该控件变为红色字体，其余颜色变灰
				ss_JL_sgname.setForeground(SystemColor.red);
				ss_JL_sp.setForeground(SystemColor.BLACK);
				ss_JL_sgid.setForeground(SystemColor.BLACK);

				// flag=2,通过群名进行找群操作
				flag = 2;
				setss_txt_s(flag);
			}

		});
		ss_JL_sgname.setFont(new Font("楷体", Font.PLAIN, 20));

		JButton btnNewButton = new JButton("");
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(0, 70, 557, 9);
		getContentPane().add(btnNewButton);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 557, 420);
		getContentPane().add(panel);
		afterPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nowPage != maxPage) {
					nowPage++;
					reslultTable(nowPage);
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "已经最后页");
				}
			}
		});
		setss_txt_s(flag);
	}

	/**
	 * 通过页号初始化表格
	 * 
	 * @param now
	 */
	public void reslultTable(int now) {
		for (int i = 0; i < STRIP; i++) {
			if (flag == 0) {
				headtable[0] = "用户昵称";
				if (userlist.size() > (i + (now - 1) * STRIP)) {
					obj[i][0] = userlist.get(i + (now - 1) * STRIP).getName();
				} else {
					obj[i][0] = "";
				}
			} else {
				headtable[0] = "群昵称";
				if (grouplist.size() > (i + (now - 1) * STRIP)) {
					obj[i][0] = grouplist.get(i + (now - 1) * STRIP);
				} else {
					obj[i][0] = "";
				}
			}

		}
		table.setModel(new DefaultTableModel(obj, headtable));
		nowPage = now;
		// table.updateUI();
		pageNumber.setText("第" + now + "页");
		textPage.setText("" + now);
	}

	/**
	 * 创建右键菜单
	 */
	private void createPopupMenu() {
		m_popupMenu = new JPopupMenu();

		menuItem1 = new JMenuItem();
		menuItem1.setText(" 查看资料  ");
		menuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// 该操作需要做的事
				// JOptionPane.showMessageDialog(getContentPane(),
				// table.getSelectedRow() + "");
				int row = table.getSelectedRow();
				if (userlist.size() > ((nowPage - 1) * STRIP + row)) {
					String friendname = userlist.get(
							(nowPage - 1) * STRIP + row).getName();
					JDialogUserDetail detail = new JDialogUserDetail(
							friendname, ownuser.getName());
					detail.setModal(true);
					detail.setVisible(true);
				}

			}
		});
		menuItem2 = new JMenuItem();
		menuItem2.setText(" 添加好友  ");
		menuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// 该操作需要做的事
				// 通过判断flag的值，来分辨用户是在查找什么
				/**
				 * 找人操作
				 */
				FriendsDao friendsDao = new FriendsDao();
				int row = table.getSelectedRow();
				if (userlist.size() > ((nowPage - 1) * STRIP + row)) {
					String friendname = userlist.get(
							(nowPage - 1) * STRIP + row).getName();
					String myname = ownuser.getName();
					if (!friendname.equals(myname)) {
						if (!friendsDao.isFriends(friendname, myname)) {
							Friends f1 = new Friends(myname, friendname);
							Friends f2 = new Friends(friendname, myname);
							friendsDao.AddFriends(f1);
							friendsDao.AddFriends(f2);
							DataUtil.showMessage("添加成功");
							userlist.remove(friendname);
							reslultTable(nowPage);
						} else {
							DataUtil.showMessage("已经是好友关系");
						}
					} else {
						DataUtil.showMessage("不能添加自己为好友");
					}
				}

			}
		});

		menuItem3 = new JMenuItem();
		menuItem3.setText(" 加入该群  ");
		menuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// 该操作需要做的事
				// 通过判断flag的值，来分辨用户是在查找什么
				GroupsDao groupsDao = new GroupsDao();
				int row = table.getSelectedRow();
				/**
				 * 通过群id查找
				 */
				if (flag == 1) {
					if (grouplist.size() > ((nowPage - 1) * STRIP + row)) {
						String groupid = grouplist.get((nowPage - 1) * STRIP
								+ row);
						if(groupsDao.intoGroupByGroupid(groupid,ownuser.getName())){
							DataUtil.showMessage("添加成功");
						}else{
							DataUtil.showMessage("已加入该群");
						}
						
					}
				}
				
				/**
				 * 通过群名查找
				 */
				if(flag == 2){
					if (grouplist.size() > ((nowPage - 1) * STRIP + row)) {
						String groupname = grouplist.get((nowPage - 1) * STRIP
								+ row);
						if(groupsDao.intoGroupByGroupname(groupname,ownuser.getName())){
							DataUtil.showMessage("添加成功");
						}else{
							DataUtil.showMessage("已加入该群");
						}
					}
				}

			}
		});

		m_popupMenu.add(menuItem1);
		m_popupMenu.add(menuItem2);
		m_popupMenu.add(menuItem3);
	}
}
