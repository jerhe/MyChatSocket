package com.etc.base;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.etc.entity.Groups;
import com.etc.entity.Users;

public class BaseJFrame extends JFrame {
	public Users ownuser = new Users();
	public ArrayList<Users> friendsList = new ArrayList<>();
	public ArrayList<Groups> groupsList= new ArrayList<>();
	public ArrayList<Users> recentsList = new ArrayList<>();
	public void appendTextother(String in,String fm,String tn,int id) {	
	}
	public void appendText(String in) {
	}
}
