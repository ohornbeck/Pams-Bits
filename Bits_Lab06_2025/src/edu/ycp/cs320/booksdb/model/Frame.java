package edu.ycp.cs320.booksdb.model;

public class Frame {
	
	private int gameID;
	private String strikeBall;
	private String spareBall;
	private String frameNumber;
	private String frameScore;
	
	
	public Frame() {
	
	}

	
	public String getStrikeBall() {
		return strikeBall;
	}
	public void setStrikeBall(String strikeBall) {
		this.strikeBall = strikeBall;
	}

	
	public String getSpareBall() {
		return spareBall;
	}
	public void setSpareBall(String spareBall) {
		this.spareBall = spareBall;
	}

	
	public String getFrameNumber() {
		return frameNumber;
	}
	public void setFrame(String frameNumber) {
		this.frameNumber = frameNumber;
	}
	
	
	public String getFrameScore() {
		return frameScore;
	}
	public void setFrameScore(String frameScore) {
		this.frameScore = frameScore;
	}

	
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
}
