<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javatpoint.*" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<%
    String wordIdParam = request.getParameter("chosenWordId");
    Long wordId = null;
    WordBasic word = null;

    if (wordIdParam != null) {
        wordId = Long.parseLong(wordIdParam);
        word = Dictionary.getallBasicWords().get(wordId);
    }
%>

<html>
<head>
    <title>Edit Card</title>
</head>
<body>
<h2>Edit Word Information</h2>
    <form action="EditCardServlet" method="post">
        <input type="hidden" name="wordId" value="<%= wordId %>">
        <img src="<%=word.getImage()%>" alt="image" style="width:179px;height:113px;"><br>
        English: <input type="text" name="newEnglish" value="<%= word.getEnglish() %>"><br>
        Image: <input type="text" name="newImage" value="<%= word.getImage() %>"><br>
        <input type="submit" name="action" value="UpdateEng">
        <input type="submit" name="action" value="Delete"><br><br>
    </form>
     <br><a href="ViewCards.jsp">Go Back!</a>  
</body>
</html>
