package edu.ycp.cs320.lab02.controller;

import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class InsertBallController {

	private IDatabase db = null;

	public InsertBallController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public boolean insertBallIntoArsenal (String longname, String shortname, String brand, String type, String core, String cover, String color, String surface, String year, String serialNumber, String weight, String mapping) {
		
		// insert new book (and possibly new author) into DB
		Integer ball_id = db.insertBallIntoArsenal(longname, shortname, brand, type, core, cover, color, surface, year, serialNumber, weight, mapping);

		// check if the insertion succeeded
		if (ball_id > 0) {
			System.out.println("New Ball (ID: " + ball_id + ") successfully added to Arsenal table: <" + longname + ">");
			return true;
		} else {
			System.out.println("Failed to insert new ball (ID: " + ball_id + ") into Arsenal table: <" + longname + ">");
			return false;
		}
	}
}
