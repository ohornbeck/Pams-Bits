package edu.ycp.cs320.lab02.controller;

import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;
import edu.ycp.cs320.booksdb.model.Shot;
import java.util.ArrayList;

public class CarryPercentageController {

	private IDatabase db = null;

	public CarryPercentageController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}
	
	public Double AllCarryPercentage() {
		ArrayList<Shot> shotList = db.findAllShots();
		Double numPockets = 0.0;
		Double numStrikes = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			if ((shot.getCount().equals("X")) && (shot.getBoard().equals("pocket"))) {
			    numStrikes++;
			}
		}
		
		if (numPockets == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numPockets))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double CarryPercentageFrame(String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrame(frameNum);
		Double numPockets = 0.0;
		Double numStrikes = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			if ((shot.getCount().equals("X")) && (shot.getBoard().equals("pocket"))) {
			    numStrikes++;
			}
		}
		
		if (numPockets == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numPockets))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double CarryPercentageEvent(String event) {
		ArrayList<Shot> shotList = db.findAllShotsGivenEvent(event);
		Double numPockets = 0.0;
		Double numStrikes = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			if ((shot.getCount().equals("X")) && (shot.getBoard().equals("pocket"))) {
			    numStrikes++;
			}
		}
		
		if (numPockets == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numPockets))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double CarryPercentageSeason(String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenSeason(season);
		Double numPockets = 0.0;
		Double numStrikes = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			if ((shot.getCount().equals("X")) && (shot.getBoard().equals("pocket"))) {
			    numStrikes++;
			}
		}
		
		if (numPockets == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numPockets))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double CarryPercentageFrameEvent(String event, String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameEvent(event, frameNum);
		Double numPockets = 0.0;
		Double numStrikes = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			if ((shot.getCount().equals("X")) && (shot.getBoard().equals("pocket"))) {
			    numStrikes++;
			}
		}
		
		if (numPockets == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numPockets))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double CarryPercentageFrameSeason(String frameNum, String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameSeason(frameNum, season);
		Double numPockets = 0.0;
		Double numStrikes = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			if ((shot.getCount().equals("X")) && (shot.getBoard().equals("pocket"))) {
			    numStrikes++;
			}
		}
		
		if (numPockets == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numPockets))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double CarryPercentageEventSeason(String event, String season) {
		ArrayList<Shot> shotList = db.findAllShotsGivenEventSeason(event, season);
		Double numPockets = 0.0;
		Double numStrikes = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			if ((shot.getCount().equals("X")) && (shot.getBoard().equals("pocket"))) {
			    numStrikes++;
			}
		}
		
		if (numPockets == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numPockets))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
	
	public Double CarryPercentageFrameEventSeason(String event, String season, String frameNum) {
		ArrayList<Shot> shotList = db.findAllShotsGivenFrameEventSeason(event, season, frameNum);
		Double numPockets = 0.0;
		Double numStrikes = 0.0;
		Double percentResult = 0.0;
		
		for (Shot shot : shotList) {
			System.out.println(shot.getBoard());
			if (shot.getBoard().equals("pocket")) {
			    numPockets++;
			}
		}
		
		for (Shot shot : shotList) {
			System.out.println(shot.getCount());
			if ((shot.getCount().equals("X")) && (shot.getBoard().equals("pocket"))) {
			    numStrikes++;
			}
		}
		
		if (numPockets == 0.0) {
		    return 0.0;  // or 0.0, or set errorMessage
		} else {
			percentResult = (((numStrikes)/(numPockets))*100.0);
			System.out.println(percentResult);
			return percentResult;
		}
	}
}