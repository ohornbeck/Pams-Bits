package edu.ycp.cs320.booksdb;

import java.util.ArrayList;
import java.util.Scanner;

import edu.ycp.cs320.booksdb.model.Session;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class OverallGameAverageforEvent {
	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);

		// Initialize the database
		InitDatabase.init(keyboard);

		System.out.print("Enter an event's shortname: ");
		String eventShortname = keyboard.nextLine();
		
		IDatabase db = DatabaseProvider.getInstance();
		ArrayList<Session> gameList = db.findGamesForSessionLeague(eventShortname);

		if (gameList.isEmpty()) {
			System.out.println("No games found for this event <" + eventShortname + ">");
		} else {
			for (Session session : gameList) {
				int x = Integer.parseInt(session.getGameOneScore());
				int y = Integer.parseInt(session.getGameTwoScore());
				int z = Integer.parseInt(session.getGameThreeScore());
				
				int result = ((x + y + z)/3);
				
				System.out.println(
					result
				);
			}
		}
		
	}
	
}



