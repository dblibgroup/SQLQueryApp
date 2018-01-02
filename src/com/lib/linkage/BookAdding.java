package com.lib.linkage;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;

public class BookAdding implements ActionListener{
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gc = new GridBagConstraints();
	
	JPanel shelfpanel = new JPanel();
	JPanel detailspanel = new JPanel(gb);
	JPanel wrapper = new JPanel(new BorderLayout());
	
	int shelf_NO, shelf_order, bookNum;
	JLabel shelf = new JLabel("书架编号：");
	JLabel l_booknum = new JLabel("书籍编号: ");
	JTextField shelfno = new JTextField(10);
	JTextField shelforder = new JTextField(10);
	JTextField booknum = new JTextField(10);
	
	String ISBN, type, name, author, press, intro ;
	JLabel t_isbn, t_type, t_name, t_author, t_press, t_intro;
	JTextField f_isbn, f_type, f_name, f_author, f_press, f_intro;
	JCheckBox is_avail = new JCheckBox("上架书籍");
	JFrame frame;
	JLabel action = new JLabel();
	JButton enter = new JButton("确定");
	
	public void InitAdding(JFrame frame) {
	/* This function is being used to initialise the Book Adding
	 * interface. For help, please refer to our manual
	 */
		this.frame = frame;
		shelfpanel.add(shelf);
		shelfpanel.add(shelfno);
		shelfpanel.add(shelforder);
		shelfpanel.add(l_booknum);
		shelfpanel.add(booknum);
		
		//Initialises JLabel
		t_isbn = new JLabel("ISBN:");
		t_type = new JLabel("类别: ");
		t_name = new JLabel("书籍名称");
		t_author = new JLabel("作者名：");
		t_press = new JLabel("出版社");
		t_intro = new JLabel("简介: ");
		
		//Initialises JTextField
		f_isbn = new JTextField(10);
		f_type = new JTextField(10);
		f_name = new JTextField(10);
		f_author = new JTextField(10);
		f_press = new JTextField(10);
		f_intro = new JTextField(10);
		
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(f_isbn, gc);
		gb.setConstraints(f_type, gc);
		gb.setConstraints(f_name, gc);
		gb.setConstraints(f_author, gc);
		gb.setConstraints(f_press, gc);
		gb.setConstraints(f_intro, gc);
		gb.setConstraints(is_avail, gc);
		
		detailspanel.add(t_isbn);
		detailspanel.add(f_isbn);
		detailspanel.add(t_type);
		detailspanel.add(f_type);
		detailspanel.add(t_name);
		detailspanel.add(f_name);
		detailspanel.add(t_author);
		detailspanel.add(f_author);
		detailspanel.add(t_press);
		detailspanel.add(f_press);
		detailspanel.add(t_intro);
		detailspanel.add(f_intro);
		detailspanel.add(is_avail);
		detailspanel.add(shelfpanel);
		
		enter.addActionListener(this);
		
		wrapper.add(detailspanel, BorderLayout.CENTER);
		wrapper.add(enter, BorderLayout.SOUTH);
		
		frame.add(wrapper);
		
		frame.revalidate();
		frame.repaint();
		
	}
	
	public void HandlingQuery() {
		Object rs[][] = null;
		Object rss[][] = null;
		Object rsp[][] = null;
		Connection conn = DbConnection.DbConnect();
	    PreparedStatement pstmt = null;
	    
	    try {
	    	/*
	    	 *  Deprecated as they will not be shown in GUI
	    	 *  
	    		String ISBN = s.next();
			String type = s.next();
			String name = s.next();
			String author = s.next();
			String press = s.next();
			String intro = s.next();
		*/
	    	String ISBN = f_isbn.getText();
	    	String type = f_type.getText();
	    	String name = f_name.getText();
	    	String author = f_author.getText();
	    	String press = f_press.getText();
	    	String intro = f_intro.getText();
	    	if(ISBN == null || type == null || name == null || author == null || press == null)  
			{                                                  
				System.out.println("必须填写完整信息！");
			}else{
				//This is for testing purpose only.
				System.out.println("ISBN: "+ ISBN + " TYPE: " + type + " NAME: " + name + " AUTHOR: " + author 
								+ " PRESS: " + press + " INTRO: " + intro + ".");
			}
	    	
	    	try{
				String addSql = "INSERT INTO bookInfo(ISBN,type,name,author,press,intro) values(?,?,?,?,?,?)";
				Query usrQuery = new Query();
			    pstmt = null;
		 	    pstmt = (PreparedStatement) conn.prepareStatement(addSql,
		 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
		 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
			    pstmt.setString(1, ISBN);
			    pstmt.setString(2, type);
			    pstmt.setString(3, name);
			    pstmt.setString(4, author);
			    pstmt.setString(5, press);
			    pstmt.setString(6, intro);
				rs = usrQuery.PsExecQuery(pstmt);
			} catch (SQLException se) {
				se.printStackTrace();
			}
	    	if(rs == null || rs.length == 0 || (rs.length == 1 && rs[0].length == 0)){
				action.setText("书籍录入信息失败，请联系管理员解决！");
				//Some ERROR MESSAGE should be shown below.
			}else{
				if(!is_avail.isSelected()){
					action.setText("书籍成功添加，并未上架。");
				}else{
					
					//THen we just save the form into the database.
					try{
						Query usrQuery = new Query();
					    pstmt = null;
					    /*
					    int shelfNO = s.nextInt();
					    int shelfOrder = s.nextInt();
					    int bookNum = s.nextInt();
					    */
					    shelf_NO = Integer.parseInt(shelfno.getText());
					    shelf_order = Integer.parseInt(shelforder.getText());
					    bookNum = Integer.parseInt(booknum.getText());
					    
						String chkSql = "SELECT * FROM shelfInfo WHERE shelfNO = ? AND shelfOrder = ?";
				 	    pstmt = (PreparedStatement) conn.prepareStatement(chkSql,
				 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
				 	    		  ResultSet.CONCUR_READ_ONLY);
					    pstmt.setInt(1, shelf_NO);
				  	    pstmt.setInt(2, shelf_order);
					    rss = usrQuery.PsExecQuery(pstmt);
					    
						if(rss == null || rss.length == 0 || (rss.length == 1 && rss[0].length == 0)){
							System.out.println("不存在该书架！请重新输入！");
							//Some ERROR MESSAGE should be shown below.
							
						}else{
							try{
								usrQuery = new Query();
								pstmt = null;
								
								String istSql = "INSERT INTO shelf_book(shelfNO,shelfOrder,ISBN,num) values(?,?,?,?)";
						 	    pstmt = (PreparedStatement) conn.prepareStatement(istSql,
						 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
						 	    		  ResultSet.CONCUR_READ_ONLY);
							    pstmt.setInt(1, shelf_NO);
						  	    pstmt.setInt(2, shelf_order);
						  	    pstmt.setString(3, ISBN);
						  	    pstmt.setInt(4, bookNum);
						  	    
							    rsp = usrQuery.PsExecQuery(pstmt);
							    
							    if(rsp == null || rsp.length == 0 || (rsp.length == 1 && rsp[0].length == 0)){
									System.out.println("书籍上架失败，请联系管理员解决！");
									//Some ERROR MESSAGE should be shown below.
							    }else{
									System.out.println("书籍上架成功！");
							    }
								} catch (SQLException sqlee) {
									sqlee.printStackTrace();
								}//The end of adding books onto a shelf.
						}
					} catch (SQLException sqle){
						sqle.printStackTrace();
					}//The end of checking if the shelf exists.
				}
				}
	    	}catch(Exception e) {}
	    
	}
	public void actionPerformed(ActionEvent ae) {
		HandlingQuery();
	}
}