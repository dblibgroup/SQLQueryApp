package com.lib.linkage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class EntryQuestion implements ActionListener{
	JFrame frame;
	GridBagLayout gl = new GridBagLayout();
	JPanel Wrapper = new JPanel(new BorderLayout());
	//Consists of 4 choices
	JRadioButton a, b, c, d;
	ButtonGroup choices = new ButtonGroup();
	JPanel QA = new JPanel(gl);
	File f = new File("res/questions.txt");
	JLabel hintlabel, questionlabel, countlabel;
	int nowq , allquestions, correctquestions;
	float marks;
	FileReader fr;
	BufferedReader br;
	String string = null;
	String hint, qhint;
	String caseing;    //This is for storing the choices that user chooses
	String answer;     //This is for storing answers

	JButton nextquestion = new JButton("下一题");
	JButton submit = new JButton("提交");
	
	public void StartQuestion(JFrame frame){
		nowq = 1;
		this.frame = frame;
		CountQuestions();
		hint = "本测试共 "+ allquestions + " 题，需要有百分之 60 的答案正确"+
		"才能申请读者借还书权限，加油吧！";
		hintlabel = new JLabel(hint);
		countlabel = new JLabel();
		questionlabel = new JLabel();
		a = new JRadioButton();
		b = new JRadioButton();
		c = new JRadioButton();
		d = new JRadioButton();
		a.setActionCommand("A");
		b.setActionCommand("B");
		c.setActionCommand("C");
		d.setActionCommand("D");
		choices.add(a);
		choices.add(b);
		choices.add(c);
		choices.add(d);
		a.addActionListener(this);
		b.addActionListener(this);
		c.addActionListener(this);
		d.addActionListener(this);
		
		hintlabel.setFont(new Font(hintlabel.getFont().getFontName()
				, Font.PLAIN, 18));
		countlabel.setFont(new Font(countlabel.getFont().getFontName()
				, Font.BOLD, 20));
		
		
		GridBagConstraints gc = new GridBagConstraints();
		GridBagConstraints ans = new GridBagConstraints();
		gc.gridwidth = GridBagConstraints.REMAINDER;
		ans.anchor = GridBagConstraints.WEST;
		gl.setConstraints(countlabel,gc);
		QA.add(countlabel);
		gl.setConstraints(questionlabel, gc);
		QA.add(questionlabel);
		gl.setConstraints(a, ans);
		QA.add(a);
		ans.gridwidth = GridBagConstraints.REMAINDER;
		gl.setConstraints(b, ans);
		QA.add(b);
		//Reset the gridwidth
		ans.gridwidth = 1;
		gl.setConstraints(c, ans);
		QA.add(c);
		ans.gridwidth = GridBagConstraints.REMAINDER;
		gl.setConstraints(d, ans);
		QA.add(d);
		
		ShowQuestions(nowq);
		nextquestion.setEnabled(false);
		nextquestion.addActionListener(this);
		submit.addActionListener(this);
		Wrapper.add(hintlabel, BorderLayout.NORTH);
		Wrapper.add(QA,BorderLayout.CENTER);
		frame.add(Wrapper, BorderLayout.CENTER);
		frame.add(nextquestion, BorderLayout.SOUTH);
		frame.revalidate();
		frame.repaint();
		
	}
	public void CountQuestions() {
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			while((string = br.readLine())!= null) 
				if(string.startsWith(";")) {
					allquestions ++;
				}
			
		}catch(IOException e) {e.printStackTrace();}
	}
	public void ShowQuestions(int numbers) {
		nextquestion.setEnabled(false);
		submit.setEnabled(false);
		countlabel.setText(numbers + "/" + allquestions);
		String numtostr = ""+ numbers;
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			string = br.readLine();
			while(!string.startsWith(numtostr)) string = br.readLine();
			questionlabel.setText(string);
			while((string = br.readLine()) != null && !string.startsWith(";")) {
					string = string.replaceAll("- ", "");
					caseing = string.substring(0, 1);
					switch (caseing) {
					case "A": a.setText(string); break;
					case "B": b.setText(string); break;
					case "C": c.setText(string); break;
					case "D": d.setText(string); break;
					}	
			}
			answer = string.replaceAll(";", "");
			System.out.println("The answer is " + answer);	
		}catch(IOException ee) {}
		if(nowq == allquestions) {
			frame.remove(nextquestion);
			frame.add(submit, BorderLayout.SOUTH);
			/* If users have not been choosing a value on 
			 * last question, the button will not be shown
			*/
			submit.setEnabled(false);
			frame.repaint();
			frame.revalidate();
		}
	}
	
	public String getSelectedActionName(ButtonGroup gp) {
		for(Enumeration<AbstractButton> btn = gp.getElements();
				btn.hasMoreElements();) {
			AbstractButton bttn = btn.nextElement();
			if(bttn.isSelected()) {
				return bttn.getActionCommand();
			}
		}
		return null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == a || e.getSource() == b || e.getSource() == c
				|| e.getSource() == d) {
			nextquestion.setEnabled(true);
			submit.setEnabled(true);
		}
		if(e.getSource() == nextquestion) {
			nowq ++;
			String selection = getSelectedActionName(choices);
			if(selection.equals(answer)) {
				correctquestions ++;
				marks = ((float)correctquestions / (float)allquestions) * 100;
				System.out.println("The answer is correct. Your marks: "+
						marks + " %.");
			}
			choices.clearSelection();
			ShowQuestions(nowq);
		}
		if(e.getSource() == submit) {
			//According to user mark, go to different pages
			frame.remove(Wrapper);
			frame.remove(nextquestion);
			frame.remove(submit);
			frame.repaint();
			frame.revalidate();
			MarkShow ms = new MarkShow();
			ms.ShowYourMarks(marks, frame);
		}
		
	}
	
}