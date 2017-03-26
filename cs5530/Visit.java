package cs5530;

import java.sql.*;

public class Visit {
	
    int id, reserve_id;
    String from, to;

	public Visit(int reserve_id, String from, String to)
	{
            this.from = from;
            this.to = to;
            this.reserve_id = reserve_id;
	}

    public void add(Statement stmt) {
    	String sql = "INSERT INTO visit (`from`, `to`, `reserve_id`) VALUES ('" + from + " 00:00:00', '" + to + " 00:00:00'," + getReserveId() + ");";
        try{
        	stmt.executeUpdate(sql);
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
    
    public void setReserveId(int reserve_id)
    {
        this.reserve_id = reserve_id;
    }
    
    public int getReserveId()
    {
        return this.reserve_id;
    }
    
    public void setTo(String to)
    {
        this.to = to;
    }
    
    public String getTo()
    {
        return this.to;
    }
    
    public void setFrom(String from)
    {
        this.from = from;
    }
    
    public String getFrom()
    {
        return this.from;
    }
    
}
