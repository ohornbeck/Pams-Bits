package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.booksdb.model.Ball;
import edu.ycp.cs320.booksdb.model.Event;
import edu.ycp.cs320.lab02.controller.AllEventsController;
import edu.ycp.cs320.lab02.controller.InsertGameController;
import edu.ycp.cs320.lab02.controller.InsertBookController;
import edu.ycp.cs320.lab02.controller.InsertSessionController;
import edu.ycp.cs320.lab02.controller.ArsenalController;

public class InsertGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private InsertGameController controller = null;	
	private AllEventsController eventsController = null;
	private ArsenalController arsenalController = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nInsertGameServlet: doGet");

		String user = (String) req.getSession().getAttribute("user");
		if (user == null) {
			System.out.println("   User: <" + user + "> not logged in or session timed out");
			
			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		// now we have the user's User object,
		// proceed to handle request...
		
		System.out.println("   User: <" + user + "> logged in");
		
		ArrayList<Event> events = null;
		eventsController = new AllEventsController();
		events = eventsController.getEvents();
		req.setAttribute("events",  events);
		
		/*ArrayList<Ball> arsenal = null;
		arsenalController = new ArsenalController();
		arsenal = arsenalController.getAllBalls();
		req.setAttribute("arsenal",  arsenal);*/

		req.getRequestDispatcher("/_view/insertGame.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nInsertGameServlet: doPost");	
		
		
		String errorMessage   = null;
		String successMessage   = null;
		String league      	  = null;
		String season 	  	  = null;
		String week 	  	  = null;
		String bowledStr 	  = null;
		String   date      	  = null;
		String game     	  = null;
		String lane      	  = null;
		

		// Decode form parameters and dispatch to controller
		league    		= req.getParameter("league");
		season 		    = req.getParameter("season");
		//bowledStr 		= req.getParameter("bowledStr");
		date 		    = req.getParameter("date");
		
		
		/*if (bowledStr == null || bowledStr.trim().isEmpty()) {
		    errorMessage = "Date is required";
		    
		} else {
		    try {
		        // Trim and ensure proper format
		        bowledStr = bowledStr.trim();
		        System.out.println("Attempting to parse date: " + bowledStr);  // Debug log
		        
		        // Use strict parsing
		        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		        dateFormat.setLenient(false);  // This prevents accepting invalid dates like 02/30/2023
		        
		        java.util.Date parsedDate = dateFormat.parse(bowledStr);
		        date = new java.sql.Date(parsedDate.getTime());
		        
		        System.out.println("Successfully parsed date: " + date);  // Debug log
		        
		    } catch (ParseException e) {
		        errorMessage = "Invalid date format. Please use MM/DD/YYYY format";
		        System.err.println("Failed to parse date '" + bowledStr + "': " + e.getMessage());
		    }
		}*/
		
		week       = req.getParameter("week");
		game       = req.getParameter("game");
		lane       = req.getParameter("lane");
		
		if (league == null || league.trim().isEmpty()) {
		    errorMessage = "League is required";
		} else if (date == null) {
		    errorMessage = "Valid date is required";
		} else if (season == null || season.equals("")) {
		    errorMessage = "Season is required";
		} else if (week == null || week.equals("")) {
		    errorMessage = "Week is required";
		} else if (lane == null || week.equals("")) {
		    errorMessage = "Lane is required";
		} else {
			controller = new InsertGameController();
			
			// convert published to integer now that it is valid
			// published = Integer.parseInt(strPublished);
			Integer gameID = controller.insertGame(league, season, week, String.valueOf(date), game, lane);
			// get list of books returned from query			
			if (gameID >= 1) {
				successMessage = "League: " + league + " - Season: " + season + " - Week: " + week + " - Date: " + String.valueOf(date) + " - Game: " + game + " - Lane: " + lane;
			}
			else {
				errorMessage = "Failed to insert Session - week: " + gameID;					
			}
			req.setAttribute("game", gameID);
		}
		
		
		ArrayList<Event> events = null;
		eventsController = new AllEventsController();
		events = eventsController.getEvents();
		req.setAttribute("events",  events);
		
		// Add parameters as request attributes
		req.setAttribute("league", league);
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage",   errorMessage);
		req.setAttribute("successMessage", successMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/insertGame.jsp").forward(req, resp);
	}	
}
