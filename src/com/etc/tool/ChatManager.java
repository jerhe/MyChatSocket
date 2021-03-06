package com.etc.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.etc.base.BaseJFrame;
import com.etc.test.Mainwindowtest;
import com.etc.ui.SwingGroupChat;
import com.etc.ui.SwingSingleChat;

public class ChatManager {
	private String Name;

	boolean flag = false;

	private ChatManager() {
	}
	private static final ChatManager instance = new ChatManager();
	public static ChatManager getCM() {
		return instance;
	}
	private static Vector<BaseJFrame> windowlist = new Vector<>();
	BaseJFrame window;
	String IP;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;

	public void setWindow(SwingSingleChat window) {
		//this.window = window;
		windowlist.add(window);

	}

	public void setWindow(SwingGroupChat window) {
		//this.window = window;
		windowlist.add(window);

	}
	public void connect(String ip, String name,Socket s) {
		this.IP = ip;
		this.Name = name;
		socket = s;
		new Thread() {

			@Override
			public void run() {
				try {
					// 地址和端口号
					
					writer = new PrintWriter(new OutputStreamWriter(
							socket.getOutputStream(), "UTF-8"));

					reader = new BufferedReader(new InputStreamReader(
							socket.getInputStream(), "UTF-8"));

					String line;
					
					while ((line = reader.readLine()) != null) {
						String[] newStr = line.split("@");
						for (int i = 0; i < newStr.length; i++) {
							System.out.println(newStr[i] + " ");
						}
						/**
						 * 好友消息
						 * msgid ownuser.getName() tousers.getName() nowtime ssl_txt_send.getText()
						 * 
						 * 群消息
						 * msgid ownuser.getName() groupid           nowtime ssl_txt_send.getText()
						 */
						
						for (BaseJFrame w : windowlist) {
							window=w;
							System.out.println("***************"+windowlist.size());
							window.appendTextother(newStr[1] + newStr[3] + ":\n"
									+ newStr[4],newStr[1],newStr[2],
									Integer.valueOf(newStr[0]));
						}
						
					}
					writer.close();
					reader.close();
					writer = null;
					reader = null;
				} catch(NullPointerException e){
					System.out.println("Vecter:"+windowlist.size());
					windowlist.remove(window);
					System.out.println("Vecter:"+windowlist.size());
				}
				catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	public void send(String out) {
		if (writer != null) {

			writer.write(out + "\n");
			writer.flush();
		} else {
			for (BaseJFrame window : windowlist) {
				window.appendText("连接失败");
			}
		}
	}
}
