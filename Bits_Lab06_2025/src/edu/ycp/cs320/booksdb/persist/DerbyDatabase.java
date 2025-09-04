package edu.ycp.cs320.booksdb.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.booksdb.model.Bit;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	
	// transaction that retrieves a Book, and its Author by Title
	@Override
	public List<Pair<Author, Book>> findAuthorAndBookByTitle(final String title) {
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select authors.*, books.* " +
							"  from  authors, books, bookAuthors " +
							"  where books.title = ? " +
							"    and authors.author_id = bookAuthors.author_id " +
							"    and books.book_id     = bookAuthors.book_id"
					);
					stmt.setString(1, title);
					
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						Book book = new Book();
						loadBook(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + title + "> was not found in the books table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	
	// transaction that retrieves all Books in Library, with their respective Authors
	@Override
	public List<Pair<Author, Book>> findAllBooksWithAuthors() {
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select authors.*, books.* " +
							"  from authors, books, bookAuthors " +
							"  where authors.author_id = bookAuthors.author_id " +
							"    and books.book_id     = bookAuthors.book_id "   +
							"  order by books.title asc"
					);
					
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						Book book = new Book();
						loadBook(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
					}
					
					// check if any books were found
					if (!found) {
						System.out.println("No books were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}	
	
	
	@Override
	public ArrayList<Session> findGamesForSessionLeague(final String eventShortname) {
	    return executeTransaction(new Transaction<ArrayList<Session>>() {
	        @Override
	        public ArrayList<Session> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                // Query to select all game scores for the specified league (eventShortname)
	                stmt = conn.prepareStatement(
	                    "SELECT game_one, game_two, game_three " +
	                    "FROM sessions WHERE league = ?"
	                );
	                stmt.setString(1, eventShortname);
	                resultSet = stmt.executeQuery();

	                // List to hold all the game scores
	                ArrayList<Session> allGameScores = new ArrayList<>();

	                // Loop through the result set and add each game score to the list
	                while (resultSet.next()) {
	                    int gameOne = resultSet.getInt("game_one");
	                    int gameTwo = resultSet.getInt("game_two");
	                    int gameThree = resultSet.getInt("game_three");

	                    // Add all 3 game scores to the list
	                    allGameScores.add(gameOne);
	                    allGameScores.add(gameTwo);
	                    allGameScores.add(gameThree);
	                }

	                // Return the list of all game scores
	                return allGameScores;

	            } finally {
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}

	
	@Override
	public ArrayList<Bit> findAllBits() {
		return executeTransaction(new Transaction<ArrayList<Bit>>() {
			@Override
			public ArrayList<Bit> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from bits "
							// " order by type"
					);
					
					ArrayList<Bit> result = new ArrayList<Bit>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Bit bit = new Bit();
						loadBit(bit, resultSet, 1);
						
						result.add(bit);
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No bits were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	
	@Override
	public ArrayList<Shot> findAllShotsGivenFrameEvent(String event, String frameNum) {
	    return executeTransaction(new Transaction<ArrayList<Shot>>() {
	        @Override
	        public ArrayList<Shot> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt1 = null;
	            PreparedStatement stmt2 = null;
	            ResultSet resultSet1 = null;
	            ResultSet resultSet2 = null;

	            ArrayList<Shot> result = new ArrayList<>();

	            try {
	                // Step 1: Get all game_ids for the given event
	                stmt1 = conn.prepareStatement(
	                    "SELECT game_id FROM games WHERE league = ?"
	                );
	                stmt1.setString(1, event);
	                resultSet1 = stmt1.executeQuery();

	                List<String> gameIds = new ArrayList<>();
	                while (resultSet1.next()) {
	                    gameIds.add(resultSet1.getString("game_id"));
	                }

	                if (gameIds.isEmpty()) {
	                    System.out.println("No game_ids found for the given event.");
	                    return result;
	                }

	                // Step 2: For each game_id, get matching shots
	                stmt2 = conn.prepareStatement(
	                    "SELECT * FROM shots WHERE game_id = ? AND frame_number = ?"
	                );

	                for (String gameId : gameIds) {
	                    stmt2.setString(1, gameId);
	                    stmt2.setString(2, frameNum);
	                    resultSet2 = stmt2.executeQuery();

	                    while (resultSet2.next()) {
	                        Shot shot = new Shot();
	                        loadShot(shot, resultSet2, 1);
	                        result.add(shot);
	                    }

	                    DBUtil.closeQuietly(resultSet2);  // Close between iterations
	                }

	                if (result.isEmpty()) {
	                    System.out.println("No shots found for the given frame and event.");
	                }

	                return result;

	            } finally {
	                DBUtil.closeQuietly(resultSet1);
	                DBUtil.closeQuietly(stmt1);
	                DBUtil.closeQuietly(resultSet2);
	                DBUtil.closeQuietly(stmt2);
	            }
	        }
	    });
	}
	
	
	@Override
	public ArrayList<Shot> findAllShotsGivenFrameEventSeason(String event, String season, String frameNum) {
	    return executeTransaction(new Transaction<ArrayList<Shot>>() {
	        @Override
	        public ArrayList<Shot> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt1 = null;
	            PreparedStatement stmt2 = null;
	            ResultSet resultSet1 = null;
	            ResultSet resultSet2 = null;

	            ArrayList<Shot> result = new ArrayList<>();

	            try {
	                // Step 1: Get all game_ids for the given event
	                stmt1 = conn.prepareStatement(
	                    "SELECT game_id FROM games WHERE league = ? AND season = ?"
	                );
	                stmt1.setString(1, event);
	                stmt1.setString(2, season);
	                resultSet1 = stmt1.executeQuery();

	                List<String> gameIds = new ArrayList<>();
	                while (resultSet1.next()) {
	                    gameIds.add(resultSet1.getString("game_id"));
	                }

	                if (gameIds.isEmpty()) {
	                    System.out.println("No game_ids found for the given event.");
	                    return result;
	                }

	                // Step 2: For each game_id, get matching shots
	                stmt2 = conn.prepareStatement(
	                    "SELECT * FROM shots WHERE game_id = ? AND frame_number = ?"
	                );

	                for (String gameId : gameIds) {
	                    stmt2.setString(1, gameId);
	                    stmt2.setString(2, frameNum);
	                    resultSet2 = stmt2.executeQuery();

	                    while (resultSet2.next()) {
	                        Shot shot = new Shot();
	                        loadShot(shot, resultSet2, 1);
	                        result.add(shot);
	                    }

	                    DBUtil.closeQuietly(resultSet2);  // Close between iterations
	                }

	                if (result.isEmpty()) {
	                    System.out.println("No shots found for the given frame, event, and season.");
	                }

	                return result;

	            } finally {
	                DBUtil.closeQuietly(resultSet1);
	                DBUtil.closeQuietly(stmt1);
	                DBUtil.closeQuietly(resultSet2);
	                DBUtil.closeQuietly(stmt2);
	            }
	        }
	    });
	}
	
	
	// no horses in here because all horses will have a client ID
	@Override
	public Integer insertClient(final String firstName, final String lastName, final String farmName, 
		final String address, final String comment) {
		
		return executeTransaction(new Transaction<Integer>() {
			
			@Override
			public Integer execute(Connection conn) throws SQLException {
				
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;			
				
				ResultSet resultSet1 = null;
				ResultSet resultSet2 = null;
				ResultSet resultSet3 = null;				
				
				// for saving client_id
				Integer client_id   = -1;

				// try to retrieve client_id (if it exists) from DB
				// do this after
				try {
					
					// now insert new client into client table
					// prepare SQL insert statement to add new client to client table w/o horses (set to -1 to start)
					stmt1 = conn.prepareStatement(
							"insert into clients (first_name, last_name, farm_name, address, comment) " +
							"  values(?, ?, ?, ?, ?) "
					);
					stmt1.setString(1, firstName);
					stmt1.setString(2, lastName);
					stmt1.setString(3, farmName);
					stmt1.setString(4, address);
					stmt1.setString(5, comment);
					// execute the update
					stmt1.executeUpdate();
					System.out.println("New Client <" + firstName + " " + lastName + "> inserted into Client Table");					

					
					// now retrieve client_id for new client, so that we can set up horse entry for client
					// and return the client_id, which the DB auto-generates
					// prepare SQL statement to retrieve client_id for new Client
					stmt2 = conn.prepareStatement(
							"select client_id from clients  " +
							"  where first_name = ? and last_name = ? "
									
					);
					stmt2.setString(1, firstName);
					stmt2.setString(2, lastName);
					// execute the query
					resultSet2 = stmt2.executeQuery();
					
					// get the result - there had better be one
					if (resultSet2.next()) {
						
						client_id = resultSet2.getInt(1);
						System.out.println("New Client ID: <" + client_id + ">");	
						
					} else {	// really should throw an exception here - the new client should have been inserted, but we didn't find it
						System.out.println("New Client ID: <" + client_id + "> not found in Client table");
					}
					
					return client_id;
					
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);				
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt2);	
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	
	
	// create new horse entry in horse table given unique client ID
	@Override
	public Integer insertHorse(final int clientID, final String barnName, final String showName, final String breed, 
			final String height, final String sport) {
		
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;			
				
				ResultSet resultSet1 = null;
				ResultSet resultSet2 = null;
				ResultSet resultSet3 = null;				
				
				// for saving client_id
				Integer horse_id   = -1;

				// try to retrieve client_id (if it exists) from DB, for Author's full name, passed into query
				// do this after
				try {
					// now insert new client into client table
					// prepare SQL insert statement to add new client to client table w/o horses
					stmt1 = conn.prepareStatement(
							"insert into horses (client_id, barn_name, show_name, breed, height, sport) " +
							"  values(?, ?, ?, ?, ?, ?) "
					);
					
					stmt1.setInt(1, clientID);
					stmt1.setString(2, barnName);
					stmt1.setString(3, showName);
					stmt1.setString(4, breed);
					stmt1.setString(5, height);
					stmt1.setString(6, sport);
					
					// execute the update
					stmt1.executeUpdate();
					System.out.println("New Horse <" + showName + " '" + barnName + "'> inserted into Horses Table");					

					
					// retrieve horse_id of brand new horse entry
					// to enter into existing clients horse column
					stmt2 = conn.prepareStatement(
							"select * from horses order by desc"			
					);

					// execute the query
					resultSet2 = stmt2.executeQuery();
					
					// get the result - there had better be one
					if (resultSet2.next()) {
					
						horse_id = resultSet2.getInt(1);
						System.out.println("New Horse ID: <" + horse_id + ">");						
					
					// really should throw an exception here - the new client should have been inserted, but we didn't find it
					} else { 	
						System.out.println("New Horse ID: <" + horse_id + "> not found in Horse table");
					}
					
					/*stmt2 = conn.prepareStatement(						// do not need to put the horses into the client table
							"select * from clients where client_id = ?"		// have reference through client ID
					);
					stmt2.setInt(1, clientID);
					resultSet2 = stmt2.executeQuery();
					
					if (resultSet2.next()) {
						
						// horse_id = resultSet2.getInt(6);
						if (resultSet2.getInt(6) == -1) {
							// update client data with new horseID for horse 1
							stmt3 = conn.prepareStatement(
									"update clients " +
									"set horse_1 = ? " +
									"where client_id = ?" 
							);
							stmt3.setInt(1, horse_id);
							stmt3.setInt(2, clientID);
							stmt3.executeUpdate();
							
						} else if(resultSet2.getInt(7) == -1) {
							// update client data with new horseID for horse 2
							stmt3 = conn.prepareStatement(
									"UPDATE clients " +
									"SET horse_2 = ? " +
									"WHERE client_id = ? " 
							);
							stmt3.setInt(1, horse_id);
							stmt3.setInt(2, clientID);
							stmt3.executeUpdate();
							
						} else if(resultSet2.getInt(8) == -1) {
							// update client data with new horseID for horse 3
							stmt3 = conn.prepareStatement(
									"UPDATE clients " +
									"SET horse_3 = ? " +
									"WHERE client_id = ? " 
							);
							stmt3.setInt(1, horse_id);
							stmt3.setInt(2, clientID);
							stmt3.executeUpdate();
							
						} else {
							// all three horses are already filled, throw exception
							// for now return -2 to (not to be confused with -1 of empty
							// horse slots)
							return -2;
						}
	
					} else {			// really should throw an exception here - the new horse should have been inserted, but we didn't find it
						System.out.println("New Horse ID: <" + horse_id + "> not found in Horse table");
					}*/
					
					return horse_id; 	// should be a positive integer
					
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);				
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt2);	
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	

	
	// wrapper SQL transaction function that calls actual transaction function (which has retries)
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	// SQL transaction function which retries the transaction MAX_ATTEMPTS times before failing
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	// TODO: Here is where you name and specify the location of your Derby SQL database
	// TODO: Change it here and in SQLDemo.java under CS320_LibraryExample_Lab06->edu.ycp.cs320.sqldemo
	// TODO: DO NOT PUT THE DB IN THE SAME FOLDER AS YOUR PROJECT - that will cause conflicts later w/Git
	private Connection connect() throws SQLException {
	    Connection conn = null;

	    String os_name = System.getProperty("os.name").toLowerCase();

	    try {
	        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

	        if (!os_name.startsWith("mac os")) {
	            System.out.println("Not a Mac, using Windows path");
	            conn = DriverManager.getConnection("jdbc:derby:C:/PamsBits-2025-DB/library;create=true");
	        } else {
	            System.out.println("Running on Mac, using Mac path");
	            conn = DriverManager.getConnection("jdbc:derby:/Users/caroline/Desktop/cs320-Spring2025/RevMextrixTeamProject_CS320/CS320_LibraryExample_Lab06_2025/CS320-2025-LibraryExample-DB/library;create=true");
	        }

	        conn.setAutoCommit(false); // as you originally intended
	    } catch (ClassNotFoundException e) {
	        System.out.println("Error loading Derby driver: " + e.getMessage());
	    }

	    return conn;
	}

	

	// retrieves bit information from query result set
	private void loadBit(Bit bit, ResultSet resultSet, int index) throws SQLException {
		
		System.out.println("loading a bit");
		resultSet.getString(index++);
		bit.setType(resultSet.getString(index++));
		bit.setCheekpiece(resultSet.getString(index++));
		bit.setSize(resultSet.getString(index++));
		bit.setPurpose(resultSet.getString(index++));
		bit.setComment1(resultSet.getString(index++));
		bit.setComment2(resultSet.getString(index++));
		bit.setComment3(resultSet.getString(index++));
	}
	
	
	//  creates the Authors and Books tables
	public void createTables() {
		
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;				
			
				try {
					
					stmt1 = conn.prepareStatement(
						"create table bits (" +
						"	bit_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	type varchar(40)," +
						"	cheekpiece varchar(40)," +
						"	size varchar(40)," +
						"	purpose varchar(40)," +
						"	comment1 varchar(100)," +
						"	comment2 varchar(100)," +
						"	comment3 varchar(100)" +
						")"
					);	
					stmt1.executeUpdate();					
					System.out.println("Bit table created");
					
					// holds unique horse IDs to connect to info in horse table
					// may not have all 3 horses in system yet
					stmt2 = conn.prepareStatement(
						"create table clients (" +
						"	client_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	first_name varchar(40)," +
						"	last_name varchar(40)," +
						"	farm_name varchar(40)," +
						"	address varchar(40)," +		 	// don't need the horses to be in the clients table
						"	comment varchar(100)" +			// horses each have a client reference, now unlimited
						")"
					);	
					stmt2.executeUpdate();					
					System.out.println("Client table created");
					
					// holds unique client IDs to link to info in client table
					// client IDs must exist before horses are entered into system
					// when horses are entered into system, their client horse id must be updated with new entry
					stmt3 = conn.prepareStatement(
						"create table horses (" +
						"	horse_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +	
						"	client_id integer," +
						"	barn_name varchar(40)," +
						"	show_name varchar(40)," +
						"	breed varchar(40)," +
						"	height varchar(40)," +
						"	sport varchar(30)" +
						")"
					);	
					stmt3.executeUpdate();					
					System.out.println("Horse table created");
					
					stmt4 = conn.prepareStatement(
						"create table horse_comments (" +
						"	comment_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +	
						"	horse_id integer," +
						"	comment varchar(300)" +
						")"
					);	
					stmt4.executeUpdate();					
					System.out.println("Horse Comment table created");
					
					return true;
					
					
					// bit order table - client, specifications... horse
					// bridle order table - same
					
					
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
				}
			}
		});
	}
	
	// loads data retrieved from CSV files into DB tables in batch mode
	public void loadInitialData() {
		
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				
				List<Bit> bitList;
				
				try {
					bitList = InitialData.getBits();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertBit = null;

				try {
					// must completely populate B table before populating BookAuthors table because of primary keys
					insertBit = conn.prepareStatement("insert into bits (type, cheekpiece, size, purpose, comment1, comment2, comment3) values (?, ?, ?, ?, ?, ?, ?)");
					for (Bit bit : bitList) {
						
						insertBit.setString(1, bit.getType());
						insertBit.setString(2, bit.getCheekpiece());
						insertBit.setString(3, bit.getSize());
						insertBit.setString(4, bit.getPurpose());
						insertBit.setString(5, bit.getComment1());
						insertBit.setString(6, bit.getComment2());
						insertBit.setString(7, bit.getComment3());
						insertBit.addBatch();
					}
					insertBit.executeBatch();
					System.out.println("Bit table populated");
					
					return true;	
					
				} finally {
					DBUtil.closeQuietly(insertBit);
				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Bit DB successfully initialized!");
	}
}
