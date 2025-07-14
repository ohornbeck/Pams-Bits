package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Book;
import edu.ycp.cs320.booksdb.model.Pair;
import edu.ycp.cs320.lab02.controller.AllEventsController;
import edu.ycp.cs320.booksdb.model.Event;

public class AllEventsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AllEventsController controller = null;	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nAllEventsServlet: doGet");

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

		req.getRequestDispatcher("/_view/allEvents.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nAllEventsServlet: doPost");		
		
		String errorMessage = null;
		ArrayList<Event> events = null;
		
		controller = new AllEventsController();

		// get list of author,book pairs returned from query
		events = controller.getEvents();
	
		// any books found?
		if (events == null) {
			errorMessage = "No Events were found in the Library";
		}
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("events",  events);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/allEvents.jsp").forward(req, resp);
	}	
}
