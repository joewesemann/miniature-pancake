package cs5530;

import java.sql.*;

public class th {
	
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
    String telephone;
	
	public th(){}

	public th(int user_id, String category, String name, String city, String state, int zip_code, String street_address, String url, String picture, int year_built, String telephone)
	{
		setUserId(user_id);
		setCategory(category);
		setName(name);
		setCity(city);
		setState(state);
		setZipCode(zip_code);
		setStreetAddress(street_address);
		setUrl(url);
		setPicture(picture);
		setYearBuilt(year_built);
		setTelephone(telephone);
	}

    public void insertTh(Statement stmt) {
    	String sql = "INSERT INTO th (user_id, category, name, city, state, zip_code, street_address, url, picture, year_built, telephone) " + 
    	             "VALUES (" + user_id + ", '" + category + "', '" + name + "', '" + city + "', '" + state + "', " + zip_code + ", '" + street_address + "', '" + url + "', '" + picture + "', " + year_built + ", '" + telephone + "');";
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
            result = stmt.executeQuery("select last_insert_id() from th");
            lastid = result.getInt("last_insert_id");
            setHid(lastid);
        }
        catch(Exception e)
        {
            System.out.println("Problem obtaining ID");
            e.printStackTrace();
        }
    }


    // Populate TH from table using hid
	public void populateTH(int hid, Statement stmt) throws Exception
	{
		String query;
		String resultstr="";
		ResultSet results; 
		query="Select * from th where hid ='"+hid+"'";
		try{
			results = stmt.executeQuery(query);
	       } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
	       }
		System.out.println("User.getUser Query = "+query+"\n");
		while (results.next()){
			setHid(results.getInt("hid"));
		    setUserId(results.getInt("user_id"));
		    setCategory(results.getString("category"));
		    setName(results.getString("name"));
		    setCity(results.getString("city"));
		    setState(results.getString("state"));
		    setZipCode(results.getInt("zip_code"));
		    setStreetAddress(results.getString("street_address"));
		    setUrl(results.getString("url"));
		    setPicture(results.getString("picture"));
		    setYearBuilt(results.getInt("year_built"));
		    setTelephone(results.getString("telephone"));
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
    
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    
    public String getTelephone(){
        return telephone;
    }
}
