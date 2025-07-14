package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import java.util.List;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;
import edu.ycp.cs320.booksdb.model.Shot;

public class FindAllShotsInGameController {

	private IDatabase db = null;

	public FindAllShotsInGameController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public ArrayList<Shot> findAllShotsInGame(String gameID) {
		
		// get the list of shots from DB
		System.out.println("Receiving shot list");
		ArrayList<Shot> shotList = db.findAllShotsInGame(gameID);
		System.out.println("Shot list recieved");
		ArrayList<Shot> shots = null;
		
		if (shotList.isEmpty()) {
			System.out.println("No shots found for this game.");
			return null;
		}
		else {
			shots = new ArrayList<Shot>();
			for (Shot shot : shotList) {
				shots.add(shot);
				System.out.println(shot.getShotNumber() + ", " + shot.getScore());
			}			
		}
		
		// return shots for this game
		return shots;
	}
}

