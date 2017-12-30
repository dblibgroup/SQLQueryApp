package com.lib.linkage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class Query{
	Object a[][];
	public void InitAdapter(String domain) {
	}
	/*
	 * This fucntion is newly added by Zorrow
	 * In order to user prepared statement to 
	 * avoid most sql injections
	 */
	public Object[][] PsExecQuery(PreparedStatement pstmt){
		int row, col;
		try {
			boolean isResultAvail = pstmt.execute();
			if(isResultAvail) {
				try {
					//Since it is not a UPDATE SQL statement here, so it can be redone.
					ResultSet r = pstmt.executeQuery();
					ResultSetMetaData rm = r.getMetaData();
					col = rm.getColumnCount();
					r.last();
					row = r.getRow();
					System.out.println("There are " + row + " row(s).");
					
					if(row != -1) {
						a = new Object[row][col];
					}else {
						a = new Object[0][0];
						return a;
					}			//IF row = -1 that means some error is occurred
					
					r.beforeFirst(); //Get the beforeFirst line so that when r.next() is executed the first line can be read
					int m = 0;
					while(r.next()) {
						for(int show = 1; show <= col; show ++) {
							a[m][show-1] = r.getString(show);
						}
						m++;
					}
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
				return a;	
			}else {
				int updateCount = pstmt.getUpdateCount();
				a = new Object[1][1];
				String object = new String();
				
				if (updateCount == 1) object = " Row.";
				else object = " Rows.";
				a[0][0] = "The statement affected " 
						+ updateCount + object;
				return a;
			}
		}catch(Exception sqe) {
			a = new Object[0][0];
			return a;
		}
	}
	
	public Object[][] ExecQuery(String sql, Connection conn) {
			int row, col;
			try {
				Statement statement = conn.createStatement();
				/* We are attempting to accept wide range of queries 
				 * other than select only.
				 * So we will delete this line....
				 * I mean, will judge first before executing
				 */
				//ResultSet r = statement.executeQuery(sql); 
				boolean isResultAvail = statement.execute(sql);
				if(isResultAvail) {
					try {
						ResultSet r = statement.executeQuery(sql);
						ResultSetMetaData rm = r.getMetaData();
						col = rm.getColumnCount();
						row = getRow(sql,conn);	   //Prepare the number of row to show
						
						/* The statement above must not be skipped,
						 * it is used to calculate the number of rows to be prepared in
						 * the 2d array.
						 */
						
						/* i.e. The table is in [row][col] dimensions
						 * 
						 * Then we will use FOR STATEMENT to control the contents
						 * inside the table
						 */
						if(row != -1) {
							a = new Object[row][col];
						}else {
							a = new Object[0][0];
							return a;
						}			//IF row = -1 that means some error is occurred
	
						//System.out.println("We have already prepared the size of JTable");
						//System.out.println("Row = " + row);
						//System.out.println("Col = " + col);
						
						//statement = conn.createStatement();
						//r = statement.executeQuery(sql);
						int m = 0;
						while(r.next()) {
							for(int show = 1; show <= col; show ++) {
								a[m][show-1] = r.getString(show);
							}
							m++;
						}
					}
					catch(SQLException se) {
						se.printStackTrace();
					}
					return a;	
				}else {
					int updateCount = statement.getUpdateCount();
					
					a = new Object[1][1];
					String object = new String();
					
					/* When forming a sentence, we always
					 * form a sentence with good grammar
					 */
					
					if (updateCount == 1) object = " Row.";
					else object = " Rows.";
					a[0][0] = "The statement affected " 
							+ updateCount + object;
					return a;
				}
			}catch(Exception sqe) {
				/*
				 * In client mode, exception will not be shown to users.
				 * Users have no idea on how to handle program mistakes
				 *
				boolean isw;
				OsDecideUtils ou = new OsDecideUtils();
				isw = ou.isWindows();
				//If and only if it is windows, we are getting the error sound
				if(isw) {
					Runnable alert = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand");
					alert.run();
				}
				JOptionPane.showMessageDialog(null, sqe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				*/
				
				a = new Object[0][0];
				return a;
				
			}
	}
	
	public int getRow(String sql, Connection conn) {
		/* We don't connect every time we retrieve
		 * data, we pass it as a parameter.
		 * It saves time.
		 */
		ResultSet rs;
		try {
			Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			boolean isResultAvail = s.execute(sql);
			if(isResultAvail){
				rs = s.executeQuery(sql);
				rs.last();
				int rows = rs.getRow();
				System.out.println("There are " + rows + " row(s).");
				//Since we are not connecting, we don't close it : conn.close();
				return rows;
			}else return 1;
		}catch (SQLException e) {
			e.printStackTrace();
			return -2;
		}
	}
}

