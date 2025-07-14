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
	
	
	// transaction that retrieves a list of Books with their Authors, given Author's last name
	@Override
	public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(final String lastName) {
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// try to retrieve Authors and Books based on Author's last name, passed into query
				try {
					stmt = conn.prepareStatement(
							"select authors.*, books.* " +
							"  from  authors, books, bookAuthors " +
							"  where authors.lastname = ? " +
							"    and authors.author_id = bookAuthors.author_id " +
							"    and books.book_id     = bookAuthors.book_id "   +
							"  order by books.title asc, books.published asc"
					);
					stmt.setString(1, lastName);
					
					// establish the list of (Author, Book) Pairs to receive the result
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					// execute the query, get the results, and assemble them in an ArrayLsit
					resultSet = stmt.executeQuery();
					while (resultSet.next()) {
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						Book book = new Book();
						loadBook(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
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


	
	/*@Override
	public ArrayList<Session> findGamesWithSessionDate(final String date) {
		return executeTransaction(new Transaction<ArrayList<Session>>() {
			@Override
			public ArrayList<Session> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
						"SELECT game_one, game_two, game_three FROM sessions WHERE date_bowled = ?"
					);
					stmt.setString(1, date);
					resultSet = stmt.executeQuery();
					ArrayList<Session> sessions = new ArrayList<>();

					while (resultSet.next()) {
						Session session = new Session();
						session.setGameOneScore(Integer.parseInt(resultSet.getString("game_one")));
						session.setGameTwoScore(Integer.parseInt(resultSet.getString("game_one")));
						session.setGameThreeScore(Integer.parseInt(resultSet.getString("game_one")));
						sessions.add(session);
					}

					if (sessions.isEmpty()) {
						System.out.println("No sessions found for this date: " + date);
					}

					return sessions;

				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	*/

	
	@Override
	public ArrayList<Bit> findAllBits() {
		return executeTransaction(new Transaction<ArrayList<Bit>>() {
			@Override
			public ArrayList<Bit> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from bits " +
							" order by type"
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
	

	/*@Override
	public ArrayList<Shot> findAllShotsInGame(String gameID) {
		return executeTransaction(new Transaction<ArrayList<Shot>>() {
			@Override
			public ArrayList<Shot> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from shots " +
							" where game_id = ?" +
							" order by shot_number"
					);
					stmt.setString(1, gameID);
					
					ArrayList<Shot> result = new ArrayList<Shot>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Shot shot = new Shot();
						loadShot(shot, resultSet, 1);
						
						result.add(shot);
						System.out.println("gameID: " + gameID+ " frame: " + shot.getFrameNumber() + " shot: " + shot.getShotNumber());
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No shots were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}*/
	
	
	@Override
	public ArrayList<Shot> findAllShotsGivenFrame(String frameNum) {
	    return executeTransaction(new Transaction<ArrayList<Shot>>() {
	        @Override
	        public ArrayList<Shot> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;
	            
	            ArrayList<Shot> result = new ArrayList<>();

	            try {
	                stmt = conn.prepareStatement(
	                    "SELECT * FROM shots WHERE frame_number = ?"
	                );
	                stmt.setString(1, frameNum);
	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                   Shot shot = new Shot();
	                   loadShot(shot, resultSet, 1);
	                   result.add(shot);
	                }

	                if (result.isEmpty()) {
	                    System.out.println("No shots found for the given frame.");
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
	
	
	
	// transaction that inserts new Book into the Books table
	// also first inserts new Author into Authors table, if necessary
	// and then inserts entry into BookAuthors junction table
	/*@Override
	public Integer insertBookIntoBooksTable(final String title, final String isbn, final int published, final String lastName, final String firstName) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;				
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				ResultSet resultSet5 = null;				
				
				// for saving author ID and book ID
				Integer author_id = -1;
				Integer book_id   = -1;

				// try to retrieve author_id (if it exists) from DB, for Author's full name, passed into query
				try {
					stmt1 = conn.prepareStatement(
							"select author_id from authors " +
							"  where lastname = ? and firstname = ? "
					);
					stmt1.setString(1, lastName);
					stmt1.setString(2, firstName);
					
					// execute the query, get the result
					resultSet1 = stmt1.executeQuery();

					
					// if Author was found then save author_id					
					if (resultSet1.next())
					{
						author_id = resultSet1.getInt(1);
						System.out.println("Author <" + lastName + ", " + firstName + "> found with ID: " + author_id);						
					}
					else
					{
						System.out.println("Author <" + lastName + ", " + firstName + "> not found");
				
						// if the Author is new, insert new Author into Authors table
						if (author_id <= 0) {
							// prepare SQL insert statement to add Author to Authors table
							stmt2 = conn.prepareStatement(
									"insert into authors (lastname, firstname) " +
									"  values(?, ?) "
							);
							stmt2.setString(1, lastName);
							stmt2.setString(2, firstName);
							
							// execute the update
							stmt2.executeUpdate();
							
							System.out.println("New author <" + lastName + ", " + firstName + "> inserted in Authors table");						
						
							// try to retrieve author_id for new Author - DB auto-generates author_id
							stmt3 = conn.prepareStatement(
									"select author_id from authors " +
									"  where lastname = ? and firstname = ? "
							);
							stmt3.setString(1, lastName);
							stmt3.setString(2, firstName);
							
							// execute the query							
							resultSet3 = stmt3.executeQuery();
							
							// get the result - there had better be one							
							if (resultSet3.next())
							{
								author_id = resultSet3.getInt(1);
								System.out.println("New author <" + lastName + ", " + firstName + "> ID: " + author_id);						
							}
							else	// really should throw an exception here - the new author should have been inserted, but we didn't find them
							{
								System.out.println("New author <" + lastName + ", " + firstName + "> not found in Authors table (ID: " + author_id);
							}
						}
					}
					
					// now insert new Book into Books table
					// prepare SQL insert statement to add new Book to Books table
					stmt4 = conn.prepareStatement(
							"insert into books (title, isbn, published) " +
							"  values(?, ?, ?) "
					);
					stmt4.setString(1, title);
					stmt4.setString(2, isbn);
					stmt4.setInt(3, published);
					
					// execute the update
					stmt4.executeUpdate();
					
					System.out.println("New book <" + title + "> inserted into Books table");					

					// now retrieve book_id for new Book, so that we can set up BookAuthor entry
					// and return the book_id, which the DB auto-generates
					// prepare SQL statement to retrieve book_id for new Book
					stmt5 = conn.prepareStatement(
							"select book_id from books " +
							"  where title = ? and isbn = ? and published = ? "
									
					);
					stmt5.setString(1, title);
					stmt5.setString(2, isbn);
					stmt5.setInt(3, published);

					// execute the query
					resultSet5 = stmt5.executeQuery();
					
					// get the result - there had better be one
					if (resultSet5.next())
					{
						book_id = resultSet5.getInt(1);
						System.out.println("New book <" + title + "> ID: " + book_id);						
					}
					else	// really should throw an exception here - the new book should have been inserted, but we didn't find it
					{
						System.out.println("New book <" + title + "> not found in Books table (ID: " + book_id);
					}
					
					// now that we have all the information, insert entry into BookAuthors table
					// which is the junction table for Books and Authors
					// prepare SQL insert statement to add new Book to Books table
					stmt6 = conn.prepareStatement(
							"insert into bookAuthors (book_id, author_id) " +
							"  values(?, ?) "
					);
					stmt6.setInt(1, book_id);
					stmt6.setInt(2, author_id);
					
					// execute the update
					stmt6.executeUpdate();
					
					System.out.println("New entry for book ID <" + book_id + "> and author ID <" + author_id + "> inserted into BookAuthors junction table");						
					
					System.out.println("New book <" + title + "> inserted into Books table");					
					
					return book_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(resultSet5);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
				}
			}
		});
	}
	
	@Override
	public Integer insertBallIntoArsenal(final String longname, final String shortname, final String brand, final String type, final String core, final String cover, final String color, final String surface, final String year, final String serialNumber, final String weight, final String mapping) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;				
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				ResultSet resultSet5 = null;				
				
				// for saving ball_id
				Integer ball_id   = -1;

				// try to retrieve author_id (if it exists) from DB, for Author's full name, passed into query
				try {
					// now insert new ball into Arsenal table
					// prepare SQL insert statement to add new Book to Books table
					stmt4 = conn.prepareStatement(
							"insert into arsenal (long_name, short_name, brand, type, core, cover, color, surface, ball_year, serial_number, weight, mapping) " +
							"  values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					);
					stmt4.setString(1, longname);
					stmt4.setString(2, shortname);
					stmt4.setString(3, brand);
					stmt4.setString(4, type);
					stmt4.setString(5, core);
					stmt4.setString(6, cover);
					stmt4.setString(7, color);
					stmt4.setString(8, surface);
					stmt4.setString(9, year);
					stmt4.setString(10, serialNumber);
					stmt4.setString(11, weight);
					stmt4.setString(12, mapping);
					
					
					// execute the update
					stmt4.executeUpdate();
					
					System.out.println("New ball <" + longname + "> inserted into Arsenal table");					

					// now retrieve book_id for new Book, so that we can set up BookAuthor entry
					// and return the book_id, which the DB auto-generates
					// prepare SQL statement to retrieve book_id for new Book
					stmt5 = conn.prepareStatement(
							"select ball_id from arsenal " +
							"  where short_name = ? and serial_number = ? "
									
					);
					stmt5.setString(1, shortname);
					stmt5.setString(2, serialNumber);

					// execute the query
					resultSet5 = stmt5.executeQuery();
					
					// get the result - there had better be one
					if (resultSet5.next())
					{
						ball_id = resultSet5.getInt(1);
						System.out.println("New ball (shortname)< " + shortname + "> ID: " + ball_id);						
					}
					else	// really should throw an exception here - the new book should have been inserted, but we didn't find it
					{
						System.out.println("New ball <" + shortname + "> not found in Arsenal table (ID: " + ball_id);
					}
					
					return ball_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(resultSet5);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
				}
			}
		});
	}*/
	
	@Override
	public Integer insertGame(final String league, final String season, final String week, final String date, final String game, final String lane) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;		
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				ResultSet resultSet5 = null;				
				
				// for saving ball_id
				Integer game_id   = -1;

				// try to retrieve author_id (if it exists) from DB, for Author's full name, passed into query
				try {
					// now insert new ball into Arsenal table
					// prepare SQL insert statement to add new Book to Books table
					stmt4 = conn.prepareStatement(
							"insert into games (league, season, week, date, game, lane) " +
							"  values(?, ?, ?, ?, ?, ?) "
					);
					stmt4.setString(1, league);
					stmt4.setString(2, season);
					stmt4.setString(3, week);
					stmt4.setString(4, date);
					stmt4.setString(5, game);
					stmt4.setString(6, lane);
					
					
					// execute the update
					stmt4.executeUpdate();
					
					System.out.println("New game for <" + date + "> game number <" + game + "> inserted into Games table");					

					// now retrieve book_id for new Book, so that we can set up BookAuthor entry
					// and return the book_id, which the DB auto-generates
					// prepare SQL statement to retrieve book_id for new Book
					stmt5 = conn.prepareStatement(
							"select game_id from games " +
							"  where date = ? and game = ? and league = ?"
									
					);
					stmt5.setString(1, date);
					stmt5.setString(2, game);
					stmt5.setString(3, league);

					// execute the query
					resultSet5 = stmt5.executeQuery();
					
					// get the result - there had better be one
					if (resultSet5.next())
					{
						game_id = resultSet5.getInt(1);
						System.out.println("New game ID: " + game_id);						
					}
					else	// really should throw an exception here - the new book should have been inserted, but we didn't find it
					{
						System.out.println("New game <" + game_id + "> not found in Game table");
					}
					
					return game_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);			
					DBUtil.closeQuietly(resultSet3);		
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(resultSet5);
					DBUtil.closeQuietly(stmt5);
				}
			}
		});
	}
	
	/*@Override
	public Integer getLastInsertedGameID() {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				
				PreparedStatement stmt5 = null;		
				ResultSet resultSet5 = null;				
				
				// for saving game_id
				Integer game_id   = -1;

				// try to retrieve author_id (if it exists) from DB, for Author's full name, passed into query
				try {
					
					stmt5 = conn.prepareStatement(
							"select game_id from games order by game_id desc"
					);

					// execute the query
					resultSet5 = stmt5.executeQuery();
					
					// get the result - there had better be one
					if (resultSet5.next()) {
						game_id = resultSet5.getInt(1);
						System.out.println("Most recently entered game ID: " + game_id);
						
					// really should throw an exception here - the new book should have been inserted, but we didn't find it
					} else {	
						System.out.println("New game <" + game_id + "> not found in Game table");
					}
					
					return game_id;
					
				} finally {
					DBUtil.closeQuietly(resultSet5);
					DBUtil.closeQuietly(stmt5);
				}
			}
		});
	}*/
	
	//@Override
	public Integer insertShotIntoGame(final String shotNumber, final int gameID, final int frameNumber, final String count, final String leave, final String score, final String type, final String board, final String lane, final String ball) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;				
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				ResultSet resultSet5 = null;				
				
				// for saving ball_id
				Integer shot_id   = -1;

				// try to retrieve author_id (if it exists) from DB, for Author's full name, passed into query
				try {
					// now insert new ball into Arsenal table
					// prepare SQL insert statement to add new Book to Books table
					stmt4 = conn.prepareStatement(
							"insert into shots (shot_number, game_id, frame_number, count, leave, score, type, board, lane, ball) " +
							"  values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					);
					stmt4.setString(1, shotNumber);
					stmt4.setString(2, String.valueOf(gameID));
					stmt4.setString(3, String.valueOf(frameNumber));
					stmt4.setString(4, count);
					stmt4.setString(5, leave);
					stmt4.setString(6, score);
					stmt4.setString(7, type);
					stmt4.setString(8, board);
					stmt4.setString(9, lane);
					stmt4.setString(10, ball);
					
					
					// execute the update
					stmt4.executeUpdate();
					
					System.out.println("New Shot <" + shotNumber + "> in Frame <" + frameNumber + "> inserted into Shot Table of Game <" + gameID + ">");					

					// now retrieve book_id for new Book, so that we can set up BookAuthor entry
					// and return the book_id, which the DB auto-generates
					// prepare SQL statement to retrieve book_id for new Book
					stmt5 = conn.prepareStatement(
							"select shot_id from shots  " +
							"  where game_id = ? and shot_number = ? "
									
					);
					stmt5.setInt(1, gameID);
					stmt5.setString(2, shotNumber);

					// execute the query
					resultSet5 = stmt5.executeQuery();
					
					// get the result - there had better be one
					if (resultSet5.next())
					{
						shot_id = resultSet5.getInt(1);
						System.out.println("New shot number < " + shotNumber + "> ID: " + shot_id);						
					}
					else	// really should throw an exception here - the new book should have been inserted, but we didn't find it
					{
						System.out.println("New shot number <" + shotNumber + "> not found in Shot table (ID: " + shot_id + ") of Game ID <" + gameID + ">");
					}
					
					return shot_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(resultSet5);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
				}
			}
		});
	}
	

	
	/*@Override
	public Integer insertSession(final String league, final Date bowled, final String ball, final int startLane) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;				
				
				ResultSet resultSet = null;
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				ResultSet resultSet5 = null;				
				
				// for saving author ID and book ID
				//do event id and session id instead
				Integer session_id = -1;
				Integer event_id   = -1;
				Integer newWeek = -1;

				// try to retrieve author_id (if it exists) from DB, for Author's full name, passed into query
				try {
					
					stmt = conn.prepareStatement(
							"select sessions.week from sessions " +
							"  where sessions.league = ? " +
							"  order by sessions.session_id desc "					);
					stmt.setString(1, league);
					
					// execute the query, get the result
					resultSet = stmt.executeQuery();

					
					// if last session was found then update newWeek for this session based on last					
					if (resultSet.next()) {
						newWeek = resultSet.getInt(1) + 1;
					} else {
						newWeek = 1;
					}
					
					
					// now insert new Session into Sessions table
					// prepare SQL insert statement to add new Session to Sessions table
					stmt4 = conn.prepareStatement(
							"insert into sessions (league, date_bowled, start_lane, ball, week, game_one, game_two, game_three, series) " +
							"  values(?, ?, ?, ?, ?, 0, 0, 0, 0) "
					);
					stmt4.setString(1, league);
					stmt4.setDate(2, bowled);
					stmt4.setInt(3, startLane);
					stmt4.setString(4, ball);
					stmt4.setInt(5, Integer.valueOf(newWeek));					
					
					// execute the update
					stmt4.executeUpdate();
					
					System.out.println("New session week<" + newWeek + "> inserted into Sessions table");					

					// now retrieve session_id for new Session, so that we can set up SessionEvent entry
					// and return the session_id, which the DB SHOULD NOT-auto-generate. THE REASON-
					// User entered the week, so can't have two id's for the same thing
					// prepare SQL statement to retrieve book_id for new Book
				
					return newWeek;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(resultSet5);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
				}
			}
		});
	}*/
	
	
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
				
				/*PreparedStatement stmt3 = null;	
				PreparedStatement stmt4 = null;	
				PreparedStatement stmt5 = null;	
				PreparedStatement stmt6 = null;	
				PreparedStatement stmt7 = null;
				PreparedStatement stmt8 = null;*/
				
			
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
					
					stmt2 = conn.prepareStatement(
						"create table clients (" +
						"	client_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	first_name varchar(40)," +
						"	last_name varchar(40)," +
						"	address varchar(40)," +
						"	horse_1 integer," +
						"	horse_2 integer," +
						"	horse_3 integer," +
						"	comment varchar(100)" +
						")"
					);	
					stmt2.executeUpdate();					
					System.out.println("Client table created");
					
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
					
					return true;
					
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					/*DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
					DBUtil.closeQuietly(stmt7);*/
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
					
					bitList         = InitialData.getBits();
					
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertBit     		= null;

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
