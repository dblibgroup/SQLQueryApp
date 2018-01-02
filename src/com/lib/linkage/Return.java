package com.lib.linkage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Return implements ActionListener{
	JFrame frame;
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gc = new GridBagConstraints();
	
	JPanel returning;
	JPanel inputui = new JPanel();
	
	ImageLoader il = new ImageLoader();
	JLabel scan;
	JButton enter;
	JLabel b_hint = new JLabel("书籍编号: ");
	JTextField barcode = new JTextField(20);
	
	public void ReturnMode(JFrame frame) {
		enter = new JButton("还书");
		returning = new JPanel(gb);
		this.frame = frame;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.CENTER;
		StringBuffer hint = new StringBuffer("请将书籍放入还书口");
		String reminder1 = "记得确保书上不要夹有书签等个人物品哦";
		JLabel hinting = new JLabel("<html>"+ hint + "<br>" + reminder1+"</html>");
		hinting.setFont(new Font(hinting.getFont().getFontName(), Font.PLAIN, 20));
		
		scan = new JLabel(new ImageIcon(il.LoadImage("scan.jpg")));
		
		//Init inputUI
		inputui.add(b_hint);
		inputui.add(barcode);
		inputui.add(enter);
		
		enter.addActionListener(this);
		
		gb.setConstraints(hinting, gc);
		gb.setConstraints(scan, gc);
		gb.setConstraints(inputui, gc);
		
		returning.add(hinting);
		returning.add(scan);
		returning.add(inputui);
		
		frame.add(returning, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		//String barcode_t = barcode.getText();
		//String sql = null;
		//delete the record from Student Table
		//Query uq = new Query();
		
		
	}
	
}