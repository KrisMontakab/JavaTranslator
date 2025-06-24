<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.javatpoint.*" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<center><h1 style="font-size:2vw"> All Cards
</center>
<meta charset="ISO-8859-1">
<title>View Cards</title>
</head>
<body>
<center>
    <h2>All Cards</h2>
    <form action="WordListServlet" method="post">
        <table style="border-spacing: 20px; border-collapse: separate;"> <!-- Space between columns -->
            <% 
            // Get the sorted list of words
            ArrayList<Long> theAllWords = Dictionary.getwordIdArray();
            HashMap<Long, WordBasic> allWordsMap = Dictionary.getallBasicWords();
            theAllWords.sort((id1, id2) -> allWordsMap.get(id1).getEnglish().compareToIgnoreCase(allWordsMap.get(id2).getEnglish()));

            int columnCount = 6; // Number of columns
            int totalWords = theAllWords.size();
            int wordsPerColumn = (int) Math.ceil((double) totalWords / columnCount); // Words per column
            
            // Create arrays to store each column's words
            List<WordBasic>[] columns = new ArrayList[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columns[i] = new ArrayList<>();
            }
            
            // Distribute words into columns
            for (int i = 0; i < totalWords; i++) {
                Long wordId = theAllWords.get(i);
                WordBasic nextWord = allWordsMap.get(wordId);
                if (nextWord != null) {
                    int columnIndex = i / wordsPerColumn;
                    columns[columnIndex].add(nextWord);
                }
            }
            // Output the table with each column reading down
            for (int row = 0; row < wordsPerColumn; row++) {
                %>
                <tr>
                    <% 
                    // Loop over columns to print each word in the row
                    for (int col = 0; col < columnCount; col++) {
                        if (row < columns[col].size()) { // Check if there's a word at this row for the current column
                            WordBasic word = columns[col].get(row);
                    %>
                        <td>
                            <a href="EditLangSelect.jsp?wordId=<%= word.getId() %>"><%= word.getEnglish() %></a>
                        </td>
                    <% 
                        } else {
                    %>
                        <td></td> <!-- Empty cell if there are no more words in this column -->
                    <% 
                        } 
                    } 
                    %>
                </tr>
            <% } %>
        </table>
        <br><a href="AddNew.jsp">Add New Card</a><br><br>
        <a href="GermanFlash.jsp">Go Back!</a>
    </form>
</center>
</form>
</body>
</html>