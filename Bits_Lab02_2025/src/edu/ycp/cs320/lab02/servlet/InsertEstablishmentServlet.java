package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab02.controller.InsertEstablishmentController;

public class InsertEstablishmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private InsertEstablishmentController controller = null;	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nInsertEstablishmentServlet: doGet");

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

		req.getRequestDispatcher("/_view/insertEstablishment.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nInsertEstablishmentServlet: doPost");		
		
		String errorMessage   = null;
		String successMessage = null;
		String longName      = null;
		String shortName       = null;
		String address		  = null;
		String phone = null;
		int lanes = 0;
		String type = null;
		
		// Decode form parameters and dispatch to controller
		longName    = req.getParameter("establishment_longName");
		shortName     = req.getParameter("establishment_shortName");
		address        = req.getParameter("establishment_address");
		phone        = req.getParameter("establishment_phone");
		lanes        = Integer.parseInt(req.getParameter("establishment_lanes"));
		type        = req.getParameter("establishment_type");
		
		if (longName    == null || longName.equals("") ||
			shortName     == null || shortName.equals("")  ||
			address        == null || address.equals("")    ||
			phone        == null || phone.equals("")   ||
			lanes        == 0   ||
			type        == null || type.equals(""))  {
			
			errorMessage = "Please fill in all of the required fields";
		} else {
			controller = new InsertEstablishmentController();
			
			// get list of books returned from query			
			if (controller.insertEstablishmentIntoLibrary(longName, shortName, address, phone, lanes, type)) {
				successMessage = longName;
			}
			else {
				errorMessage = "Failed to insert Establishment: " + longName;					
			}
		}
		
		// Add parameters as request attributes
		req.setAttribute("establishment_longName", longName);
		req.setAttribute("establishment_shortName",  shortName);
		req.setAttribute("establishment_address",       address);
		req.setAttribute("establishment_phone",       phone);
		req.setAttribute("establishment_lanes",       lanes);
		req.setAttribute("establishment_type",       type);
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage",   errorMessage);
		req.setAttribute("successMessage", successMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/insertEstablishment.jsp").forward(req, resp);
	}	
}
