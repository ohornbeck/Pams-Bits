package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Ball;
import edu.ycp.cs320.lab02.controller.AllAuthorsController;
import edu.ycp.cs320.lab02.controller.ArsenalController;

public class ArsenalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArsenalController controller = null;	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nArsenalServlet: doGet");

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

		req.getRequestDispatcher("/_view/arsenal.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nArsenalServlet: doPost");		

		ArrayList<Ball> arsenal = null;
		String errorMessage       = null;

		controller = new ArsenalController();

		// get list of authors returned from query
		arsenal = controller.getAllBalls();

		// any authors found?
		if (arsenal == null) {
			errorMessage = "No Balls found in Arsenal";
		}

		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("arsenal", arsenal);
		
		for(Ball ball: arsenal) {
			System.out.println(ball.getShortname());
		}

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/arsenal.jsp").forward(req, resp);
	}	
}
