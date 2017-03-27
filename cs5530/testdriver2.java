package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class testdriver2 {

	static String name;
	static String username;
	static String type;
	static String password;
	static String phone_number;
	static String address;
	static User currentUser;
	static th currentTH;
	static boolean loggedIn = false;
	
	public static void displayMenu()
	{
		System.out.println("        Welcome to the Airbnb-like System     ");
		System.out.println("1. Create a user:");
		System.out.println("2. Log-In");
		System.out.println("3. exit:");
		System.out.println("please enter your choice:");
	}

    public static void displayLoggedInMenu(User currentUser)
    {
        System.out.println("Hello " + currentUser.getName() + ": ");
        System.out.println("1. Reserve Housing:");
        System.out.println("2. Add new property");
		System.out.println("3. Favorite a TH");
		System.out.println("4. Edit existing TH");
		System.out.println("5. Record stay during reservation");
        System.out.println("6. Give Feedback");
        System.out.println("7. Rate Feedback");
        System.out.println("8. Mark user as trusted");
        System.out.println("9. Browse Housing");
        System.out.println("10. View most popular housing in each category:");
        System.out.println("11. View most expenzive housing by category:");
        System.out.println("12. View highly rated housing by category: ");
        System.out.println("13. (ADMIN) View Top Trusted Users");
		System.out.println("14. exit:");
		System.out.println("please enter your choice:");

    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connector con=null;
		String choice;

        String sql=null;
        int c=0;
        try
		{
			con= new Connector();
			System.out.println ("Database connection established");
	         
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	             
			while(true)
			{
				displayMenu();
				while ((choice = in.readLine()) == null && choice.length() == 0);
				try{
					c = Integer.parseInt(choice);
				}catch (Exception e)
				{
					continue;
				}
				if (c<1 | c>4)
					continue;
				if (c==1)
				{
					User authUser = new User();
					System.out.println("please enter a name:");
					while ((name = in.readLine()) == null && name.length() == 0);

					System.out.println("please enter a usernamename:");
					while ((username = in.readLine()) == null && username.length() == 0);
					if(authUser.usernameExists(username, con.stmt))
					{
						System.out.println("Username currently in use, returning to main menu.");
						continue;
					}
					
					
					System.out.println("please enter a type (Regular: 1, Admin: 2):");
					while ((type = in.readLine()) == null && type.length() == 0);

					System.out.println("please enter a password:");
					while ((password = in.readLine()) == null && password.length() == 0);
                               
					System.out.println("please enter a phone_number:");
					while ((phone_number = in.readLine()) == null && phone_number.length() == 0);

					System.out.println("please enter an address:");
					while ((address = in.readLine()) == null && address.length() == 0);

					currentUser = new User(name, username, Integer.parseInt(type), password, phone_number, address);
					currentUser.insertUser(con.stmt);
					loggedIn = true;
					break;
                        
	            	}
	            else if (c==2)
	            {	 
					String userName;
					String passWord;
					User authUser = new User();
					System.out.println("Please Enter User Name:");
	            	while ((userName = in.readLine()) == null && userName.length() == 0);
	            		 
	            	System.out.println("Please Enter Password:");
	            	while ((passWord = in.readLine()) == null & passWord.length() == 0);
	            	authUser.populateUser(userName, con.stmt);
	            		
	            	if(authUser.getPassword() != null && authUser.getPassword().equals(passWord)){
	            		System.out.println("Authentication Successful\n");
                        loggedIn = true;
                        currentUser = authUser;
                        break;
	            	}
	            	else{
	            		 System.out.println("Unsuccessful log-in attempt");
	            	}
	            } 
	            else
	            {   
	            	System.out.println("EoM");
	            	con.stmt.close(); 
	        
	            	break;
	            }
			}


			while(loggedIn) {
				displayLoggedInMenu(currentUser);
				while ((choice = in.readLine()) == null && choice.length() == 0);
				try{
					c = Integer.parseInt(choice);
				}catch (Exception e)
				{
					continue;
				}
				if (c==1)
                {
                   	reserveTH(currentUser, con.stmt);
                    continue;
				}
				else if (c==2)
				{
					createNewTH(currentUser, con.stmt);
					System.out.println("Your new Property's ID is: " + currentTH.getHid());
					continue;
                }
                else if(c == 3) 
                {
					favoriteTH(currentUser, con.stmt);
					continue;
                }
                else if(c == 4)
                {
                	updateTH(currentUser, con.stmt);
                	continue;
                }
                else if(c == 5)
                {
                	recordStay(currentTH, currentUser, con.stmt);
                	continue;
                }
                else if (c == 6)
                {
                	recordFeedback(currentUser, con.stmt);
                }
                else if (c == 7) 
                {
                	rateFeedback(currentUser, con.stmt);
                }
                else if (c == 8) 
                { 
                	trustUser(currentUser, con.stmt);
                }
                else if (c == 9)
                {
                        browseTh(currentUser, con.stmt);
                }
                else if (c == 10)
                {
                	viewMostPopular(con.stmt);
                }
                else if (c == 11) 
                {
                	viewMostExpensive(con.stmt);
                }
                else if (c == 12) 
                {
                        viewHighlyRated(con.stmt);
                }
                else if (c == 13)
                {
                	topMUsers(con.stmt);
                }
                else 
                {
                    con.stmt.close();
                    break;
                }
            }

		 }
         catch (Exception e)
         {
        	 e.printStackTrace();
        	 System.err.println ("Either connection error or query execution error!");
         }
         finally
         {
        	 if (con != null)
        	 {
        		 try
        		 {
        			 con.closeConnection();
        			 System.out.println ("Database connection terminated");
        		 }
        	 
        		 catch (Exception e) { /* ignore close errors */ }
        	 }	 
         }
	}
	
	// Method for recording stays
	public static void recordStay(th currentTH, User currentUser, Statement stmt) throws Exception
	{
		boolean proceed = true;
		ArrayList<Visit> visitsToAdd = new ArrayList<Visit>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while(proceed)
		{
			Reserve reservation = new Reserve();
			System.out.println("Please enter reservation number for your stay (stay will not be recorded without valid reservation):");
			String resId = "";
			while ((resId = in.readLine()) == null && resId.length() == 0);
			if(!reservation.verifyID(currentUser.getId(), Integer.parseInt(resId), stmt)){
				System.out.println("You do not own this reservation, please try again.");
				continue;
			}
		
			System.out.println("Please enter first day in form YYYY-MM-DD for your stay:");
			String from = "";
			while ((from = in.readLine()) == null && from.length() == 0);
			if(!reservation.verifyDate(from, Integer.parseInt(resId), stmt))
			{
				System.out.println("Your start date does not fit in your reservation, please try again");
				continue;
			}
			
			System.out.println("Please enter last day of your stay in form YYYY-MM-DD:");
			String to = "";
			while ((to = in.readLine()) == null && to.length() == 0);
			if(!reservation.verifyDate(to, Integer.parseInt(resId), stmt))
			{
				System.out.println("Your end date does not fit in your reservation, please try again.");
				continue;
			}
			else{
				visitsToAdd.add(new Visit(Integer.parseInt(resId), from, to));
			}
			
			System.out.println("\n\nYour stays:");
			for(Visit v : visitsToAdd)
			{
				System.out.println("Reservation ID: " + v.getReserveId());
				System.out.println("Staying from: " + v.getFrom());
				System.out.println("Staying to: " + v.getTo());
		        System.out.println();
			}
			
			System.out.println("What would you like to do?");
			System.out.println("1. Add additional stay");
			System.out.println("2. Confirm Stays and exit");
			System.out.println("3. Exit without saving");
			
			String choice;
			int c = 0;
        	while ((choice = in.readLine()) == null && choice.length() == 0);
			try{
				c = Integer.parseInt(choice);
			}catch (Exception e)
			{
				continue;
			}
			if (c<1 | c>2)
				continue;
			if (c==1)
            {
                continue;
			}
			else if (c==2)
			{
				for(Visit v : visitsToAdd)
				{
					//TODO: Check to see if stays are within reservation periods.
					v.add(stmt);
				}
				break;
            }
            else 
            {
            	break;
            }
		}
	}
	
	// Method for adding Keywords
	public static void addKeywords(th currentTH, Statement stmt) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String word = "";
		System.out.println("Enter keywords to associate with " + currentTH.getName() + " (empty line stop):");
		while (true)
		{
			word = in.readLine();
			if(word.isEmpty()) break;
			else{
				Keyword newkeyword = new Keyword(word, "english");
				newkeyword.addWord(stmt);
				Haskeyword haskey = new Haskeyword(currentTH.getHid(), newkeyword.getId());
				haskey.add(stmt);
			}
		}
	}
	
	// suggest TH's
	public static void suggestTH(int hid, Statement stmt) throws Exception
	{
		String sql = "SELECT th_id, COUNT(th_id) AS Count  FROM (select th_id from (select user_id, th_id from reserve, visit where reserve.id = visit.reserve_id) AS one " +
							 "where user_id IN (SELECT user_id from (SELECT user_id, th_id from reserve, visit WHERE reserve.id = visit.reserve_id) AS two WHERE th_id = " + hid + ")) AS three " + 
							 "WHERE th_id != " + hid + " GROUP BY th_id ORDER BY COUNT(th_id) DESC LIMIT 5;";
							 
		System.out.println("Based off of your reservation, we would suggest the following places (By housing ID): ");
		
		try{
            ResultSet result;
            result = stmt.executeQuery(sql);
            while(result.next()){
                System.out.println(result.getString("th_id"));
            }
        }
        catch(Exception e)
        {
        	System.out.println("cannot execute the query");
            e.printStackTrace();
        }
		
	}

        // search TH's
        public static void browseTh(User currentUser, Statement stmt) throws Exception
        {
                System.out.println("Welcome to the housing browser. Enter the following criteria. Anything you don't fill out will not be considered in the search. (Skip any fields by leaving them blank and pressing enter)");

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Please enter the min price range (e.g. 100.00): ");
                String min_price = "";
                while ((min_price = in.readLine()) == null && min_price.length() == 0);

                System.out.println("Please enter the max price range (e.g. 100.00): ");
                String max_price = "";
                while ((max_price = in.readLine()) == null && max_price.length() == 0);

                System.out.println("Please enter city: ");
                String city = "";
                while ((city = in.readLine()) == null && city.length() == 0);

                System.out.println("Please enter state: ");
                String state = "";
                while ((state = in.readLine()) == null && state.length() == 0);

                System.out.println("Please enter category: ");
                String category = "";
                while ((category = in.readLine()) == null && category.length() == 0);

                System.out.println("Please enter keywords to search by (enter multiple by seperating them by a space and pressing enter when finished): ");
                String keywords_string = "";
                while ((keywords_string = in.readLine()) == null && keywords_string.length() == 0);
                String[] keywords = keywords_string.split(" ");

                System.out.println("How would you like to sort the results (1 = by price, 2 = by average rating): ");
                String sort = "";
                while ((sort = in.readLine()) == null && sort.length() == 0);

                th th = new th();
                ResultSet rst = th.complexSearch(min_price, max_price, city, state, category, keywords, sort, stmt);

                System.out.println("hid     category     name     city     state     address     price     avg_rating");

                while(rst.next()){
                   System.out.println(rst.getInt("r.hid") + "     " +  rst.getString("r.category") + "     " + rst.getString("r.name") + "     " + rst.getString("r.city")+ "     " + rst.getString("r.state")+ "     " + rst.getString("r.street_address") + "     " + rst.getString("r.price")+ "     " + rst.getString("feedback.avg_rating"));
                }
        }

        // method for marking a user as trusted
        public static void trustUser(User currentUser, Statement stmt) throws Exception
        {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Please enter the ID of the user that you want to mark as trusted or Untrusted: ");
                String trusted_id = "";
                while ((trusted_id = in.readLine()) == null && trusted_id.length() == 0);
                
                System.out.println("1 to Trust User - 0 to Untrust user");
				String trustNum = "";
				while ((trustNum = in.readLine()) == null && trustNum.length() == 0);
				
				if(trustNum.equals("0"))
				{
					trustNum = "-1";
				}
				else if (!trustNum.equals("1"))
				{
					System.out.println("Not valid input - exiting to menu");
					return;
				}

                Trust trust = new Trust(currentUser.getId(), Integer.parseInt(trusted_id), Integer.parseInt(trustNum));
                trust.create(stmt);
                System.out.println("Successfully marked user as trusted. Thanks.");
        }

        // method for rating feedback 
        public static void rateFeedback(User currentUser, Statement stmt) throws Exception
        {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Please enter the Feedback ID of the feedback you would like to rate");
                String feedback_id = "";
                while ((feedback_id = in.readLine()) == null && feedback_id.length() == 0);

                System.out.println("Please enter the rating for this feedback (0 = useless, 1 = useful, 2 = very useful): ");
                String rating = "";
                while ((rating = in.readLine()) == null && rating.length() == 0);


                Rating ratingobj = new Rating(currentUser.getId(), Integer.parseInt(feedback_id), Integer.parseInt(rating));
                ratingobj.create(stmt);
                System.out.println("Successfully rated feedback. Thanks.");
        } 

        public static void viewMostExpensive(Statement stmt) throws Exception
        {
                th th = new th();
                ResultSet rst = th.viewMostExpensive(stmt);
                System.out.println("Most expensive housing by category: ");
                System.out.println("name                 category           price");
                while(rst.next()){
                   System.out.println(rst.getString("name") + "     " +  rst.getString("category") + "     " + rst.getString("price"));
                }
        }

        public static void viewMostPopular(Statement stmt) throws Exception
        {
                th th = new th();
                ResultSet rst = th.viewMostPopular(stmt);
                System.out.println("Most popular housing by category: ");
                System.out.println("name               stay_count        category");
                while(rst.next()){
                   System.out.println(rst.getString("a.name") + "     " +  rst.getString("stay_count") + "     " + rst.getString("category"));
                }
        }

        public static void viewHighlyRated(Statement stmt) throws Exception
        {
                th th = new th();
                ResultSet rst = th.viewHighlyRated(stmt);
                System.out.println("Highly rated housing by category: ");
                System.out.println("name              category            rating");
                while(rst.next()){
                   System.out.println(rst.getString("th.name") + "     " +  rst.getString("th.category") + "     " + rst.getString("feedback.rating"));
                }
        }

        // method for giving feedback 
        public static void recordFeedback(User currentUser, Statement stmt) throws Exception
        {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        	System.out.println("Please enter the Housing ID of the housing you would like to give feedback for: ");
        	String housing_id = "";
        	while ((housing_id = in.readLine()) == null && housing_id.length() == 0);
                
                System.out.println("Please enter the rating for this housing (1 - 10): ");
                String rating = "";
                while ((rating = in.readLine()) == null && rating.length() == 0);

                System.out.println("Please enter your feedback for this housing: ");
                String text = "";
                while ((text = in.readLine()) == null && text.length() == 0);

        	Feedback feedback = new Feedback(Integer.parseInt(housing_id), Integer.parseInt(rating), currentUser.getId(), text);
        	feedback.create(stmt);
        	System.out.println("Successfully submitted feedback. Thanks.");
        }
	
	// Method for favoriting housing
	public static void favoriteTH(User currentUser, Statement stmt) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the Housing ID of the housing you would like to favorite: ");
        String housing_id_favorite = "";
        while ((housing_id_favorite = in.readLine()) == null && housing_id_favorite.length() == 0);
                              
        Favorites fav = new Favorites(Integer.parseInt(housing_id_favorite), currentUser.getId());
        fav.add(stmt);
        System.out.println("Successfully added favorite. Thanks.");
	}
	
	// Method for reserve housing
	public static void reserveTH(User currentUser, Statement stmt) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter the Housing ID of the house you want to reserve, or '0' to return to main menu: ");
		String housing_id = "";
		while ((housing_id = in.readLine()) == null && housing_id.length() == 0);
		if(Integer.parseInt(housing_id) == 0) {
			return;
		}
		System.out.println("Please enter the start date you want to reserve for e.g. '2017-01-01', or '0' to return to main menu: ");
		String housing_date_start = "";
		while ((housing_date_start = in.readLine()) == null && housing_date_start.length() == 0);
		if(housing_date_start.equals("0")) {
			return;
		}

		System.out.println("Please enter the end date you want to reserve for e.g. '2017-01-01', or '0' to return to main menu: ");
		String housing_date_end = "";
		while ((housing_date_end = in.readLine()) == null && housing_date_end.length() == 0);
		if(housing_date_end.equals("0")) {
			return;
		}

		System.out.println("Please enter the number in your party, or '0' to return to main menu: ");
		String party = "";
		while ((party = in.readLine()) == null && party.length() == 0);
		if(Integer.parseInt(party) == 0) {
			return;
		}
                         
		Reserve reserve = new Reserve(housing_date_start, housing_date_end, 0, currentUser.getId(), Integer.parseInt(housing_id), Integer.parseInt(party));
		System.out.println("Are you sure you want to reserve this house from " + housing_date_start + " until " + housing_date_end + "? (0 for yes, 1 for no)");
		String confirm = "";
		while ((confirm = in.readLine()) == null && confirm.length() == 0);

		if(Integer.parseInt(confirm) == 0){
			reserve.create(stmt);
			System.out.println("Your reservation was succesfully created. Thanks.");
			suggestTH(reserve.getHid(), stmt);
		}
	}
	
	// Method to add a new TH to the database
	public static void createNewTH(User currentUser, Statement stmt) throws Exception
    {
        String category;
        String name;
        String city;
        String state;
        String zip_code;
		String street_address;
		String url;
		String picture;
		String year_built;
		String telephone;
		String price;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                         	
		System.out.println("please enter a Property Name:");
		while ((name = in.readLine()) == null && name.length() == 0);

		System.out.println("please enter category:");
		while ((category = in.readLine()) == null && category.length() == 0);
	            			
		System.out.println("please enter a Street Address:");
		while ((street_address = in.readLine()) == null && street_address.length() == 0);

        System.out.println("please enter the property's city:");
        while ((city = in.readLine()) == null && city.length() == 0);
                               
        System.out.println("Please enter the property's State:");
        while ((state = in.readLine()) == null && state.length() == 0);


        System.out.println("please enter the property's ZIP code:");
        while ((zip_code = in.readLine()) == null && zip_code.length() == 0);
                               
        System.out.println("Please enter the property's URL:");
        while ((url = in.readLine()) == null && url.length() == 0);

        System.out.println("please enter a URL for the property's picture:");
        while ((picture = in.readLine()) == null && picture.length() == 0);
                               
        System.out.println("Please enter the year the property was built:");
        while ((year_built = in.readLine()) == null && year_built.length() == 0);

        System.out.println("please enter the telephone number for the property:");
        while ((telephone = in.readLine()) == null && telephone.length() == 0);
        
        System.out.println("please enter the current Price Per Night:");
        while ((price = in.readLine()) == null && price.length() == 0);
        
        int uid = currentUser.getId();
        
        currentTH = new th(uid, category, name, city, state, Integer.parseInt(zip_code), street_address, url, picture, Integer.parseInt(year_built), telephone, Double.parseDouble(price));
		
		try{
			currentTH.insertTh(stmt);
			addKeywords(currentTH, stmt);
		}
		catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println ("Error with query.");
        }
    }
    
    //Method to get top M trusted Users
    public static void topMUsers(Statement stmt) throws Exception
    {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String m = "";	

		if(currentUser.getType() != 2)
		{
			System.out.println("Not Admin User.");
			return;
		}
		else
		{
			System.out.println("please enter a number of users:");

			while ((m = in.readLine()) == null && m.length() == 0);
        }
    	
    	String sql = "SELECT user_2, SUM(trusted) " + 
    				 "FROM trust " +
    				 "GROUP BY user_2 " +
    				 "DESC LIMIT " + m + ";";
							 
		System.out.println("Top Trusted Users: ");
		
		try{
            ResultSet result;
            result = stmt.executeQuery(sql);
            while(result.next()){
                System.out.println(result.getString("user_2"));
            }
        }
        catch(Exception e)
        {
        	System.out.println("cannot execute the query");
            e.printStackTrace();
        }
    }
    
    // Method to update TH values to the database
	public static void updateTH(User currentUser, Statement stmt) throws Exception
    {
        String category;
        String name;
        String city;
        String state;
        String zip_code;
		String street_address;
		String url;
		String picture;
		String year_built;
		String telephone;
		String hid;
		String price;
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Housing ID# of the property you wish to edit: ");
        while ((hid = in.readLine()) == null && hid.length() == 0);
        try{
        	currentTH = new th();
        	currentTH.populateTH(Integer.parseInt(hid), stmt);
        	if(currentTH.getUserID() != currentUser.getId())
        	{
        		System.out.println("You do not own this property");
        		System.out.println(currentTH.getUserID() + " " + currentUser.getId());
        		return;
        	}
        } catch(Exception e){
        	e.printStackTrace();
        	System.err.println("Error looking up housing ID with given ID#");
        	return;
        }
        
 
        
        int c = 0;
        
        while(true){
            System.out.println("Hello " + currentUser.getName() + ", update which of the following for ID#: " + currentTH.getHid() + "?");
			System.out.println("1.  Category:        " + currentTH.getCategory());
        	System.out.println("2.  Name:            " + currentTH.getName());
			System.out.println("3.  Street Address:  " + currentTH.street_address);
			System.out.println("4.  City:            " + currentTH.getCity());
        	System.out.println("5.  State:           " + currentTH.getState());
			System.out.println("6.  Zip Code:        " + currentTH.getZipCode());
			System.out.println("7.  URL:             " + currentTH.getURL());
        	System.out.println("8.  Picture URL:     " + currentTH.getPicture());
			System.out.println("9.  Year Built:      " + currentTH.getYearBuilt());
			System.out.println("10. Telephone:       " + currentTH.getTelephone());
			System.out.println("11. Price Per Night: " + currentTH.getPrice());
			System.out.println("12. Add Key Words");
			System.out.println("13. Save & exit");
			System.out.println("14. Exit without Saving");
			System.out.println("please enter your choice:");
        	
        	String choice;
        	while ((choice = in.readLine()) == null && choice.length() == 0);
			try{
				c = Integer.parseInt(choice);
			}catch (Exception e)
			{
				continue;
			}
			if (c<1 | c>14)
				continue;
			if (c==1)
            {
			System.out.println("please enter new Category:");
				while ((category = in.readLine()) == null && category.length() == 0);
				currentTH.setCategory(category);
                continue;
			}
			else if (c==2)
			{
				System.out.println("please enter new Name:");
				while ((name = in.readLine()) == null && name.length() == 0);
				currentTH.setName(name);
                continue;
            }
            else if(c == 3) 
            {
				System.out.println("please enter new Street Address:");
				while ((street_address = in.readLine()) == null && street_address.length() == 0);
				currentTH.setStreetAddress(street_address);
				continue;
			}
			else if(c == 4)
			{
			System.out.println("please enter new City:");
			while ((city = in.readLine()) == null && city.length() == 0);
				currentTH.setCity(city);
                continue;
            }
            else if(c == 5)
            {
            	System.out.println("please enter new State:");
				while ((state = in.readLine()) == null && state.length() == 0);
				currentTH.setState(state);
                continue;
            }
            else if(c == 6)
            {
             	System.out.println("please enter new Zip Code:");
				while ((zip_code = in.readLine()) == null && zip_code.length() == 0);
				currentTH.setZipCode(Integer.parseInt(zip_code));
                continue;
            }
            else if(c == 7)
            {
               	System.out.println("please enter new URL:");
				while ((url = in.readLine()) == null && url.length() == 0);
				currentTH.setUrl(url);
                continue;
            }
            else if(c == 8)
            {
              	System.out.println("please enter new Picture URL:");
				while ((picture = in.readLine()) == null && picture.length() == 0);
				currentTH.setPicture(picture);
                continue;
            }
            else if(c == 9)
            {
              	System.out.println("please enter new Year Built:");
				while ((year_built = in.readLine()) == null && year_built.length() == 0);
				currentTH.setYearBuilt(Integer.parseInt(year_built));
                continue;
            }
            else if(c == 10)
            {
              	System.out.println("please enter new Telephone #:");
				while ((telephone = in.readLine()) == null && telephone.length() == 0);
				currentTH.setTelephone(telephone);
                continue;
            }
			else if(c == 11)
            {
              	System.out.println("please enter new price per night:");
				while ((price = in.readLine()) == null && price.length() == 0);
				currentTH.setPrice(price);
                continue;
            }
            else if(c == 12)
            {
            	addKeywords(currentTH, stmt);
            	continue;
            }
            else if(c == 13)
            {
              	try{
					currentTH.updateTH(stmt);
				}
				catch (Exception e)
        		{
        			e.printStackTrace();
        			System.err.println ("Error with query.");
        		}
                break;
            }
            else 
            {
            	break;
            }
        }
    }
}
