package com.etc.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.bsb.test.main.Mainwindowtest;
import com.bsb.tool.ChatManager;
import com.etc.base.BaseJFrame;
import com.etc.dao.UsersDao;
import com.etc.entity.Users;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

/**
 * 登录界面
 * @author zhuzhen
 *
 */
public class SwingLogin extends BaseJFrame {
	private UsersDao usersDao = new UsersDao();
	private JPanel contentPane;
	private JTextField sl_txt_name;
	private JPasswordField sl_txt_pwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingLogin frame = new SwingLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 初始化界面
	 */
	public SwingLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setResizableTo(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 499, 388);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel sl_img_head = new JLabel("");
		sl_img_head.setIcon(new ImageIcon(
				"C:\\Users\\zhuzhen\\Pictures\\a3.png"));
		sl_img_head.setBounds(59, 200, 80, 73);
		contentPane.add(sl_img_head);

		JLabel lblNewLabel_1 = new JLabel("\u804A\u5457");
		lblNewLabel_1.setFont(new Font("方正舒体", Font.PLAIN, 69));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(null);
		lblNewLabel_1.setBounds(0, 0, 483, 176);
		contentPane.add(lblNewLabel_1);
		
		sl_txt_name = new JTextField();
		sl_txt_name.setForeground(new Color(0, 0, 0));
		sl_txt_name.addFocusListener(new MyFocusListener("用户名", sl_txt_name));
		sl_txt_name.setBounds(171, 200, 170, 30);
		contentPane.add(sl_txt_name);
		sl_txt_name.setColumns(10);

		JLabel lblNewLabel = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		lblNewLabel.setForeground(new Color(65, 105, 225));
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setBounds(355, 215, 80, 20);
		contentPane.add(lblNewLabel);

		JLabel label = new JLabel("\u5FD8\u8BB0\u5BC6\u7801");
		label.setForeground(new Color(65, 105, 225));
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setBounds(355, 244, 80, 20);
		contentPane.add(label);

		JCheckBox chckbxNewCheckBox = new JCheckBox("\u8BB0\u4F4F\u5BC6\u7801");
		chckbxNewCheckBox.setBounds(171, 267, 80, 23);
		contentPane.add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox(
				"\u81EA\u52A8\u767B\u5F55");
		chckbxNewCheckBox_1.setBounds(261, 267, 80, 23);
		contentPane.add(chckbxNewCheckBox_1);
		
		/**
		 * 登录按钮
		 */
		JButton sl_btn_lg = new JButton("\u767B\u5F55");
		sl_btn_lg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 登录事件
				String str1 = sl_txt_name.getText();
				String str2 = new String(sl_txt_pwd.getPassword());
				Users users = usersDao.getUsersByName(str1);
				if (users != null) {
					if (users.getPwd().equals(str2)) {
						System.out.println(users);
						setVisible(false);// 窗体不可见
						SwingHome frame= new SwingHome(users);
						frame.setVisible(true);
						
						//ChatManager.getCM().setWindow(frame);
						
						
					} else {
						JOptionPane.showMessageDialog(null, "密码错误");
					}
				} else {
					JOptionPane.showMessageDialog(null, "用户名不存在");
				}

			}
		});
		sl_btn_lg.setFont(new Font("宋体", Font.PLAIN, 12));
		sl_btn_lg.setForeground(new Color(255, 255, 255));
		sl_btn_lg.setIcon(null);
		sl_btn_lg.setBackground(new Color(30, 144, 255));
		sl_btn_lg.setBounds(171, 296, 170, 30);
		contentPane.add(sl_btn_lg);

		sl_txt_pwd = new JPasswordField();
		sl_txt_pwd.setBounds(171, 231, 170, 30);
		contentPane.add(sl_txt_pwd);
	}
}
