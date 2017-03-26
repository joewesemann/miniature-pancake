package cs5530;

import java.sql.*;

public class Favorites {
	
        int id, th_id, user_id;
        String favorite_date;

	public Favorites(int th_id, int user_id)
	{
            this.th_id = th_id;
            this.user_id = user_id;
	}

    public void add(Statement stmt) {
    	String sql = "INSERT INTO favorites (`th_id`, `user_id`, `favorite_date`) VALUES (" + th_id + "," + user_id + ", NOW());";
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
