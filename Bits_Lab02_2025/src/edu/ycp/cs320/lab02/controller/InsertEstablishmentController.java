package edu.ycp.cs320.lab02.controller;

import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class InsertEstablishmentController {

	private IDatabase db = null;

	public InsertEstablishmentController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public boolean insertEstablishmentIntoLibrary(String longName, String shortName, String address, String phone, Integer lanes, String type) {
		
		// insert new establishment into DB
		Integer establishment_id = db.insertEstablishmentIntoEstablishmentsTable(longName, shortName, address, phone, lanes, type);

		// check if the insertion succeeded
		if (establishment_id > 0)
		{
			System.out.println("New establishment (ID: " + establishment_id + ") successfully added to Establishments table: <" + longName + ">");
			
			return true;
		}
		else
		{
			System.out.println("Failed to insert new establishment (ID: " + establishment_id + ") into Establishments table: <" + longName + ">");
			
			return false;
		}
	}
}
