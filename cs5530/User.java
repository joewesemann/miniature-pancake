package cs5530;

import java.sql.*;

public class User {
	
	String name;
	String username;
	int type;
	String password;
	String phone_number;
	String address;
	
	public User(){}

	public User(String name, String username, int type, String password, String phone_number, String address)
	{
		this.name = name;
		this.username = username;
		this.type = type;
		this.password = password;
		this.phone_number = phone_number;
		this.address = address;
	}

    public void insertUser(Statement stmt) {
    	String sql = "INSERT INTO user (name, username, type, password, phone_number, address) VALUES ('" + name + "', '" + username + "', " + type + ", '" + password + "', '" + phone_number + "', '" + address + "');";
        System.out.println("executing "+sql);
        try{
        	stmt.executeUpdate(sql);
            }
        catch(Exception e)
        {
        	System.out.println("cannot execute the query");
            e.printStackTrace();
        }
    }




// todo: change to getUser.... 		
	public void populateUser(String username, Statement stmt) throws Exception
	{
		String query;
		String resultstr="";
		ResultSet results; 
		query="Select * from user where username ='"+username+"'";
		try{
			results = stmt.executeQuery(query);
	       } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
	       }
		System.out.println("User.getUser Query = "+query+"\n");
		while (results.next()){
			setName(results.getString("name"));
			System.out.println(getName());
			setUsername(results.getString("username"));
			setType(results.getInt("type"));
			setPassword(results.getString("password"));
			setPhoneNumber(results.getString("phone_number"));
			setAddress(results.getString("address"));
		}
	}
	
	//Getters and Setters
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return this.type;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPhoneNumber(String phone_number)
	{
		this.phone_number = phone_number;
	}
	
	public String getPhoneNumber(){
		return this.phone_number;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return this.address;
	}
}
