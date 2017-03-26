package cs5530;

import java.sql.*;

public class Keyword {
	
    String word, language;
    int id;

	public Keyword(String word, String language)
	{
	    this.word = word;
	    this.language = language;
	}

    public void addWord(String word, String language, Statement stmt) {
        this.word = word;
        this.language = language;
        String sql = "SELECT COUNT(*) FROM keyword WHERE word = " + word + ";";
        try{
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                if(rs.getInt("COUNT(*)") == 0)
                {
                    sql = "INSERT INTO keyword (word, language) VALUES ('"+ word +"', '"+ language +"');";
                    stmt.executeUpdate(sql);
                    int lastid;
                    ResultSet result;
                    result = stmt.executeQuery("select last_insert_id() from keyword;");
                    while(result.next()){
            	    lastid = result.getInt("last_insert_id()");
            	    setId(lastid);
                    }
                }
                else
                {
                    sql = "SELECT id FROM keyword WHERE word = " + word + ";";
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        setId(rs.getInt("id"));
                    }
                }
            }
        }
        catch(Exception e)
        {
        	System.out.println("cannot execute the query");
            e.printStackTrace();
        }
    }
    
    public void loadWordById(int id, Statement stmt)
    {
        String sql = "SELECT * FROM keyword WHERE id = "+id+";";
        try{
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                setId(rs.getInt("id"));
                setWord(rs.getString("word"));
                this.language = rs.getString("language");
            }
        }catch(Exception e)
        {
        	System.out.println("cannot execute the query");
            e.printStackTrace();
        }
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setWord(String word){
        this.word = word;
    }
    
    public String getWord(){
        return this.word;
    }
}
