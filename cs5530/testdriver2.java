package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;

public class testdriver2 {

	/**
	 * @param args
	 */
	public static void displayMenu()
	{
		 System.out.println("        Welcome to the Airbnb-like System     ");
    	         System.out.println("1. create a user:");
    	         System.out.println("3. exit:");
    	         System.out.println("please enter your choice:");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Example for cs5530");
		Connector con=null;
		String choice;

        String name;
        String username;
        String type; 
        String password; 
        String phone_number;
        String address;        


        String sql=null;
        int c=0;
         try
		 {
			//remember to replace the password
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

	            		 User user=new User();
                                 user.createUser(name, username, Integer.parseInt(type), password, phone_number, address, con.stmt);
	            	 }
	            	 else if (c==2)
	            	 {	 
	            		 System.out.println("please enter your query below:");
	            		 while ((sql = in.readLine()) == null && sql.length() == 0)
	            			 System.out.println(sql);
	            		 ResultSet rs=con.stmt.executeQuery(sql);
	            		 ResultSetMetaData rsmd = rs.getMetaData();
	            		 int numCols = rsmd.getColumnCount();
	            		 while (rs.next())
	            		 {
	            			 //System.out.print("cname:");
	            			 for (int i=1; i<=numCols;i++)
	            				 System.out.print(rs.getString(i)+"  ");
	            			 System.out.println("");
	            		 }
	            		 System.out.println(" ");
	            		 rs.close();
	            	 }
	            	 else
	            	 {   
	            		 System.out.println("EoM");
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
