package cs5530;

import java.sql.*;

public class Rating {
	
        int id, feedback_id, user_id, rating;

	public Rating(int user_id, int feedback_id, int rating)
	{
            this.feedback_id = feedback_id;
            this.rating = rating;
            this.user_id = user_id;
	}

    public void create(Statement stmt) {
    	String sql = "INSERT INTO rating (`rating`, `user_id`, `feedback_id`) VALUES ("+ rating + "," + user_id + "," + feedback_id + ");";
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
