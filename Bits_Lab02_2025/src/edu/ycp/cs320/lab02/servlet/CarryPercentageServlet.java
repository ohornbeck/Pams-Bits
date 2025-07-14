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
import edu.ycp.cs320.lab02.controller.CarryPercentageController;

public class CarryPercentageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CarryPercentageController controller = null;
	private AllEventsController eventsController = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    System.out.println("\nCarryPercentageServlet: doGet");

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

	    req.getRequestDispatcher("/_view/carryPercentage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nCarryPercentageServlet: doPost");		
		
		String errorMessage = null;
		String frameNum = null;
		String event = null;
		String season = null;
		Double percentResult = null;
		
		// Decode form parameters and dispatch to controller
		frameNum = req.getParameter("frameNum");
		event = req.getParameter("event");
		season = req.getParameter("season");
		
		controller = new CarryPercentageController();
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
	            	percentResult = controller.AllCarryPercentage();
	            } else if ((event == null || event.equals("")) && (season == null || season.equals(""))) {
	            	percentResult = controller.CarryPercentageFrame(frameNum);
	            } else if ((frameNum == null || frameNum.equals("")) && (season == null || season.equals(""))) {
	            	percentResult = controller.CarryPercentageEvent(event);
	            } else if ((frameNum == null || frameNum.equals("")) && (event == null || event.equals(""))) {
	            	percentResult = controller.CarryPercentageSeason(season);
	            } else if (frameNum == null || frameNum.equals("")) {
	            	percentResult = controller.CarryPercentageEventSeason(event, season);
	            } else if (event == null || event.equals("")) {
	            	percentResult = controller.CarryPercentageFrameSeason(frameNum, season);
	            } else if (season == null || season.equals("")) {
	                percentResult = controller.CarryPercentageFrameEvent(event, frameNum);
	            } else {
	                percentResult = controller.CarryPercentageFrameEventSeason(event, season, frameNum);
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
		req.getRequestDispatcher("/_view/carryPercentage.jsp").forward(req, resp);
	}	
}