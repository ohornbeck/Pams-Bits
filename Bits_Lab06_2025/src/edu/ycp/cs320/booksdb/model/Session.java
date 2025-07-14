package edu.ycp.cs320.booksdb.model;

import java.sql.Date;

public class Session {
	
	private int eventId;
	
	private String league;
	private String season;
	private String house;
	
	//this is the session ID 
	private int week;
	//Will possibly need to import DateFormat to parse
	private Date scheduled;
	private Date bowled;
	//this may be boolean if applicable
	private String regSub;
	private String opponent;
	private String lanes;
	//start is the lane started on
	private int start;
	private String ball;
	//game scores
	private int gameOneScore = 0;
	private int gameTwoScore = 0;
	private int gameThreeScore = 0;
	private int series = 0;
	//make the games;
	/*
	private Game gameOne;
	private Game gameTwo;
	private Game gameThree;
	*/
	
	//lg = league
	private int lgTotal;
	private int lgGames;
	private double lgAve;
	//ave = average
	private int allTotal;
	private int allGames;
	private double allAve;
	
	private float wins;
	private float losses;
	//h = home (?)
	private int hWins;
	private int hLosses;
	private int hPosition;
	
	private int lWins;
	private int lLosses;
	private String lPosition;
	
	private int frames;
	private int pocket;
	private double pocketPer;
	//per = percentage
	private int strikes;
	private int bndl;
	private double strikePer;
	private double carryPer;
	
	private int high;
	private int low;
	private int leaves;
	private int spares;
	private int attempts;
	private double conversionPer;
	private int right;
	private int left;
	private int misses;
	private int splits;
	private int washouts;
	private int opens;
	
	
	public Session() {
		
	}


	public String getLeague() {
		return league;
	}


	public void setLeague(String league) {
		this.league = league;
	}


	public String getSeason() {
		return season;
	}


	public void setSeason(String season) {
		this.season = season;
	}


	public String getHouse() {
		return house;
	}


	public void setHouse(String house) {
		this.house = house;
	}


	public int getWeek() {
		return week;
	}


	public void setWeek(int week) {
		this.week = week;
	}


	public Date getScheduled() {
		return scheduled;
	}


	public void setScheduled(Date scheduled) {
		this.scheduled = scheduled;
	}


	public Date getBowled() {
		return bowled;
	}


	public void setBowled(Date bowled) {
		this.bowled = bowled;
	}


	public String getRegSub() {
		return regSub;
	}


	public void setRegSub(String regSub) {
		this.regSub = regSub;
	}


	public String getOpponent() {
		return opponent;
	}


	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}


	public String getLanes() {
		return lanes;
	}


	public void setLanes(String lanes) {
		this.lanes = lanes;
	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public String getBall() {
		return ball;
	}


	public void setBall(String ball) {
		this.ball = ball;
	}


	public int getSeries() {
		return series;
	}


	public void setSeries(int series) {
		this.series = series;
	}



	public int getLgTotal() {
		return lgTotal;
	}


	public void setLgTotal(int lgTotal) {
		this.lgTotal = lgTotal;
	}


	public int getLgGames() {
		return lgGames;
	}


	public void setLgGames(int lgGames) {
		this.lgGames = lgGames;
	}


	public double getLgAve() {
		return lgAve;
	}


	public void setLgAve(double lgAve) {
		this.lgAve = lgAve;
	}


	public int getAllTotal() {
		return allTotal;
	}


	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}


	public int getAllGames() {
		return allGames;
	}


	public void setAllGames(int allGames) {
		this.allGames = allGames;
	}


	public double getAllAve() {
		return allAve;
	}


	public void setAllAve(double allAve) {
		this.allAve = allAve;
	}


	public float getWins() {
		return wins;
	}


	public void setWins(float wins) {
		this.wins = wins;
	}


	public float getLosses() {
		return losses;
	}


	public void setLosses(float losses) {
		this.losses = losses;
	}


	public int gethWins() {
		return hWins;
	}


	public void sethWins(int hWins) {
		this.hWins = hWins;
	}


	public int gethLosses() {
		return hLosses;
	}


	public void sethLosses(int hLosses) {
		this.hLosses = hLosses;
	}


	public int gethPosition() {
		return hPosition;
	}


	public void sethPosition(int hPosition) {
		this.hPosition = hPosition;
	}


	public int getlWins() {
		return lWins;
	}


	public void setlWins(int lWins) {
		this.lWins = lWins;
	}


	public int getlLosses() {
		return lLosses;
	}


	public void setlLosses(int lLosses) {
		this.lLosses = lLosses;
	}


	public String getlPosition() {
		return lPosition;
	}


	public void setlPosition(String lPosition) {
		this.lPosition = lPosition;
	}


	public int getFrames() {
		return frames;
	}


	public void setFrames(int frames) {
		this.frames = frames;
	}


	public int getPocket() {
		return pocket;
	}


	public void setPocket(int pocket) {
		this.pocket = pocket;
	}


	public double getPocketPer() {
		return pocketPer;
	}


	public void setPocketPer(double pocketPer) {
		this.pocketPer = pocketPer;
	}


	public int getStrikes() {
		return strikes;
	}


	public void setStrikes(int strikes) {
		this.strikes = strikes;
	}


	public int getBndl() {
		return bndl;
	}


	public void setBndl(int bndl) {
		this.bndl = bndl;
	}


	public double getStrikePer() {
		return strikePer;
	}


	public void setStrikePer(double strikePer) {
		this.strikePer = strikePer;
	}


	public double getCarryPer() {
		return carryPer;
	}


	public void setCarryPer(double carryPer) {
		this.carryPer = carryPer;
	}


	public int getHigh() {
		return high;
	}


	public void setHigh(int high) {
		this.high = high;
	}


	public int getLow() {
		return low;
	}


	public void setLow(int low) {
		this.low = low;
	}


	public int getLeaves() {
		return leaves;
	}


	public void setLeaves(int leaves) {
		this.leaves = leaves;
	}


	public int getSpares() {
		return spares;
	}


	public void setSpares(int spares) {
		this.spares = spares;
	}


	public int getAttempts() {
		return attempts;
	}


	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}


	public double getConversionPer() {
		return conversionPer;
	}


	public void setConversionPer(double conversionPer) {
		this.conversionPer = conversionPer;
	}


	public int getRight() {
		return right;
	}


	public void setRight(int right) {
		this.right = right;
	}


	public int getLeft() {
		return left;
	}


	public void setLeft(int left) {
		this.left = left;
	}


	public int getMisses() {
		return misses;
	}


	public void setMisses(int misses) {
		this.misses = misses;
	}


	public int getSplits() {
		return splits;
	}


	public void setSplits(int splits) {
		this.splits = splits;
	}


	public int getWashouts() {
		return washouts;
	}


	public void setWashouts(int washouts) {
		this.washouts = washouts;
	}


	public int getOpens() {
		return opens;
	}


	public void setOpens(int opens) {
		this.opens = opens;
	}


	public int getEventId() {
		return eventId;
	}


	public void setEventId(int eventId) {
		this.eventId = eventId;
	}


	public int getGameOneScore() {
		return gameOneScore;
	}


	public void setGameOneScore(int gameOneScore) {
		this.gameOneScore = gameOneScore;
	}


	public int getGameTwoScore() {
		return gameTwoScore;
	}


	public void setGameTwoScore(int gameTwoScore) {
		this.gameTwoScore = gameTwoScore;
	}


	public int getGameThreeScore() {
		return gameThreeScore;
	}


	public void setGameThreeScore(int gameThreeScore) {
		this.gameThreeScore = gameThreeScore;
	}
	
	/*
	//the single setters could be used to signify edits to scores and additionally
	//change a boolean flag value.
	public Game getGameOne() {
		return gameOne;
	}


	public void setGameOne(Game gameOne) {
		this.gameOne = gameOne;
	}
	
	public Game getGameTwo() {
		return gameTwo;
	}


	public void setGameTwo(Game gameTwo) {
		this.gameTwo = gameTwo;
	}
	
	public Game getGameThree() {
		return gameThree;
	}


	public void setGameThree(Game gameThree) {
		this.gameThree = gameThree;
	}
	
	//will need a method that handles being passed an ArrayList<Game>
	//and split them apart into different games.
	
	*/


}