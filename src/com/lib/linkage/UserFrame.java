package com.lib.linkage;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class UserFrame{
	JPanel login = new JPanel(new GridBagLayout());
	JPanel formlayout = new JPanel(new GridLayout(0,2));
	ImageLoader loader = new ImageLoader();
	
	public void showFrame(JFrame frame) {
		GridBagConstraints gc = new GridBagConstraints();
		JLabel usershow, ushow, pshow, hint;
		ushow = new JLabel("用户名：");
		pshow = new JLabel("密码： ");
		hint = new JLabel("请输入正确的密码以继续");
		JTextField username = new JTextField();
		JPasswordField password = new JPasswordField();
		//Initialise all the fields
		formlayout.add(ushow);
		formlayout.add(username);
		formlayout.add(pshow);
		formlayout.add(password);
		
		//Initialize the unknown photo
		BufferedImage userimg = loader.LoadImage("UnknownId.png");
		ImageIcon usericon = new ImageIcon(userimg);
		usershow = new JLabel(usericon);
		
		login.add(usershow, GridBagConstraints.REMAINDER);
		frame.add(login, BorderLayout.CENTER);
		frame.add(formlayout, BorderLayout.SOUTH);
		frame.revalidate();
		frame.repaint();
		
	}
}