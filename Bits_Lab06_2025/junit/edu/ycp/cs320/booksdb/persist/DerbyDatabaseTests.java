package edu.ycp.cs320.booksdb.persist;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

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
import edu.ycp.cs320.booksdb.model.Session;
import edu.ycp.cs320.booksdb.model.Shot;
import edu.ycp.cs320.booksdb.model.Pair;

public class DerbyDatabaseTests<Date> {

	private IDatabase db = null;
	
	ArrayList<Author> authors = null;
	ArrayList<Book>   books   = null;
	List<Pair<Author, Book>> bookAuthorList = null;
	List<Pair<Author, Book>> authorBookList = null;	
	
	ArrayList<Ball> arsenal = null;
	ArrayList<Establishment> establishments = null;
	ArrayList<Event> event = null;
	ArrayList<Session> session = null;
	ArrayList<Shot> shot = null;
	
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

	@Test
	public void testFindAuthorAndBookByTitle() {
		System.out.println("\n*** Testing findAuthorAndBookByTitle ***");
		
		String title = "A Briefer History of Time";

		// get the list of (Author, Book) pairs from DB
		authorBookList = db.findAuthorAndBookByTitle(title);
		
		// NOTE: this is a simple test to check if no results were found in the DB
		if (authorBookList.isEmpty()) {
			System.out.println("No book found in library with title <" + title + ">");
			fail("No book with title <" + title + "> returned from Library DB");
		}
		// NOTE: assembling the results into Author and Book lists so that they could be
		//       inspected for correct content - well-formed objects with correct content
		else {			
			authors = new ArrayList<Author>();
			for (Pair<Author, Book> authorBook : authorBookList) {
				Author author = authorBook.getLeft();
				Book   book   = authorBook.getRight();
				authors.add(author);
				System.out.println(author.getLastname() + "," + author.getFirstname() + ", " + book.getTitle() + "," + book.getIsbn());
			}			
		}
	}

	@Test
	public void testFindAuthorAndBookByAuthorLastName() {
		System.out.println("\n*** Testing findAuthorAndBooksByAuthorLastName ***");

		String lastName = "Hawking";
		
		// get the list of (Author, Book) pairs from DB
		authorBookList = db.findAuthorAndBookByAuthorLastName(lastName);
		
		// NOTE: this is a simple test to check if no results were found in the DB
		if (authorBookList.isEmpty()) {
			System.out.println("No books found for author <" + lastName + ">");
			fail("No books for author <" + lastName + "> returned from Library DB");
		}
		// NOTE: assembling the results into Author and Book lists so that they could be
		//       inspected for correct content - well-formed objects with correct content
		else {
			books = new ArrayList<Book>();
			for (Pair<Author, Book> authorBook : authorBookList) {
				Author author = authorBook.getLeft();
				Book book = authorBook.getRight();
				books.add(book);
				System.out.println(author.getLastname() + "," + author.getFirstname() + "," + book.getTitle() + "," + book.getIsbn());
			}			
		}
	}

	@Test
	public void testFindAllBooksWithAuthors() {
		System.out.println("\n*** Testing findAllBooksWithAuthors ***");

		// get the list of (Author, Book) pairs from DB
		bookAuthorList = db.findAllBooksWithAuthors();
		
		// NOTE: this is a simple test to check if no results were found in the DB
		if (bookAuthorList.isEmpty()) {
			System.out.println("No books in database");
			fail("No books returned from Library DB");
		}
		// NOTE: assembling the results into Author and Book lists so that they could be
		//       inspected for correct content - well-formed objects with correct content
		else {
			books = new ArrayList<Book>();
			for (Pair<Author, Book> authorBook : bookAuthorList) {
				Author author = authorBook.getLeft();
				Book book = authorBook.getRight();
				books.add(book);
				System.out.println(book.getTitle() + ", " + book.getIsbn() + ", " + author.getLastname() + ", " + author.getFirstname());
			}			
		}
	}

	@Test
	public void testFindAllAuthors() {

		System.out.println("\n*** Testing findAllAuthors ***");

		// get the list of (Author, Book) pairs from DB
		List<Author> authorList = db.findAllAuthors();

		// NOTE: this is a simple test to check if no results were found in the DB
		if (authorList.isEmpty()) {
			System.out.println("No authors found in library");
			fail("No authors returned from Library DB");
		}
		// NOTE: assembling the results into Author and Book lists so that they could be
		//       inspected for correct content - well-formed objects with correct content
		else {
			authors = new ArrayList<Author>();
			for (Author author : authorList) {
				authors.add(author);
				System.out.println(author.getLastname() + ", " + author.getFirstname());
			}			
		}
	}

	@Test
	public void testInsertBookIntoBooksTable() {
		System.out.println("\n*** Testing insertBookIntoBooksTable ***");

		String title     = "Wired for War";
		String isbn      = "0-143-11684-3";
		int    published = 2009;
		String lastName  = "Singer";
		String firstName = "P.J.";
  
				
		// insert new book (and possibly new author) into DB
		Integer book_id = db.insertBookIntoBooksTable(title, isbn, published, lastName, firstName);

		// check the return value - should be a book_id > 0
		if (book_id > 0)
		{
			// try to retrieve the book and author from the DB
			// get the list of (Author, Book) pairs from DB
			authorBookList = db.findAuthorAndBookByAuthorLastName(lastName);
			
			if (authorBookList.isEmpty()) {
				System.out.println("No books found for author <" + lastName + ">");
				fail("Failed to insert new book <" + title + "> into Library DB");
			}
			// otherwise, the test was successful.  Now remove the book just inserted to return the DB
			// to it's original state, except for using an author_id and a book_id
			else {
				System.out.println("New book (ID: " + book_id + ") successfully added to Books table: <" + title + ">");
				
				// now delete Book (and its Author) from DB
				// leaving the DB in its previous state - except that an author_id, and a book_id have been used
				List<Author> authors = db.removeBookByTitle(title);				
			}
		}
		else
		{
			System.out.println("Failed to insert new book (ID: " + book_id + ") into Books table: <" + title + ">");
			fail("Failed to insert new book <" + title + "> into Library DB");
		}
	}
	
	//test was created on 4/27/25 @ 10pm, this test shows a new establishment being added
	@Test
	public void testInsertEstablishmentIntoEstablishmentsTable() {
		System.out.println("\n*** Testing insertEstablishmentIntoEstablishmentsTable ***");

		String longname    	= "Laser Lanes";
		String shortname    = "LA";
		String address 		= "3905 E. Market St  York  PA  17402";
		String phone 		= "610-123-4567";
		Integer lanes       = 12;
		String type 		= "Brunswick";
  
				
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
		String type					= "Practice";
		String establishmentShort 	= "SB"; 
		String season				= "Fa-24";
		Integer team				= 4;
		String composition			= "Women's";
		String weeknight 			= "33";
		String time					= "10:00a";
		String start 				= "05/15/25"; 
		String end_date 			= "08/20/25";
		Integer gamesPerSession 	= 3;
		Integer weeks				= 32;
		Integer playoffs			= 2;
					
		// insert new event (and possibly new author) into DB
		Integer event_id = db.insertEvent(longname, shortname, type, establishmentShort, season, team, composition, weeknight, time, start, end_date, gamesPerSession, weeks, playoffs);

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
	public void testRemoveBookByTitle() {
		System.out.println("\n*** Testing removeBookByTitle ***");
		
		String title     = "Outliers";
		String isbn      = "0-316-01793-0";
		int    published = 2010;		
		String lastName  = "Gladwell";
		String firstName = "Malcolm";
				
		// insert new book (and new author) into DB
		Integer book_id = db.insertBookIntoBooksTable(title, isbn, published, lastName, firstName);
		
		// check to see that insertion was successful before proceeding
		if (book_id > 0) {
			// now delete Book (and its Author) from DB
			List<Author> authors = db.removeBookByTitle(title);
			
			if (authors.isEmpty()) {
				System.out.println("Failed to remove Author(s) for book with title <" + title + ">");
				fail("No Author(s) removed from DB for Book with title <" + title + ">");
			}
			else {
				System.out.println("Author <" + authors.get(0).getLastname() + ", " + authors.get(0).getFirstname() + "> removed from Library DB");
			}					
			
			// get the list of (Author, Book) pairs from DB
			authorBookList = db.findAuthorAndBookByTitle(title);
			
			if (authorBookList.isEmpty()) {
				System.out.println("All Books with title <" + title + "> were removed from the Library DB");
			}
			else {
				fail("Book with title <" + title + "> remains in Library DB after delete");			
			}
		}
		else {
			System.out.println("Failed to insert new book (ID: " + book_id + ") into Books table: <" + title + ">");
			fail("Failed to insert new book <" + title + "> into Library DB");			
		}
	}
	
	@Test
	public void testInsertGame() {
		System.out.println("\n*** Testing insertGame ***");

		String league   	= "test";
		String season    	= "test";
		String week 		= "test";
		String date 		= "test";
		String game 		= "test";
		String lane			= "test";
  
				
		try {
			// Attempt to insert the game
			Integer game_id = db.insertGame(league, season, week, date, game, lane);

			// Check the return value
			assertNotNull("Insert returned null game_id", game_id);
			assertTrue("Insert returned invalid game_id", game_id > 0);

			System.out.println("Successfully inserted game. ID: " + game_id);


		} catch (Exception e) {
			System.err.println("Exception occurred during testInsertGame: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during insertGame: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllEvents() {
		System.out.println("\n*** Testing findAllEvents ***");		
		try {
			// Attempt to insert the game
			ArrayList<Event> result = db.findAllEvents();

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found events." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllEvents: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllEvents: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllBalls() {
		System.out.println("\n*** Testing findAllBalls ***");		
		try {
			// Attempt to insert the game
			ArrayList<Ball> result = db.findAllBalls();

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found balls." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllBalls: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllBalls: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllSessions() {
		System.out.println("\n*** Testing findAllSessions ***");		
		try {
			// Attempt to insert the game
			ArrayList<Session> result = db.findAllSessions();

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found sessions." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllSessions: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllSessions: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllEstablishments() {
		System.out.println("\n*** Testing findAllEstablishments ***");		
		try {
			// Attempt to insert the game
			ArrayList<Establishment> result = db.findAllEstablishments();

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found establishments." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllEstablishments: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllEstablishments: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShots() {
		System.out.println("\n*** Testing findAllShots ***");		
		try {
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShots();

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShots: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShots: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShotsInGame() {
		System.out.println("\n*** Testing findAllShotsInGame ***");		
		try {
			String gameID = "12";
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShotsInGame(gameID);

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShotsInGame: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShotsInGame: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShotsGivenFrame() {
		System.out.println("\n*** Testing findAllShotsGivenFrame ***");		
		try {
			String frame = "5";
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShotsGivenFrame(frame);

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShotsGivenFrame: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShotsGivenFrame: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShotsGivenEvent() {
		System.out.println("\n*** Testing findAllShotsGivenEvent ***");		
		try {
			String event = "BowlerMaxx";
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShotsGivenFrame(event);

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShotsGivenEvent: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShotsGivenEvent: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShotsGivenSeason() {
		System.out.println("\n*** Testing findAllShotsGivenSeason ***");		
		try {
			String season = "Fa-24";
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShotsGivenSeason(season);

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShotsGivenSeason: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShotsGivenSeason: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShotsGivenFrameEvent() {
		System.out.println("\n*** Testing findAllShotsGivenFrameEvent ***");		
		try {
			String frame = "5";
			String event = "BowlerMaxx";
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShotsGivenFrameEvent(frame, event);

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShotsGivenFrameEvent: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShotsGivenFrameEvent: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShotsGivenFrameSeason() {
		System.out.println("\n*** Testing findAllShotsGivenFrameSeason ***");		
		try {
			String frame = "5";
			String season = "Fa-24";
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShotsGivenFrameSeason(frame, season);

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShotsGivenFrameSeason: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShotsGivenFrameSeason: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShotsGivenEventSeason() {
		System.out.println("\n*** Testing findAllShotsGivenEventSeason ***");		
		try {
			String event = "BowlerMaxx";
			String season = "Fa-24";
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShotsGivenEventSeason(event, season);

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShotsGivenEventSeason: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShotsGivenEventSeason: " + e.getMessage());
		}
	}
	
	@Test
	public void testFindAllShotsGivenFrameEventSeason() {
		System.out.println("\n*** Testing findAllShotsGivenFrameEventSeason ***");		
		try {
			String frame = "5";
			String event = "BowlerMaxx";
			String season = "Fa-24";
			// Attempt to insert the game
			ArrayList<Shot> result = db.findAllShotsGivenFrameEventSeason(frame, event, season);

			// Check the return value
			assertNotNull("Insert returned null result", result);
			assertTrue("Insert returned invalid result", result != null);

			System.out.println("Successfully found shots." + result);


		} catch (Exception e) {
			System.err.println("Exception occurred during testFindAllShotsGivenFrameEventSeason: " + e.getMessage());
			e.printStackTrace();
			fail("Exception thrown during findAllShotsGivenFrameEventSeason: " + e.getMessage());
		}
	}
}
