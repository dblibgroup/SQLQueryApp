package com.lib.linkage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Return implements ActionListener{
	JFrame frame;
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gc = new GridBagConstraints();
	
	JPanel returning;
	JPanel borrowing;
	JPanel inputui = new JPanel();
	
	ImageLoader il = new ImageLoader();
	JLabel scan;
	JButton enter;
	JLabel b_hint = new JLabel("书籍编号: ");
	JTextField barcode = new JTextField(20);
	JLabel action = new JLabel();
	
	int Brw_Rtn = 0;     //"1" indicating the borrowMode and "2" indicating the returnMode
	public void ReturnMode(JFrame frame) {
		Brw_Rtn = 2;
		
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

		returning.add(action);
		action.setForeground(Color.RED);
		
		frame.add(returning, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}
	
	public void BorrowMode(JFrame frame) {
		Brw_Rtn = 1;
		
		enter = new JButton("借书");
		borrowing = new JPanel(gb);
		this.frame = frame;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.CENTER;
		String hint = "请扫描书籍二维码";
		JLabel hinting = new JLabel(hint);
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
		
		borrowing.add(hinting);
		borrowing.add(scan);
		borrowing.add(inputui);

		borrowing.add(action);
		action.setForeground(Color.RED);
		
		frame.add(borrowing, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		//set campus = 1 as default
		String ISBN = barcode.getText();
		String stuID = "2015051471";
		System.out.println("stuID: " + stuID + " ISBN: " + ISBN);
		
		Object rs[][] = null;
		Object rsi[][] = null;
		Object rsd[][] = null;
		Object rsu[][] = null;
		Connection conn = DbConnection.DbConnect();
	    PreparedStatement pstmt = null;

  	    Date date = new Date();
  	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  	    String brwDate = formatter.format(date);
  	    
  	    Calendar c = Calendar.getInstance();
  	    c.setTime(date);
  	    c.add(Calendar.MONTH, 1);
  	    Date monthLater = c.getTime();
  	    String rtnDate = formatter.format(monthLater);

	    if(Brw_Rtn == 0){
	    	System.out.println("Illegal Access！");
	    }else if(Brw_Rtn == 1){
	    	System.out.println("Borrow Mode Start:");
	    	
	    	//Borrowing Mode
	    	try{
				String schSql = "SELECT * FROM shelf_book WHERE ISBN = ?";
				Query usrQuery = new Query();
			    pstmt = null;
		 	    pstmt = (PreparedStatement) conn.prepareStatement(schSql,
		 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
		 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
			    pstmt.setString(1, ISBN);
				rs = usrQuery.PsExecQuery(pstmt);

				//Check if it is a legal input
				if(rs == null || rs.length == 0 || (rs.length == 1 && rs[0].length == 0)){
					action.setText("该书未上架！");
					//Some ERROR MESSAGE should be shown below.
				}else{
					//Adding borrowing records
					int shelfNO = Integer.parseInt((String)rs[0][0]);

					usrQuery = new Query();
					pstmt = null;
					String updSql = "UPDATE shelf_book set num = num - 1 WHERE shelfNO = ? AND ISBN = ? ";
			 	    pstmt = (PreparedStatement) conn.prepareStatement(updSql,
			 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
			 	    		  ResultSet.CONCUR_READ_ONLY);
				    pstmt.setInt(1, shelfNO);
			  	    pstmt.setString(2, ISBN);
				    rsu = usrQuery.PsExecQuery(pstmt);
				    
					usrQuery = new Query();
					pstmt = null;
					String istSql = "INSERT INTO stu_book(stuID,ISBN,shelfNO,campus,brwDate,rtnDate) values(?,?,?,?,?,?)";
			 	    pstmt = (PreparedStatement) conn.prepareStatement(istSql,
			 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
			 	    		  ResultSet.CONCUR_READ_ONLY);
				    pstmt.setString(1, stuID);
			  	    pstmt.setString(2, ISBN);
			  	    pstmt.setInt(3, shelfNO);     
			  	    pstmt.setInt(4, 1);     //Currently the south campus is default
			  	    pstmt.setString(5, brwDate);
			  	    pstmt.setString(6, rtnDate);
				    rsi = usrQuery.PsExecQuery(pstmt);
				    
				    if((rsi == null || rsi.length == 0 || (rsi.length == 1 && rsi[0].length == 0)) ||
				    		(rsu == null || rsu.length == 0 || (rsu.length == 1 && rsu[0].length == 0))){
				    	action.setText("书籍借阅失败，请联系管理员解决！");
						//Some ERROR MESSAGE should be shown below.
				    }else{
						//System.out.println("借书成功！您的还书日期为：" + rtnDate);
				    	JOptionPane.showMessageDialog(null, rtnDate, "您的还书日期是 ", JOptionPane.INFORMATION_MESSAGE); 
						ShowMenu sm = new ShowMenu();
						frame.remove(borrowing);
						frame.revalidate();
						frame.repaint();
						sm.OperationTabInit(frame);
				    }
				}
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    }else if(Brw_Rtn == 2){
	    	System.out.println("Return Mode Start:");

		    //Returning Mode
			try{
				String schSql = "SELECT * FROM stu_book WHERE ISBN = ? AND stuID = ?";
				Query usrQuery = new Query();
			    pstmt = null;
		 	    pstmt = (PreparedStatement) conn.prepareStatement(schSql,
		 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
		 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
			    pstmt.setString(1, ISBN);
			    pstmt.setString(2, stuID);
				rs = usrQuery.PsExecQuery(pstmt);
				
				//Delete the borrowing record
				if(rs == null || rs.length == 0 || (rs.length == 1 && rs[0].length == 0)){
					action.setText("没有该借书记录或ISBN编号输入错误！");
					//Some ERROR MESSAGE should be shown below.
				}else{
					int shelfNO = Integer.parseInt((String) rs[0][2]);
					System.out.println("书架号为：" + shelfNO);
					String delSql = "DELETE FROM stu_book WHERE ISBN = ? AND stuID = ?";
					usrQuery = new Query();
				    pstmt = null;
			 	    pstmt = (PreparedStatement) conn.prepareStatement(delSql,
			 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
			 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
				    pstmt.setString(1, ISBN);
				    pstmt.setString(2, stuID);
					rsd = usrQuery.PsExecQuery(pstmt);
					
					//Adding books to the bookshelf
					if(rsd == null || rsd.length == 0 || (rsd.length == 1 && rsd[0].length == 0)){
						action.setText("还书过程出错，请联系管理员！");
						//Some ERROR MESSAGE should be shown below.
						//If any error occurs here, that must be the unexpected error from the database.
					}else{
						//Add info to the shelf_book indicating that there'll be a book to that shelf
						String uptSql = "UPDATE shelf_book set num = num + 1 WHERE shelfNO = ? AND ISBN = ? ";
						usrQuery = new Query();
					    pstmt = null;
				 	    pstmt = (PreparedStatement) conn.prepareStatement(uptSql,
				 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
				 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
					    pstmt.setInt(1, shelfNO);
					    pstmt.setString(2, ISBN);
					    rsu = usrQuery.PsExecQuery(pstmt);

						if(rsu == null || rsu.length == 0 || (rsu.length == 1 && rsu[0].length == 0)){
							System.out.println("还书过程出错，请联系管理员！");
							//Some ERROR MESSAGE should be shown below.
						}else{
					    	JOptionPane.showMessageDialog(null, "还书成功！", "提示信息", JOptionPane.INFORMATION_MESSAGE); 
							ShowMenu sm = new ShowMenu();
							frame.remove(returning);
							frame.revalidate();
							frame.repaint();
							sm.OperationTabInit(frame);
						}
					}
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
	    }
	}
	
}