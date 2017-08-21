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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.AbstractListModel;
import javax.swing.JScrollBar;

public class SwingGroupChat {

	private JFrame frame;
	private final JPanel panel_north = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingGroupChat window = new SwingGroupChat();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingGroupChat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 665, 519);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
		frame.getContentPane().setLayout(null);
		panel_north.setBounds(0, 0, 488, 86);
		frame.getContentPane().add(panel_north);
		panel_north.setLayout(null);
		
		JLabel sgc_img_head = new JLabel("\u5934\u50CF");
		sgc_img_head.setBackground(Color.BLACK);
		sgc_img_head.setFont(new Font("Ò¶¸ùÓÑÃ«±ÊÐÐÊé2.0°æ", Font.PLAIN, 34));
		sgc_img_head.setBounds(10, 10, 69, 66);
		panel_north.add(sgc_img_head);
		
		JLabel sgc_jl_name = new JLabel("name");
		sgc_jl_name.setFont(new Font("Ò¶¸ùÓÑÃ«±ÊÐÐÊé2.0°æ", Font.PLAIN, 20));
		sgc_jl_name.setBounds(97, 10, 85, 29);
		panel_north.add(sgc_jl_name);
		
		JLabel sgc_jl_remark = new JLabel("remark");
		sgc_jl_remark.setFont(new Font("Ò¶¸ùÓÑÃ«±ÊÐÐÊé2.0°æ", Font.PLAIN, 15));
		sgc_jl_remark.setBounds(97, 47, 174, 29);
		panel_north.add(sgc_jl_remark);
		
		JPanel panel_east = new JPanel();
		panel_east.setBounds(487, 0, 162, 481);
		frame.getContentPane().add(panel_east);
		panel_east.setLayout(null);
		
		JLabel sgc_jl_group = new JLabel("\u7FA4\u6210\u5458\uFF1A");
		sgc_jl_group.setFont(new Font("Ò¶¸ùÓÑÃ«±ÊÐÐÊé2.0°æ", Font.PLAIN, 20));
		sgc_jl_group.setBounds(0, 0, 162, 36);
		panel_east.add(sgc_jl_group);
		
		textField = new JTextField();
		textField.setText("\u641C\u7D22\u7FA4\u6210\u5458");
		textField.setBounds(0, 36, 162, 30);
		panel_east.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 69, 162, 412);
		panel_east.add(scrollPane_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		scrollPane_1.setViewportView(panel_1);
		
		JPanel panel_floor = new JPanel();
		panel_floor.setBounds(0, 338, 488, 143);
		frame.getContentPane().add(panel_floor);
		panel_floor.setLayout(null);
		
		JButton ssc_btn_send = new JButton("\u53D1\u9001\uFF08S\uFF09");
		ssc_btn_send.setBounds(369, 110, 105, 23);
		panel_floor.add(ssc_btn_send);
		
		JButton ssc_btn_close = new JButton("\u5173\u95ED\uFF08C\uFF09");
		ssc_btn_close.setBounds(257, 110, 102, 23);
		panel_floor.add(ssc_btn_close);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 486, 104);
		panel_floor.add(scrollPane);
		
		JTextArea ssc_txt_send = new JTextArea();
		scrollPane.setViewportView(ssc_txt_send);
		ssc_txt_send.setFont(new Font("Monospaced", Font.PLAIN, 45));
		
		JButton ssc_btn_record = new JButton("\u6D88\u606F\u8BB0\u5F55");
		ssc_btn_record.setBounds(145, 110, 102, 23);
		panel_floor.add(ssc_btn_record);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 86, 488, 3);
		frame.getContentPane().add(panel);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 89, 488, 245);
		frame.getContentPane().add(scrollPane_2);
		
		JTextArea sgv_txt_content = new JTextArea();
		scrollPane_2.setViewportView(sgv_txt_content);
//		frame.setUndecorated(true);
	}
}
