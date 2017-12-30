package com.lib.linkage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
//import java.sql.*;

public class Linkage{
	public static void main(String args[]) {
		System.out.println("Library Demo");
		MainPage p = new MainPage();
		p.MainPaging();
	}
}

class MainPage implements ComponentListener, MouseListener{
	/* Explaination:
	 * icon - library icon
	 * bimg - borrow books icon
	 * rimg - return icon
	 * aimg - admin icon
	 * simg - search icon
	 * apimg - apply icon
	 * mimg - My Library Icon
	 */
	BufferedImage icon, bimg, rimg, aimg, simg, apimg, mimg;
	ImageIcon logo, o1, o2, o3, o4, o5, o6;
	JFrame frame = new JFrame();
	JLabel lhint = new JLabel();
	JLabel logolabel;
	JPanel title = new JPanel();
	JPanel operations = new JPanel();
	JLabel borrow, return_bb, administration, search, entrance;
	JButton eng = new JButton("ENG");
	JButton chinese = new JButton();
	String colourpicker;
	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	float o_width =0;
	
	//创建加载图片的管理器对象
	ImageLoader il = new ImageLoader();
	//模拟化环境提示
	String hint = "本系统模拟了学校的图书管理系统，并非"+
	"真实的借书系统。";
	public void MainPaging(){
		frame.setBackground(Color.white);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//gd.setFullScreenWindow(frame);
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
		operations = OperationTabInit(operations, frame);
		
		o_width = frame.getWidth();
		System.out.println("Width Now: "+ o_width);
		
		
		//To make things valid, we should revalidate all the items
		frame.revalidate();
		frame.repaint();
	}
	public JPanel OperationTabInit (JPanel operation, JFrame frame) {
		this.frame = frame;
		operation.setLayout(new GridLayout(0,3));
		//Load custom Borrow ImageButton
		bimg = il.LoadImage("borrow.jpg");
		o1 = new ImageIcon(bimg);
		borrow = new JLabel(o1);
		
		//设置按钮文字，前景，背景色等相关属性
		colourpicker = returnColor("1");
		borrow = setImageButtonStyle(borrow, Color.decode(colourpicker));
		borrow.setText("借书");
		borrow.setFont(new Font(borrow.getFont().getFontName(), Font.BOLD, 20));
		borrow.setForeground(Color.WHITE);
		borrow.addMouseListener(this);
		
		//Load custom Return ImageButton
		rimg = il.LoadImage("return.jpg");
		o2 = new ImageIcon(rimg);
		return_bb = new JLabel(o2);
		
		//设置按钮文字，前景，背景色等相关属性
		colourpicker = returnColor("2");
		return_bb = setImageButtonStyle(return_bb, Color.decode(colourpicker));		
		return_bb.setText("还书");
		return_bb.setFont(new Font(borrow.getFont().getFontName(), Font.BOLD, 20));
		return_bb.setForeground(Color.WHITE);
		return_bb.addMouseListener(this);
		
		//Load custom Admin ImageButton
		aimg = il.LoadImage("admin.jpg");
		o3 = new ImageIcon(aimg);
		administration = new JLabel(o3);
		
		//设置按钮文字，前景，背景色等相关属性
		colourpicker = returnColor("3");
		administration = setImageButtonStyle(administration, Color.decode(colourpicker));
		administration.setText("管理员设置");
		administration.setFont(new Font(borrow.getFont().getFontName(), Font.BOLD, 20));
		administration.setForeground(Color.WHITE);
		administration.addMouseListener(this);
		
		
		//Load custom Search ImageButton
		simg = il.LoadImage("search.jpg");
		o4 = new ImageIcon(simg);
		search = new JLabel(o4);
		
		//设置按钮背景色等相关属性
		search = setImageButtonStyle(search, Color.decode(returnColor("4")));
		search.setText("搜索");
		search.setFont(new Font(borrow.getFont().getFontName(), Font.BOLD, 20));
		search.setForeground(Color.WHITE);
		search.addMouseListener(this);
		
		//Load custom Apply ImageButton
		apimg = il.LoadImage("apply.jpg");
		o5 = new ImageIcon(apimg);
		entrance = new JLabel(o5);
		
		//设置按钮背景色等相关属性
		entrance = setImageButtonStyle(entrance, Color.decode(returnColor("5")));
		entrance.setText("新生入馆申请");
		entrance.setFont(new Font(borrow.getFont().getFontName(), Font.BOLD, 20));
		entrance.setForeground(Color.WHITE);
		entrance.addMouseListener(this);
		
		operation.add(borrow);
		operation.add(return_bb);
		operation.add(administration);
		operation.add(search);
		operation.add(entrance);
		operation.addComponentListener(this);
		
		frame.add(operations, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
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
		case "3": return "#ACACAC";
		case "4": return "#ff8000";
		case "5": return "#551a8b";
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
    		/*为了防止照片失真，因此我们不会调整图书馆标志图片的大小
    		if(o1.getIconWidth() * 3 > frame.getWidth()) {
    			//We don't need to judge it every time. Just resize both of them.
    			borrow.setIcon(il.resizeIcon(o1, borrow));
    			return_bb.setIcon(il.resizeIcon(o2, return_bb));
    			administration.setIcon(il.resizeIcon(o3, administration));
    			search.setIcon(il.resizeIcon(o4, search));
    			entrance.setIcon(il.resizeIcon(o5, entrance));
    		}
    		*/
    		borrow.setIcon(new ImageIcon(il.resizeImg(bimg, 150, 240)));
    		return_bb.setIcon(new ImageIcon(il.resizeImg(rimg, 150, 240)));
    		administration.setIcon(new ImageIcon(il.resizeImg(aimg, 150, 240)));
    		search.setIcon(new ImageIcon(il.resizeImg(simg, 150, 240)));
    		entrance.setIcon(new ImageIcon(il.resizeImg(apimg, 150, 240)));
    }
    public void componentShown(ComponentEvent arg0) {
    	  		//Not our concern
      }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		//mouseClicked is buggy, we use mousePressed instead
		
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
		else if(arg0.getSource() == administration) {
			administration.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			administration.setBackground(Color.decode("#005cff"));
		}
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		if(arg0.getSource() == borrow) {
			/*
			 * Let's simplify our writing to maximize performance
			
			color = returnColor("1");
			borrow.setBackground(Color.decode(color));
			
			*/
			borrow.setBackground(Color.decode(returnColor("1")));
		}
		else if(arg0.getSource() == return_bb) {
			return_bb.setBackground(Color.decode(returnColor("2")));
		}
		else if(arg0.getSource() == administration) {
			administration.setBackground(Color.decode(returnColor("3")));
		}
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		/* The options pane have to be removed
		 * in any situations. So remove them at
		 * once.
		*/
		frame.remove(operations);
		frame.revalidate();
		frame.repaint();
		  if(arg0.getSource() == borrow) {
			  System.out.println("Borrow Mode Selected. " +
					  "Dropping Selection Menu");
			  UserFrame uf = new UserFrame();
			  uf.showFrame(frame);
			  
		  }
		  if (arg0.getSource() == return_bb) {
			  System.out.println("Return mode selected.");
			  Return r = new Return();
			  r.ReturnMode(frame);
		  }
		  if (arg0.getSource() == administration) {
			  System.out.println("Admin Mode Selected." +
					  "Now the options pane is dropped for password pane");
			  Management m = new Management();
			  m.ShowPWUI(frame);
		  }
		  if(arg0.getSource() == entrance) {
			  //new ShowRules(frame);
			  //Register page will be shown here directly for testing purpose. Normally student have to pass the test first.
			  NewStudRegister nsr = new NewStudRegister();
			  nsr.showRegisterMenu(frame);
		  }
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
}