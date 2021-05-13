package com.example.demo;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WordDatabase {
    static Connection conn = null; 
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:mem:testdb";
	static final String WORDLIST_URL = "https://raw.githubusercontent.com/dwyl/english-words/master/words.txt";
	
	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";
	
	private static void downloadFile(URL url, String fileName) throws
	Exception {
		try (InputStream in = url.openStream())
		{
			Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);				
		}
	
	}
	
	public static ArrayList<String> SearchForWords(String searchcriteria) throws SQLException {
	 String sql = "";
	 ArrayList<String> returnrows = new ArrayList<String>();
   	 sql = "SELECT LISTITEM FROM WORDLIST WHERE UPPER(LISTITEM) LIKE UPPER( ? )";
	    try (PreparedStatement pStatement = conn.prepareStatement(sql)) {
	        pStatement.setString(1, searchcriteria);
	        try (ResultSet rSet = pStatement.executeQuery()) {

	            while (rSet.next()) {
	            	returnrows.add(rSet.getString(1));
	            }
	        }
	    }			
		return returnrows;
	}
	
	public WordDatabase () {
	    conn = null; 
	    Statement stmt = null; 
		try {
			downloadFile(new URL(WORDLIST_URL) , System.getProperty("user.dir") + "\\wordlist.txt");
	
	
			// STEP 1: Register JDBC driver 
	        Class.forName(JDBC_DRIVER); 
	            
	        //STEP 2: Open a connection 
	        System.out.println("Connecting to database..."); 
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);  
	        
	        //STEP 3: Execute a query 
	        System.out.println("Creating table in given database..."); 
	        stmt = conn.createStatement(); 
	        String sql =  "CREATE TABLE WORDLIST " + 
	           "(LISTITEM VARCHAR(80))";  
	        stmt.executeUpdate(sql);
	        System.out.println("Created table in given database..."); 
		
	
		    File file = new File(System.getProperty("user.dir") + "\\wordlist.txt");
	
			// STEP 1: Register JDBC driver 
	        Class.forName(JDBC_DRIVER); 
	            
	        //STEP 2: Open a connection 
	        System.out.println("Connecting to database..."); 
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);  
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
	
	         // STEP 3: Execute a query 
	         stmt = conn.createStatement();
	         sql = "";
	         
	
	    	 while ((st = br.readLine()) != null)
			 {
	    		 
	    	   sql = "INSERT INTO WORDLIST VALUES ( ? )";
	    	    try (PreparedStatement pStatement = conn.prepareStatement(sql)) {
	    	        pStatement.setString(1, st);
	
	    	        pStatement.executeUpdate();
	    	    }

			 }
	    	 br.close();
		    	 


     } catch(Exception e) { 
        //Handle errors for Class.forName 
        e.printStackTrace(); 


	}		
		
		
	}
}
