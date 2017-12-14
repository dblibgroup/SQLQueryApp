package com.lib.linkage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
//import java.sql.*;

public class Linkage{
	public static void main(String args[]) {
		System.out.println("Library Demo");
		new MainPage();
	}
}

class MainPage implements ComponentListener, MouseListener{
	BufferedImage icon, bimg, rimg;
	ImageIcon logo, o1, o2, o3;
	JFrame frame = new JFrame();
	JLabel lhint = new JLabel();
	JLabel logolabel;
	JPanel title = new JPanel();
	JPanel operations = new JPanel();
	JLabel borrow, return_bb;
	JButton return_b = new JButton("还书");
	JButton mang = new JButton("管理员操作");
	JButton eng = new JButton("ENG");
	JButton chinese = new JButton();
	String colourpicker;
	float o_width =0;
	
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
		title.setLayout(new GridLayout(0,1));
		lhint.setText(hint);
		lhint.setForeground(Color.WHITE);
		frame.setVisible(true);
		frame.setTitle("暨南大学图书馆一体化系统");
		title.setBackground(Color.decode("#18304a"));
		title.add(lhint);
		title.add(logolabel);
		frame.add(title, BorderLayout.NORTH);
		operations = OperationTabInit(operations);
		frame.add(operations, BorderLayout.CENTER);
		
		o_width = frame.getWidth();
		System.out.println("Width Now: "+ o_width);
		
		
		//To make things valid, we should revalidate all the items
		frame.revalidate();
		frame.repaint();
	}
	public JPanel OperationTabInit (JPanel operation) {
		operation.setLayout(new GridLayout(0,1));
		//Load custom Borrow ImageButton
		bimg = il.LoadImage("BorrowSign.png");
		o1 = new ImageIcon(bimg);
		borrow = new JLabel(o1);
		
		//设置安按钮背景色等相关属性
		colourpicker = returnColor("1");
		borrow = setImageButtonStyle(borrow, Color.decode(colourpicker));
		borrow.addMouseListener(this);
		
		//Load custom Return ImageButton
		rimg = il.LoadImage("BorrowSign.png");
		o2 = new ImageIcon(rimg);
		return_bb = new JLabel(o2);
		
		//设置安按钮背景色等相关属性
		colourpicker = returnColor("2");
		return_bb = setImageButtonStyle(return_bb, Color.decode(colourpicker));		
		return_bb.addMouseListener(this);
		
		operation.add(borrow);
		operation.add(return_bb);
		operation.add(mang);
		operation.addComponentListener(this);
		
		return operation;
	}
	
	public JLabel setImageButtonStyle(JLabel btn, Color color) {
		btn.setOpaque(true);
		btn.setBackground(color);
		return btn;
	}
	public String returnColor(String Options) {
		switch(Options) {
		
		case "1": return "#00c5ff";
		case "2": return "#0fd29c";
		default: return "YELLO";
		}
	}
	
	
	
	public void componentHidden(ComponentEvent arg0) {
		  //Not our concern
    }
    public void componentMoved(ComponentEvent arg0) { 
    	  		//Not our concern
    }
    public void componentResized(ComponentEvent arg0) {
    		//为了防止照片失真，因此我们不会调整图书馆标志图片的大小
    		if(o1.getIconWidth() > frame.getWidth()) {
    			//We don't need to judge it every time. Just resize both of them.
    			borrow.setIcon(il.resizeIcon(o1, borrow));
    			return_bb.setIcon(il.resizeIcon(o2, return_bb));
    		}
    }
    public void componentShown(ComponentEvent arg0) {
    	  		//Not our concern
      }
	@Override
	public void mouseClicked(MouseEvent arg0) {
	  if(arg0.getSource() == borrow) {
		  System.out.print("Borrow Mode Selected. " +
				  "Dropping Selection Menu");
		  frame.remove(operations);
		  frame.revalidate();
		  frame.repaint();
		  UserFrame uf = new UserFrame();
		  uf.showFrame(frame);
		  
	  }
	  if (arg0.getSource() == return_bb) {
		  System.out.print("Return mode selected.");
		  frame.remove(operations);
		  frame.revalidate();
		  frame.repaint();
		  Return r = new Return();
		  r.ReturnMode(frame);
	  }
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		//This is a fake 'JButton' :D
		if(arg0.getSource() == borrow) {
			borrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			borrow.setBackground(Color.decode("#005cff"));
		}
		else if(arg0.getSource() == return_bb) {
			return_bb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			return_bb.setBackground(Color.decode("#005cff"));
		}
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		String color;
		if(arg0.getSource() == borrow) {
			color = returnColor("1");
			borrow.setBackground(Color.decode(color));
		}
		else if(arg0.getSource() == return_bb) {
			color = returnColor("2");
			return_bb.setBackground(Color.decode(color));
		}
		
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