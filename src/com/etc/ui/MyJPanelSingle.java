package com.etc.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.bsb.tool.ChatManager;
import com.etc.entity.Users;

/**
 * 跳转到单聊界面
 * @author zhuzhen
 *
 */
public class MyJPanelSingle extends JPanel{
	private Users users;
	private JFrame frame;
	private Users ownuser;
	public MyJPanelSingle(Users u,Users owner) {
		users=u;
		ownuser=owner;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//
				//frame.setVisible();
				SwingSingleChat frame =new SwingSingleChat(users);
				ChatManager.getCM().setWindow(frame);
				frame.setFrame(frame);
				frame.setVisible(true);
				
				
			}
		});
		
	}
}
