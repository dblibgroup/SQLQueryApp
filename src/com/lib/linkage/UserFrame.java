package com.lib.linkage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.swing.*;
import javax.swing.event.*;

import java.sql.*;
public class UserFrame implements ActionListener, DocumentListener{
	GridBagLayout gbl = new GridBagLayout();
	JPanel login = new JPanel(gbl);
	JPanel formlayout = new JPanel(new GridLayout(0,2));
	ImageLoader loader = new ImageLoader();
	JFrame frame;
	JButton Enter = new JButton("登录");
	JLabel entercontain;
	JTextField username = new JTextField();
	JPasswordField password = new JPasswordField();
	
	public void showFrame(JFrame frame) {
		this.frame = frame;
		JLabel usershow, ushow, pshow, hint;
		ushow = new JLabel("用户名：");
		pshow = new JLabel("密码： ");
		hint = new JLabel("请刷校园卡/输入用户名登录");
		
		//Initialize the unknown photo
		BufferedImage userimg = loader.LoadImage("UnknownId.png");
		ImageIcon usericon = new ImageIcon(userimg);
		
		//Before displaying it, we'll  resize the image into 128 * 128
		Image img = usericon.getImage();
		img = img.getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH);
		usericon = new ImageIcon(img);
	
		//Now it is available. We just put it into the JLabel Container
		usershow = new JLabel(usericon);
		usershow.setBackground(Color.decode("#005cff"));
		
		username.setMinimumSize(new Dimension(10,100));
		
		//Show Enter Button
		BufferedImage enterico = loader.LoadImage("arrow_right.jpg");
		ImageIcon entericon = new ImageIcon(enterico);
		Image image = entericon.getImage();
		image = image.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
		entericon = new ImageIcon(image);
		entercontain = new JLabel(entericon);
		
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(usershow, gc);
		gbl.setConstraints(hint, gc);
		login.add(hint);
		login.add(usershow);
		login.add(ushow);
		gc.anchor = GridBagConstraints.WEST;
		gbl.setConstraints(username, gc);
		login.add(username);
		login.add(pshow);
		gbl.setConstraints(password, gc);
		login.add(password);
		gc.anchor = GridBagConstraints.CENTER;
		gbl.setConstraints(entercontain, gc);
		login.add(entercontain);
		username.setOpaque(false);
		username.getDocument().addDocumentListener(this);
		
		//Set the default width of username field
		setMinimumWidth(username);
		password.setOpaque(false);
		password.getDocument().addDocumentListener(this);
		
		Enter.addActionListener(this);
		String fontname = login.getFont().getFontName();
		login.setFont(new Font(fontname, Font.PLAIN, 18));
		frame.add(login, BorderLayout.CENTER);
		frame.add(Enter, BorderLayout.SOUTH);
		frame.revalidate();
		frame.repaint();
		
	}

	public void setMinimumWidth(JComponent c) {
		int height = c.getHeight();
		int width = 400;
		c.setSize(width, height);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JLabel warning1 = new JLabel("不存在该学生！");
		JLabel warning2 = new JLabel("密码错误！");
		this.frame = frame;
		if(e.getSource() == Enter) {
			// SQL SHOULD BE HERE INSTEAD. The coding below is for
			// testing purpose
			//String uname = username.getText();
			//System.out.println("The name filled in is: " + uname);
			
			//function trim() is used to avoid extra spaces typed by users which may lead to a mismatching
			String uname = username.getText().trim();
			//function getPassword() is suggested rather than getText() for safety reasons
			String passwd = new String(password.getPassword());
			System.out.println("username: "+ uname + " password: " + passwd);

			Object rs[][] = null;
			try{
				//check if the user exists
				Query usrQuery = new Query();
				Connection conn = DbConnection.DbConnect();
				String sql = "SELECT * FROM userInfo WHERE stuID = ?";
			    PreparedStatement pstmt = null;
		 	    pstmt = (PreparedStatement) conn.prepareStatement(sql,
		 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
		 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
			    pstmt.setString(1, uname);
				rs = usrQuery.PsExecQuery(pstmt);
			} catch (SQLException se){
			    se.printStackTrace();
		    }
			
			//The code below is to find out if there are any data retrieved from the database
			if(rs == null || rs.length == 0 || (rs.length == 1 && rs[0].length == 0)){
				System.out.println("不存在该学生！");
				frame.add(warning1, BorderLayout.SOUTH);
			}else{
				for(int i = 0; i < rs.length; i++){
					String pwdInDb = String.valueOf(rs[i][2]);     //rs[i][2] relates to the password
					try{
						if(MD5.validPassword(passwd, pwdInDb)){
							System.out.println("密码正确！");
							//Since the login have been succeeded, THE PAGE FOR CODE-SCANNING should be shown.
							Return r = new Return ();
							frame.remove(login);
							frame.remove(Enter);
							frame.revalidate();
							frame.repaint();
							r.BorrowMode(frame);
							
						}else{
							System.out.println("密码错误！");
							//Leave a error message on the screen and wait for another input.
							
						}
					} catch (Exception me){
						me.printStackTrace();
					}
				}
				/*
				//Check the Object array for testing purpose
				for(int i = 0; i < rs.length; i++){
					for(int j = 0; j < rs[i].length; j++){
						System.out.print(rs[i][j] + " ");
					}
				}
				*/
			}
		}
	}
	public void changedUpdate(DocumentEvent arg0) {
		
	}
	public void insertUpdate(DocumentEvent arg0) {
		//System.out.println("Changing text. Resizing...");   //For testing purpose
		frame.revalidate();
		frame.repaint();
	}
	public void removeUpdate(DocumentEvent arg0) {
		//System.out.println("Changing text. Resizing...");     //For testing purpose
		frame.revalidate();
		frame.repaint();
	}
}