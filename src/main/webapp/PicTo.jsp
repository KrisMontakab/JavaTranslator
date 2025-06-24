</html><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javatpoint.*" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Random"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<%
String categoryIdParam = request.getParameter("categoryId");
Long categoryId = null;
Word randomWord = null;
String langPic = request.getParameter("langpic");
String languagePic = "";
languagePic = langPic;
    if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
        categoryId = Long.parseLong(categoryIdParam);
        
        List<Word> wordsInCategory = Dictionary.getGermanWordsInCategory(categoryId, languagePic);

        if (wordsInCategory != null && !wordsInCategory.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(wordsInCategory.size());
            randomWord = wordsInCategory.get(randomIndex);
        }
    }

    if (randomWord != null) {
%>
        <h2>English to <%=languagePic%></h2>
        <p>English: <%= randomWord.getEnglish() %></p>
        <img src="<%= randomWord.getImage() %>" alt="Image of <%= randomWord.getEnglish() %>" style="width:179px;height:113px;"><br>
			<br><button type="button" onclick="revealWord()">Reveal <%= languagePic %> Word</button><br>
        <div id="<%= languagePic %>Word" style="display: none;">
        <p><%=languagePic%> Word: <%= randomWord.getGerman() %><br>
            Plural: <%= randomWord.getPlural() %><br>
            Gender: <%= randomWord.getGender() %><br></p>
        </div>
		<form action="PicTo.jsp" method="post">
				<input type="hidden" name="langpic" value="<%= languagePic %>">
		    <input type="hidden" name="categoryId" value="<%= categoryId %>" />
		    <input type="submit" value="Next Word" />
		</form>
		     <br><a href="GermanFlash.jsp">Go Back!</a>  
<%
    } else {
%>
        <p>No words available in the selected category.</p>
<%
    }
%>
        <script>
        function revealWord() {
        	var languagePic = "<%= languagePic %>";
            document.getElementById(languagePic + "Word").style.display = "block";
        }
    </script>
