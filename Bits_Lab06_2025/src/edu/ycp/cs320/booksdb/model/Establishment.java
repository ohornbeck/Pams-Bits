package edu.ycp.cs320.booksdb.model;

public class Establishment {
	private String longname;
	private String shortname;
	private String address;
	private String phone;
	private Integer lanes;
	private String type;
	
	public Establishment() {
		
	}

	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getLanes() {
		return lanes;
	}

	public void setLanes(Integer lanes) {
		this.lanes = lanes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
