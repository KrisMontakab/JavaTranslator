package com.javatpoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Random;

public class PicToGermServlet {
	private static Connection conn = null; 
	int range = 3;
	int rdm;
	Long rdmWordId;
	
	public static Connection getConnection(){
		if (conn == null) {
			try {
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://localhost:3306/germandictionary";
				String username = "root";
				String password = "";
				Class.forName(driver);

				conn = DriverManager.getConnection(url,username,password);
				System.out.println("Connected");
			} catch(Exception e){System.out.println(e);}

		}
		return conn;
	}	
	
    public static void main(String[] args){
    	HashMap<Long, WordBasic> allWordHash = Dictionary.getallBasicWords();
    	Long rdmWordId = generateRandomNumber();
    	WordBasic foundWord = allWordHash.get(rdmWordId);
    }
    
    
	
    public static Long generateRandomNumber() {
    	Random rand = new Random();
    	int range = Dictionary.wordBasicIdArray.size();
        int rdm = rand.nextInt(range);
        rdm = rdm + 1;
        Long rdmId = Long.valueOf(rdm);
        return rdmId;
    }
	
	

}
