package com.javatpoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	public Long newWordId;
//	private static Connection conn = null; 
	public Connection getConnection(){
		Connection conn = null;
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
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
    	if (request.getParameter("submitButtonNew") != null) {
    		String newEnglish = request.getParameter("newEnglish");
    		String newImage = request.getParameter("newImage");
    		String gptIn = " in ";
    		String gptEnd = ". Print nothing except for a translation, then the plural form, then the gender (m,f,n). Each of these should be comma seperated.";
    		String gptGerman = (newEnglish + gptIn + "german" + gptEnd);
    		String gptFrench = (newEnglish + gptIn + "french" + gptEnd);
    		String gptSpanish = (newEnglish + gptIn + "spanish" + gptEnd);
    		
    		String gerList = Gptintegration.getChatGPTResponse(gptGerman);
    		String frList = Gptintegration.getChatGPTResponse(gptFrench);
    		String spanList = Gptintegration.getChatGPTResponse(gptSpanish);
    		
            String[] gerParts = gerList.split(",");
            for (int i = 0; i < gerParts.length; i++) {
            	gerParts[i] = gerParts[i].trim();
            }
            String gerWord = gerParts[0];
            String gerPl = gerParts[1];
            String gerGen = gerParts[2];
            
            String[] frParts = frList.split(",");
            for (int i = 0; i < frParts.length; i++) {
            	frParts[i] = frParts[i].trim();
            }
            String frWord = frParts[0];
            String frPl = frParts[1];
            String frGen = frParts[2];
            
            String[] spanParts = spanList.split(",");
            for (int i = 0; i < spanParts.length; i++) {
            	spanParts[i] = spanParts[i].trim();
            }
            String spanWord = spanParts[0];
            String spanPl = spanParts[1];
            String spanGen = spanParts[2];
    		
    				
    		String insertSQLEng = "INSERT INTO dictionarytable (wordEnglish, wordImage) VALUES (?, ?)";
    		String getWordIdSQL = "SELECT wordId FROM dictionarytable ORDER BY wordId DESC LIMIT 1;";
    		String insertSQLGer = "INSERT INTO germantable (wordId, germanWord, germanGender, germanPlural) VALUES (?, ?, ?, ?)";
    		String insertSQLFr = "INSERT INTO frenchtable (wordId, frenchWord, frenchGender, frenchPlural) VALUES (?, ?, ?, ?)";
    		String insertSQLSpan = "INSERT INTO spanishtable (wordId, spanishWord, spanishGender, spanishPlural) VALUES (?, ?, ?, ?)";
    		String insertSQLCat = "INSERT INTO word_category (wordId, categoryId) VALUES (?, ?)";
    		
    		try (Connection con = getConnection();
    		    PreparedStatement pstmtEng = con.prepareStatement(insertSQLEng)) {
    		    pstmtEng.setString(1, newEnglish);
    		    pstmtEng.setString(2, newImage);
    		    pstmtEng.executeUpdate();
    		    con.close();
    		} catch (SQLException e) {
    		    e.printStackTrace();
    		}
    		
    		try (Connection con = getConnection();
        		 PreparedStatement pstmtGetID = con.prepareStatement(getWordIdSQL);
    		     ResultSet rs = pstmtGetID.executeQuery()) { 
    		    	    if (rs.next()) {
    		    	        newWordId = rs.getLong("wordId");
    		    	    }

        		    con.close();
        		} catch (SQLException e) {
        		    e.printStackTrace();
        		}
    		
    		try (Connection con = getConnection();
        		    PreparedStatement pstmtGer = con.prepareStatement(insertSQLGer)) {
    				pstmtGer.setLong(1, newWordId);
        		    pstmtGer.setString(2, gerWord);
        		    pstmtGer.setString(3, gerGen);
        		    pstmtGer.setString(4, gerPl);
        		    pstmtGer.executeUpdate();
        		    con.close();
        		} catch (SQLException e) {
        		    e.printStackTrace();
        		}
    		
    		try (Connection con = getConnection();
        		    PreparedStatement pstmtFr = con.prepareStatement(insertSQLFr)) {
    				pstmtFr.setLong(1, newWordId);
        		    pstmtFr.setString(2, frWord);
        		    pstmtFr.setString(3, frGen);
        		    pstmtFr.setString(4, frPl);
        		    pstmtFr.executeUpdate();
        		    con.close();
        		} catch (SQLException e) {
        		    e.printStackTrace();
        		}
    		
    		try (Connection con = getConnection();
        		    PreparedStatement pstmtSpan = con.prepareStatement(insertSQLSpan)) {
    				pstmtSpan.setLong(1, newWordId);
        		    pstmtSpan.setString(2, spanWord);
        		    pstmtSpan.setString(3, spanGen);
        		    pstmtSpan.setString(4, spanPl);
        		    pstmtSpan.executeUpdate();
        		    con.close();
        		} catch (SQLException e) {
        		    e.printStackTrace();
        		}		
    		
    		try (Connection con = getConnection();
        		    PreparedStatement pstmtCat = con.prepareStatement(insertSQLCat)) {
        		    pstmtCat.setLong(1, newWordId);
        		    String catIdNew = request.getParameter("categoryIdNew");
        		    Long catIdNewLong = Long.parseLong(catIdNew);
        		    pstmtCat.setLong(2, catIdNewLong);
        		    pstmtCat.executeUpdate();
        		    con.close();
        		} catch (SQLException e) {
        		    e.printStackTrace();
        		}		
    		
    }	
	}
}
