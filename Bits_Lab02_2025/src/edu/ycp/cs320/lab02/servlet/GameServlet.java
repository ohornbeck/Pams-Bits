package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Ball;
import edu.ycp.cs320.booksdb.model.Game;
import edu.ycp.cs320.booksdb.model.Shot;
import edu.ycp.cs320.lab02.controller.AllAuthorsController;
import edu.ycp.cs320.lab02.controller.FindAllShotsInGameController;
import edu.ycp.cs320.lab02.controller.InsertShotController;
import edu.ycp.cs320.lab02.controller.LastGameIDController;

public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FindAllShotsInGameController controller = null;	
	private InsertShotController shotController = null;	
	private LastGameIDController gameIDController = null;	
	// use this to keep referencing the correct game id for 
	// finding the shots with the same game_id attribute and entering new shots with the same game_id attribute
	// into the database

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nGameServlet: doGet");

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

		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nGameServlet: doPost");		

		ArrayList<Shot> game = null;
		String errorMessage       = null;
		String gameID = null;
		
		gameID = req.getParameter("gameID");
	
		controller = new FindAllShotsInGameController();

		// get list of authors returned from query
		game = controller.findAllShotsInGame(gameID);

		// any authors found?
		if (game == null) {
			errorMessage = "No Shots found in Game";
		}

		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("game", game);
		
		for(Shot shot: game) {
			System.out.println(shot.getCount());
		}

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}	
}
