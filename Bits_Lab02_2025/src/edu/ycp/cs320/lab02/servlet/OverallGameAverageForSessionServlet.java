package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.booksdb.model.Session;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;

public class OverallGameAverageForSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
	    super.init();
	    System.out.println("Initializing database instance in OverallGameAverageForSessionServlet");
	    DatabaseProvider.setInstance(new DerbyDatabase());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    System.out.println("\nOverallGameAverageForSessionServlet: doGet");

	    req.setAttribute("formSubmitted", false);
	    req.setAttribute("errorMessage", null);
	    req.setAttribute("percentResult", null);
	    req.setAttribute("sessionDate", null);

	    req.getRequestDispatcher("/_view/overallGameAverageForSession.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    System.out.println("\nOverallGameAverageForSessionServlet: doPost");

	    String rawDate = req.getParameter("sessionDate");
	    String formattedDate = null;
	    Double average = null;
	    String errorMessage = null;

	    req.setAttribute("formSubmitted", true);
	    req.setAttribute("sessionDate", rawDate);  // Keep ISO format for redisplay in form

	    if (rawDate == null || rawDate.trim().isEmpty()) {
	        errorMessage = "Please enter a session date.";
	    } else {
	        try {
	            // Parse ISO 8601 input (yyyy-MM-dd) and reformat to match DB's expected format (e.g., M/d/yyyy)
	            LocalDate parsedDate = LocalDate.parse(rawDate); // will throw DateTimeParseException if invalid
	            DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	            formattedDate = parsedDate.format(dbFormatter);

	            System.out.println("Converted input date: " + rawDate + " → formatted for DB: " + formattedDate);

	            IDatabase db = DatabaseProvider.getInstance();
	            ArrayList<Session> gameList = db.findGamesWithSessionDate(formattedDate);

	            if (gameList == null || gameList.isEmpty()) {
	                errorMessage = "No sessions found for the entered date.";
	            } else {
	                int total = 0;
	                int count = 0;

	                for (Session session : gameList) {
	                    total += session.getGameOneScore();
	                    total += session.getGameTwoScore();
	                    total += session.getGameThreeScore();
	                    count += 3;
	                }

	                average = (double) total / count;
	            }
	        } catch (DateTimeParseException e) {
	            errorMessage = "Invalid date format. Please use the date picker.";
	        } catch (Exception e) {
	            errorMessage = "Error calculating average: " + e.getMessage();
	        }
	    }

	    req.setAttribute("percentResult", average);
	    req.setAttribute("errorMessage", errorMessage);

	    req.getRequestDispatcher("/_view/overallGameAverageForSession.jsp").forward(req, resp);
	}
}
