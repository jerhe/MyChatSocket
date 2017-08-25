package com.etc.ui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import com.etc.dao.FriendsDao;
import com.etc.dao.UsersDao;
import com.etc.entity.Friends;
import com.etc.entity.Users;
import com.etc.util.DataUtil;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class JDialogUserDetail extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel sdsd_img_head;  //头像标签
	private JLabel sdsd_jl_name2;    //获取昵称标签
	private JLabel sdsd_jl_remark2;  //获取个人简介标签
	private JLabel sdsd_jl_sex2;     //获取性别标签
	private JLabel sdsd_jl_birthday2; //获取生日标签
	private JLabel sdsd_jl_isonline2; //获取是否在线状态
	private JButton sdsd_jb_back;    //返回先前界面按钮
	private JButton sdsd_jb_add;    //添加好友按钮
	private String username;
	private String myname;
	private FriendsDao friendsDao = new FriendsDao();
	
	
	

	/**
	 * 创建jdialog界面
	 */
	public JDialogUserDetail(String un,String mn) {
		username=un;
		myname=mn;
		setBounds(100, 100, 428, 599);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 106, 421, 3);
		contentPanel.add(panel);
		{
			sdsd_img_head = new JLabel("");
			sdsd_img_head.setBackground(Color.WHITE);
			sdsd_img_head.setBounds(155, 10, 91, 87);
			contentPanel.add(sdsd_img_head);
		}
		{
			JLabel sdsd_jl_name = new JLabel("\u6635    \u79F0\uFF1A");
			sdsd_jl_name.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_name.setBounds(34, 139, 85, 29);
			contentPanel.add(sdsd_jl_name);
		}
		//sdsd_jl_name2为获取昵称标签
		sdsd_jl_name2 = new JLabel("");
		sdsd_jl_name2.setFont(new Font("宋体", Font.PLAIN, 15));
		sdsd_jl_name2.setBounds(139, 139, 263, 29);
		contentPanel.add(sdsd_jl_name2);
		{
			JLabel sdsd_jl_remark = new JLabel("\u4E2A\u4EBA\u7B80\u4ECB\uFF1A");
			sdsd_jl_remark.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_remark.setBounds(34, 199, 85, 29);
			contentPanel.add(sdsd_jl_remark);
		}
		{
			//sdsd_jl_remark2为获取个人简介标签
			sdsd_jl_remark2 = new JLabel("");
			sdsd_jl_remark2.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_remark2.setBounds(139, 199, 263, 29);
			contentPanel.add(sdsd_jl_remark2);
		}
		{
			JLabel sdsd_jl_sex = new JLabel("\u6027    \u522B\uFF1A");
			sdsd_jl_sex.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_sex.setBounds(34, 260, 85, 29);
			contentPanel.add(sdsd_jl_sex);
		}
		{
			//获取性别标签
			sdsd_jl_sex2 = new JLabel("");
			sdsd_jl_sex2.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_sex2.setBounds(139, 260, 263, 29);
			contentPanel.add(sdsd_jl_sex2);
		}
		{
			JLabel sdsd_jl_birthday = new JLabel("\u751F    \u65E5\uFF1A");
			sdsd_jl_birthday.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_birthday.setBounds(34, 320, 85, 29);
			contentPanel.add(sdsd_jl_birthday);
		}
		{
			//获取生日标签
			sdsd_jl_birthday2 = new JLabel("");
			sdsd_jl_birthday2.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_birthday2.setBounds(139, 320, 263, 29);
			contentPanel.add(sdsd_jl_birthday2);
		}
		{
			JLabel sdsd_jl_isonline = new JLabel("\u5728\u7EBF\u72B6\u6001\uFF1A");
			sdsd_jl_isonline.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_isonline.setBounds(34, 379, 85, 29);
			contentPanel.add(sdsd_jl_isonline);
		}
		{
			//获取是否在线状态
			sdsd_jl_isonline2 = new JLabel("");
			sdsd_jl_isonline2.setFont(new Font("宋体", Font.PLAIN, 15));
			sdsd_jl_isonline2.setBounds(139, 379, 263, 29);
			contentPanel.add(sdsd_jl_isonline2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 452, 412, 61);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				//返回先前界面按钮
				sdsd_jb_back = new JButton("\u8FD4\u56DE");
				sdsd_jb_back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				sdsd_jb_back.setBounds(226, 7, 98, 32);
				sdsd_jb_back.setActionCommand("Cancel");
				buttonPane.add(sdsd_jb_back);
			}
			
			//添加好友按钮
			sdsd_jb_add = new JButton("\u6DFB\u52A0\u597D\u53CB");
			sdsd_jb_add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//点击事件
					if(!username.equals(myname)){
						if (!friendsDao.isFriends(username, myname)) {
							Friends f1 = new Friends(myname, username);
							Friends f2 = new Friends(username, myname);
							friendsDao.AddFriends(f1);
							friendsDao.AddFriends(f2);
							DataUtil.showMessage("添加成功");
						} else {
							DataUtil.showMessage("已经是好友关系");
						}
					}else{
						DataUtil.showMessage("不能添加自己为好友");
					}
				}
			});
			sdsd_jb_add.setActionCommand("Cancel");
			sdsd_jb_add.setBounds(68, 7, 98, 32);
			buttonPane.add(sdsd_jb_add);
		}
		
		initData();
	}
	private void initData(){
		UsersDao usersDao = new UsersDao();
		Users nowUsers=usersDao.getUsersByName(username);
		sdsd_img_head.setIcon(new ImageIcon(nowUsers.getHeadimg()));
		sdsd_jl_name2.setText(nowUsers.getName());
		if(nowUsers.getRemark()==null){
			sdsd_jl_remark2.setText("");
		}else{
			sdsd_jl_remark2.setText(nowUsers.getRemark());
		}
		if(nowUsers.getSex()==null){
			sdsd_jl_sex2.setText("");
		}else{
			sdsd_jl_sex2.setText(nowUsers.getSex());
		}
		sdsd_jl_birthday2.setText(nowUsers.getBirthday());
		if(nowUsers.getIsonline()==1){
			sdsd_jl_isonline2.setText("在线");
		}else{
			sdsd_jl_isonline2.setText("离线");
		}
		
	}
}
