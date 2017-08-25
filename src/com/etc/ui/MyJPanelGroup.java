package com.etc.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.etc.entity.Groups;
import com.etc.entity.Users;
import com.etc.tool.ChatManager;

public class MyJPanelGroup extends JPanel {
	private SwingGroupChat frame;
	private Groups groups;
	private Users ownuser;

	public MyJPanelGroup(Groups g, Users owner) {
		groups = g;
		ownuser = owner;
		frame = null;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//
				// frame.setVisible();
				if (frame == null) {
					frame = new SwingGroupChat(groups, ownuser);
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
