package edu.ycp.cs320.lab02.controller;

import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class InsertEventController {

	private IDatabase db = null;

	public InsertEventController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public boolean insertEvent(String longname, String shortname, String type, String establishment, String season, Integer team, String composition, String day, String time, String start, String end, Integer gamesPerSession, Integer weeks, Integer playoffs) {
		
		// insert new book (and possibly new author) into DB
		Integer event_id = db.insertEvent(longname, shortname, type, establishment, season, team, composition, day, time, start, end, gamesPerSession, weeks, playoffs);

		// check if the insertion succeeded
		if (event_id > 0)
		{
			System.out.println("New Event (ID: " + event_id + ") successfully added to Events table: <" + longname + ">");
			
			return true;
		}
		else
		{
			System.out.println("Failed to insert new Event (ID: " + event_id + ") into Events table: <" + longname + ">");
			
			return false;
		}
	}
}