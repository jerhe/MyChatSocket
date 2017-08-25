package com.etc.ui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;

public class JDialogChanageHeadImg extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8355001898939218941L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JDialogChanageHeadImg dialog = new JDialogChanageHeadImg();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public JDialogChanageHeadImg() {
		setBounds(100, 100, 282, 445);
		getContentPane().setLayout(null);
		
		JButton dchI_btn_select = new JButton("\u9009\u62E9\u56FE\u7247");
		dchI_btn_select.setBounds(76, 59, 93, 30);
		getContentPane().add(dchI_btn_select);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(76, 138, 93, 93);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u786E\u8BA4");
		btnNewButton_1.setBounds(76, 298, 93, 30);
		getContentPane().add(btnNewButton_1);

	}

}
