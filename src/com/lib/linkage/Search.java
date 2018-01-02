package com.lib.linkage;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

import java.awt.event.*;

public class Search implements ActionListener {

	JFrame frame;
	JTextField SearchText = new JTextField();
	JLabel inputhint = new JLabel("请输入要查询的书籍名称:");
	JPanel searchPanel, resultPanel, panelgroup;
	JButton search = new JButton("搜索");
	JLabel t_name = new JLabel("书名");
	JLabel t_isbn = new JLabel("ISBN");
	JLabel t_author = new JLabel("作者");
	JLabel t_publisher = new JLabel("出版社");
	JLabel t_intro = new JLabel("简介");
	JLabel t_picname = new JLabel("图片编号");
	JLabel t_noresult = new JLabel("无书籍查询结果");
	
	
	
	public void startSearchFrame(JFrame frame) {
		//Init ALL JPanel
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		searchPanel = new JPanel();
		resultPanel = new JPanel(gb);
		panelgroup = new JPanel();
		
		this.frame = frame;
		SearchText.setColumns(20);
		search.addActionListener(this);
		
		searchPanel.add(inputhint);
		searchPanel.add(SearchText);
		searchPanel.add(search);
		panelgroup.setLayout(new BorderLayout());
		panelgroup.add(searchPanel,BorderLayout.NORTH);
		panelgroup.add(resultPanel, BorderLayout.SOUTH);
		frame.add(panelgroup, BorderLayout.CENTER);
		
		frame.revalidate();
		frame.repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String input = SearchText.getText();
		Object rs[][] = null;
		Object rsp[][] = null;
		Object rsi[][] = null;
		int selection = 0;
		String sql = "";
		String keyword = "%" + input + "%";  //The keywords to be executed
		PreparedStatement pstmt = null;
		try {
			Query uq = new Query();
			Connection conn = DbConnection.DbConnect();
			switch(selection){
			case 0:
				sql = "SELECT * FROM bookInfo WHERE type like ?";
				break;
			case 1:
				sql = "SELECT * FROM bookInfo WHERE name like ?";
				break;
			case 2:
				sql = "SELECT * FROM bookInfo WHERE author like ?";
				break;
			case 3:
				sql = "SELECT * FROM bookInfo WHERE press like ?";
				break;
			}
			pstmt = (PreparedStatement)conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, keyword);
			rs = uq.PsExecQuery(pstmt);
			if(rs == null || rs.length == 0 || 
				(rs.length == 1 && rs[0].length == 0)) {
				resultPanel.add(t_noresult);
			}else {
				//Do Nothing
			}
		}catch(SQLException se) {}
 		
	}
}
