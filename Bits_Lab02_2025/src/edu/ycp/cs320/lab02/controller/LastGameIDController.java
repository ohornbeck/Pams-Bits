package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Book;
import edu.ycp.cs320.booksdb.model.Pair;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class LastGameIDController {

	private IDatabase db    = null;

	public LastGameIDController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public Integer getLastGameID() {
		
		// get the list of (Author, Book) pairs from DB
		int gameID = db.getLastInsertedGameID();
		
		if (gameID == -1) {
			System.out.println("Something went wrong with last game insertion");
			return null;
		}
		else {
			System.out.println("Last inserted gameID : " + gameID);
				
		}
		
		// return last game ID inserted into database
		return gameID;
	}
}