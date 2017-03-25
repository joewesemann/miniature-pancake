package cs5530;

import java.sql.*;

public class Th {
	
    int hid;
    int user_id;
    String category;
    String name;
    String city;
    String state;
    int zip_code;
    String street_address;
    String url;
    String picture;
    int year_built;
    int telephone;
	
	public Th(){}

	public Th(int hid, int user_id, String category, String name, String city, String state, int zip_code, String street_address, String url, String picture, int year_built, int telephone)
	{
		setHid(hid);
		setUserId(user_id);
		setCate
	}

    public void inserTh(Statement stmt) {
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




    //TODO: change to getTH	
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
	public void setHid(int hid){
	    this.hid = hid;
	}
	
	public int getHid(){
	    return this.hid;
	}
    
    public void setUserId(int user_id){
        this.user_id = user_id;
    }
    
    public int getUserID(){
        return this.user_id;
    }
    
    public void setCategory(String category){
        this.category = category;
    }
    
    public String getCategory(){
        return this.category;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setCity(String city){
        this.city = city;
    }
    
    public String getCity(){
        return this.city;
    }
    
    public void setState(String state){
        this.state = state;
    }
    
    public String getState(){
        return this.state;
    }
    
    public void setZipCode(int zip_code)
    {
        this.zip_code = zip_code;
    }
    
    public int getZipCode(){
        return this.zip_code;
    }
    
    public void setStreetAddress(String street_address){
        this.street_address = street_address;
    }
    
    public String getStreetAddress(){
        return this.street_address;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getURL(){
        return url;
    }
    
    public void setPicture(String picture){
        this.picture = picture;
    }
    
    public String getPicture(){
        return this.picture;
    }
    
    public void setYearBuilt(int year_built){
        this.year_built = year_built;
    }
    
    public int getYearBuilt(){
        return year_built;
    }
    
    public void setTelephone(int telephone){
        this.telephone = telephone;
    }
    
    public int getTelephone(){
        return telephone;
    }
}
