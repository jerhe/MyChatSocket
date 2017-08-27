package com.etc.util;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.etc.dao.GroupMessagesDao;
import com.etc.dao.GroupsDao;
import com.etc.dao.MessagesDao;
import com.etc.entity.SendFlag;
import com.etc.entity.Users;

public class DataUtil {
	public static String IPSTRING = "127.0.0.1";
	public static File file = new File("info.ini");
	
	
	public static synchronized void setContent(JTextArea content,String in,SendFlag sendflag){
		if(sendflag.isSendflag()){
			content.append("\n" + in);
			sendflag.setSendflag(false);
		}
	}
	
	/**
	 * 设置同步群聊接受消息
	 * 
	 * @param content
	 * @param in
	 * @param fromname
	 * @param toname
	 * @param msgid
	 */
	public static synchronized void readGroupMessage(JTextArea content,
			String in, int gmsgid,SendFlag sendflag) {
		GroupMessagesDao groupMessagesDao = new GroupMessagesDao();
		if (groupMessagesDao.Read(gmsgid)) {
			content.append("\n" + in);
		}
		sendflag.setIsNeedBottom(0);
	}

	/**
	 * 设置同步单聊接受消息
	 * 
	 * @param content
	 * @param in
	 * @param fromname
	 * @param toname
	 * @param msgid
	 */
	public static synchronized void readMessage(JTextArea content, String in,
			int msgid,SendFlag sendflag) {
		MessagesDao messagesDao = new MessagesDao();
		if (messagesDao.isReadById(msgid)) {
			content.append("\n" + in);
			messagesDao.setRead(msgid);
			sendflag.setIsNeedBottom(0);
		}
	}

	/**
	 * 把用户和密码存到数据库中
	 * 
	 * @return
	 */
	public static Users fromFile() {
		Users u = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				u = (Users) ois.readObject();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return u;
	}

	public static void toFile(Users u) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		if (file.exists()) {

			try {
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(u);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (oos != null) {
					try {
						oos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else {
			try {
				file.createNewFile();
				try {
					fos = new FileOutputStream(file);
					oos = new ObjectOutputStream(fos);
					oos.writeObject(u);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (oos != null) {
						try {
							oos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 时间日期 转字符日期
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 字符日期转时间日期
	 * 
	 * @param s
	 * @return
	 */
	public static Date stringToDate(String s) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 显示消息
	 * 
	 * @param msg
	 *            消息
	 */
	public static void showMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	/**
	 * 显示提示窗口
	 * 
	 * @param msg
	 */
	public static void showComfirm(String msg) {
		JOptionPane.showConfirmDialog(null, msg, "提示消息",
				JOptionPane.OK_CANCEL_OPTION);
	}

}
