package com.lib.linkage;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.*;

public class NewStudRegister implements DocumentListener,
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
	
	Border border = BorderFactory.createEmptyBorder(10,10,10,10);
	Border ImageBorder = BorderFactory.createEmptyBorder(10,10,10,10);

	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gc = new GridBagConstraints();
	JPanel RegisterPanel = new JPanel(gbl);
	
	String adjustedstcard;
	
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
		enteredname.setBorder(border);
		
		sno.getDocument().addDocumentListener(this);
		sno.addFocusListener(this);
		
		enteredsno.addMouseListener(this);
		enteredsno.setBorder(border);
		
		scard.getDocument().addDocumentListener(this);
		scard.addFocusListener(this);
		
		enteredscard.addMouseListener(this);
		enteredscard.setBorder(border);
		
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
		
		gbl.setConstraints(sname, gc);
		RegisterPanel.add(sname);
		gbl.setConstraints(enteredname, gc);
		RegisterPanel.add(enteredname);
		enteredname.setVisible(false);
		
		//Themeing Stdnumber Tag
		RegisterPanel.add(stdno);
		sno = TranslucentTextField(sno);     //Style sno TextField
		
		gbl.setConstraints(sno, gc);
		sno.setBorder(border);
		
		RegisterPanel.add(sno);
		gbl.setConstraints(enteredsno, gc);
		RegisterPanel.add(enteredsno);
		enteredsno.setVisible(false);
		
		//Themeing card number Tag
		RegisterPanel.add(idcard);
		
		scard = TranslucentTextField(scard);
		gbl.setConstraints(scard, gc);
		scard.setBorder(border);
		
		RegisterPanel.add(scard);
		gbl.setConstraints(enteredscard, gc);
		RegisterPanel.add(enteredscard);
		enteredscard.setVisible(false);
		
		//Themeimg password number tag
		RegisterPanel.add(pw);
		password = TranslucentPwField(password);
		gbl.setConstraints(password, gc);
		password.setBorder(border);
		RegisterPanel.add(password);
		
		
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
				System.out.print("Here");
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

}