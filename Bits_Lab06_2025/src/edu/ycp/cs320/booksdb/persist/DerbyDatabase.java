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


	
	@Override
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




	
	
	// transaction that retrieves all Authors in Library
	@Override
	public List<Author> findAllAuthors() {
		return executeTransaction(new Transaction<List<Author>>() {
			@Override
			public List<Author> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from authors " +
							" order by lastname asc, firstname asc"
					);
					
					List<Author> result = new ArrayList<Author>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						
						result.add(author);
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No authors were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	private void loadEstablishment(Establishment establishment, ResultSet resultSet, int index) throws SQLException {
		resultSet.getString(index++);
		establishment.setLongname(resultSet.getString(index++));
		establishment.setShortname(resultSet.getString(index++));
		establishment.setAddress(resultSet.getString(index++));
		establishment.setPhone(resultSet.getString(index++));
		establishment.setLanes(Integer.parseInt(resultSet.getString(index++)));
		establishment.setType(resultSet.getString(index++));
	}

	
	@Override
	public ArrayList<Establishment> findAllEstablishments() {
		return executeTransaction(new Transaction<ArrayList<Establishment>>() {
			@Override
			public ArrayList<Establishment> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from establishments"
					);
					
					ArrayList<Establishment> result = new ArrayList<Establishment>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Establishment establishment = new Establishment();
						loadEstablishment(establishment, resultSet, 1);
						
						result.add(establishment);
					}
					
					// check if any establishments were found
					if (!found) {
						System.out.println("No establishments were found in the database");
					}
					
					return (ArrayList<Establishment>) result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	

	@Override
	public ArrayList<Ball> findAllBalls() {
		return executeTransaction(new Transaction<ArrayList<Ball>>() {
			@Override
			public ArrayList<Ball> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from arsenal " +
							" order by short_name"
					);
					
					ArrayList<Ball> result = new ArrayList<Ball>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Ball ball = new Ball();
						loadBall(ball, resultSet, 1);
						
						result.add(ball);
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No balls were found in the database");
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
	
	@Override
	public ArrayList<Session> findAllSessions() {
		return executeTransaction(new Transaction<ArrayList<Session>>() {
			@Override
			public ArrayList<Session> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from sessions " +
							" order by week asc, date_bowled asc"
					);
					
					ArrayList<Session> result = new ArrayList<Session>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Session session = new Session();
						loadSession(session, resultSet, 1);
						
						result.add(session);
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No sessions were found in the database");
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
	}
	
	@Override
	public ArrayList<Shot> findAllShots() {
	    return executeTransaction(new Transaction<ArrayList<Shot>>() {
	        @Override
	        public ArrayList<Shot> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;
	            
	            ArrayList<Shot> result = new ArrayList<>();

	            try {
	                stmt = conn.prepareStatement(
	                    "SELECT * FROM shots"
	                );
	                
	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                   Shot shot = new Shot();
	                   loadShot(shot, resultSet, 1);
	                   result.add(shot);
	                }

	                if (result.isEmpty()) {
	                    System.out.println("No shots found.");
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
	public ArrayList<Shot> findAllShotsGivenEvent(String event) {
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
	                    "SELECT * FROM shots WHERE game_id = ?"
	                );

	                for (String gameId : gameIds) {
	                    stmt2.setString(1, gameId);
	                    resultSet2 = stmt2.executeQuery();

	                    while (resultSet2.next()) {
	                        Shot shot = new Shot();
	                        loadShot(shot, resultSet2, 1);
	                        result.add(shot);
	                    }

	                    DBUtil.closeQuietly(resultSet2);  // Close between iterations
	                }

	                if (result.isEmpty()) {
	                    System.out.println("No shots found for the given event.");
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
	public ArrayList<Shot> findAllShotsGivenSeason(String season) {
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
	                    "SELECT game_id FROM games WHERE season = ?"
	                );
	                stmt1.setString(1, season);
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
	                    "SELECT * FROM shots WHERE game_id = ?"
	                );

	                for (String gameId : gameIds) {
	                    stmt2.setString(1, gameId);
	                    resultSet2 = stmt2.executeQuery();

	                    while (resultSet2.next()) {
	                        Shot shot = new Shot();
	                        loadShot(shot, resultSet2, 1);
	                        result.add(shot);
	                    }

	                    DBUtil.closeQuietly(resultSet2);  // Close between iterations
	                }

	                if (result.isEmpty()) {
	                    System.out.println("No shots found for the given event.");
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
	public ArrayList<Shot> findAllShotsGivenFrameSeason(String frameNum, String season) {
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
	                    "SELECT game_id FROM games WHERE season = ?"
	                );
	                stmt1.setString(1, season);
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
	                    System.out.println("No shots found for the given frame and season.");
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
	public ArrayList<Shot> findAllShotsGivenEventSeason(String event, String season) {
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
	                    "SELECT * FROM shots WHERE game_id = ?"
	                );

	                for (String gameId : gameIds) {
	                    stmt2.setString(1, gameId);
	                    resultSet2 = stmt2.executeQuery();

	                    while (resultSet2.next()) {
	                        Shot shot = new Shot();
	                        loadShot(shot, resultSet2, 1);
	                        result.add(shot);
	                    }

	                    DBUtil.closeQuietly(resultSet2);  // Close between iterations
	                }

	                if (result.isEmpty()) {
	                    System.out.println("No shots found for the given event and season.");
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
	
	@Override
	public ArrayList<Event> findAllEvents() {
		return executeTransaction(new Transaction<ArrayList<Event>>() {
			@Override
			public ArrayList<Event> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from events "
					);
					
					ArrayList<Event> result = new ArrayList<Event>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Event event = new Event();
						loadEvent(event, resultSet, 1);
						
						result.add(event);
					}
					
					// check if any events were found
					if (!found) {
						System.out.println("No events were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// transaction that inserts new Book into the Books table
	// also first inserts new Author into Authors table, if necessary
	// and then inserts entry into BookAuthors junction table
	@Override
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
	public Integer insertEstablishmentIntoEstablishmentsTable(final String longName, final String shortName, final String address, final String phone, final Integer lanes, final String type) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;				
				
				ResultSet resultSet5 = null;				
				
				Integer establishment_id = -1;
				try {
					
					// now insert new Establishment into Establishments table
					// prepare SQL insert statement to add new Establishment to Establishments table
					stmt4 = conn.prepareStatement(
							"insert into establishments (longname, shortname, address, phone, lanes, type) " +
							"  values(?, ?, ?, ?, ?, ?) "
					);
					stmt4.setString(1, longName);
					stmt4.setString(2, shortName);
					stmt4.setString(3, address);
					stmt4.setString(4, phone);
					stmt4.setInt(5, lanes);
					stmt4.setString(6, type);
					
					// execute the update
					stmt4.executeUpdate();
					
					System.out.println("New establishment <" + longName + "> inserted into Establishments table");					

					// now retrieve establishment_id for new Establishment, so that we can set up Establishment entry
					// and return the establishment_id, which the DB auto-generates
					// prepare SQL statement to retrieve establishment_id for new Book
					stmt5 = conn.prepareStatement(
							"select establishment_id from establishments " +
							"  where longName = ? and shortName = ? and address = ? and phone = ? and lanes = ? and type = ? "
									
					);
					stmt5.setString(1, longName);
					stmt5.setString(2, shortName);
					stmt5.setString(3, address);
					stmt5.setString(4, phone);
					stmt5.setInt(5, lanes);
					stmt5.setString(6, type);

					// execute the query
					resultSet5 = stmt5.executeQuery();
					
					// get the result - there had better be one
					if (resultSet5.next())
					{
						establishment_id = resultSet5.getInt(1);
						System.out.println("New establishment <" + longName + "> ID: " + establishment_id);						
					}
					else	// really should throw an exception here - the new establishment should have been inserted, but we didn't find it
					{
						System.out.println("New establishment <" + longName + "> not found in Establishments table (ID: " + establishment_id);
					}				
					
					return establishment_id;
				} finally {				
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
	}
	
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
	
	@Override
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
	}
	
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
	
	@Override
	public Integer insertEvent(final String longname, final String shortname, final String type, final String establishment, final String season, final Integer team, final String composition, final String day, final String time, final String start, final String end_date, final Integer gamesPerSession, final Integer weeks, final Integer playoffs) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				//PreparedStatement stmt3 = null;				
				
				ResultSet resultSet1 = null;
				//ResultSet resultSet3 = null;				
				
				// for saving event ID
				Integer event_id = -1;

				// try to retrieve event_id (if it exists) from DB, for Event's long and short names
				try {

					// now insert new Establishment into Establishments table
					// prepare SQL insert statement to add new Establishment to Establishments table
					stmt1 = conn.prepareStatement(
							"insert into events (longname, shortname, type, establishment, season, team, composition, day, time, start_date, end_date, games_per_session, weeks, playoffs) " +
							"  values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					);
					stmt1.setString(1, longname);
					stmt1.setString(2, shortname);
					stmt1.setString(3, type);
					stmt1.setString(4, establishment);
					stmt1.setString(5, season);
					stmt1.setInt(6, team);
					stmt1.setString(7, composition);
					stmt1.setString(8, day);
					stmt1.setString(9, time);
					stmt1.setString(10, start);
					stmt1.setString(11, end_date);
					stmt1.setInt(12, gamesPerSession);
					stmt1.setInt(13, weeks);
					stmt1.setInt(14, playoffs);
					
					// execute the update
					stmt1.executeUpdate();
					
					System.out.println("New event <" + longname + "> inserted into Events table");					

					// now retrieve establishment_id for new Establishment, so that we can set up Establishment entry
					// and return the establishment_id, which the DB auto-generates
					// prepare SQL statement to retrieve establishment_id for new Book
					stmt2 = conn.prepareStatement(
							"select event_id from events " +
							"  where longname = ? and shortname = ? and type = ? and establishment = ? and season = ? and team = ? and composition = ? and day = ? and time = ? and start_date = ? and end_date = ? and games_per_session = ? and weeks = ? and playoffs = ? "
									
					);
					stmt2.setString(1, longname);
					stmt2.setString(2, shortname);
					stmt2.setString(3, type);
					stmt2.setString(4, establishment);
					stmt2.setString(5, season);
					stmt2.setInt(6, team);
					stmt2.setString(7, composition);
					stmt2.setString(8, day);
					stmt2.setString(9, time);
					stmt2.setString(10, start);
					stmt2.setString(11, end_date);
					stmt2.setInt(12, gamesPerSession);
					stmt2.setInt(13, weeks);
					stmt2.setInt(14, playoffs);
					
					// execute the query
					resultSet1 = stmt2.executeQuery();
					
					// get the result - there had better be one
					if (resultSet1.next())
					{
						event_id = resultSet1.getInt(1);
						System.out.println("New event <" + longname + "> ID: " + event_id);						
					}
					else	// really should throw an exception here - the new establishment should have been inserted, but we didn't find it
					{
						System.out.println("New event <" + longname + "> not found in Events table (ID: " + event_id + ")");
					}				
					
					return event_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
				}
			}
		});
	}
	
	@Override
	public Integer insertEstablishment(final String longname, final String shortname, final String address, final String phone, final Integer lanes, final String type) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;				
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;				
				
				// for saving establishment ID
				Integer establishment_id = -1;

				// try to retrieve establishment_id (if it exists) from DB, for Establishment's long and short names
				try {
					stmt1 = conn.prepareStatement(
							"select establishment_id from establishments " +
							"  where longname = ? and shortname = ? "
					);
					stmt1.setString(1, longname);
					stmt1.setString(2, shortname);
					
					// execute the query, get the result
					resultSet1 = stmt1.executeQuery();

					
					// if Establishment was found then save establishment_id					
					if (resultSet1.next())
					{
						establishment_id = resultSet1.getInt(1);
						System.out.println("Establishment <" + longname + ", " + shortname + "> found with ID: " + establishment_id);						
					}
					else
					{
						System.out.println("Establishment <" + longname + ", " + shortname + "> not found");
				
						// if the Establishment is new, insert new Establishment into Establishments table
						if (establishment_id <= 0) {
							// prepare SQL insert statement to add Establishment to Establishments table
							stmt2 = conn.prepareStatement(
									"insert into establishments (longname, shortname, address, phone, lanes, type) " +
									"  values(?, ?, ?, ?, ?, ?) "
							);
							stmt2.setString(1, longname);
							stmt2.setString(2, shortname);
							stmt2.setString(3, address);
							stmt2.setString(4, phone);
							stmt2.setString(5, lanes);
							stmt2.setString(6, type);
							
							// execute the update
							stmt2.executeUpdate();
							
							System.out.println("New Establishment <" + longname + ", " + shortname + "> inserted in Establishments table");						
						
							// try to retrieve establishment_id for new Establishment - DB auto-generates establishment_id
							stmt3 = conn.prepareStatement(
									"select establishment_id from establishments " +
									"  where longname = ? and shortname = ? "
							);
							stmt3.setString(1, longname);
							stmt3.setString(2, shortname);
							
							// execute the query							
							resultSet3 = stmt3.executeQuery();
							
							// get the result - there had better be one							
							if (resultSet3.next())
							{
								establishment_id = resultSet3.getInt(1);
								System.out.println("New Establishment <" + longname + ", " + shortname + "> ID: " + establishment_id);						
							}
							else	// really should throw an exception here - the new establishment should have been inserted, but we didn't find them
							{
								System.out.println("New establishment <" + longname + ", " + shortname + "> not found in Establishments table (ID: " + establishment_id);
							}
						}
					}				
					
					return establishment_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
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
				/*PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
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
						"	cheekpiece varchar(40)" +
						"	size varchar(40)," +
						"	purpose varchar(40)," +
						"	comment1 varchar(100)," +
						"	comment2 varchar(100)," +
						"	comment3 varchar(100)" +
						")"
					);	
					stmt1.executeUpdate();					
					System.out.println("Bit table created");
					
					
					/*stmt2 = conn.prepareStatement(
							"create table books (" +
							"	book_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	title varchar(70)," +
							"	isbn varchar(15)," +
							"   published integer" +
							")"
					);
					stmt2.executeUpdate();					
					System.out.println("Books table created");					
					
					
					stmt3 = conn.prepareStatement(
							"create table bookAuthors (" +
							"	book_id   integer constraint book_id references books, " +
							"	author_id integer constraint author_id references authors " +
							")"
					);
					stmt3.executeUpdate();
					System.out.println("BookAuthors table created");	
					
					
					stmt4 = conn.prepareStatement(
							"create table establishments (" +
									"	establishment_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	longname varchar(60), " +
									"	shortname varchar(30), " +
									"	address varchar(60), " +
									"	phone varchar(60), " +
									"	lanes integer, " +
									"	type varchar(60) " +
							")"
					);	
					stmt4.executeUpdate();
					System.out.println("Establishment table created");
					
					
					stmt5 = conn.prepareStatement(
							"create table events (" +
									"	event_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	longname varchar(60), " +
									"	shortname varchar(30), " +
									"   type varchar(30), " +
									"	establishment varchar(30), " +
									"   season varchar(30), " +
									"   team integer, " +
									"   composition varchar(60), " +
									"	day varchar(10), " +
									"   time varchar(30), " +
									"	start_date varchar(10), " +
									"	end_date varchar(10), " +
									"	games_per_session integer, " +
									"   weeks integer, " +
									"   playoffs integer " +
							")"
					);	
					stmt5.executeUpdate();
					System.out.println("Events table created");
					
					
					stmt6 = conn.prepareStatement(
							"create table arsenal (" +
									"	ball_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	long_name varchar(60), " +
									"	short_name varchar(30), " +
									"	brand varchar(30), " +
									"	type varchar(30), " +
									"	core varchar(30), " +
									"	cover varchar(30), " +
									"	color varchar(30), " +
									"	surface varchar(30), " +
									"	ball_year varchar(10), " +
									"	serial_number varchar(20), " +
									"	weight varchar(10), " +
									"	mapping varchar(30) " +
							")"
					);	
					stmt6.executeUpdate();
					System.out.println("Arsenal table created");
					
					stmt7 = conn.prepareStatement(
							"create table sessions (" +
									"	session_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	league varchar(30), " +
									"	season varchar(30), " +
									"	week integer, " +
									"	date_bowled Date, " +
									"	reg_sub varchar(10), " +
									"	opponent varchar(50), " +
									"	start_lane integer, " +
									"	ball varchar(10), " +
									"	game_one integer, " +
									"	game_two integer, " +
									"	game_three integer, " +
									"	series integer " +
									")"
					);
					stmt7.executeUpdate();
					System.out.println("Sessions table created");
					
					
					stmt7 = conn.prepareStatement(
							"create table games (" +
									"	game_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	league varchar(30), " +
									"	season varchar(10), " +
									"	week varchar(10), " +
									"	date varchar(10), " +
									"	game varchar(10), " +
									"	lane varchar(10) " +
									")"
					);
					stmt7.executeUpdate();
					System.out.println("Games table created");
					
					
					stmt8 = conn.prepareStatement(
							"create table frames (" +
									"	frame_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id varchar(30), " +
									"	frame_number varchar(10) " +
									")"
					);
					stmt8.executeUpdate();
					System.out.println("Frames table created");
					
					
					stmt8 = conn.prepareStatement(
							"create table shots (" +
									"	shot_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id varchar(30), " +
									"	frame_number varchar(30), " +
									"	shot_number Integer, " +
									"	count varchar(30), " +
									"	leave varchar(30), " +
									"	score varchar(30), " +
									"	type varchar(30), " +
									"	board varchar(30), " +
									"	lane varchar(30), " +
									"	ball varchar(10) " +
									")"
					);
					stmt8.executeUpdate();
					System.out.println("Shots table created");		*/							
					
					return true;
					
				} finally {
					DBUtil.closeQuietly(stmt1);
					/*DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
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
				/*List<Book> bookList;
				List<BookAuthor> bookAuthorList;
				List<Establishment> establishmentList;		// new
				List<Event> eventList;						// new
				List<Ball> arsenal;
				List<Session> sessionList;					// new
				List<Game> gameList;
				List<Frame> frameList;	
				List<Shot> shotList;	*/
				
				try {
					
					bitList         = InitialData.getBits();
					/*bookList           = InitialData.getBooks();
					bookAuthorList     = InitialData.getBookAuthors();	
					establishmentList  = InitialData.getEstablishments();
					eventList 	   	   = InitialData.getEvents();
					arsenal 	       = InitialData.getArsenal();
					sessionList		   = InitialData.getSessions();
					gameList		   = (List<Game>)InitialData.getGames().get(0);
					frameList		   = (List<Frame>)InitialData.getGames().get(1);
					shotList		   = (List<Shot>)InitialData.getGames().get(2);*/
					
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertBit     		= null;
				/*PreparedStatement insertBook       		= null;
				PreparedStatement insertBookAuthor 		= null;
				
				PreparedStatement insertEstablishment 	= null;		// new
				PreparedStatement insertEvent       	= null;		// new
				PreparedStatement insertBall       		= null;		// new
				PreparedStatement insertSession 		= null;
				PreparedStatement insertGame	 		= null;
				PreparedStatement insertFrame	 		= null;
				PreparedStatement insertShot	 		= null; */

				try {
					// must completely populate Authors table before populating BookAuthors table because of primary keys
					insertBit = conn.prepareStatement("insert into bits (type, cheekpiece, size, purpose, comment1, comment2, comment3) values (?, ?, ?, ?, ?, ?, ?)");
					for (Bit bit : bitList) {
//						insertAuthor.setInt(1, author.getAuthorId());	// auto-generated primary key, don't insert this
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
					
					
					// must completely populate Books table before populating BookAuthors table because of primary keys
					/* insertBook = conn.prepareStatement("insert into books (title, isbn, published) values (?, ?, ?)");
					for (Book book : bookList) {
						insertBook.setString(1, book.getTitle());
						insertBook.setString(2, book.getIsbn());
						insertBook.setInt(3, book.getPublished());
						insertBook.addBatch();
					}
					insertBook.executeBatch();
					System.out.println("Books table populated");					
					
					
					// must wait until all Books and all Authors are inserted into tables before creating BookAuthor table
					// since this table consists entirely of foreign keys, with constraints applied
					insertBookAuthor = conn.prepareStatement("insert into bookAuthors (book_id, author_id) values (?, ?)");
					for (BookAuthor bookAuthor : bookAuthorList) {
						insertBookAuthor.setInt(1, bookAuthor.getBookId());
						insertBookAuthor.setInt(2, bookAuthor.getAuthorId());
						insertBookAuthor.addBatch();
					}
					insertBookAuthor.executeBatch();	
					System.out.println("BookAuthors table populated");	
					
					
					// must completely populate Establishment table before events
					insertEstablishment = conn.prepareStatement("insert into establishments (longname, shortname, address, phone, lanes, type) values (?, ?, ?, ?, ?, ?)");
					for (Establishment establishment : establishmentList) {
						insertEstablishment.setString(1, establishment.getLongname());
						insertEstablishment.setString(2, establishment.getShortname());
						insertEstablishment.setString(3, establishment.getAddress());
						insertEstablishment.setString(4, establishment.getPhone());
						insertEstablishment.setInt(5, establishment.getLanes());
						insertEstablishment.setString(6, establishment.getType());
						insertEstablishment.addBatch();
					}
					insertEstablishment.executeBatch();
					System.out.println("Establishment table populated");
					
					
					// must completely populate Establishment table before events
					insertEvent = conn.prepareStatement("insert into events (longname, shortname, type, establishment, season, team, composition, day, time, start_date, end_date, games_per_session, weeks, playoffs) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for (Event event : eventList) {
						insertEvent.setString(1, event.getLongname());
						insertEvent.setString(2, event.getShortname());
						insertEvent.setString(3, event.getType());
						insertEvent.setString(4, event.getEstablishment());
						insertEvent.setString(5, event.getSeason());
						insertEvent.setInt(6, event.getTeam());
						insertEvent.setString(7,  event.getComposition());
						insertEvent.setString(8, event.getDay());
						insertEvent.setString(9,  event.getTime());
						insertEvent.setString(10, event.getStart());
						insertEvent.setString(11, event.getEnd());
						insertEvent.setInt(12, event.getGamesPerSession());
						insertEvent.setInt(13, event.getWeeks());
						insertEvent.setInt(14,  event.getPlayoffs());
						insertEvent.addBatch();
					}
					insertEvent.executeBatch();
					System.out.println("Event table populated");
					
					
					// must completely populate Establishment table before events
					insertBall = conn.prepareStatement("insert into arsenal (long_name, short_name, brand, type, core, cover, color, surface, ball_year, serial_number, weight, mapping) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for (Ball ball : arsenal) {
						insertBall.setString(1, ball.getLongname());
						insertBall.setString(2, ball.getShortname());
						insertBall.setString(3, ball.getBrand());
						insertBall.setString(4, ball.getType());
						insertBall.setString(5, ball.getCore());
						insertBall.setString(6, ball.getCover());
						insertBall.setString(7, ball.getColor());
						insertBall.setString(8, ball.getSurface());
						insertBall.setString(9, ball.getYear());
						insertBall.setString(10, ball.getSerialNumber());
						insertBall.setString(11, ball.getWeight());
						insertBall.setString(12, ball.getMapping());
						insertBall.addBatch();
					}
					insertBall.executeBatch();
					System.out.println("Arsenal table populated");
					
					
					insertSession = conn.prepareStatement("insert into sessions (league, season, week, date_bowled, reg_sub, opponent, start_lane, ball, game_one, game_two, game_three, series) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for(Session session : sessionList) {
						insertSession.setString(1, session.getLeague());
						insertSession.setString(2,  session.getSeason());
						insertSession.setInt(3, session.getWeek());
						insertSession.setDate(4, session.getBowled());
						insertSession.setString(5, session.getRegSub());
						insertSession.setString(6, session.getOpponent());
						insertSession.setInt(7, session.getStart());
						insertSession.setString(8, session.getBall());
						insertSession.setInt(9, session.getGameOneScore());
						insertSession.setInt(10, session.getGameTwoScore());
						insertSession.setInt(11, session.getGameThreeScore());
						insertSession.setInt(12, session.getSeries());
						insertSession.addBatch();
					}
					insertSession.executeBatch();
					System.out.println("Session table populated!");
					//YIPPEE
					
					
					insertGame = conn.prepareStatement("insert into games (league, season, week, date, game, lane) values (?, ?, ?, ?, ?, ?)");
					for(Game game : gameList) {
						insertGame.setString(1, game.getLeague());
						insertGame.setString(2, game.getSeason());
						insertGame.setString(3, game.getWeek());
						insertGame.setString(4, game.getDate());
						insertGame.setString(5, game.getGame());
						insertGame.setString(6, game.getLane());
						insertGame.addBatch();
					}
					insertGame.executeBatch();
					System.out.println("Game table populated!");
					//YIPPEE
					
					
					insertFrame = conn.prepareStatement("insert into frames (game_id, frame_number) values (?, ?)");
					for(Frame frame : frameList) {
						insertFrame.setString(1, String.valueOf(frame.getGameID()));
						insertFrame.setString(2, frame.getFrameNumber());
					}
					insertFrame.executeBatch();
					System.out.println("Frame table populated!");
					//YIPPEE
					
					
					insertShot = conn.prepareStatement("insert into shots (game_id, frame_number, shot_number, count, leave, score, type, board, lane, ball) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for(Shot shot : shotList) {
						insertShot.setString(1, String.valueOf(shot.getGameID())); // game ID
						insertShot.setString(2, String.valueOf(shot.getFrameNumber()));
						insertShot.setInt(3, Integer.valueOf(shot.getShotNumber()));
						insertShot.setString(4, shot.getCount());
						insertShot.setString(5, shot.getLeave());
						insertShot.setString(6, shot.getScore());
						insertShot.setString(7, shot.getType());
						insertShot.setString(8, shot.getBoard());
						insertShot.setString(9, shot.getLane());
						insertShot.setString(10, shot.getBall());
						insertShot.addBatch();
					}
					insertShot.executeBatch();
					System.out.println("Shot table populated!");
					//YIPPEE */
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertBit);
					/*DBUtil.closeQuietly(insertAuthor);
					DBUtil.closeQuietly(insertBookAuthor);	
					DBUtil.closeQuietly(insertEstablishment);
					DBUtil.closeQuietly(insertEvent);	
					DBUtil.closeQuietly(insertBall);
					DBUtil.closeQuietly(insertSession);
					DBUtil.closeQuietly(insertGame);
					DBUtil.closeQuietly(insertFrame);
					DBUtil.closeQuietly(insertShot);*/
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
