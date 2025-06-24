package com.javatpoint;

public class WordBasic {
	private Long id;
	private String english;
	private String image;
	
	public WordBasic(Long id, String english, String image) {
		this.id = id;
	    this.english = english;
	    this.image = image;
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
}
