package com.lib.linkage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.sql.*;
import net.miginfocom.swing.MigLayout;

public class Linkage{
	public static void main(String args[]) {
		System.out.println("Try out this lib app");
		new MainPage();
	}
}

class MainPage implements ComponentListener, MouseListener{
	BufferedImage icon, borrowicon;
	ImageIcon logo, o1, o2, o3;
	JFrame frame = new JFrame();
	JLabel lhint = new JLabel();
	JLabel logolabel;
	JPanel title = new JPanel();
	JPanel operations = new JPanel();
	JLabel borrow;
	JButton return_b = new JButton("还书");
	JButton mang = new JButton("管理员操作");
	JButton eng = new JButton("ENG");
	JButton chinese = new JButton();
	
	//创建加载图片的管理器对象
	ImageLoader il = new ImageLoader();
	//模拟化环境提示
	String hint = "本系统模拟了学校的图书管理系统，并非"+
	"真实的借书系统。";
	MainPage(){
		icon = il.LoadImage("lib.png");
		logo = new ImageIcon(icon);
		logolabel = new JLabel(logo);
		frame.setLayout(new BorderLayout());
		title.setLayout(new BorderLayout());
		lhint.setText(hint);
		lhint.setForeground(Color.WHITE);
		frame.setVisible(true);
		frame.setTitle("暨南大学图书馆一体化系统");
		title.setBackground(Color.decode("#18304a"));
		title.add(lhint, BorderLayout.NORTH);
		title.add(logolabel, BorderLayout.SOUTH);
		frame.add(title, BorderLayout.NORTH);
		operations = OperationTabInit(operations);
		frame.add(operations, BorderLayout.CENTER);
		frame.addComponentListener(this);
	}
	public JPanel OperationTabInit (JPanel operation) {
		operation.setLayout(new GridLayout(0,1));
		BufferedImage bimg = il.LoadImage("BorrowSign.png");
		o1 = new ImageIcon(bimg);
		borrow = new JLabel(o1);
		borrow = setImageButtonStyle(borrow, Color.decode("#00c5ff"));
		borrow.addMouseListener(this);
		operation.add(borrow);
		operation.add(return_b);
		operation.add(mang);
		operation.addComponentListener(this);
		return operation;
	}
	
	public JLabel setImageButtonStyle(JLabel btn, Color color) {
		btn.setOpaque(true);
		btn.setBackground(color);
		return btn;
	}
	public void componentHidden(ComponentEvent arg0) {
		  //Not our concern
    }
    public void componentMoved(ComponentEvent arg0) { 
    	  		//Not our concern
    }
    public void componentResized(ComponentEvent arg0) {
    		float ratio =0;
    		ratio = logo.getIconWidth() / logo.getIconHeight();
    		if(logo.getIconWidth() > frame.getWidth())
    		logolabel.setIcon(il.resizeIcon(logo, frame,ratio));
    		
    		ratio = o1.getIconWidth() / o1.getIconHeight();
    		if(o1.getIconWidth() > frame.getWidth())
    		borrow.setIcon(il.resizeIcon(o1, frame, ratio));
    }
    public void componentShown(ComponentEvent arg0) {
    	  		//Not our concern
      }
	@Override
	public void mouseClicked(MouseEvent arg0) {
	  if(arg0.getSource() == borrow) {
		  System.out.print("Borrow Mode Selected");
	  }
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		//This is a fake 'JButton' :D
		borrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		borrow.setBackground(Color.decode("#005cff"));
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		borrow.setBackground(Color.decode("#00c5ff"));
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
}