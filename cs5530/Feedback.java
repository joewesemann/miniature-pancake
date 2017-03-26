package cs5530;

import java.sql.*;

public class Feedback {
	
        int id, th_id, user_id, rating;
        String feedback_date, text;

	public Feedback(int housing_id, int rating, int user_id, String text)
	{
            this.th_id = housing_id;
            this.rating = rating;
            this.user_id = user_id;
            this.text = text;
	}

    public void create(Statement stmt) {
    	String sql = "INSERT INTO feedback (`text`, `rating`, `th_id`, `user_id`, `feedback_date`) VALUES ('" + text + "',"+ rating + "," + th_id + "," + user_id + ", NOW());";
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
