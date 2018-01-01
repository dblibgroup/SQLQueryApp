package com.lib.linkage;

import java.awt.*;
import javax.swing.*;

public class Search {

	JFrame frame;
	JTextField SearchText = new JTextField();
	JLabel inputhint = new JLabel("请输入要查询的书籍名称:");
	JPanel searchPanel, resultPanel, panelgroup;
	
	
	public void startSearchFrame(JFrame frame) {
		//Init ALL JPanel
		searchPanel = new JPanel();
		resultPanel = new JPanel();
		panelgroup = new JPanel();
		
		this.frame = frame;
		SearchText.setColumns(20);
		
		searchPanel.add(inputhint);
		searchPanel.add(SearchText);
		panelgroup.setLayout(new BorderLayout());
		panelgroup.add(searchPanel,BorderLayout.NORTH);
		frame.add(panelgroup, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}
}
