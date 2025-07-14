package edu.ycp.cs320.booksdb.model;

public class Shot {
	
	private String shotNumber;
	private int gameID;
	private int frameNumber;
	private String count;
	private String leave;
	private String score;
	private String type;
	private String board;
	private String lane;
	private String ball;
	
	
	public Shot() {
		
	}
	
	
	public String getShotNumber() {
		return shotNumber;
	}
	public void setShotNumber(String shotNumber) {
		this.shotNumber = shotNumber;
	}

	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

	
	public String getLeave() {
		return leave;
	}
	public void setLeave(String leave) {
		this.leave = leave;
	}

	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}

	
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}

	
	public String getBall() {
		return ball;
	}
	public void setBall(String ball) {
		this.ball = ball;
	}


	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}


	public int getFrameNumber() {
		return frameNumber;
	}
	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	
}
