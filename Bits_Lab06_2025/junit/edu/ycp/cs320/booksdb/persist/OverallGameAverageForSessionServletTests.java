package edu.ycp.cs320.booksdb.persist;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Ball;
import edu.ycp.cs320.booksdb.model.Book;
import edu.ycp.cs320.booksdb.model.Establishment;
import edu.ycp.cs320.booksdb.model.Event;
import edu.ycp.cs320.booksdb.model.Pair;
import edu.ycp.cs320.booksdb.model.Shot;
//import edu.ycp.cs320.booksdb.persist.DerbyDatabase.Transaction;

public class OverallGameAverageForSessionServletTests {

	private IDatabase db = null;
	
	ArrayList<Ball> arsenal = null;
	ArrayList<Establishment> establishments = null;
	ArrayList<Event> event = null;
	ArrayList <Shot> shot = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	//test was created on 4/27/25 @ 10pm, this test shows a new establishment being added
	@Test
	public void testInsertEstablishmentIntoEstablishmentsTable() {
		System.out.println("\n*** Testing insertEstablishmentIntoEstablishmentsTable ***");

		String longname    	= "Laser Alleys";
		String shortname    = "LA";
		String address 		= "3905 E. Market St  York  PA  17402";
		String phone    	= "(717) 755-2946";
		int lanes    	= 30;
		String type    	= "Brunswick";
  
				
		// insert new book (and possibly new author) into DB
		Integer establishment_id = db.insertEstablishmentIntoEstablishmentsTable(longname, shortname, address, phone, lanes, type);

		// check the return value - should be a book_id > 0
		if (establishment_id > 0)
		{
			// try to retrieve the book and author from the DB
			// get the list of (Author, Book) pairs from DB
			establishments = db.findAllEstablishments();
			
			if (establishments.isEmpty()) {
				System.out.println("No establishments found within establishments table <" + establishment_id + ">");
				fail("Failed to insert new establishment <" + longname + "> into DB");
			}
			// otherwise, the test was successful.  Now remove the book just inserted to return the DB
			// to it's original state, except for using an author_id and a book_id
			else {
				System.out.println("New establishment: " + longname + ") successfully added to establishments table: <" + establishment_id + ">");
				
				// now delete establishment from DB
				// leaving the DB in its previous state - except that an author_id, and a book_id have been used
				//we do not have this function implemented yet
			}
		}
		else
		{
			System.out.println("Failed to insert new establishment: " + longname + ") into establishments table: <" + establishment_id + ">");
			fail("Failed to insert new establishment <" + longname + "> into DB");
		}
	}
	
	//test was created on 4/27/25 @ 10pm, this test shows a new ball being added to arsenal
	@Test
	public void testInsertBallIntoArsenal() {
		System.out.println("\n*** Testing insertBallIntoArsenal ***");
		
		String longname 	= "Blue Pearl Hammer";
		String shortname 	= "BPH"; 
		String brand 		= "Hammer";
		String type 		= "Urethane";
		String core 		= "Symmetric";
		String cover 		= "Solid";
		String color 		= "Blue";
		String surface 		= "?";
		String year 		= "2025";
		String serialNumber = "1BVJ9KL0O";
		String weight 		= "15";
		String mapping 		= "?";
  
					
		// insert new book (and possibly new author) into DB
		Integer ball_id = db.insertBallIntoArsenal(longname, shortname, brand, type, core, cover, color, surface, year, serialNumber, weight, mapping);

		// check the return value - should be a book_id > 0
		if (ball_id > 0)
		{
			// try to retrieve the book and author from the DB
			// get the list of (Author, Book) pairs from DB
			arsenal = db.findAllBalls();
				
			if (arsenal.isEmpty()) {
				System.out.println("No balls found within arsenal with ID: <" + ball_id + ">");
				fail("Failed to insert new ball <" + longname + "> into DB");
			}
			// otherwise, the test was successful.  Now remove the book just inserted to return the DB
			// to it's original state, except for using an author_id and a book_id
			else {
				System.out.println("New ball: " + longname + ") successfully added to arsenal with this ID: <" + ball_id + ">");
					
				// now delete establishment from DB
				// leaving the DB in its previous state - except that an author_id, and a book_id have been used
				//we do not have this function implemented yet
			}
		}
		else
		{
			System.out.println("Failed to insert new ball: " + longname + ") into arsenal with ID: <" + ball_id + ">");
			fail("Failed to insert new ball <" + longname + "> into DB");
		}
	}
	
	//test was created on 4/27/25 @ 10:30pm, this test shows a new event being added to the event list
	@Test
	public void testInsertEvent() {
		System.out.println("\n*** Testing insertEvent ***");

		String longname 			= "Summer Women's Duo";
		String shortname 			= "SWD"; 
		String type = "League";
		String establishment 	= "SB"; 
		String season = "Fa-24";
		int team = 1;
		String composition = "Women's";
		String day = "Monday";
		String time = "10:30a";
		String start 				= "05/15/25"; 
		String end_date 			= "08/20/25";
		Integer gamesPerSession 	= 3;
		int weeks = 32;
		int playoffs = 0;
					
		// insert new event (and possibly new author) into DB
		Integer event_id = db.insertEvent(longname, shortname, type, establishment, season, team, composition, day, time, start, end_date, gamesPerSession, weeks, playoffs);

		// check the return value - should be a book_id > 0
		if (event_id > 0)
		{
			// try to retrieve the book and author from the DB
			// get the list of (Author, Book) pairs from DB
			event = db.findAllEvents();
		
			if (event.isEmpty()) {
				System.out.println("No events found within event table <" + event_id + ">");
				fail("Failed to insert new event <" + longname + "> into DB");
			}
			// otherwise, the test was successful.  Now remove the book just inserted to return the DB
			// to it's original state, except for using an author_id and a book_id
			else {
				System.out.println("New event: " + longname + ") successfully added to event table: <" + event_id + ">");
					
				// now delete establishment from DB
				// leaving the DB in its previous state - except that an author_id, and a book_id have been used
				//we do not have this function implemented yet
			}
		}
		else
		{
			System.out.println("Failed to insert new event: " + longname + ") into event table: <" + event_id + ">");
			fail("Failed to insert new event <" + longname + "> into DB");
		}
	}
	
	
	@Test
	public void testFindAllEvents() {
		System.out.println("\n*** Testing findAllEvents ***");

		event = db.findAllEvents();

		assertNotNull("findAllEvents should not return null", event);

		if (event.isEmpty()) {
			System.out.println("No events found in the database.");
			// Not failing the test here, just logging — database might be empty intentionally
		} else {
			System.out.println("Events found in database: " + event.size());
			for (Event e : event) {
				System.out.println("Event: " + e.getLongname() + " (" + e.getShortname() + ")");
				assertNotNull("Event long name should not be null", e.getLongname());
				assertNotNull("Event short name should not be null", e.getShortname());
			}
		}
	}
	
	
	@Test
	public void testInsertSession() {
		System.out.println("\n*** Testing insertSession ***");

		// Setup test data
		String league = "SWD"; // Make sure this matches an existing league in your DB
		Date bowled = Date.valueOf("2025-05-15"); // Adjust as needed
		String ball = "BPH"; // Ensure this ball exists or the string is valid
		int startLane = 1;

		// Insert the session
		Integer returnedWeek = db.insertSession(league, bowled, ball, startLane);

		// Validate the returned week
		assertNotNull("Returned week should not be null", returnedWeek);
		assertTrue("Returned week should be positive", returnedWeek > 0);

		System.out.println("Session inserted for league <" + league + "> on week <" + returnedWeek + ">");

		// Optionally validate that it was inserted correctly
		// If you have a findAllSessions or findSessionsByLeague method:
		/*
		List<Session> sessions = db.findSessionsByLeague(league);
		assertFalse("No sessions found for league: " + league, sessions.isEmpty());

		boolean matchFound = sessions.stream()
			.anyMatch(s -> s.getWeek() == returnedWeek && s.getStartLane() == startLane && s.getBall().equals(ball));

		assertTrue("Inserted session not found in DB", matchFound);
		*/
	}

	


	
}
