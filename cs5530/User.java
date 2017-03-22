package cs5530;

import java.sql.*;

public class User {

		public User()
		{}

                public void createUser(String name, String username, int type, String password, String phone_number, String address, Statement stmt) {

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
		public String getCourse(String cname, String dname, Statement stmt)
		{
			String sql="select * from course where cname like '%"+cname+"%' and dname like '%"+dname+"%'";
			String output="";
			ResultSet rs=null;
   		 	System.out.println("executing "+sql);
   		 	try{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				 {
					//System.out.print("cname:");
				        output+=rs.getString("cname")+"   "+rs.getString("dname")+"\n"; 
				 }
			     
			     rs.close();
   		 	}
   		 	catch(Exception e)
   		 	{
   		 		System.out.println("cannot execute the query");
   		 	}
   		 	finally
   		 	{
   		 		try{
	   		 		if (rs!=null && !rs.isClosed())
	   		 			rs.close();
   		 		}
   		 		catch(Exception e)
   		 		{
   		 			System.out.println("cannot close resultset");
   		 		}
   		 	}
		    return output;
		}
}
