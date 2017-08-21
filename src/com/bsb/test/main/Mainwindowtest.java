package com.bsb.test.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.bsb.tool.ChatManager;
import com.etc.base.BaseJFrame;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mainwindowtest extends BaseJFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;

	

	/**
	 * Create the frame.
	 */
	public Mainwindowtest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBounds(0, 0, 579, 292);
		contentPane.add(textArea);

		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
			}

			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		textField.setBounds(41, 330, 297, 33);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton send = new JButton("\u53D1\u9001");
		/**
		 * 发送按钮的点击事件
		 */
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
				String hehe = dateFormat.format(now);

				ChatManager.getCM().send(
						ownuser.getName() + "(" + hehe + ")" + "!"
								+ textField.getText());
				//System.out.println(textArea.getText());
				appendText(ownuser.getName() + "(" + hehe + ")" + "说：" + "\n"
						+ textField.getText());
				textField.setText("");

			}
		});
		send.setBounds(367, 330, 110, 33);
		contentPane.add(send);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent evt) {
				focusEvt(evt);
			}
		});

	}

	// 定义窗体打开时的默认获得焦点的组件
	private void focusEvt(java.awt.event.WindowEvent evt) {
		textField.requestFocus();
	}
	
	public void appendText(String in) {

		textArea.append("\n" + in);
	}
	
	public void appendTextother(String in) {
		
		textArea.append("\n" + in);

	}
}
