package com.etc.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.etc.entity.Users;
import com.etc.tool.ChatManager;

/**
 * 跳转到单聊界面
 * 
 * @author zhuzhen
 * 
 */
public class MyJPanelSingle extends JPanel {

	private static final long serialVersionUID = -4431342396147684729L;
	private Users users;
	private SwingSingleChat frame;
	private Users ownuser;

	public MyJPanelSingle(Users u, Users owner) {
		users = u;
		ownuser = owner;
		frame = null;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (frame == null) {
					frame = new SwingSingleChat(users, ownuser);
					ChatManager.getCM().setWindow(frame);
					frame.setFrame(frame);
					frame.setVisible(true);
				} else {
					frame.setVisible(true);
				}
			}
		});
	}

}
