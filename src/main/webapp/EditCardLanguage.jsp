<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javatpoint.*" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<%
    String wordIdParam = request.getParameter("chosenWordId");
	String wordLangParam = request.getParameter("langedit");
    Long wordId = null;
    WordBasic wordBasic = null;
    Word word = null;

    if (wordIdParam != null) {
    	long longIdParam = Long.parseLong(wordIdParam);
        wordBasic = Dictionary.getallBasicWords().get(longIdParam);
        word = Dictionary.getWordInLanguage(longIdParam, wordLangParam);
    }
%>

<html>
<head>
    <title>Edit Card</title>
</head>
<body>
<h2>Edit Word Information</h2>
    <form action="EditCardServlet" method="post">
        <input type="hidden" name="wordId" value="<%= wordIdParam %>">
        <input type="hidden" name="langtoEdit" value="<%= wordLangParam %>">
        <img src="<%=wordBasic.getImage()%>" alt="image" style="width:179px;height:113px;"><br>
        Editing word: <%= wordBasic.getEnglish() %><br>

         <%= wordLangParam %>: <input type="text" name="newGerman" value="<%= word.getGerman() %>"><br>
        Plural: <input type="text" name="newPlural" value="<%= word.getPlural() %>"><br>
                Gender: <input type="text" name="newGender" value="<%= word.getGender() %>"><br>
        <input type="submit" name="action" value="UpdateLang">
        <input type="submit" name="action" value="Delete"><br><br>
    </form>
     <br><a href="ViewCards.jsp">Go Back!</a>  
</body>
</html>
