package com.etc.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Toolkit;

import javax.swing.JTable;

import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;

import com.etc.base.BaseJFrame;
import com.etc.dao.FriendsDao;
import com.etc.dao.UsersDao;
import com.etc.entity.Users;
import com.etc.util.DataUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class SwingDelFriend extends BaseJFrame {

	private JPanel contentPane;
	private JTextField sdf_txt_name;
	private JTable table;
	private SwingHome homeframe;
	private JScrollPane scrollPane;
	private JButton sdf_btn_sch;
	private ArrayList<String> friendsName = new ArrayList<>();
	private JButton sdf_btn_del;
	private String[][] obj;
	private String[] headtable = new String[] { "昵称" };

	public void setHomeframe(SwingHome homeframe) {
		this.homeframe = homeframe;
	}

	/**
	 * 创建窗口
	 */
	public SwingDelFriend(Users u) {
		ownuser = u;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				SwingDelFriend.class.getResource("/pic/chat.png")));
		setTitle("\u5220\u9664\u597D\u53CB");
		// 设置关闭窗口监听
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setBounds(100, 100, 423, 463);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		sdf_txt_name = new JTextField();
		sdf_txt_name.addFocusListener(new MyFocusListener("用户名", sdf_txt_name));
		sdf_txt_name.setBounds(56, 64, 160, 21);
		contentPane.add(sdf_txt_name);
		sdf_txt_name.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 107, 278, 227);
		contentPane.add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int ColIndex) {
				return false;
			}
		};
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(29);
		scrollPane.setViewportView(table);

		sdf_btn_sch = new JButton("查询");
		sdf_btn_sch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 设置点击事件模糊查询
				
				initTable();
			}
		});
		sdf_btn_sch.setBounds(241, 63, 93, 23);
		contentPane.add(sdf_btn_sch);

		sdf_btn_del = new JButton("删除");
		sdf_btn_del.setBounds(56, 353, 278, 23);
		sdf_btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FriendsDao friendsDao = new FriendsDao();
				int row = table.getSelectedRow();
				if (row == -1) {
					DataUtil.showMessage("请先选中再操作");
				} else {
					int result = JOptionPane.showConfirmDialog(null, "是否要删除",
							"提示", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						friendsDao.deleteFriendRelation(friendsName.get(row),
								ownuser.getName());
						initTable();
					}
					
				}

			}
		});
		contentPane.add(sdf_btn_del);

	}

	private void initTable() {
		table.removeAll();
		String name = sdf_txt_name.getText().trim();
		if(name.equals("用户名")){
			name="";
		}
		FriendsDao friendsDao = new FriendsDao();
		friendsName = friendsDao.getMyFriendsName(name,ownuser.getName());
		obj = new String[friendsName.size()][1];
		for (int i = 0; i < friendsName.size(); i++) {
			
			obj[i][0] = friendsName.get(i);
		}
		table.setModel(new DefaultTableModel(obj, headtable));
	}

}
