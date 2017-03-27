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
    double price;
	
	public th(){}

	public th(int user_id, String category, String name, String city, String state, int zip_code, String street_address, String url, String picture, int year_built, String telephone, Double price)
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
		setPrice(price);
	}

    public ResultSet viewMostPopular(Statement stmt) {

        String query = "select  a.name, max(stay_count) as stay_count, category from ( select  count(r.th_id) as stay_count,  r.th_id as hid,  th.category as category, th.name as name from reserve r  join visit v on v.reserve_id = r.id  join th on th.hid = r.th_id  group by r.th_id, th.category  ) a group by a.category, a.hid limit 5;";

        ResultSet result = null;
        try {
            result = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public ResultSet viewMostExpensive(Statement stmt) {

        String query = "select name, category,  price from th category order by price desc limit 5";

        ResultSet result = null;
        try {
            result = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public ResultSet viewHighlyRated(Statement stmt) {

        String query = "select th.name, th.category, feedback.rating from th join feedback on feedback.th_id = th.hid limit 5;";

        ResultSet result = null;
        try {
            result = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public ResultSet complexSearch(String min_price, String max_price, String city, String state, String category, String[] keywords, String sort, Statement stmt) {

        String query = ""; 

        /* handle a keywords search (THE BEAST QUERY) */
        if(keywords.length != 0 && !keywords[0].isEmpty()){
            query = " SELECT " + 
                     "  r.hid," + 
                     " r.category, " +
                     " r.name, " +
                     " r.city, " +
                     " r.state," +
                     " r.street_address," +
                     " r.price," +
                     " feedback.avg_rating" +
                     " FROM (" +
                      "  SELECT *" +
                      "  FROM (" +
                      "    SELECT a.th_id" +
                      "    FROM (" +
                      "        SELECT hk.th_id, k.word " +
                      "        FROM keyword k " +
                      "        JOIN has_keywords hk " +
                      "        on k.id = hk.keyword_id " +
                      "    ) a " +
                      "    WHERE LOWER(a.word) IN (";

                      for(int i = 0; i < keywords.length; i++){
                          
                          query += "'" + keywords[i].toLowerCase() + "'";
                          if(i != keywords.length -1)
                              query += ",";
                      }


                      query += ") " +
                      "    GROUP BY a.th_id " +
                      "  ) b " +
                      "  JOIN th t " +
                      "  ON t.hid = b.th_id " +
                      " ) r " +

                      "JOIN ( " +
                      "  SELECT th_id, AVG(rating) as avg_rating " +
                      "  FROM feedback " +
                      "  GROUP BY th_id " +
                      ") feedback " +
                      "ON r.hid = feedback.th_id " +

                      "WHERE 1=1 ";
 
                      if(!city.isEmpty()){
                          query += " AND r.city LIKE '%" + city + "%'";
                      }

                      if(!state.isEmpty()){
                          query += " AND r.state LIKE '%" + state + "%'";
                      }

                      if(!category.isEmpty()){
                          query += " AND r.category LIKE '%" + category + "%'";
                      }

                      if(!max_price.isEmpty()){
                          query += " AND r.price <= " + max_price;
                      }

                      if(!min_price.isEmpty()){
                          query += " AND r.price >= " + min_price;
                      }

                      if(sort.equals("2")){
                          query += " ORDER BY feedback.avg_rating;";
                      } else {
                          query += " ORDER BY feedback.avg_rating;";
                      }
        } else {

                query = " SELECT " +
                     "  r.hid," +
                     " r.category, " +
                     " r.name, " +
                     " r.city, " +
                     " r.state," +
                     " r.street_address," +
                     " r.price," +
                     " feedback.avg_rating" +
                     " FROM th r" +
                     " LEFT JOIN ( " +
                     " SELECT th_id, AVG(rating) as avg_rating " +
                     "  FROM feedback " +
                     "  GROUP BY th_id " +
                     " ) feedback " +
                     " ON r.hid = feedback.th_id " +
                     
                     "WHERE 1=1 ";

                      if(!city.isEmpty()){
                          query += " AND r.city LIKE '%" + city + "%'";
                      }

                      if(!state.isEmpty()){
                          query += " AND r.state LIKE '%" + state + "%'";
                      }

                      if(!category.isEmpty()){
                          query += " AND r.category LIKE '%" + category + "%'";
                      }

                      if(!max_price.isEmpty()){
                          query += " AND r.price <= " + max_price;
                      }

                      if(!min_price.isEmpty()){
                          query += " AND r.price >= " + min_price;
                      }

                      if(sort.equals("2")){
                          query += " ORDER BY feedback.avg_rating;";
                      } else {
                          query += " ORDER BY feedback.avg_rating;";
                      }
        }

        ResultSet result = null;
        try {
            result = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void insertTh(Statement stmt) {
    	String sql = "INSERT INTO th (user_id, category, name, city, state, zip_code, street_address, url, picture, year_built, telephone, price) " + 
    	             "VALUES (" + user_id + ", '" + category + "', '" + name + "', '" + city + "', '" + state + "', " + zip_code + ", '" + street_address + "', '" + url + "', '" + picture + "', " + year_built + ", '" + telephone + "', " + getPrice() + ");";
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
            result = stmt.executeQuery("select last_insert_id() from th;");
            while(result.next()){
            	lastid = result.getInt("last_insert_id()");
            	setHid(lastid);	
            }
        }
        catch(Exception e)
        {
            System.out.println("Problem obtaining ID");
            e.printStackTrace();
        }
    }
    
    //update housing database to current values
    public void updateTH(Statement stmt){
            	String sql = "UPDATE th SET category = '" + getCategory() + "', name = '" + getName() + "', city = '" + getCity() + "', state = '" + getState() + "', zip_code = " + getZipCode() + ", street_address = '" + getStreetAddress() +
            	                "', url = '" + getURL() + "', picture = '" + getPicture() + "', year_built = " + getYearBuilt() + ", telephone = '" + getTelephone() + "' WHERE hid = " + getHid() + ";";
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
			if(results.getString("hid") != null) setHid(results.getInt("hid"));
		    if(results.getString("user_id") != null) setUserId(results.getInt("user_id"));
		    if(results.getString("category") != null) setCategory(results.getString("category"));
		    if(results.getString("name") != null) setName(results.getString("name"));
		    if(results.getString("city") != null) setCity(results.getString("city"));
		    if(results.getString("state") != null) setState(results.getString("state"));
		    if(results.getString("zip_code") != null) setZipCode(results.getInt("zip_code"));
		    if(results.getString("street_address") != null) setStreetAddress(results.getString("street_address"));
		    if(results.getString("url") != null) setUrl(results.getString("url"));
		    if(results.getString("picture") != null) setPicture(results.getString("picture"));
		    if(results.getString("year_built") != null) setYearBuilt(results.getInt("year_built"));
		    if(results.getString("telephone") != null) setTelephone(results.getString("telephone"));
		    if(results.getString("price") != null) setPrice(results.getString("price"));
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
    
    public void setPrice(double price)
    {
        this.price = price;
    }
    
    public void setPrice(String price)
    {
        this.price = Double.parseDouble(price);
    }
    
    public double getPrice()
    {
        return this.price;
    }
}
