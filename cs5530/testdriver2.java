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
                System.out.println("2. exit:");
                System.out.println("please enter your choice:");
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
                                 System.out.println("please enter a type:");
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
	            		 if(authUser.getPassword().equals(passWord)){

	            			 System.out.println("Authentication Successful\n");
                                         loggedIn = true;
                                         currentUser = authUser;
                                         break;
	            		 }
	            		 else{
	            			 System.out.println("password entered = " + passWord + "Real PW: " + authUser.getPassword());
	            			 System.out.println(passWord == authUser.getPassword());
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
                                 System.out.println("Please enter the Housing ID of the house you want to reserve, or '0' to return to main menu: ");
                                 String housing_id = "";
                                 while ((housing_id = in.readLine()) == null && housing_id.length() == 0);
                                 if(housing_id == "0") {
                                     continue;
                                 }

                                 System.out.println("Please enter the start date you want to reserve for, or '0' to return to main menu: ");
                                 String housing_date_start = "";
                                 while ((housing_date_start = in.readLine()) == null && housing_date_start.length() == 0);
                                 if(housing_date_start == "0") {
                                     continue;
                                 }

                                 System.out.println("Please enter the end date you want to reserve for, or '0' to return to main menu: ");
                                 String housing_date_end = "";
                                 while ((housing_date_end = in.readLine()) == null && housing_date_end.length() == 0);
                                 if(housing_date_end == "0") {
                                     continue;
                                 }

                                 System.out.println("Please enter the number in your party, or '0' to return to main menu: ");
                                 String party = "";
                                 while ((party = in.readLine()) == null && party.length() == 0);
                                 if(party == "0") {
                                     continue;
                                 }

                                 Reserve reserve = new Reserve(housing_date_start, housing_date_end, 0, currentUser.getId(), Integer.parseInt(housing_id), Integer.parseInt(party));
                                 reserve.create(con.stmt);
                                 System.out.println("Your reservation was succesfully created. Thanks.");
                                 continue;
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
