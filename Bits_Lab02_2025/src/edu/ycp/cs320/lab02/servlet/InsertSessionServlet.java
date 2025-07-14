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
import edu.ycp.cs320.lab02.controller.InsertBallController;
import edu.ycp.cs320.lab02.controller.InsertBookController;
import edu.ycp.cs320.lab02.controller.InsertSessionController;
import edu.ycp.cs320.lab02.controller.ArsenalController;

public class InsertSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private InsertSessionController controller = null;	
	private AllEventsController eventsController = null;
	private ArsenalController arsenalController = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nInsertSessionServlet: doGet");

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
		
		ArrayList<Ball> arsenal = null;
		arsenalController = new ArsenalController();
		arsenal = arsenalController.getAllBalls();
		req.setAttribute("arsenal",  arsenal);

		req.getRequestDispatcher("/_view/insertSession.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nInsertSessionServlet: doPost");	
		
		
		String errorMessage   = null;
		String successMessage = null;
		String league      	  = null;
		String bowledStr 	  = null;
		Date   bowled      	  = null;
		//String week      	  = null;
		String strikeBall     = null;
		String spareBall      = null;
		String ball      	  = null;
		int startLane         = 0;
		

		
		// Decode form parameters and dispatch to controller
		
		
		league    		= req.getParameter("league");
		bowledStr 		= req.getParameter("bowled");
		if (bowledStr == null || bowledStr.trim().isEmpty()) {
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
		        bowled = new java.sql.Date(parsedDate.getTime());
		        
		        System.out.println("Successfully parsed date: " + bowled);  // Debug log
		    } catch (ParseException e) {
		        errorMessage = "Invalid date format. Please use MM/DD/YYYY format";
		        System.err.println("Failed to parse date '" + bowledStr + "': " + e.getMessage());
		    }
		}
		strikeBall      = req.getParameter("strikeBall");
		spareBall       = req.getParameter("spareBall");
		startLane       = Integer.parseInt(req.getParameter("startLane"));
		//week        = req.getParameter("week");
		
		if (league == null || league.trim().isEmpty()) {
		    errorMessage = "League is required";
		} else if (bowled == null) {
		    errorMessage = "Valid date is required";
		} else if (strikeBall == null || strikeBall.trim().isEmpty()) {
		    errorMessage = "Strike ball is required";
		} else if (spareBall == null || spareBall.trim().isEmpty()) {
		    errorMessage = "Spare ball is required";
		} else if (startLane <= 0) {
		    errorMessage = "Valid start lane is required";
		} else {
			controller = new InsertSessionController();
			if(strikeBall.equals(spareBall)) {
				ball = strikeBall;
			}
			else {
				ball = strikeBall + "/" + spareBall;
			}
			// convert published to integer now that it is valid
			// published = Integer.parseInt(strPublished);
			Integer weekID = controller.insertSession(league, bowled, ball, startLane);
			// get list of books returned from query			
			if (weekID >= 1) {
				successMessage = "League: " + league + " - Bowled: " + bowled + " - Ball: " + ball+ " - Start Lane: " + startLane+ " - Week: " + weekID;
			}
			else {
				errorMessage = "Failed to insert Session - week: " + weekID;					
			}
			req.setAttribute("week", weekID);
		}
		
		
		
		
		ArrayList<Event> events = null;
		eventsController = new AllEventsController();
		events = eventsController.getEvents();
		req.setAttribute("events",  events);
		
		ArrayList<Ball> arsenal = null;
		arsenalController = new ArsenalController();
		arsenal = arsenalController.getAllBalls();
		req.setAttribute("arsenal",  arsenal);
		
		// Add parameters as request attributes
		req.setAttribute("league", league);
		req.setAttribute("bowled", bowled);
		req.setAttribute("startLane",startLane);
		
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage",   errorMessage);
		req.setAttribute("successMessage", successMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/insertSession.jsp").forward(req, resp);
	}	
}
