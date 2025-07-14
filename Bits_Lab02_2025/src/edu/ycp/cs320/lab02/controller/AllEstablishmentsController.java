package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Establishment;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class AllEstablishmentsController {

	private IDatabase db = null;

	public AllEstablishmentsController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public ArrayList<Establishment> getAllEstablishments() {
		
		// get the list of establishments from DB
		ArrayList<Establishment> establishmentList = db.findAllEstablishments();
		ArrayList<Establishment> establishments = null;
		
		if (establishmentList.isEmpty()) {
			System.out.println("No establishments found in library");
			return null;
		}
		else {
			establishments = new ArrayList<Establishment>();
			for (Establishment establishment : establishmentList) {
				establishments.add(establishment);
				System.out.println(establishment.getLongname() + ", " + establishment.getShortname() + ", " + establishment.getAddress() +  establishment.getPhone() + establishment.getLanes() + establishment.getType());
			}			
		}
		
		// return establishments for this title
		return establishments;
	}
}

