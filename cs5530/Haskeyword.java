package cs5530;

import java.sql.*;

public class Haskeyword {
	
    int id, th_id, keyword_id;

	public Haskeyword(int th_id, int keyword_id)
	{
            this.th_id = th_id;
            this.keyword_id = keyword_id;
	}

    public void add(Statement stmt) {
    	String sql = "INSERT INTO has_keywords (`th_id`, `keyword_id`) VALUES (" + th_id + "," + keyword_id + ");";
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
