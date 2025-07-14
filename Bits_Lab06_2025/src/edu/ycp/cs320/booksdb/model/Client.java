package edu.ycp.cs320.booksdb.model;

public class Client {
	
	private String firstName;
	private String lastName;
	private String farmName;
	private String address;
	private int horse1;
	private int horse2;
	private int horse3;
	private String comment;
	
	
	public Client() {
		
	}


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFarmName() {
		return farmName;
	}
	public void setFarmName(String barnName) {
		this.farmName = barnName;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public int getHorse1() {
		return horse1;
	}
	public void setHorse1(int horse1) {
		this.horse1 = horse1;
	}

	public int getHorse2() {
		return horse2;
	}
	public void setHorse2(int horse2) {
		this.horse2 = horse2;
	}

	public int getHorse3() {
		return horse3;
	}
	public void setHorse3(int horse3) {
		this.horse3 = horse3;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}


}
