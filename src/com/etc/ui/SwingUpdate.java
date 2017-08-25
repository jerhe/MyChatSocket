package com.etc.ui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JButton;

import com.etc.base.BaseJFrame;
import com.etc.dao.UsersDao;
import com.etc.entity.Users;
import com.etc.util.DataUtil;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * �޸ĸ������Ͻ���
 * */
public class SwingUpdate extends BaseJFrame {

	private JFrame frame;
	private JTextField su_txt_name;// �����޸ĺ���û���
	private JTextField su_txt_birth;// �����޸ĺ������
	private JTextField su_txt_remark;// �����޸ĺ�ĸ���ǩ��
	private JLabel su_jl_doc;// ��ʾ������
	private JLabel su_jl_name;// ��ʾ���û���
	private JLabel su_jl_sex;// ��ʾ���Ա�
	private JLabel su_jl_birth;// ��ʾ������
	private JLabel su_jl_remark;// ��ʾ������ǩ��
	private JButton su_btn_cancel;// ȡ����ť��ȡ���޸Ĳ��� �ı���ָ�ԭ�������ݣ�
	private JButton su_btn_save;// ���水ť�������޸ĺ��ı�������ݣ�
	private JRadioButton su_rb_girl;
	private JRadioButton su_rb_boy;
	private JLabel su_hint_date;
	private JLabel su_hint_name;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private UsersDao usersDao = new UsersDao();
	private SwingHome homeFrame;
	private JPanel panel_2;

	/**
	 * �޸����Ͻ���
	 */
	public SwingUpdate(SwingHome sh, Users u) {
		homeFrame = sh;
		ownuser = u;
		initialize();
	}

	/**
	 * ��ʼ������
	 */
	private void initialize() {

		setLocationRelativeTo(this);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setTitle("\u7F16\u8F91");
		setBounds(100, 100, 455, 622);
		/**
		 * ���ô��ڵĹر��¼�
		 */
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// �رմ���
				dispose();
			}
		});

		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(50, 205, 50));
		panel.setBounds(0, 94, 131, 4);
		getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 0, 0));
		panel_1.setBounds(131, 94, 308, 4);
		getContentPane().add(panel_1);

		su_jl_doc = new JLabel("\u8D44\u6599");
		su_jl_doc.setHorizontalAlignment(SwingConstants.CENTER);
		su_jl_doc.setFont(new Font("����", Font.PLAIN, 17));
		su_jl_doc.setBounds(168, 29, 80, 39);
		getContentPane().add(su_jl_doc);

		su_jl_name = new JLabel("\u7528\u6237\u540D\uFF1A");
		su_jl_name.setBounds(42, 150, 62, 31);
		getContentPane().add(su_jl_name);

		su_jl_sex = new JLabel("\u6027\u522B\uFF1A");
		su_jl_sex.setBounds(42, 221, 62, 31);
		getContentPane().add(su_jl_sex);

		su_jl_birth = new JLabel("\u751F\u65E5\uFF1A");
		su_jl_birth.setBounds(42, 282, 62, 31);
		getContentPane().add(su_jl_birth);

		su_jl_remark = new JLabel("\u4E2A\u6027\u7B7E\u540D\uFF1A");
		su_jl_remark.setBounds(42, 347, 79, 31);
		getContentPane().add(su_jl_remark);

		su_txt_name = new JTextField();
		su_txt_name.setEditable(false);
		su_txt_name.setBounds(131, 155, 157, 21);
		getContentPane().add(su_txt_name);
		su_txt_name.setColumns(10);

		su_txt_birth = new JTextField();
		su_txt_birth.setColumns(10);
		su_txt_birth.setBounds(131, 287, 157, 21);
		getContentPane().add(su_txt_birth);

		su_txt_remark = new JTextField();
		su_txt_remark.setColumns(10);
		su_txt_remark.setBounds(131, 352, 157, 21);
		getContentPane().add(su_txt_remark);

		/**
		 * ����
		 */
		su_txt_name.setText(ownuser.getName());
		su_txt_birth.setText(ownuser.getBirthday());
		su_txt_remark.setText(ownuser.getRemark());

		/**
		 * br��
		 */
		su_rb_boy = new JRadioButton("\u7537");
		su_rb_boy.setBackground(Color.WHITE);
		buttonGroup.add(su_rb_boy);
		su_rb_boy.setBounds(131, 225, 62, 23);
		getContentPane().add(su_rb_boy);

		/**
		 * brŮ
		 */
		su_rb_girl = new JRadioButton("\u5973");
		su_rb_girl.setBackground(Color.WHITE);
		buttonGroup.add(su_rb_girl);
		su_rb_girl.setBounds(226, 225, 62, 23);
		getContentPane().add(su_rb_girl);

		/**
		 * ��ʼ����ѡ��
		 */
		if (ownuser.getSex() != null) {
			switch (ownuser.getSex()) {
			case "��":
				su_rb_boy.setSelected(true);
				break;
			case "Ů":
				su_rb_girl.setSelected(true);
				break;
			}
		} else {
			su_rb_boy.setSelected(true);
		}

		/**
		 * ������ʾ��
		 */
		su_hint_name = new JLabel("");
		su_hint_name.setForeground(Color.RED);
		su_hint_name.setBounds(298, 154, 111, 23);
		su_hint_name.setVisible(false);
		getContentPane().add(su_hint_name);

		/**
		 * ʱ����ʾ
		 */
		su_hint_date = new JLabel("\u4F8B\uFF1A2017-02-03");
		su_hint_date.setForeground(Color.GRAY);
		su_hint_date.setBounds(298, 284, 111, 27);
		// su_hint_date.setVisible(false);
		getContentPane().add(su_hint_date);

		/**
		 * ȡ����ť
		 */
		su_btn_cancel = new JButton("\u53D6\u6D88");
		su_btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		su_btn_cancel.setBounds(233, 470, 93, 23);
		getContentPane().add(su_btn_cancel);

		/**
		 * ���水ť
		 */
		su_btn_save = new JButton("\u4FDD\u5B58");
		su_btn_save.setBounds(100, 470, 93, 23);
		getContentPane().add(su_btn_save);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 0, 449, 594);
		getContentPane().add(panel_2);
		su_btn_save.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// �����¼�
				/**
				 * ʱ���ʽ�ж�
				 */
				String eL1 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
				String eL2 = "[0-9]{4}-[1-9]{1}-[1-9]{1}";
				Pattern p1 = Pattern.compile(eL1);
				Pattern p2 = Pattern.compile(eL2);
				Matcher m1 = p1.matcher(su_txt_birth.getText());
				Matcher m2 = p2.matcher(su_txt_birth.getText());
				boolean dateFlag1 = m1.matches();
				boolean dateFlag2 = m2.matches();
				if (!dateFlag1 && !dateFlag2) {
					su_hint_date.setForeground(Color.RED);
					su_hint_date.setText("��ʽ����");
					su_txt_birth.setText("");
					return;
				}
				String newsex;
				if (su_rb_boy.isSelected()) {
					newsex = su_rb_boy.getText();
				} else {
					newsex = su_rb_girl.getText();
				}

				Users newUsers = new Users(ownuser.getId(), su_txt_name
						.getText(), ownuser.getPwd(), su_txt_remark.getText(),
						ownuser.getHeadimg(), newsex, su_txt_birth.getText(),
						ownuser.getEmail(), 0);
				System.out.println(newUsers);
				usersDao.updateUsers(newUsers, ownuser.getName());
				DataUtil.showMessage("�޸����ϳɹ������µ�¼");
				usersDao.setUnOnlineById(ownuser.getId());
				homeFrame.getLoginFrame().clear();
				homeFrame.getLoginFrame().setVisible(true);
				homeFrame.dispose();
				dispose();
			}
		});

	}
}
