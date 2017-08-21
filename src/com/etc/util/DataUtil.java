package com.etc.util;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class DataUtil {
	public static String IPSTRING="127.0.0.1"; 
	/**
	 * 时间日期 转字符日期
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	/**
	 * 字符日期转时间日期
	 * @param s
	 * @return
	 */
	public static Date stringToDate(String s){
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
	 * @param msg 消息
	 */
	public static void showMessage(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}
	
	/**
	 * 显示提示窗口
	 * @param msg
	 */
	public static void showComfirm(String msg){
		JOptionPane.showConfirmDialog(null, msg, "提示消息",JOptionPane.OK_CANCEL_OPTION);
	}

}
