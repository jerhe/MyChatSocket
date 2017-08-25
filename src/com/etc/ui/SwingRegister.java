package com.etc.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.etc.dao.UsersDao;
import com.etc.util.DataUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

/**
 * 注册窗口
 * 
 * @author Administrator
 * 
 */
public class SwingRegister extends JFrame {

	private JPanel contentPane;
	private JTextField sr_txt_name;
	private JPasswordField sr_txt_pwd;
	private JPasswordField sr_txt_pwd1;
	private JTextField sr_txt_email;
	private JCheckBox sr_cb_agree;
	private boolean flag = false;
	private JLabel prompt1;
	private JLabel prompt2;
	private JLabel prompt3;
	private JLabel prompt4;
	private SwingLogin loginHome;


	/**
	 * 初始化界面
	 */
	public SwingRegister(SwingLogin slframe ) {
		loginHome=slframe;
		setVisible(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SwingRegister.class.getResource("/pic/chat.png")));
		setTitle("\u7528\u6237\u6CE8\u518C");
		/**
		 * 设置窗口的关闭事件
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				loginHome.setVisible(true);
				dispose();
			}
		});
		setBounds(100, 100, 466, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u6635\u79F0\uFF1A");
		label.setBounds(88, 56, 48, 15);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setBounds(88, 96, 48, 15);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		label_2.setBounds(62, 142, 74, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u90AE\u7BB1\uFF1A");
		label_3.setBounds(88, 188, 48, 15);
		contentPane.add(label_3);
		
		sr_txt_name = new JTextField();
		sr_txt_name.setBounds(146, 53, 136, 21);
		contentPane.add(sr_txt_name);
		sr_txt_name.setColumns(10);

		sr_txt_pwd = new JPasswordField();
		sr_txt_pwd.setBounds(146, 93, 136, 21);
		contentPane.add(sr_txt_pwd);


		sr_txt_pwd1 = new JPasswordField();
		sr_txt_pwd1.setBounds(146, 139, 136, 21);
		contentPane.add(sr_txt_pwd1);

		sr_cb_agree = new JCheckBox(
				"\u6211\u5DF2\u9605\u8BFB\u5E76\u540C\u610F\u6761\u6B3E\u548C\u9690\u79C1\u653F\u7B56");
		sr_cb_agree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (sr_cb_agree.isSelected()) {
					flag = true;
				} else {
					flag = false;
				}

			}
		});
		sr_cb_agree.setBounds(100, 311, 225, 23);
		contentPane.add(sr_cb_agree);

	

		sr_txt_email = new JTextField();
		sr_txt_email.setColumns(10);
		sr_txt_email.setBounds(146, 185, 136, 21);
		contentPane.add(sr_txt_email);

		JButton sr_btn_rg = new JButton("\u6CE8\u518C");
		sr_btn_rg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pwd1 = new String(sr_txt_pwd.getPassword());
				String pwd2 = new String(sr_txt_pwd1.getPassword());
				String mail = sr_txt_email.getText();
				//判断用户名不能为空
				if ("".equals(sr_txt_name.getText())) {
					DataUtil.showMessage("用户名不能为空");
					return;
				}
				//判断用户的昵称不能为用户名
				if("用户名".equals(sr_txt_name.getText())){
					DataUtil.showMessage("昵称不能为用户名");
					return;
				}
				//判断密码是否为空
				if ("".equals(pwd1)) {
					prompt1.setVisible(true);
					prompt2.setVisible(false);
					sr_txt_pwd1.setText("");
					return;
				}
				//判断邮箱是否为空和邮箱的格式是否合法
				if ("".equals(mail)) {
					prompt3.setVisible(true);
				} else {
					prompt3.setVisible(false);
					String format = "[a-z 0-9]{6,}[@][a-z 0-9]{2,}[.]\\p{Lower}{2,}";
					if (mail.matches(format)) {
						prompt4.setVisible(false);
						// return true;// 邮箱名合法，返回true			
					} else {
						prompt4.setVisible(true);
						return;
						// return false;// 邮箱名不合法，返回false
					}
				}
				if (pwd1.equals(pwd2)) {
					// flag判断是否有同意协议
					if (flag) {
						UsersDao userDao = new UsersDao();
						if (!userDao.isExistence(sr_txt_name.getText().trim()) ) {
							userDao.addUsers(sr_txt_name.getText().trim(), new String(sr_txt_pwd.getPassword()), sr_txt_email.getText().trim());
							DataUtil.showMessage("恭喜"+sr_txt_name.getText().trim()+",注册成功");
							loginHome.setLoginText(sr_txt_name.getText().trim(), new String(sr_txt_pwd.getPassword()));
							dispose();
						} else {
							DataUtil.showMessage("用户名已存在");
						}
					} else {
						prompt2.setVisible(false);
						DataUtil.showMessage("未同意条款");
					}
				} else {
					prompt1.setVisible(false);
					prompt2.setVisible(true);
				}

			}
		});
		sr_btn_rg.setBounds(100, 252, 225, 33);
		contentPane.add(sr_btn_rg);

		prompt1 = new JLabel("\u8BF7\u8F93\u5165\u5BC6\u7801");
		prompt1.setForeground(Color.RED);
		prompt1.setFont(new Font("宋体", Font.PLAIN, 11));
		prompt1.setBounds(292, 97, 66, 15);
		prompt1.setVisible(false);
		contentPane.add(prompt1);

		prompt2 = new JLabel("\u4E24\u6B21\u8F93\u5165\u7684\u4E0D\u4E00\u81F4");
		prompt2.setForeground(Color.RED);
		prompt2.setFont(new Font("宋体", Font.PLAIN, 11));
		prompt2.setBounds(292, 142, 88, 15);
		prompt2.setVisible(false);
		contentPane.add(prompt2);

		prompt3 = new JLabel("\u90AE\u7BB1\u4E0D\u80FD\u4E3A\u7A7A");
		prompt3.setBounds(292, 188, 88, 15);
		prompt3.setForeground(Color.RED);
		prompt3.setFont(new Font("宋体", Font.PLAIN, 11));
		prompt3.setVisible(false);
		contentPane.add(prompt3);

		prompt4 = new JLabel("\u90AE\u7BB1\u4E0D\u5408\u6CD5");
		prompt4.setBounds(292, 188, 88, 15);
		prompt4.setForeground(Color.RED);
		prompt4.setFont(new Font("宋体", Font.PLAIN, 11));
		prompt4.setVisible(false);
		contentPane.add(prompt4);
	}
}
