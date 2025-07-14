package edu.ycp.cs320.booksdb.model;

public class Horse {
	
	private int clientID;
	private String barnName;
	private String showName;
	private String breed;
	private String height;
	private String sport;
	
	
	public Horse() {
		
	}


	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getBarnName() {
		return barnName;
	}
	public void setBarnName(String barnName) {
		this.barnName = barnName;
	}

	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}

	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}

}
