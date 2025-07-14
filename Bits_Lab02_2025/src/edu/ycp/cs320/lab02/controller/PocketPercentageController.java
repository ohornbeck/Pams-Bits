package edu.ycp.cs320.lab02.controller;

import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;
import edu.ycp.cs320.booksdb.model.Shot;
import java.util.ArrayList;

public class PocketPercentageController {

	private IDatabase db = null;

	public PocketPercentageController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}
	
	public Double AllPocketPercentage() {
		ArrayList<Shot> shotList = db.findAllShots();
		Double numPockets = 0.0;
		Double numShots = 0.0;
		Double numFrames = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			numShots++;
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			numFrames = numShots/2;
			percentResult = (((numPockets)/(numFrames))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double PocketPercentageFrame(String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrame(frameNum);
		Double numPockets = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			numShots++;
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numPockets)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double PocketPercentageEvent(String event) {
		ArrayList<Shot> shotList = db.findAllShotsGivenEvent(event);
		Double numPockets = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			numShots++;
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numPockets)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double PocketPercentageSeason(String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenSeason(season);
		Double numPockets = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			numShots++;
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numPockets)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double PocketPercentageFrameEvent(String event, String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameEvent(event, frameNum);
		Double numPockets = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			numShots++;
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numPockets)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double PocketPercentageFrameSeason(String frameNum, String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameSeason(frameNum, season);
		Double numPockets = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			numShots++;
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numPockets)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double PocketPercentageEventSeason(String event, String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenEventSeason(event, season);
		Double numPockets = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			numShots++;
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numPockets)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double PocketPercentageFrameEventSeason(String event, String season, String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameEventSeason(event, season, frameNum);
		Double numPockets = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			numShots++;
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numPockets)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
}