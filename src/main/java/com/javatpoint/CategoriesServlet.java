package com.javatpoint;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CategoriesServlet")
public class CategoriesServlet extends HttpServlet {
	
	public static Connection getConnection(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
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
	
	public static List<Category> loadCategories(){

		String sql = "SELECT categoryId, categoryName FROM germandictionary.Categories";
	    List<Category> categories = new ArrayList<>();
		
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            int categoryId = rs.getInt("categoryId");
	            String categoryName = rs.getString("categoryName");
	            categories.add(new Category(categoryId, categoryName));
	            System.out.println("Category ID: " + categoryId + ", Category Name: " + categoryName);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return categories;
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = loadCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("categoryDropdown.jsp").forward(request, response);
    }

}
	
