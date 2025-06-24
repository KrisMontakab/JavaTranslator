package com.javatpoint;

public class Word {
	private Long id;
	private String english;
	private String image;
	private String german;
	private String gender;
	private String plural;
	
	public Word(Long id, String english, String image, String german, String gender, String plural) {
		this.id = id;
	    this.english = english;
	    this.image = image;
	    this.german = german;
	    this.gender = gender;
	    this.plural = plural;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getGerman() {
		return german;
	}
	public void setGerman(String german) {
		this.german = german;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPlural() {
		return plural;
	}
	public void setPlural(String plural) {
		this.plural = plural;
	}
	
    public void setGermanDetails(String wordGerman, String wordGender, String wordPlural) {
        this.german = wordGerman;
        this.gender = wordGender;
        this.plural = wordPlural;
    }

	
}
