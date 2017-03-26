package cs5530;

import java.sql.*;

public class Trust {
	
        int id, user_id, trusted_id, trusted;

	public Trust(int user_id, int trusted_id)
	{
            this.trusted_id = trusted_id;
            this.trusted = 1;
            this.user_id = user_id;
	}

    public void create(Statement stmt) {
    	String sql = "INSERT INTO trust (`trusted`, `user_1`, `user_2`) VALUES ("+ trusted + "," + user_id + "," + trusted_id + ");";
        try{
        	stmt.executeUpdate(sql);
            }
        catch(Exception e)
        {
        	System.out.println("cannot execute the query");
            e.printStackTrace();
        }
    }



}
