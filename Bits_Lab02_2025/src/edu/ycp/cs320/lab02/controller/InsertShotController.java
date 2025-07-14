package edu.ycp.cs320.lab02.controller;

import java.sql.Date;

import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class InsertShotController {

	private IDatabase db = null;

	public InsertShotController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public Integer insertShot(String shotNumber, int gameID, int frameNumber, String count, String leave, String score, String type, String board, String lane, String ball) {
		
		// insert new book (and possibly new author) into DB
		Integer shot_id = db.insertShotIntoGame(shotNumber, gameID, frameNumber, count, leave, score, type, board, lane, ball);

		// check if the insertion succeeded
		if (shot_id > 0)
		{
			System.out.println("New Session (ID: " + shot_id + ") successfully added to Session table");
			
			return shot_id;
		}
		else
		{
			System.out.println("Failed to insert new session (ID: " + shot_id + ") into Sessions table");
			
			return shot_id;
		}
	}
}
