package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import java.util.List;
import edu.ycp.cs320.booksdb.model.Bit;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class BitsController {

	private IDatabase db = null;

	public BitsController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public ArrayList<Bit> getAllBits() {
		
		// get the list of (Author, Book) pairs from DB
		ArrayList<Bit> bitList = db.findAllBits();
		ArrayList<Bit> bits = null;
		
		if (bitList.isEmpty()) {
			System.out.println("No bits found in bit table");
			return null;
		}
		else {
			bits = new ArrayList<Bit>();
			for (Bit bit : bitList) {
				bits.add(bit);
				System.out.println(bit.getType() + ", " + bit.getCheekpiece());
			}			
		}
		
		// return authors for this title
		// return arsenal;
		return bitList;
	}
}

