package edu.ycp.cs320.booksdb.model;

public class Event {
	//private int establishmentId;
	private String longname;
	private String shortname;
	private String type;
	private String establishment;
	private String season;
	private int team;
	private String composition;
	private String day;
	private String time;
	private String start;
	private String end;
	private int gamesPerSession;
	private int weeks;
	private int playoffs;
	
	
	public Event() {
		
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;	
	}
	
	public String getEstablishment() {
		return establishment;
	}
	
	public void setEstablishment(String establishment) {
		this.establishment = establishment;
	}
	
	public String getSeason() {
		return season;
	}
	
	public void setSeason(String season) {
		this.season = season;
	}
	
	public int getTeam() {
		return team;
	}
	
	public void setTeam(int team) {
		this.team = team;
	}
	
	public String getComposition() {
		return composition;
	}
	
	public void setComposition(String composition) {
		this.composition = composition;
	}

	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}

	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}

	public int getGamesPerSession() {
		return gamesPerSession;
	}
	public void setGamesPerSession(int gamesPerSession) {
		this.gamesPerSession = gamesPerSession;
	}
	
	public int getWeeks() {
		return weeks;
	}
	
	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}
	
	public int getPlayoffs() {
		return playoffs;
	}
	
	public void setPlayoffs(int playoffs) {
		this.playoffs = playoffs;
	}
}
