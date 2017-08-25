package com.etc.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.etc.dao.UsersDao;
import com.etc.util.DataUtil;
import java.awt.Color;

public class JDialogPwdReset extends JDialog {
	private JPasswordField jpr_txt_pwd1;
	private JPasswordField jpr_txt_pwd2;
	private 	JButton jpr_btn_cancel;
	private 	JButton jpr_btn_finish;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private String username;
	private SwingLogin lgframe;
	private SwingForgot sframe;
	private JPanel panel;

	public JDialogPwdReset(SwingLogin sl,SwingForgot sf,String name ) {
		sframe=sf;
		lgframe=sl;
		username=name;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogPwdReset.class.getResource("/pic/chat.png")));
		setTitle("\u8BF7\u8F93\u5165\u5BC6\u7801");
		setBounds(100, 100, 458, 324);
		getContentPane().setLayout(null);
		/**
		 * 窗口关闭事件
		 */
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				lgframe.setVisible(true);
				sframe.dispose();
				dispose();
			}
		});
		{
			jpr_txt_pwd1 = new JPasswordField();
			jpr_txt_pwd1.setBounds(110, 44, 170, 33);
			getContentPane().add(jpr_txt_pwd1);
			jpr_txt_pwd1.setColumns(10);
		}
		{
			jpr_txt_pwd2 = new JPasswordField();
			jpr_txt_pwd2.setColumns(10);
			jpr_txt_pwd2.setBounds(110, 106, 170, 33);
			getContentPane().add(jpr_txt_pwd2);
		}
		{
			jpr_btn_finish = new JButton("\u786E\u8BA4");
			jpr_btn_finish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//确认
					if(new String(jpr_txt_pwd2.getPassword()).equals(new String(jpr_txt_pwd1.getPassword()))){
						UsersDao usersDao = new UsersDao();
						usersDao.resetPwdByName(username, new String(jpr_txt_pwd2.getPassword()));
						DataUtil.showMessage("修改成功");
						lgframe.setLoginText(username,new String(jpr_txt_pwd2.getPassword()) );
						lgframe.setVisible(true);
						sframe.dispose();
						dispose();

					}else{
						DataUtil.showMessage("两次输入的密码不一致");
					}
				}
			});
			jpr_btn_finish.setBounds(110, 182, 82, 33);
			getContentPane().add(jpr_btn_finish);
		}
		{
			jpr_btn_cancel = new JButton("\u53D6\u6D88");
			jpr_btn_cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//取消按钮
					lgframe.setVisible(true);
					sframe.dispose();
					dispose();
				}
			});
			jpr_btn_cancel.setBounds(198, 182, 82, 33);
			getContentPane().add(jpr_btn_cancel);
		}
		{
			lblNewLabel = new JLabel("\u5BC6\u7801");
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel.setBounds(10, 44, 76, 33);
			getContentPane().add(lblNewLabel);
		}
		{
			lblNewLabel_1 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_1.setBounds(10, 106, 76, 33);
			getContentPane().add(lblNewLabel_1);
		}
		{
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(0, 0, 452, 296);
			getContentPane().add(panel);
		}
	}

}
