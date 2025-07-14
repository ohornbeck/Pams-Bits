package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Establishment;
import edu.ycp.cs320.lab02.controller.AllAuthorsController;
import edu.ycp.cs320.lab02.controller.AllEstablishmentsController;

public class AllEstablishmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AllEstablishmentsController controller = null;	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nAllEstablishmentsServlet: doGet");

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

		req.getRequestDispatcher("/_view/allEstablishments.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nAllEstablishmentsServlet: doPost");		

		ArrayList<Establishment> establishments = null;
		String errorMessage       = null;

		controller = new AllEstablishmentsController();

		// get list of authors returned from query
		establishments = controller.getAllEstablishments();

		// any establishments found?
		if (establishments == null) {
			errorMessage = "No Establishments were found in the Library";
		}

		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("establishments", establishments);

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/allEstablishments.jsp").forward(req, resp);
	}	
}
