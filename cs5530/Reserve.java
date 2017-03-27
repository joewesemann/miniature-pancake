package cs5530;

import java.sql.*;
import java.util.Date;
import java.text.*;

public class Reserve {
	
        String from;
        String to;
        int price_per_night;
        int user_id; 
        int th_id;
        int number_in_party;
        int id;
        
    public Reserve(){}

	public Reserve(String from, String to, int price_per_night, int user_id, int th_id, int number_in_party)
	{
		this.from = from;
		this.to = to;
		this.price_per_night = price_per_night;
		this.user_id = user_id;
		this.th_id = th_id;
		this.number_in_party = number_in_party;
	}
	
	//verify if user id is a match
	public boolean verifyID(int user_id, int id, Statement stmt)
	{
	    String sql = "SELECT user_id FROM reserve WHERE id = " + id + ";";
	    try{
	        ResultSet result;
            result = stmt.executeQuery(sql);
            
            while(result.next()){
                if(result.getInt("user_id") == user_id) return true;
                else return false;
            }
	    }
	    catch(Exception e)
	    {
	        System.out.println("cannot execute query");
	        e.printStackTrace();
	        return false;
	    }
	    return false;
	}
	
	// Verify if a date is within a reservation time
	public boolean verifyDate(String date, int id, Statement stmt)
	{
	    java.util.Date from  = new java.util.Date();
	    java.util.Date to = new java.util.Date();
	    String sqlto = "SELECT `to` FROM reserve WHERE id = " + id;
	    String sqlfrom = "SELECT `from` FROM reserve WHERE id = " + id;
	    try{
	        ResultSet result;
            result = stmt.executeQuery(sqlfrom);
            
            while(result.next()){
                from = result.getDate("from");
            }
            result = stmt.executeQuery(sqlto);
            while(result.next()){
                to = result.getDate("to");
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
            java.util.Date d = df.parse(date);
            return d.compareTo(from) >= 0 && d.compareTo(to) <= 0;
            
	    }
	    catch(Exception e)
	    {
	        System.out.println("cannot execute query");
	        e.printStackTrace();
	        return false;
	    }
	}

    public void create(Statement stmt) {
    	String sql = "INSERT INTO reserve (`from`, `to`, `price_per_night`, `date_reserved`, `user_id`, `th_id`, `number_in_party`) VALUES ('" + from + "', '" + to + "', " + price_per_night + ", NOW(), " +  user_id + ", " + th_id + "," + number_in_party + ");";
        try{
        	stmt.executeUpdate(sql);
        
            int lastid;
            ResultSet result;
            result = stmt.executeQuery("select last_insert_id() from keyword;");
            while(result.next()){
                lastid = result.getInt("last_insert_id()");
                setId(lastid);
            }
        }
        catch(Exception e)
        {
        	System.out.println("cannot execute the query");
            e.printStackTrace();
        }
    }

    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return this.id;
    }

    public int getHid()
    {
        return this.th_id;
    }
}
