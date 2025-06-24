</html><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javatpoint.*" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<%
    String wordIdParam = request.getParameter("wordId");
    Long randomId = null;
    WordBasic word = null;
//     randomId = PicToGermServlet.generateRandomNumber();
    word = Dictionary.getallBasicWords().get(randomId);
    CategoriesServlet.loadCategories();
    String lang = request.getParameter("lang");
    String language = "";

    if (lang != null) {
        language = lang;
        } 
   List<Category> categories = (List<Category>) request.getAttribute("categories");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Select your Test</title>
</head>
<body>
        <h2>English to <%=language%></h2>
		<form action="PicTo.jsp" method="get">
		<input type="hidden" name="langpic" value="<%= language %>">
		<label for="categorySelect">Select a Category:</label>
<select name="categoryId" id="categorySelect">
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
    <br><br>
    <input type="submit" value="Show Random Card">
     <br><a href="GermanFlash.jsp">Go Back!</a>  
</body>
</html>