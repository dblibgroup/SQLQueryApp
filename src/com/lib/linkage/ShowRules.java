package com.lib.linkage;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;

/* This is a page where rules are being showned 
 * to new library users who attempt to register
 * borrow and return rights. 
 * 
 * The file is in Rules.txt where the content include
 * the rules that's shown on the JTextPane
 */

public class ShowRules implements ActionListener{
	JFrame frame;
	JTextPane rule_area = new JTextPane();
	JScrollPane scroll = new JScrollPane(rule_area);
	File file = new File("res/Rules.txt");
	String rules, breader;
	JButton Enter = new JButton("我已确定阅读完成，继续答题");
	
	ShowRules(JFrame frame){
		rule_area.setEditable(false);
		rule_area.setFont(new Font(rule_area.getFont().getFontName(), Font.PLAIN, 18));
		Enter.addActionListener(this);
		this.frame = frame;
		rules = "在回答入馆题目前，请先阅读以下须知：\n\n";
		try {
			FileReader r = new FileReader(file);
			BufferedReader bf = new BufferedReader(r);
			
			while((breader = bf.readLine()) != null) {
				rules = rules + breader + "\n";
				rule_area.setText(rules);
			}
			bf.close();
				
		}catch(IOException e) {
			e.printStackTrace();
		}
		frame.add(scroll, BorderLayout.CENTER);
		frame.add(Enter, BorderLayout.SOUTH);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Agreed");
		frame.remove(scroll);
		frame.remove(Enter);
		frame.revalidate();
		frame.repaint();
		EntryQuestion eq = new EntryQuestion();
		eq.StartQuestion(frame);
		
	}
}