package com.lib.linkage;

import java.sql.*;

public class DbConnection{
	public static Connection DbConnect(){
	    // Create a variable for the connection string.  
	    String connectionUrl = "jdbc:sqlserver://localhost:1433;" +  "databaseName=Library;user=sb;password=39541966";  
	
	    // Declare the JDBC objects.  
	    Connection conn = null;  
	    try{
	        // Establish the connection.  
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
	        conn = DriverManager.getConnection(connectionUrl);  
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
}