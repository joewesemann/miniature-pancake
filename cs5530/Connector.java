package cs5530;

import java.sql.*;

public class Connector {
	public Connection con;
	public Statement stmt;
	public Connector() throws Exception {
		try{
			System.out.println("Connecting...");
		 	String userName = "5530u01";
	   		String password = "v9ebodl2";
	        	String url = "jdbc:mysql://georgia.eng.utah.edu/5530db01";
		        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        		con = DriverManager.getConnection (url, userName, password);

			stmt = con.createStatement();
        } catch(Exception e) {
			System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
            		System.err.println(e.getMessage());
			throw(e);
		}
	}
	
	public void closeConnection() throws Exception{
		con.close();
	}
}
