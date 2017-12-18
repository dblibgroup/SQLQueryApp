package com.lib.linkage;

import javax.swing.*;
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
	
	JLabel enteredname = new JLabel();
	JTextField sname = new JTextField();
	JTextField sno = new JTextField();
	

	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gc = new GridBagConstraints();
	JPanel RegisterPanel = new JPanel(gbl);

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
		
		sname.setMinimumSize(new Dimension(sname.getHeight(), 400));
		sname.getDocument().addDocumentListener(this);
		sname.addFocusListener(this);
		
		enteredname.addMouseListener(this);
		
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(register,	 gc);
		RegisterPanel.add(register);
		
		//Put icon here
		gbl.setConstraints(icon, gc);
		RegisterPanel.add(icon);
		
		//Themeing name tag
		name.setForeground(Color.WHITE);
		RegisterPanel.add(name);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.WEST;
		sname.setColumns(10);
		sname.setMinimumSize(sname.getPreferredSize());
		
		gbl.setConstraints(sname, gc);
		RegisterPanel.add(sname);
		gbl.setConstraints(enteredname, gc);
		RegisterPanel.add(enteredname);
		enteredname.setVisible(false);
		
		RegisterPanel.add(stdno);
		
		gbl.setConstraints(sno, gc);
		RegisterPanel.add(sno);
		frame.add(RegisterPanel,BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		//System.out.println("Changing text. Resizing...");   //For testing purpose
		enteredname.setText(sname.getText());
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		//System.out.println("Changing text. Resizing...");   //For testing purpose
		enteredname.setText(sname.getText());
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
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}