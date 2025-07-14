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
import edu.ycp.cs320.lab02.controller.AllEstablishmentsController;
import edu.ycp.cs320.lab02.controller.AllEventsController;

public class InsertEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private InsertEventController controller = null;
	private AllEstablishmentsController establishmentsController = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nInsertEventServlet: doGet");

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
		
		ArrayList<Establishment> establishments = null;
		establishmentsController = new AllEstablishmentsController();
		establishments = establishmentsController.getAllEstablishments();
		req.setAttribute("establishments",  establishments);

		req.getRequestDispatcher("/_view/insertEvent.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nInsertEventServlet: doPost");		
		
		String errorMessage = null;
		String successMessage = null;
		String longname = null;
		String shortname = null;
		String type = null;
		String establishment = null;
		String season = null;
		int team = 0;
		String composition = null;
		String day = null;
		String time = null;
		String start = null;
		String end = null;
		int gamesPerSession = 0;
		int weeks = 0;
		int playoffs = 0;
		
		// Decode form parameters and dispatch to controller
		longname = req.getParameter("event_longname");
		shortname = req.getParameter("event_shortname");
		type = req.getParameter("event_type");
		establishment = req.getParameter("establishment");
		season = req.getParameter("event_season");
		team = Integer.parseInt(req.getParameter("event_team"));
		composition = req.getParameter("event_composition");
		day = req.getParameter("event_day");
		time = req.getParameter("event_time");
		start = req.getParameter("event_start");
		end = req.getParameter("event_end");
		gamesPerSession = Integer.parseInt(req.getParameter("event_gamesPerSession"));
		weeks = Integer.parseInt(req.getParameter("event_weeks"));
		playoffs = Integer.parseInt(req.getParameter("event_playoffs"));
		
		if (longname == null || longname.equals("") ||
			shortname == null || shortname.equals("")  ||
			type == null || type.equals("") ||
			establishment == null || establishment.equals("") ||
			season == null || season.equals("") ||
			team == 0 ||
			composition == null || composition.equals("") ||
			day == null || day.equals("") ||
			time == null || time.equals("") ||
			start == null || start.equals("") ||
			end == null || end.equals("") ||
			gamesPerSession == 0 ||
			weeks == 0 ||
			playoffs == 0) {
			
			errorMessage = "Please fill in all of the required fields";
		} else {
			controller = new InsertEventController();
			
			// get list of books returned from query			
			if (controller.insertEvent(longname, shortname, type, establishment, season, team, composition, day, time, start, end, gamesPerSession, weeks, playoffs)) {
				successMessage = longname;
			}
			else {
				errorMessage = "Failed to insert Event: " + longname;					
			}
		}
		
		// Add parameters as request attributes
		req.setAttribute("event_longname", longname);
		req.setAttribute("event_shortname",  shortname);
		req.setAttribute("event_type",  type);
		
		ArrayList<Establishment> establishments = null;
		establishmentsController = new AllEstablishmentsController();
		establishments = establishmentsController.getAllEstablishments();
		req.setAttribute("establishments",  establishments);
		
		req.setAttribute("event_season", season);
		req.setAttribute("event_team", team);
		req.setAttribute("event_composition", composition);
		req.setAttribute("event_day", day);
		req.setAttribute("event_time", time);
		req.setAttribute("event_start", start);
		req.setAttribute("event_end", end);
		req.setAttribute("event_gamesPerSession", gamesPerSession);
		req.setAttribute("event_weeks", weeks);
		req.setAttribute("event_playoffs", playoffs);
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage",   errorMessage);
		req.setAttribute("successMessage", successMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/insertEvent.jsp").forward(req, resp);
	}	
}