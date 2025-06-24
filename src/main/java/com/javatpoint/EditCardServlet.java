package com.javatpoint;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditCardServlet")
public class EditCardServlet extends HttpServlet{
	private static Connection conn = null; 
	
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
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String wordIdParam = request.getParameter("wordId");
    	String action = request.getParameter("action");
    	String editLanguageParam = request.getParameter("langtoEdit");
    	
    	if (wordIdParam != null) {
            Long wordId = null;

            try {
                wordId = Long.parseLong(wordIdParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid word ID");
                return;
            }
            
            
    		try (Connection conn = getConnection()) {
    			if("UpdateLang".equals(action)) {
    				String newGerman = request.getParameter("newGerman");
                    String newGender = request.getParameter("newGender");
                    String newPlural = request.getParameter("newPlural");
                    String updateSQLLang = ("UPDATE " + editLanguageParam + "table SET " + editLanguageParam + "Word=?, " + editLanguageParam + "Gender=?, " + editLanguageParam + "Plural=? WHERE wordId=?");
                    PreparedStatement pstmtlang = conn.prepareStatement(updateSQLLang);
                    pstmtlang.setString(1, newGerman);
                    pstmtlang.setString(2, newGender);
                    pstmtlang.setString(3, newPlural);
                    pstmtlang.setLong(4, wordId);
                    pstmtlang.executeUpdate();
                    response.sendRedirect("ViewCards.jsp");
                    
    			} else if ("UpdateEng".equals(action)) {
    				String newEnglish = request.getParameter("newEnglish");
    				String newImage = request.getParameter("newImage");
                    String updateSQLEng = "UPDATE dictionarytable SET wordEnglish=?, wordImage=? WHERE wordId=?";
	                    PreparedStatement pstmt = conn.prepareStatement(updateSQLEng);
	                         pstmt.setString(1, newEnglish);
	                         pstmt.setString(2, newImage);
	                         pstmt.setLong(3, wordId);
	                         pstmt.executeUpdate();
	                         response.sendRedirect("ViewCards.jsp");
	                         
	                    } else if ("Delete".equals(action)) {
                    String deleteSQL = "DELETE FROM dictionarytable WHERE wordId=?";
                    String deleteGerman = "DELETE FROM germantable WHERE wordId=?";
                    String deleteFrench = "DELETE FROM frenchtable WHERE wordId=?";
                    String deleteSpanish = "DELETE FROM spanishtable WHERE wordId=?";
                    
                    PreparedStatement pstmt = conn.prepareStatement(deleteSQL);
                        pstmt.setLong(1, wordId);
                        pstmt.executeUpdate();
                        
                    PreparedStatement pstmtGer = conn.prepareStatement(deleteGerman);
                        pstmtGer.setLong(1, wordId);
                        pstmtGer.executeUpdate();
                    
                    PreparedStatement pstmtFr = conn.prepareStatement(deleteFrench);
                        pstmtFr.setLong(1, wordId);
                        pstmtFr.executeUpdate();
                    
                    PreparedStatement pstmtSpan = conn.prepareStatement(deleteSpanish);
                        pstmtSpan.setLong(1, wordId);
                        pstmtSpan.executeUpdate();
                    
                    response.sendRedirect("ViewCards.jsp");
                }

    		} catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
    		}
    		
    	} else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing word ID");
        }	
    	}	
    }