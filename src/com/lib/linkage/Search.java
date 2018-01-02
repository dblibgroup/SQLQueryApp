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
	JLabel t_type = new JLabel("类别");
	JLabel t_isbn = new JLabel("ISBN");
	JLabel t_author = new JLabel("作者");
	JLabel t_publisher = new JLabel("出版社");
	JLabel t_intro = new JLabel("简介");
	JLabel t_picname = new JLabel("图片编号");
	JLabel t_noresult = new JLabel("无书籍查询结果");
	
	//Init ComoboBox
	String[] type = {"类别", "书名", "作者", "出版社"};
	JComboBox<String> item = new JComboBox<String>(type);
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	
	public void startSearchFrame(JFrame frame) {
		//Init ALL JPanel
		searchPanel = new JPanel();
		resultPanel = new JPanel(gb);
		panelgroup = new JPanel();
		
		
		
		this.frame = frame;
		SearchText.setColumns(20);
		search.addActionListener(this);
		
		searchPanel.add(item);
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
		int selection = item.getSelectedIndex();  //Select the query type according to user's selection
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
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gb.setConstraints(t_picname, gbc);
				resultPanel.add(t_isbn);
				resultPanel.add(t_type);
				resultPanel.add(t_name);
				resultPanel.add(t_author);
				resultPanel.add(t_publisher);
				resultPanel.add(t_intro);
				resultPanel.add(t_picname);
				
				for(int i = 0; i < rs.length; i++){
					for(int j = 0; j < rs[i].length; j++){
						if(j == 5)                          //Any introduction more than 20 chars will be cut
						{
							String substring = (String)rs[i][j];
							if(substring.length() >= 15)
							{
								substring = substring.substring(0, 14);
								substring += "...";
								//rs[i][j] = substring;     //We will show the intro together with the amount
								
							}
							
							String posSql = "SELECT * FROM shelf_book WHERE ISBN = ?";
							pstmt = null;
							rsp = null;
						 	pstmt = (PreparedStatement) conn.prepareStatement(posSql,
						 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
						 	    		  ResultSet.CONCUR_READ_ONLY);      
						 	pstmt.setString(1, (String)rs[i][0]);                  //Find out all the positions according to the ISBN
						 	rsp = uq.PsExecQuery(pstmt);
							if(rsp == null || rsp.length == 0 || (rsp.length == 1 && rsp[0].length == 0)){
								//System.out.println("抱歉，该书籍尚未上架！");
								String notavail = "该书籍未上架或全部借出";
								substring += "\n" + notavail;
									
							}else{
								for(int k = 0; k < rsp.length; k++){
									/*System.out.println("书架号：" + rsp[j][0] + "." + rsp[j][1] + " 上，ISBN为："+ rsp[j][2] + 
												" 的书数量为：" + rsp[j][3] + " 本"); */
									String shelfnum = "书架号: " + rsp[j][0] + "." + rsp[j][1];
									String amount = "馆藏数量: " + rsp[j][3];
									substring += "\n"+shelfnum +"\n" + amount;
									rs[i][j] = substring;
								}
							}
						}
						//System.out.print(rs[i][j] + " ");
						
						if(j < rs[i].length - 1) resultPanel.add(new JLabel(rs[i][j].toString()));
						else {
							JLabel llabel = new JLabel(rs[i][j].toString());
							gb.setConstraints(llabel, gbc);
							resultPanel.add(llabel);	
						}
						
					}
					
				}
				
			}
		}catch(SQLException se) {}
 		
	}
}

