package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;
import edu.ycp.cs320.booksdb.model.Session;

public class AllSessionsController {

	private IDatabase db = null;

	public AllSessionsController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public ArrayList<Session> getSessions() {
		
		
		// get the list of sessions from DB
		ArrayList<Session> sessionList = db.findAllSessions();
		//Have to temporary make findAllSessions ArrayList from List type in IDatabase (I did it wrong originally)
		ArrayList<Session> sessions = null;
		
		if (sessionList.isEmpty()) {
			System.out.println("No events found in library");
			return null;
		}
		else {
			sessions = new ArrayList<Session>();
			for (Session session : sessionList) {
				sessions.add(session);
				System.out.println(session.getLeague() + ", " + session.getSeason() + ", " + session.getWeek() + ", " + session.getBowled() + ", " + session.getRegSub() + ", " + session.getOpponent() + ", " + session.getStart() + ", " + session.getBall() + ", " + session.getGameOneScore() + ", " + session.getGameTwoScore() + ", " + session.getGameThreeScore() + ", " + session.getSeries() + ", ");
			}			
		}
		
		// return authors for this title
		return sessions;
	}
}

