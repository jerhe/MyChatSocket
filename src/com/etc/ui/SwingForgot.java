package com.etc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.etc.base.BaseJFrame;
import com.etc.dao.UsersDao;
import com.etc.entity.Users;
import com.etc.util.DataUtil;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class SwingForgot extends BaseJFrame {

	private JPanel contentPane;
	private JTextField sf_txt_name;
	private JTextField sf_txt_mail;
	private SwingLogin loginFrame;
	private SwingForgot fogotFrame;
	private 	JButton sf_btn_confirm;
	private JLabel lblNewLabel;
	private JLabel label;

	/**
	 * Create the frame.
	 */
	public SwingForgot(SwingLogin ln) {
		//lostFocus(null, this);
		loginFrame = ln;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				SwingForgot.class.getResource("/pic/chat.png")));
		setTitle("\u91CD\u7F6E\u5BC6\u7801");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		sf_txt_name = new JTextField();
		sf_txt_name.requestFocus();
		sf_txt_name.addFocusListener(new MyFocusListener("用户名", sf_txt_name));
		sf_txt_name.setBounds(140, 88, 150, 30);
		contentPane.add(sf_txt_name);
		sf_txt_name.setColumns(10);

		sf_txt_mail = new JTextField();
		sf_txt_mail.setText("\u90AE\u7BB1");
		sf_txt_mail.setForeground(Color.LIGHT_GRAY);
		sf_txt_mail.setFont(new Font("宋体", Font.PLAIN, 14));
		sf_txt_mail.addFocusListener(new MyFocusListener("邮箱",
				sf_txt_mail));
		sf_txt_mail.setColumns(10);
		sf_txt_mail.setBounds(140, 153, 150, 30);
		contentPane.add(sf_txt_mail);

		sf_btn_confirm = new JButton("\u786E\u8BA4");
		sf_btn_confirm.setBounds(140, 227, 150, 35);
		sf_btn_confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UsersDao usersDao = new UsersDao();
				Users u = usersDao.getUsersByName(sf_txt_name.getText().trim());
				if (u != null) {
					if (u.getEmail().equals(sf_txt_mail.getText().trim())) {
						JDialogPwdReset hdpr = new JDialogPwdReset(loginFrame,
								fogotFrame, u.getName());
						hdpr.setModal(true);
						hdpr.setVisible(true);
					} else {
						DataUtil.showMessage("邮箱输入错误");
					}
				} else {
					DataUtil.showMessage("用户名不存在");
				}

			}
		});
		contentPane.add(sf_btn_confirm);
		
		lblNewLabel = new JLabel("\u7528\u6237\u540D");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setBounds(42, 89, 87, 27);
		contentPane.add(lblNewLabel);
		
		label = new JLabel("\u90AE\u7BB1");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		label.setBounds(42, 155, 87, 27);
		contentPane.add(label);
		
	}

	public void setFrame(SwingForgot f) {
		this.fogotFrame = f;
	}
}
