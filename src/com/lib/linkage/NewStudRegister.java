package com.lib.linkage;
import java.sql.*;  
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.*;

public class NewStudRegister implements ActionListener, DocumentListener,
FocusListener, MouseListener{
	ImageLoader il = new ImageLoader();
	
	//Initialise all graphical elements
	JLabel register = new JLabel("注册以使用图书馆权限");
	JLabel icon;
	
	JFrame frame;
	JLabel name = new JLabel("姓名: ");
	JLabel stdno = new JLabel("学号: ");
	JLabel idcard = new JLabel("校园卡号: "); 
	JLabel pw = new JLabel("借书/登录密码: ");
	
	JLabel enteredname = new JLabel("<请输入姓名>");
	JLabel enteredsno = new JLabel("<请输入学号>");
	JLabel enteredscard = new JLabel("<请输入校园卡号>");
	
	JTextField sname = new JTextField();
	JTextField sno = new JTextField();
	JTextField scard = new JTextField();
	JPasswordField password = new JPasswordField();
	
	Border tfborder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE);
	Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,10,8,10),
			tfborder);
	Border invisibleborder = BorderFactory.createEmptyBorder(10,10,10,10);
	Border ImageBorder = BorderFactory.createEmptyBorder(10,10,10,10);

	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gc = new GridBagConstraints();
	JPanel RegisterPanel = new JPanel(gbl);
	
	String adjustedstcard;
	
	//WARNING!!!: This block is newly added here by zorrow to add a REGISTER button
	JLabel entercontain;
	JButton Enter = new JButton("注册");
	
	public void showRegisterMenu(JFrame frame) {
		this.frame = frame;

		RegisterPanel.setOpaque(false);
		frame.getContentPane().setBackground(Color.decode("#00c5ff"));
		register.setFont(new Font(register.getFont().getFontName(), Font.BOLD, 20));
		register.setForeground(Color.WHITE);
		
		//Load image from file
		BufferedImage img = il.resizeImg(il.LoadImage("UnknownIdWhite.png"), 128, 128);
		ImageIcon iconn = new ImageIcon(img);
		icon = new JLabel(iconn);
		icon.setBorder(ImageBorder);
		
		sname.setMinimumSize(new Dimension(sname.getHeight(), 400));
		sname.getDocument().addDocumentListener(this);
		sname.addFocusListener(this);
		sname.setBorder(border);
		
		enteredname.addMouseListener(this);
		enteredname.setBorder(invisibleborder);
		
		sno.getDocument().addDocumentListener(this);
		sno.addFocusListener(this);
		
		enteredsno.addMouseListener(this);
		enteredsno.setBorder(invisibleborder);
		
		scard.getDocument().addDocumentListener(this);
		scard.addFocusListener(this);
		
		enteredscard.addMouseListener(this);
		enteredscard.setBorder(invisibleborder);
		
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(register,	 gc);
		RegisterPanel.add(register);
		
		//Put icon here
		gbl.setConstraints(icon, gc);
		RegisterPanel.add(icon);
		
		//Themeing name tag
		RegisterPanel.add(name);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.WEST;
		
		sname = TranslucentTextField(sname);  //Style sname TextField
		sname.setVisible(false);
		
		gbl.setConstraints(sname, gc);
		RegisterPanel.add(sname);
		gbl.setConstraints(enteredname, gc);
		RegisterPanel.add(enteredname);
		
		//Themeing Stdnumber Tag
		RegisterPanel.add(stdno);
		sno = TranslucentTextField(sno);     //Style sno TextField
		
		gbl.setConstraints(sno, gc);
		sno.setBorder(border);
		sno.setVisible(false);
		
		RegisterPanel.add(sno);
		gbl.setConstraints(enteredsno, gc);
		RegisterPanel.add(enteredsno);
		
		//Themeing card number Tag
		RegisterPanel.add(idcard);
		
		scard = TranslucentTextField(scard);
		gbl.setConstraints(scard, gc);
		scard.setBorder(border);
		scard.setVisible(false);
		
		RegisterPanel.add(scard);
		gbl.setConstraints(enteredscard, gc);
		RegisterPanel.add(enteredscard);
		
		//Themeimg password number tag
		RegisterPanel.add(pw);
		password = TranslucentPwField(password);
		gbl.setConstraints(password, gc);
		password.setBorder(border);
		RegisterPanel.add(password);
		

		//WARNING!!!: This block is newly added here by zorrow to add a REGISTER button
		BufferedImage enterico = il.LoadImage("arrow_right.jpg");
		ImageIcon entericon = new ImageIcon(enterico);
		Image image = entericon.getImage();
		image = image.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
		entericon = new ImageIcon(image);
		entercontain = new JLabel(entericon);
		Enter.addActionListener(this);
		frame.add(Enter, BorderLayout.SOUTH);
		
		frame.add(RegisterPanel,BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}
	public JTextField TranslucentTextField(JTextField field) {
		field.setMinimumSize(field.getPreferredSize());
		field.setBackground(new Color(0,0,0,0));
		field.setOpaque(false);
		field.setColumns(10);
		return field;
	}
	
	public JPasswordField TranslucentPwField(JPasswordField field) {
		field.setMaximumSize(field.getPreferredSize());
		field.setBackground(new Color(0,0,0,0));
		field.setOpaque(false);
		field.setColumns(10);
		return field;
	}
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent de) {
		if(de.getDocument() == sname.getDocument()) {
			//System.out.println("Changing text. Resizing...");   //For testing purpose
			if(sname.getText().equals("")) enteredname.setText("<请输入姓名>");
			else enteredname.setText(sname.getText());
		}
		if(de.getDocument() == sno.getDocument()) {
			if(sno.getText().equals("")) enteredsno.setText("<请输入学号>");
			else enteredsno.setText(sno.getText());
		}
		if(de.getDocument() == scard.getDocument()) {
			if(scard.getText().equals("")) enteredscard.setText("<请输入卡号>");
			else {
				adjustedstcard = fillDigit(scard.getText());
				enteredscard.setText(adjustedstcard);
			}
		}
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void removeUpdate(DocumentEvent de) {
		if(de.getDocument() == sname.getDocument()) {
			//System.out.println("Changing text. Resizing...");   //For testing purpose
			if(sname.getText().equals("")) enteredname.setText("<请输入姓名>");
			else enteredname.setText(sname.getText());
		}
		if(de.getDocument() == sno.getDocument()) {
			if(sno.getText().equals("")) enteredsno.setText("<请输入学号>");
			else enteredsno.setText(sno.getText());
		}
		if(de.getDocument() == scard.getDocument()) {
			if(scard.getText().equals("")) enteredscard.setText("<请输入卡号>");
			else {
				adjustedstcard = fillDigit(scard.getText());
				enteredscard.setText(adjustedstcard);
			}
		}
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() == sname) {
			sname.setVisible(false);
			enteredname.setVisible(true);
		}
		if(e.getSource() == sno) {
			sno.setVisible(false);
			enteredsno.setVisible(true);
		}
		if(e.getSource() == scard) {
			scard.setVisible(false);
			enteredscard.setVisible(true);
		}
	}

	public String fillDigit(String str) {
		//Adding zeros to form an eight digit code
		int maxlen = 8;
		int length = str.length();
		int num_of_zero = maxlen - length;
		for(int i=num_of_zero; i>0; i--) {
			str = "0"+str;
		}
		return str;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == enteredname) {
			sname.setVisible(true);
			sname.grabFocus();
			enteredname.setVisible(false);
		}
		if(e.getSource() == enteredsno) {
			sno.setVisible(true);
			sno.grabFocus();
			enteredsno.setVisible(false);
		}
		if(e.getSource() == enteredscard) {
			scard.setVisible(true);
			scard.grabFocus();
			enteredscard.setVisible(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//function trim() is used to avoid extra spaces typed by users which may lead to a mismatching
		String sno = enteredsno.getText().trim();
		String scard = enteredscard.getText().trim();
		String sname = enteredname.getText().trim();
		//function getPassword() is suggested rather than getText() for safety reasons
		String passwd = new String(password.getPassword());
		System.out.println("USERNAME: "+ sname + " PASSWORD: " + passwd + " Student ID: " + sno + " Card ID: " + scard + ".");
		
		//These validation rules haven't been considered thoroughly so it needs to be improved.
		if(sno.length() != 10 || !sno.matches("^\\d+$"))      //For instance, the string '2015051471' has 10 characters.
		{                                                     //And it should be numerical.
			System.out.println("学号输入有误，请重新尝试！");
		}else if(scard.length() > 8 || !scard.matches("^\\d+$")){
			System.out.println("卡号输入有误，请重新尝试！");
		}else if(passwd.length() < 6 || passwd.matches("^\\d+$") || passwd.matches("^[a-zA-Z]+$")){ 
			//The password should be long enough(at least 6) and contains both numbers and letters(or any other characters).
			System.out.println("密码强度较低，请增加密码复杂度！");
		}else if(sname == null || (!sname.matches("[\u4e00-\u9fa5]{2,}") && !sname.matches("^[a-zA-Z]+$"))){
			//The name should be Chinese words or letters only.
			System.out.println("姓名输入有误，请重新尝试！");
		}else{
			System.out.println("Validation test passed.");
			//check if the user exists
			Object rs[][] = null;
			Connection conn = DbConnection.DbConnect();
			try{
				Query usrQuery = new Query();
				String checkSql = "SELECT * FROM stuInfo WHERE stuID = ?  OR cardID = ?";
			    PreparedStatement pstmt = null;
		 	    pstmt = (PreparedStatement) conn.prepareStatement(checkSql,
		 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
		 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
			    pstmt.setString(1, sno);
			    pstmt.setString(2, scard);
				rs = usrQuery.PsExecQuery(pstmt);
			} catch (SQLException se) {
				se.printStackTrace();
			}
			
			//The code below is to find out if there are any data retrieved from the database
			if(rs == null || rs.length == 0 || (rs.length == 1 && rs[0].length == 0)){
				System.out.println("学生信息录入确认！");
				//THen we just save the form into the database.
				try{
					Query usrQuery = new Query();
				    PreparedStatement pstmt = null;
				    
					String stuSql = "INSERT INTO stuInfo(stuID,stuName,cardID) values(?,?,?)";
			 	    pstmt = (PreparedStatement) conn.prepareStatement(stuSql,
			 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
			 	    		  ResultSet.CONCUR_READ_ONLY);
				    pstmt.setString(1, sno);
			  	    pstmt.setString(2, sname);
				    pstmt.setString(3, scard);			
				    rs = usrQuery.PsExecQuery(pstmt);
				    
				    String userSql = "INSERT INTO userInfo(stuID, password) values(?,?)";
			 	    pstmt = (PreparedStatement) conn.prepareStatement(userSql,
			 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
			 	    		  ResultSet.CONCUR_READ_ONLY);
			 	    pstmt.setString(1, sno);
			 	    pstmt.setString(2, MD5.getEncryptedPwd(passwd));
				    rs = usrQuery.PsExecQuery(pstmt);
				    
				    //Here should turn to ANOTHER PAGE
				    ShowMenu sm = new ShowMenu();
					frame.remove(RegisterPanel);
					frame.remove(Enter);
					frame.revalidate();
					frame.repaint();
					sm.OperationTabInit(frame);
				    
				} catch (SQLException sqle){
					sqle.printStackTrace();
			    } catch (Exception ee){
			    	ee.printStackTrace();
			    }
			}else{
				System.out.println("该学生或该卡的信息已经登记！");
				//Some ERROR MESSAGE should be shown below.
			}
		}
		return; 
	}
}