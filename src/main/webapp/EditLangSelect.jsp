<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String wordIdParam = request.getParameter("wordId");

%>

<html>
<head>
<meta charset="UTF-8">
<title>Language Edit</title>
</head>
<body>
<body>
		<input type="hidden" name="chosenWordId" value="<%= wordIdParam %>">
        <h2>What Language would you like to edit Test</h2>
	 <br><a href="EditCard.jsp?chosenWordId=<%= wordIdParam %>">English</a>  
	 <br><a href="EditCardLanguage.jsp?chosenWordId=<%= wordIdParam %>&langedit=german">German</a>  
	 <br><a href="EditCardLanguage.jsp?chosenWordId=<%= wordIdParam %>&langedit=french">French</a>
	 <br><a href="EditCardLanguage.jsp?chosenWordId=<%= wordIdParam %>&langedit=spanish">Spanish</a>    
</body>
</body>
</html>