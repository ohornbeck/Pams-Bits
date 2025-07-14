package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab02.controller.InsertBallController;
import edu.ycp.cs320.lab02.controller.InsertBookController;

public class InsertBallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private InsertBallController controller = null;	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nInsertBallServlet: doGet");

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

		req.getRequestDispatcher("/_view/insertBall.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("\nInsertBallServlet: doPost");		
		
		String errorMessage   = null;
		String successMessage = null;
		String longname      = null;
		String shortname       = null;
		String brand		  = null;
		String type			  = null;
		String core   	  = null;
		String cover	  = null;
		String color	  = null;
		String surface	  = null;
		String year	  = null;
		String serialNumber	  = null;
		String weight	  = null;
		String mapping	  = null;
		
		// Decode form parameters and dispatch to controller
		longname    = req.getParameter("longname");
		shortname     = req.getParameter("shortname");
		brand        = req.getParameter("brand");
		type         = req.getParameter("type");
		core = req.getParameter("core");
		cover = req.getParameter("cover");
		color = req.getParameter("color");
		surface = req.getParameter("surface");
		year = req.getParameter("year");
		serialNumber = req.getParameter("serialNumber");
		weight = req.getParameter("weight");
		mapping = req.getParameter("mapping");
		
		if(longname == null || longname.equals("")) {
			longname = "?";
		}
		if(shortname == null || shortname.equals("")) {
			shortname = "?";
		}
		if(brand == null || brand.equals("")) {
			brand = "?";
		}
		if(type == null || type.equals("")) {
			type = "?";
		}
		if(core == null || core.equals("")) {
			core = "?";
		}
		if(cover == null || cover.equals("")) {
			cover = "?";
		}
		if(color == null || color.equals("")) {
			color = "?";
		}
		if(surface == null || surface.equals("")) {
			surface = "?";
		}
		if(year == null || year.equals("")) {
			year = "?";
		}
		if(serialNumber == null || serialNumber.equals("")) {
			serialNumber = "?";
		}
		if(weight == null || weight.equals("")) {
			weight = "?";
		}
		if(mapping == null || mapping.equals("")) {
			mapping = "?";
		}
		
		/*if (firstName    == null || firstName.equals("") ||
			lastName     == null || lastName.equals("")  ||
			title        == null || title.equals("")     ||
			isbn         == null || isbn.equals("")	     ||
			strPublished == null) {
			
			errorMessage = "Please fill in all of the required fields";
		} else {*/
		controller = new InsertBallController();

		// convert published to integer now that it is valid
		// published = Integer.parseInt(strPublished);
		
		// get list of books returned from query			
		if (controller.insertBallIntoArsenal(longname,  shortname,  brand,  type,  core,  cover, color, surface, year, serialNumber, weight, mapping)) {
			successMessage = longname;
		}
		else {
			errorMessage = "Failed to insert Ball: " + longname;					
		}
		
		
		// Add parameters as request attributes
		req.setAttribute("longname", longname);
		req.setAttribute("shortname", shortname);
		req.setAttribute("brand", brand);
		req.setAttribute("type", type);
		req.setAttribute("core", core);
		req.setAttribute("cover", cover);
		req.setAttribute("color", color);
		req.setAttribute("surface", surface);
		req.setAttribute("year", year);
		req.setAttribute("serialNumber", serialNumber);
		req.setAttribute("weight", weight);
		req.setAttribute("mapping", mapping);
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage",   errorMessage);
		req.setAttribute("successMessage", successMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/insertBall.jsp").forward(req, resp);
	}	
}
