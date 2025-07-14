package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Establishment;
import edu.ycp.cs320.booksdb.model.Shot;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class StatsController {

	private IDatabase db = null;

	public StatsController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	

		public Integer OverallGameAverageforEvent(Integer date) {
		
			// get the list of shots from DB
			ArrayList<Average> gameList = db.findAllShotsInGame(gameID);
			ArrayList<Game> shots = null;
		
			if (gameList.isEmpty()) {
				System.out.println("No games found for this event.");
				return null;
			}
			else {
				shots = new ArrayList<Shot>();
				for (Shot shot : shotList) {
					shots.add(shot);
				System.out.println(shot.getShotNumber() + ", " + shot.getScore());
				}			
			}
		
		// return overall average
		return average;
		}
	}
}



