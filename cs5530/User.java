package cs5530;

import java.sql.*;

public class User {
	
    int id;
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
	

	//inputs a new user into the database;
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
        try{
            int lastid;
            ResultSet result;
            result = stmt.executeQuery("select last_insert_id() from user;");
            while(result.next()){
            	lastid = result.getInt("last_insert_id()");
            	setId(lastid);	
            }
        }
        catch(Exception e)
        {
            System.out.println("Problem obtaining ID");
            e.printStackTrace();
        }
    }



	//populates user object variables from the table.	
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
            setId(results.getInt("id"));
			setName(results.getString("name"));
			setUsername(results.getString("username"));
			setType(results.getInt("type"));
			setPassword(results.getString("password"));
			setPhoneNumber(results.getString("phone_number"));
			setAddress(results.getString("address"));
		}
	}
	

	//returns a boolean of whether the current username already exists in the server
	public boolean usernameExists(String username, Statement stmt) throws Exception
	{
		String query = "SELECT count(*) AS number FROM user WHERE username = '" + username + "';";
		int result = 0;
		ResultSet results;
		try{
			results = stmt.executeQuery(query);
		} catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
			System.err.println(e.getMessage());
			throw(e);
		}
		while(results.next()){
			result = results.getInt("number");
		}
		return (result > 0);
	}
	
	

	//Getters and Setters
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}

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
