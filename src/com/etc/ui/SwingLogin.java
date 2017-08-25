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
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.etc.base.BaseJFrame;
import com.etc.dao.UsersDao;
import com.etc.entity.Users;
import com.etc.test.Mainwindowtest;
import com.etc.tool.ChatManager;
import com.etc.util.DataUtil;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPasswordField;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * 登录界面
 * 
 * @author zhuzhen
 * 
 */
public class SwingLogin extends BaseJFrame{
	private UsersDao usersDao = new UsersDao();
	private JPanel contentPane;
	private JTextField sl_txt_name;
	private JPasswordField sl_txt_pwd;
	private SwingLogin myframe;
	private JLabel sl_jl_rg;
	private JCheckBox sl_cb_recode;
	private JLabel sl_jl_foget;
	private JButton sl_btn_lg;
	private boolean ischecked = false;
	
	/**
	 * Launch the application.
	 */
	public static synchronized void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e) {
			// TODO exception
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingLogin frame = new SwingLogin();
					frame.MyFrame(frame);
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
		setTitle("\u767B\u5F55");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				SwingLogin.class.getResource("/pic/chat.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setResizableTo(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 480, 404);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel sl_img_head = new JLabel("");
		sl_img_head.setIcon(new ImageIcon("img\\bb.jpg"));
		sl_img_head.setBounds(36, 158, 80, 73);
		contentPane.add(sl_img_head);

		JLabel lblNewLabel_1 = new JLabel("\u804A\u5457");
		lblNewLabel_1.setEnabled(false);
		lblNewLabel_1.setFont(new Font("叶根友毛笔行书2.0版", Font.PLAIN, 69));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(null);
		lblNewLabel_1.setBounds(0, 0, 402, 148);
		contentPane.add(lblNewLabel_1);

		sl_txt_name = new JTextField();
		sl_txt_name.setForeground(new Color(0, 0, 0));
		sl_txt_name.addFocusListener(new MyFocusListener("用户名", sl_txt_name));
		sl_txt_name.setBounds(126, 158, 170, 30);
		contentPane.add(sl_txt_name);
		sl_txt_name.setColumns(10);

		/**
		 * 添加监听键盘监听
		 */
		sl_txt_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});

		// 注册帐号
		sl_jl_rg = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		sl_jl_rg.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 鼠标点击事件
				SwingRegister srframe = new SwingRegister(myframe);
				srframe.setVisible(true);
				// setVisible(false);
			}
		});
		sl_jl_rg.setForeground(new Color(65, 105, 225));
		sl_jl_rg.setFont(new Font("宋体", Font.PLAIN, 14));

		sl_jl_rg.setBounds(306, 162, 80, 20);
		contentPane.add(sl_jl_rg);

		sl_jl_foget = new JLabel("\u5FD8\u8BB0\u5BC6\u7801");
		sl_jl_foget.setForeground(new Color(65, 105, 225));
		sl_jl_foget.setFont(new Font("宋体", Font.PLAIN, 14));
		sl_jl_foget.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingForgot forgotFrame = new SwingForgot(myframe);
				forgotFrame.setFrame(forgotFrame);
				forgotFrame.setVisible(true);

			}
		});
		sl_jl_foget.setBounds(306, 202, 80, 20);
		contentPane.add(sl_jl_foget);

		sl_cb_recode = new JCheckBox("\u8BB0\u4F4F\u5BC6\u7801");
		sl_cb_recode.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (sl_cb_recode.isSelected()) {
					ischecked = true;
				}else{
					ischecked = false;
				}
			}
		});
		sl_cb_recode.setBounds(126, 234, 170, 23);
		contentPane.add(sl_cb_recode);

		/**
		 * 登录按钮
		 */
		sl_btn_lg = new JButton("\u767B\u5F55");
		sl_btn_lg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 登录
				login();
			}
		});
		sl_btn_lg.setFont(new Font("宋体", Font.PLAIN, 12));
		sl_btn_lg.setForeground(Color.BLACK);
		sl_btn_lg.setIcon(null);
		sl_btn_lg.setBackground(new Color(30, 144, 255));
		sl_btn_lg.setBounds(126, 263, 170, 30);
		contentPane.add(sl_btn_lg);

		sl_txt_pwd = new JPasswordField();
		sl_txt_pwd.setBounds(126, 198, 170, 30);
		contentPane.add(sl_txt_pwd);
		/**
		 * 添加监听键盘监听
		 */
		sl_txt_pwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		initData();
	}

	/**
	 * 登录
	 */
	private void login() {
		// 登录事件
		String str1 = sl_txt_name.getText();
		String str2 = new String(sl_txt_pwd.getPassword());
		Users users = usersDao.getUsersByName(str1);
		if (users != null) {
			if (users.getPwd().equals(str2)) {
				if (users.getIsonline() == 0) {
					System.out.println(users);
					setVisible(false);// 窗体不可见
					usersDao.setOnlineById(users.getId());
					SwingHome frame = new SwingHome(users);
					frame.setLoginFrame(myframe);
					frame.setHomeFrame(frame);
					frame.setVisible(true);
					// 把密码保存到本地
					if (ischecked) {
						DataUtil.toFile(users);
					}
				} else {
					DataUtil.showMessage("不能重复登录");
				}

			} else {
				DataUtil.showMessage("密码错误");
			}
		} else {
			DataUtil.showMessage("用户名不存在");
		}
	}

	private void initData() {
		ownuser = DataUtil.fromFile();
		if (ownuser != null) {
			sl_cb_recode.setSelected(true);
			sl_txt_name.setText(ownuser.getName());
			sl_txt_pwd.setText(ownuser.getPwd());
		}

	}

	public void MyFrame(SwingLogin f) {
		this.myframe = f;
	}
	
	public void setLoginText(String setname ,String setpwd){
		sl_txt_name.setText(setname);
		sl_txt_pwd.setText(setpwd);
	}
	public void clear() {
		sl_txt_name.setText("");
		sl_txt_pwd.setText("");
	}
}
