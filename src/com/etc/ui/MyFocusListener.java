package com.etc.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class MyFocusListener implements FocusListener {
	String info;
    JTextField jtf;
    public MyFocusListener(String info, JTextField jtf) {
        this.info = info;
        this.jtf = jtf;
    }
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		  String temp = jtf.getText();
	        if(temp.equals(info)){
	            jtf.setText("");
	            jtf.setForeground(Color.BLACK);
	        }
	}

	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		 String temp = jtf.getText();
	        if(temp.equals("")){
	            jtf.setText(info);
	            jtf.setForeground(Color.LIGHT_GRAY);
	            jtf.setFont(new Font("ËÎÌו", Font.PLAIN, 14));
	            
	        }
	}

}
