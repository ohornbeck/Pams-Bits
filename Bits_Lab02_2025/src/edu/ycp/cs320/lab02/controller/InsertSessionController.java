package edu.ycp.cs320.lab02.controller;

import java.sql.Date;

import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class InsertSessionController {

	private IDatabase db = null;

	public InsertSessionController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public Integer insertSession(String league, Date bowled, String ball, int startLane) {
		
		// insert new book (and possibly new author) into DB
		Integer weekID = db.insertSession(league, bowled, ball, startLane);

		// check if the insertion succeeded
		if (weekID > 0)
		{
			System.out.println("New Session (ID: " + weekID + ") successfully added to Session table");
			
			return weekID;
		}
		else
		{
			System.out.println("Failed to insert new session (ID: " + weekID + ") into Sessions table");
			
			return weekID;
		}
	}
}
