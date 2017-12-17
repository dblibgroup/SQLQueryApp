package com.lib.linkage;

import javax.swing.*;
import java.awt.*;

public class MarkShow{
	JFrame frame;
	JPanel mp = new JPanel(new GridLayout(0,1));
	JLabel title, markshow, inst;
	String lower = "低于 60 分";
	String higher = "高于 60 分";
	String hint;
	String instruct;
	
	public void ShowYourMarks(float marks, JFrame frame) {
		this.frame = frame;
		hint = "你的分数:" + marks;
		title = new JLabel();
		if(marks >= 60) {
			hint += " . 恭喜你已经通过了入馆测试。";
			instruct = "请点击下一步填写个人信息";
			title.setText(higher);
		}
		else {
			hint += ". 很遗憾，你没有通过入馆测试";
			instruct = "请返回问题再试一次";
			title.setText(lower);
		}
		mp.add(title);
		frame.add(mp, BorderLayout.CENTER);
		
		
	}
	
}