package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab02.controller.InsertEventController;
import edu.ycp.cs320.booksdb.model.Event;
import edu.ycp.cs320.booksdb.model.Establishment;
import edu.ycp.cs320.lab02.controller.AllEventsController;
import edu.ycp.cs320.lab02.controller.PocketPercentageController;

public class PocketPercentageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PocketPercentageController controller = null;
	private AllEventsController eventsController = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    System.out.println("\nPocketPercentageServlet: doGet");

	    String user = (String) req.getSession().getAttribute("user");
	    if (user == null) {
	        System.out.println("   User: <" + user + "> not logged in or session timed out");

	        // user is not logged in, or the session expired
	        resp.sendRedirect(req.getContextPath() + "/login");
	        return;
	    }

	    // Now we have the user's User object, proceed to handle request...
	    System.out.println("   User: <" + user + "> logged in");

	    req.getSession().removeAttribute("formSubmitted");
	    req.getSession().removeAttribute("errorMessage");
	    req.getSession().removeAttribute("percentResult");
	    req.getSession().removeAttribute("frameNum");
	    req.getSession().removeAttribute("selectedEvent");
	    req.getSession().removeAttribute("season");
	    
	    ArrayList<Event> events = null;
	    eventsController = new AllEventsController();
	    events = eventsController.getEvents();
	    req.setAttribute("events", events);

	    req.getRequestDispatcher("/_view/pocketPercentage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nPocketPercentageServlet: doPost");		
		
		String errorMessage = null;
		String frameNum = null;
		String event = null;
		String season = null;
		Double percentResult = null;
		
		// Decode form parameters and dispatch to controller
		frameNum = req.getParameter("frameNum");
		event = req.getParameter("event");
		season = req.getParameter("season");
		
		controller = new PocketPercentageController();
		// Normalize common variations
		if ("Sr. Singles".equalsIgnoreCase(event)) {
		    event = "Sr-Singles";  // format as it appears in DB
		} else if ("Sr. Trio".equalsIgnoreCase(event)) {
			event = "Sr-Trio";
		} else {
		}
		
		// Only calculate the result if the form was submitted
	    if (frameNum != null && event != null) {
	        try {
	            // Compute the strike percentage result
	            if ((frameNum == null || frameNum.equals("")) && (event == null || event.equals("")) && (season == null || season.equals(""))) {
	            	percentResult = controller.AllPocketPercentage();
	            } else if ((event == null || event.equals("")) && (season == null || season.equals(""))) {
	            	percentResult = controller.PocketPercentageFrame(frameNum);
	            } else if ((frameNum == null || frameNum.equals("")) && (season == null || season.equals(""))) {
	            	percentResult = controller.PocketPercentageEvent(event);
	            } else if ((frameNum == null || frameNum.equals("")) && (event == null || event.equals(""))) {
	            	percentResult = controller.PocketPercentageSeason(season);
	            } else if (frameNum == null || frameNum.equals("")) {
	            	percentResult = controller.PocketPercentageEventSeason(event, season);
	            } else if (event == null || event.equals("")) {
	            	percentResult = controller.PocketPercentageFrameSeason(frameNum, season);
	            } else if (season == null || season.equals("")) {
	                percentResult = controller.PocketPercentageFrameEvent(event, frameNum);
	            } else {
	                percentResult = controller.PocketPercentageFrameEventSeason(event, season, frameNum);
	            }

	            if (percentResult == 0.0) {
	                errorMessage = "Stats cannot be generated for this event (no data available).";
	            }
	        } catch (NullPointerException e) {
	            errorMessage = "Stats cannot be generated for this event (missing data).";
	            percentResult = null;  // Ensure it's cleared
	        }
	    }
		
		// Add parameters as request attributes
		req.setAttribute("formSubmitted", true);

		req.setAttribute("frameNum", frameNum);
		ArrayList<Event> events = null;
		eventsController = new AllEventsController();
		events = eventsController.getEvents();
		req.setAttribute("events", events);
		req.setAttribute("selectedEvent", req.getParameter("event"));
		req.setAttribute("season", season);
		
		req.setAttribute("percentResult",  percentResult);
		// Add result objects as request attributes
		req.setAttribute("errorMessage",   errorMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/pocketPercentage.jsp").forward(req, resp);
	}	
}