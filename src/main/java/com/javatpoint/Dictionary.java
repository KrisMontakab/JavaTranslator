package com.javatpoint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dictionary {
	private static Connection conn = null; 
	private static HashMap<Long, WordBasic> allBasicWords = new HashMap<Long, WordBasic>();
	private static HashMap<Long, Word> categoryWordMap = new HashMap<Long, Word>();
	public static ArrayList<Long> wordBasicIdArray = new ArrayList<Long>();
	public static List<Word> listOfWords = new ArrayList<Word>();
	public static List<Long> listOfWordIDs = new ArrayList<Long>();
	private static List<Long> categoryGerId = new ArrayList<Long>();
	private static List<String> categoryGerEng = new ArrayList<String>();
	private static List<String> categoryGerPic = new ArrayList<String>();
	private static List<String> categoryGerWord = new ArrayList<String>();
	private static List<String> categoryGerGender = new ArrayList<String>();
	private static List<String> categoryGerPlural = new ArrayList<String>();
	private static List<Long> categoryLoadId = new ArrayList<Long>();
	private static List<String> categoryLoadEng = new ArrayList<String>();
	private static List<String> categoryLoadPic = new ArrayList<String>();
	private static List<String> categoryLoadWord = new ArrayList<String>();
	private static List<String> categoryLoadGender = new ArrayList<String>();
	private static List<String> categoryLoadPlural = new ArrayList<String>();

	public static List<Long> listOfChosenWordIDs = new ArrayList<Long>();

	
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
	
    public static void main(String[] args){
    	load();
    }

    
	private static void load(){

		if (allBasicWords.size() > 0) {
			allBasicWords.clear();
			wordBasicIdArray.clear();
			categoryLoadId.clear();
			categoryLoadEng.clear();
			categoryLoadPic.clear();
			categoryLoadWord.clear();
			categoryLoadPlural.clear();
			categoryLoadGender.clear();
		}

		Statement wordStatement = null;
		ResultSet rs = null;
		Connection con = getConnection();
		WordBasic wordBasic;

		try{
			wordStatement = con.createStatement();
			rs = wordStatement.executeQuery("SELECT * FROM dictionarytable");
			while(rs.next()){
				Long wordId = rs.getLong("wordId");
				String wordEnglish = rs.getString("wordEnglish");
				String wordImage = rs.getString("wordImage");			
				categoryLoadId.add(wordId);
				categoryLoadEng.add(wordEnglish);
				categoryLoadPic.add(wordImage);	

				wordBasic = new WordBasic(wordId, wordEnglish, wordImage);
				allBasicWords.put(wordId, wordBasic);
    			wordBasicIdArray.add(wordId);


			}         	
			wordStatement.close();
			rs.close();
			
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static List<Word> getGermanWordsInCategory(Long categoryId, String languageChoice) {
		
		if (listOfWords.size() > 0) {
			listOfWords.clear();
			listOfWordIDs.clear();
			categoryWordMap.clear();
			categoryGerId.clear();
			categoryGerEng.clear();
			categoryGerPic.clear();
			categoryGerWord.clear();
			categoryGerPlural.clear();
			categoryGerGender.clear();
		}
		Statement wordStatement = null;
		ResultSet rs = null;
		Connection con = getConnection();
		Word word;
		
		try{
			wordStatement = con.createStatement();
			rs = wordStatement.executeQuery("SELECT * FROM word_category");
			while(rs.next()){
				Long SQLCategoryId = rs.getLong("categoryId");
				Long wordId = rs.getLong("wordId");
			
				if (categoryId == SQLCategoryId) {
					listOfWordIDs.add(wordId);
				}
			}         	
			wordStatement.close();
			rs.close();
			
			wordStatement = con.createStatement();
			rs = wordStatement.executeQuery("SELECT * FROM dictionarytable");	
			
			while(rs.next()){
				Long wordId = rs.getLong("wordId");
				String wordEnglish = rs.getString("wordEnglish");
				String wordImage = rs.getString("wordImage");
				
				
				for (Long listWordId: listOfWordIDs) {
					if (wordId == listWordId) {
						categoryGerId.add(wordId);
						categoryGerEng.add(wordEnglish);
						categoryGerPic.add(wordImage);			
					}
				}
			}      
			
			wordStatement.close();
			rs.close();
				
			wordStatement = con.createStatement();
			rs = wordStatement.executeQuery("SELECT * FROM " + languageChoice + "table");	
			
			while(rs.next()){
				Long wordId = rs.getLong("wordId");
				String wordGerman = rs.getString(languageChoice + "Word");
				String wordGender = rs.getString(languageChoice + "Gender");
				String wordPlural = rs.getString(languageChoice + "Plural");
				
				for (Long listOfWordId: listOfWordIDs) {
					if (wordId == listOfWordId) {
						categoryGerWord.add(wordGerman);
						categoryGerPlural.add(wordPlural);
						categoryGerGender.add(wordGender);		
					}
				}			
			}
			wordStatement.close();
			rs.close();
			}catch(Exception ex){
			ex.printStackTrace();
		}
		for (int i = 0; i < categoryGerId.size(); i++) {
		    Long wordId = categoryGerId.get(i);
		    String wordEnglish = categoryGerEng.get(i);
		    String wordImage = categoryGerPic.get(i);
		    String wordGerman = categoryGerWord.get(i);
		    String wordGender = categoryGerGender.get(i);
		    String wordPlural = categoryGerPlural.get(i);
		    word = new Word(wordId, wordEnglish, wordImage, wordGerman, wordGender, wordPlural);
		    listOfWords.add(word);
		}
		return listOfWords;
	}
	
	
	public static Word getWordInLanguage(Long chosenLangId, String chosenLangName) {
		
		Statement wordStatement = null;
		ResultSet rs = null;
		Connection con = getConnection();
		Word word;
		
		try{
			
			wordStatement = con.createStatement();
			rs = wordStatement.executeQuery("SELECT * FROM dictionarytable");	
			Long thisChosenLangId = null;
			String chosenLangEng = null;
			String chosenLangImage = null;
			while(rs.next()){
				Long ChosenWordId = rs.getLong("wordId");
				String ChosenWordEnglish = rs.getString("wordEnglish");
				String ChosenWordImage = rs.getString("wordImage");
				listOfChosenWordIDs.add(ChosenWordId);

					if (chosenLangId == ChosenWordId) {
						thisChosenLangId = ChosenWordId;
						chosenLangEng = ChosenWordEnglish;
						chosenLangImage = ChosenWordImage;			
				}
			}      
			
			wordStatement.close();
			rs.close();
				
			wordStatement = con.createStatement();
			rs = wordStatement.executeQuery("SELECT * FROM " + chosenLangName + "table");	
			String chosenLangTran = null;
			String chosenLangPl = null;
			String chosenLangGen = null;
			
			while(rs.next()){
				Long wordId = rs.getLong("wordId");
				String ChosenWordTran = rs.getString(chosenLangName + "Word");
				String ChosenWordGender = rs.getString(chosenLangName + "Gender");
				String ChosenWordPlural = rs.getString(chosenLangName + "Plural");
				
					if (chosenLangId == wordId) {
						chosenLangTran = ChosenWordTran;
						chosenLangPl = ChosenWordPlural;
						chosenLangGen = ChosenWordGender;		
				}			
			}
			wordStatement.close();
			rs.close();
			
		    Word chosenLangWord = new Word(thisChosenLangId, chosenLangEng, chosenLangImage, chosenLangTran, chosenLangGen, chosenLangPl);
			return chosenLangWord;

			}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}

	}
	
		
	public static ArrayList<Long> getwordIdArray() {
		load();
		return wordBasicIdArray;
	}
	public static HashMap<Long, WordBasic> getallBasicWords() {
		load();
		return allBasicWords;
	}
	
}