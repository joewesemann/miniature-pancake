package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;

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
    	         System.out.println("4. exit:");
    	         System.out.println("please enter your choice:");
	}

    public static void displayLoggedInMenu(User currentUser)
    {
        System.out.println("Hello " + currentUser.getName() + ": ");
        System.out.println("1. Reserve Housing:");
        System.out.println("2. Add new property");
        System.out.println("3. exit:");
        System.out.println("please enter your choice:");
    }
    
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
        
        int uid = currentUser.getUserId();
        
        currentTH = new th(uid, category, name, city, state, Integer.parseInt(zip_code), street_address, url, picture, Integer.parseInt(year_built), telephone);
		currentTH.insertTh(stmt);
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Example for cs5530");
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
	            	 if (c<1 | c>3)
	            		 continue;
	            	 if (c==1)
	            	 {

	            		 System.out.println("please enter a name:");
	            		 while ((name = in.readLine()) == null && name.length() == 0);

	            		 System.out.println("please enter a usernamename:");
	            		 while ((username = in.readLine()) == null && username.length() == 0);
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
                         if (c<1 | c>3)
                                 continue;
                         if (c==1)
                         {
                             System.out.println("please kill me\n");
                         }
                         else if (c==2)
                         {
                         	createNewTH(currentUser, con.stmt);
                            break;
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
}
