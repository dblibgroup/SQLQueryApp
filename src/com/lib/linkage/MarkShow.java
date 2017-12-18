package com.lib.linkage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public class MarkShow implements MouseListener{
	JFrame frame;
	GridBagLayout gl = new GridBagLayout();
	ImageLoader il = new ImageLoader();
	BufferedImage bi;
	ImageIcon icon;
	boolean passed = false;
	JPanel mp = new JPanel(gl);
	GridBagConstraints gc = new GridBagConstraints();
	JLabel title, markshow, inst;
	JLabel next;
	String lower = "低于 60 分";
	String higher = "高于 60 分";
	String hint;
	String instruct;
	
	public void ShowYourMarks(float marks, JFrame frame) {
		this.frame = frame;
		hint = "你的分数:" + (int)marks;
		title = new JLabel();
		if(marks >= 60) {
			bi  = il.LoadImage("arrow_right.png");
			passed = true;
			hint += " 。恭喜你已经通过了入馆测试。";
			instruct = "请点击下一步填写个人信息";
			title.setText(higher);
		}
		else {
			bi  = il.LoadImage("arrow_right.png");
			passed = false;
			hint += " 。很遗憾，你没有通过入馆测试。";
			instruct = "请返回问题再试一次";
			title.setText(lower);
		}
		//Load the buffered image
		bi = il.resizeImg(bi, 32, 32);
		icon = new ImageIcon(bi);
		next = new JLabel(icon);
		
		markshow = new JLabel(hint);
		inst = new JLabel(instruct);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 20));
		
		//Setting contents Font size
		markshow.setFont(new Font(markshow.getFont().getFontName(), Font.PLAIN, 16));
		inst.setFont(new Font(inst.getFont().getFontName(), Font.PLAIN, 16));
		
		gl.setConstraints(title, gc);
		mp.add(title);
		gl.setConstraints(markshow, gc);
		mp.add(markshow);
		gl.setConstraints(inst, gc);
		mp.add(inst);
		gl.setConstraints(next, gc);
		mp.add(next);
		next.addMouseListener(this);
		
		frame.add(mp, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("Mouse Clicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println("Mouse Pressed");
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}