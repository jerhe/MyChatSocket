package com.bsb.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.bsb.test.main.Mainwindowtest;
import com.etc.ui.SwingSingleChat;




public class ChatManager {
	private String Name;
	
  boolean flag=false;
	private ChatManager(){}
	private static final ChatManager instance = new ChatManager();
	public static ChatManager getCM() {
		return instance;
	}
	
	SwingSingleChat window;
	String IP;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	
	public void setWindow(SwingSingleChat window) {
		this.window = window;
		
	}
	
	public void connect(String ip,String name) {
		this.IP = ip;
        this.Name=name;
		new Thread(){

			@Override
			public void run() {
				try {
					socket = new Socket(IP, 8066);
					writer = new PrintWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),"UTF-8"));
					
					reader = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream(),"UTF-8"));

					String line;
					while ((line = reader.readLine()) != null) {
						String[] newStr=line.split("@");
						for (int i = 0; i < newStr.length; i++) {
							System.out.println(newStr[i]);
						}
						window.appendTextother(newStr[0]+"\n"+newStr[1]);
					}
					writer.close();
					reader.close();
					writer = null;
					reader = null;
				} catch (UnknownHostException e) {
				e.printStackTrace();
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void send(String out) {
		if (writer != null) {
			
			writer.write(out+"\n");
			writer.flush();
		}else {
			window.appendText("连接到的数据流为空");
		}
	}
}
