<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ page import="com.javatpoint.*" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<head>
<meta charset="UTF-8">
<title>Add A New Card</title>
</head>
<body>
 <form action="ControllerServlet" method="post">
   English:<input type="text" name="newEnglish"><br> 
   Image:<input type="text" name="newImage"><br> 
   
   		<label for="categorySelect">Select a Category:</label>
<select name="categoryIdNew" id="categorySelectNew">
    <option value="">--Select a Category--</option>
    <% 
   		List<Category> catList = CategoriesServlet.loadCategories();
        if (catList != null) {
            for (Category category : catList) {
    %>
                <option value="<%= category.getCategoryId() %>"><%= category.getCategoryName() %></option>
    <% 
            }
        } else {
    %>
        <option value="">No categories available</option>
    <% } %>
</select>
   
<br>    <input type="submit" value="Submit" name="submitButtonNew"><br> 
    </form>
  <a href="ViewCards.jsp">Go Back!</a>  
</body>
</html>