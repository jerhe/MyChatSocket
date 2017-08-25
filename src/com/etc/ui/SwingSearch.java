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
 * ����ʵ�ֲ��Һ��Ѳ���Ⱥ����
 * 
 * @author Administrator
 * 
 */

public class SwingSearch extends BaseJFrame {

	private UsersDao usersDao = new UsersDao();
	private GroupsDao groupsDao = new GroupsDao();
	private JTextField ss_txt_s;// ������
	private JLabel ss_JL_sp; // �����ˡ�
	private JLabel ss_JL_sgid; // ��ͨ��Ⱥ�Ų���Ⱥ��
	private JLabel ss_JL_sgname;// ��ͨ��Ⱥ������Ⱥ��
	private JButton ss_btn_s; // ������ť
	int flag = 0;// flagΪ���в��Ҳ����ı�ʶ����flag=0Ϊ���ˡ�flag=1Ϊͨ��Ⱥ�Ų���Ⱥ��flag=2Ϊͨ��Ⱥ������Ⱥ��
	private JTable table;
	private String[] headtable = new String[] { "�ǳ�" };
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
	 * �޲ι�����ý����ʼ������
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
	 * ���������еı����ַ�װ��setss_txt_s����
	 * 
	 * @param flag
	 */
	public void setss_txt_s(int flag) {
		if (flag == 0) {
			ss_txt_s.setText("��������Ҫ���ҵĺ�������");
		} else if (flag == 1) {
			ss_txt_s.setText("��������Ҫ���ҵ�Ⱥ��");
		} else if (flag == 2) {
			ss_txt_s.setText("��������Ҫ���ҵ�Ⱥ����");
		}
		ss_txt_s.setCaretPosition(0);
		ss_txt_s.setForeground(SystemColor.gray);
	}

	/**
	 * �����ʼ��
	 */
	private void initialize() {
		// frameΪ���Һ��Ѽ�Ⱥ�����������ڽ���
		setTitle("\u67E5\u627E");
		setLocationRelativeTo(this);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						SwingSearch.class
								.getResource("/com/sun/java/swing/plaf/windows/icons/Question.gif")));
		setBounds(100, 100, 569, 454);
		// ����ر���������̲����ùر��¼�
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// ���ڹر��¼�
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
		 * ��table����һ��¼�
		 */
		createPopupMenu();

		/**
		 * ��һҳ
		 */

		/**
		 * ��һҳ
		 */
		// ss_txt_sΪ������
		ss_txt_s = new JTextField();
		ss_txt_s.setBounds(75, 100, 223, 29);
		ss_txt_s.addKeyListener(new KeyAdapter() {
			/**
			 * ���̴����¼�����ʼ����ʱ�������Ϊ��ɫ
			 */
			@Override
			public void keyTyped(KeyEvent e) {
				ss_txt_s.setForeground(SystemColor.BLACK);
			}

			/**
			 * ���̰����¼�������ʼ���֣��ı���������ʧ
			 */
			@Override
			public void keyPressed(KeyEvent e) {

				if (ss_txt_s.getText().equals("��������Ҫ���ҵĺ�������")) {
					ss_txt_s.setText("");
				} else if (ss_txt_s.getText().equals("��������Ҫ���ҵ�Ⱥ��")) {
					ss_txt_s.setText("");
				} else if (ss_txt_s.getText().equals("��������Ҫ���ҵ�Ⱥ����")) {
					ss_txt_s.setText("");
				}
			}
		});
		ss_txt_s.addMouseListener(new MouseAdapter() {
			/**
			 * ������¼�������������ı���ʱ���ı�Ϊ��
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				ss_txt_s.setText("");
			}
		});
		getContentPane().add(ss_txt_s);
		ss_txt_s.setColumns(10);

		// ss_btn_sΪ������ť
		ss_btn_s = new JButton("\u641C\u7D22\uFF08S\uFF09");
		ss_btn_s.setBounds(322, 100, 103, 29);
		ss_btn_s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// flag==0ʱΪ����
				if (flag == 0) {
					System.out.println("���ڽ������˲���");
					String finduser = ss_txt_s.getText();

					userlist = usersDao.geteVagueUsersByName(finduser);
					obj = new Object[STRIP][1];
					nowPage = 1;
					maxPage = (int) Math.ceil(((double) userlist.size())
							/ STRIP);
					reslultTable(nowPage);

				}
				// flag==1ʱΪͨ��Ⱥ����Ⱥ
				else if (flag == 1) {
					System.out.println("����ͨ��Ⱥ����Ⱥ");
					String findgroupbyid = ss_txt_s.getText();
					if (findgroupbyid.matches("^\\d+$")) {
						grouplist = groupsDao.getGroupsByGroupid(findgroupbyid);
						obj = new Object[STRIP][1];

						nowPage = 1;
						maxPage = (int) Math.ceil(((double) grouplist.size())
								/ STRIP);
						reslultTable(nowPage);

					} else {
						JOptionPane.showMessageDialog(null, "������ĸ�ʽ����ȷ");
					}

				}
				// flag==2ʱΪͨ��Ⱥ����Ⱥ
				else if (flag == 2) {
					System.out.println("����ͨ��Ⱥ����Ⱥ");
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
					// ͨ�����λ���ҵ����Ϊ����е���
					int focusedRowIndex = table.rowAtPoint(e.getPoint());
					if (focusedRowIndex == -1) {
						return;
					}
					// �������ѡ����Ϊ��ǰ�Ҽ��������
					table.setRowSelectionInterval(focusedRowIndex,
							focusedRowIndex);
					// �����˵�
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
							.showMessageDialog(getContentPane(), "�����ҳ��������Χ");
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
					JOptionPane.showMessageDialog(getContentPane(), "�Ѿ���ǰҳ");
				}
			}
		});
		getContentPane().add(frontPage);

		pageNumber = new JLabel("\u7B2C1\u9875");
		pageNumber.setBounds(53, 322, 53, 15);
		pageNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(pageNumber);

		// ss_JL_spΪ�����ˡ���JLabel�ؼ�
		ss_JL_sp = new JLabel("\u627E\u4EBA");
		ss_JL_sp.setBounds(65, 34, 54, 29);
		getContentPane().add(ss_JL_sp);
		// Ĭ�ϡ����ˡ�Ϊ��ɫ
		ss_JL_sp.setForeground(SystemColor.red);
		// Ϊss_JL_sp��ӵ����¼�
		ss_JL_sp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ����������ˡ��ؼ�ʱ���ÿؼ���Ϊ��ɫ���壬������ɫ���
				ss_JL_sp.setForeground(SystemColor.red);
				ss_JL_sgid.setForeground(SystemColor.BLACK);
				ss_JL_sgname.setForeground(SystemColor.BLACK);

				// flag=0,Ĭ��ûѡ��ʱ�������˲���
				flag = 0;
				setss_txt_s(flag);
			}
		});

		ss_JL_sp.setFont(new Font("����", Font.PLAIN, 20));
		// ss_JL_sgidΪ��ͨ��Ⱥ�Ų���Ⱥ����JLabel�ؼ�
		ss_JL_sgid = new JLabel("\u901A\u8FC7\u7FA4\u53F7\u627E\u7FA4");
		ss_JL_sgid.setBounds(133, 34, 144, 29);
		getContentPane().add(ss_JL_sgid);
		// Ϊss_JL_sgid��ӵ����¼�
		ss_JL_sgid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ss_JL_sgnameΪ��ͨ��Ⱥ�Ų���Ⱥ����JLabel�ؼ�
				ss_JL_sgid.setForeground(SystemColor.red);
				ss_JL_sgname.setForeground(SystemColor.BLACK);
				ss_JL_sp.setForeground(SystemColor.BLACK);

				// flag=1,ͨ��Ⱥ�Ž�����Ⱥ����
				flag = 1;
				setss_txt_s(flag);
			}
		});
		ss_JL_sgid.setFont(new Font("����", Font.PLAIN, 20));
		// ss_JL_sgnameΪ��ͨ��Ⱥ������Ⱥ����JLabel�ؼ�
		ss_JL_sgname = new JLabel("\u901A\u8FC7\u7FA4\u540D\u627E\u7FA4");
		ss_JL_sgname.setBounds(293, 31, 144, 29);
		getContentPane().add(ss_JL_sgname);
		// Ϊss_JL_sgname��ӵ����¼�
		ss_JL_sgname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// �������ͨ��Ⱥ������Ⱥ���ؼ�ʱ���ÿؼ���Ϊ��ɫ���壬������ɫ���
				ss_JL_sgname.setForeground(SystemColor.red);
				ss_JL_sp.setForeground(SystemColor.BLACK);
				ss_JL_sgid.setForeground(SystemColor.BLACK);

				// flag=2,ͨ��Ⱥ��������Ⱥ����
				flag = 2;
				setss_txt_s(flag);
			}

		});
		ss_JL_sgname.setFont(new Font("����", Font.PLAIN, 20));

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
					JOptionPane.showMessageDialog(getContentPane(), "�Ѿ����ҳ");
				}
			}
		});
		setss_txt_s(flag);
	}

	/**
	 * ͨ��ҳ�ų�ʼ�����
	 * 
	 * @param now
	 */
	public void reslultTable(int now) {
		for (int i = 0; i < STRIP; i++) {
			if (flag == 0) {
				headtable[0] = "�û��ǳ�";
				if (userlist.size() > (i + (now - 1) * STRIP)) {
					obj[i][0] = userlist.get(i + (now - 1) * STRIP).getName();
				} else {
					obj[i][0] = "";
				}
			} else {
				headtable[0] = "Ⱥ�ǳ�";
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
		pageNumber.setText("��" + now + "ҳ");
		textPage.setText("" + now);
	}

	/**
	 * �����Ҽ��˵�
	 */
	private void createPopupMenu() {
		m_popupMenu = new JPopupMenu();

		menuItem1 = new JMenuItem();
		menuItem1.setText(" �鿴����  ");
		menuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// �ò�����Ҫ������
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
		menuItem2.setText(" ��Ӻ���  ");
		menuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// �ò�����Ҫ������
				// ͨ���ж�flag��ֵ�����ֱ��û����ڲ���ʲô
				/**
				 * ���˲���
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
							DataUtil.showMessage("��ӳɹ�");
							userlist.remove(friendname);
							reslultTable(nowPage);
						} else {
							DataUtil.showMessage("�Ѿ��Ǻ��ѹ�ϵ");
						}
					} else {
						DataUtil.showMessage("��������Լ�Ϊ����");
					}
				}

			}
		});

		menuItem3 = new JMenuItem();
		menuItem3.setText(" �����Ⱥ  ");
		menuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// �ò�����Ҫ������
				// ͨ���ж�flag��ֵ�����ֱ��û����ڲ���ʲô
				GroupsDao groupsDao = new GroupsDao();
				int row = table.getSelectedRow();
				/**
				 * ͨ��Ⱥid����
				 */
				if (flag == 1) {
					if (grouplist.size() > ((nowPage - 1) * STRIP + row)) {
						String groupid = grouplist.get((nowPage - 1) * STRIP
								+ row);
						if(groupsDao.intoGroupByGroupid(groupid,ownuser.getName())){
							DataUtil.showMessage("��ӳɹ�");
						}else{
							DataUtil.showMessage("�Ѽ����Ⱥ");
						}
						
					}
				}
				
				/**
				 * ͨ��Ⱥ������
				 */
				if(flag == 2){
					if (grouplist.size() > ((nowPage - 1) * STRIP + row)) {
						String groupname = grouplist.get((nowPage - 1) * STRIP
								+ row);
						if(groupsDao.intoGroupByGroupname(groupname,ownuser.getName())){
							DataUtil.showMessage("��ӳɹ�");
						}else{
							DataUtil.showMessage("�Ѽ����Ⱥ");
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
