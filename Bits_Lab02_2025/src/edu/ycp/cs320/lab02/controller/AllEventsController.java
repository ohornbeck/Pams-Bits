package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;
import edu.ycp.cs320.booksdb.model.Event;

public class AllEventsController {

	private IDatabase db = null;

	public AllEventsController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public ArrayList<Event> getEvents() {
		
		// get the list of (Author, Book) pairs from DB
		ArrayList<Event> eventList = db.findAllEvents();
		ArrayList<Event> events = null;
		
		if (eventList.isEmpty()) {
			System.out.println("No events found in library");
			return null;
		}
		else {
			events = new ArrayList<Event>();
			for (Event event : eventList) {
				events.add(event);
				System.out.println(event.getLongname() + ", " + event.getShortname());
			}			
		}
		
		// return authors for this title
		return events;
	}
}

