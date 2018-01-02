package com.lib.linkage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ManagementStudio implements ActionListener{
	JFrame frame;
	JPanel operation = new JPanel(new GridLayout(0,1));
	
	JButton exit = new JButton("退出系统");
	JButton regbooks = new JButton("新到书籍注册");
	JButton deletebooks = new JButton("书籍注销");
	JButton revert = new JButton("系统复位");
	
	
	public void showFrame(JFrame frame) {
		this.frame = frame;
		operation.add(regbooks);
		operation.add(deletebooks);
		operation.add(revert);
		operation.add(exit);
		
		regbooks.addActionListener(this);
		deletebooks.addActionListener(this);
		revert.addActionListener(this);
		exit.addActionListener(this);
		frame.add(operation);
		frame.revalidate();
		frame.repaint();
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		frame.remove(operation);
		frame.revalidate();
		frame.repaint();
		if(ae.getSource() == regbooks) {
			BookAdding ba = new BookAdding();
			ba.InitAdding(frame);
			
		}
		if(ae.getSource() == revert) {
			ShowMenu sm = new ShowMenu();

			sm.OperationTabInit(frame);
		}
		if(ae.getSource() == exit) {
			frame.dispose();
		}
	}
}