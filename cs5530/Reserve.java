package cs5530;

import java.sql.*;

public class Reserve {
	
        String from;
        String to;
        int price_per_night;
        int user_id; 
        int th_id;
        int number_in_party;
        int id;

	public Reserve(String from, String to, int price_per_night, int user_id, int th_id, int number_in_party)
	{
		this.from = from;
		this.to = to;
		this.price_per_night = price_per_night;
		this.user_id = user_id;
		this.th_id = th_id;
		this.number_in_party = number_in_party;
	}

    public void create(Statement stmt) {
    	String sql = "INSERT INTO reserve (`from`, `to`, `price_per_night`, `date_reserved`, `user_id`, `th_id`, `number_in_party`) VALUES ('" + from + " 00:00:00', '" + to + " 00:00:00', " + price_per_night + ", NOW(), " +  user_id + ", " + th_id + "," + number_in_party + ");";
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

}
