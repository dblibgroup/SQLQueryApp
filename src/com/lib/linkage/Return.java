package com.lib.linkage;

import javax.swing.*;
import java.awt.*;

public class Return{
	JFrame frame;
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gc = new GridBagConstraints();
	
	JPanel returnUI = new JPanel(gb);
	
	ImageLoader il = new ImageLoader();
	JLabel scan, hinting;
	
	public void ReturnMode(JFrame frame) {
		frame = this.frame;
		gc.anchor = GridBagConstraints.REMAINDER;
		StringBuffer hint = new StringBuffer("请将书籍放入还书箱");
		String reminder1 = "记住确保书上不要夹有书签等个人物品哦";
		hinting = new JLabel("<html>"+ hint + "<br>" + reminder1);
		gb.setConstraints(hinting, gc);
		returnUI.add(hinting);
		frame.add(returnUI, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}
	
}