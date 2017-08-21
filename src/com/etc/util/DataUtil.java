package com.etc.util;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class DataUtil {
	public static String IPSTRING="127.0.0.1"; 
	/**
	 * ʱ������ ת�ַ�����
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	/**
	 * �ַ�����תʱ������
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
	 * ��ʾ��Ϣ
	 * @param msg ��Ϣ
	 */
	public static void showMessage(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}
	
	/**
	 * ��ʾ��ʾ����
	 * @param msg
	 */
	public static void showComfirm(String msg){
		JOptionPane.showConfirmDialog(null, msg, "��ʾ��Ϣ",JOptionPane.OK_CANCEL_OPTION);
	}

}
