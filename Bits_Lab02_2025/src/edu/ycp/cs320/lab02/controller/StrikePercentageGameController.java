package edu.ycp.cs320.lab02.controller;

import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;
import edu.ycp.cs320.booksdb.model.Shot;
import java.util.ArrayList;

public class StrikePercentageGameController {

	private IDatabase db = null;

	public StrikePercentageGameController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}
	
	public Double AllStrikePercentage() {
		ArrayList<Shot> shotList = db.findAllShots();
		Double numStrikes = 0.0;
		Double numShots = 0.0;
		Double numFrames = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			numShots++;
			if (shot.getCount().equals("X")) {
			    numStrikes++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			numFrames = numShots/2;
			percentResult = (((numStrikes)/(numFrames))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double StrikePercentageFrame(String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrame(frameNum);
		Double numStrikes = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			numShots++;
			if (shot.getCount().equals("X")) {
			    numStrikes++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double StrikePercentageEvent(String event) {
		ArrayList<Shot> shotList = db.findAllShotsGivenEvent(event);
		Double numStrikes = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			numShots++;
			if (shot.getCount().equals("X")) {
			    numStrikes++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double StrikePercentageSeason(String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenSeason(season);
		Double numStrikes = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			numShots++;
			if (shot.getCount().equals("X")) {
			    numStrikes++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double StrikePercentageFrameEvent(String event, String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameEvent(event, frameNum);
		Double numStrikes = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			numShots++;
			if (shot.getCount().equals("X")) {
			    numStrikes++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double StrikePercentageFrameSeason(String frameNum, String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameSeason(frameNum, season);
		Double numStrikes = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			numShots++;
			if (shot.getCount().equals("X")) {
			    numStrikes++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double StrikePercentageEventSeason(String event, String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenEventSeason(event, season);
		Double numStrikes = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			numShots++;
			if (shot.getCount().equals("X")) {
			    numStrikes++;
			}
		}
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double StrikePercentageFrameEventSeason(String event, String season, String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameEventSeason(event, season, frameNum);
		Double numStrikes = 0.0;
		Double numShots = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			numShots++;
			if (shot.getCount().equals("X")) {
			    numStrikes++;
			}
		}
		
		System.out.println(numStrikes);
		
		if (numShots == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numShots))*200.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
}