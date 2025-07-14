package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.booksdb.model.Event;
import edu.ycp.cs320.booksdb.model.Shot;
import edu.ycp.cs320.booksdb.persist.DatabaseProvider;
import edu.ycp.cs320.booksdb.persist.DerbyDatabase;
import edu.ycp.cs320.booksdb.persist.IDatabase;
import edu.ycp.cs320.lab02.controller.AllEventsController;
import edu.ycp.cs320.lab02.controller.FindAllShotsInGameController;
import edu.ycp.cs320.lab02.controller.InsertGameController;
import edu.ycp.cs320.lab02.controller.InsertShotController;

public class InsertShotServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private InsertShotController shotController = null;    
    private AllEventsController eventsController = null;
    private InsertGameController gameController = null;
    private FindAllShotsInGameController gameShotsController = null;
    private IDatabase db = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("\nInsertShotServlet: doGet");

        String user = (String) req.getSession().getAttribute("user");
        if (user == null) {
            System.out.println("   User: <" + user + "> not logged in or session timed out");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        System.out.println("   User: <" + user + "> logged in");
        
        // Initialize controllers and database
        DatabaseProvider.setInstance(new DerbyDatabase());
        db = DatabaseProvider.getInstance();
        
        gameController = new InsertGameController();
        shotController = new InsertShotController();
        gameShotsController = new FindAllShotsInGameController();
        int currentFrame = 0;
        int currentShot = 0;
        
        // Get the last game ID
        int gameID = db.getLastInsertedGameID();
        
        if (gameID <= 0) {
            req.setAttribute("errorMessage", "No game found to add shots to. Please create a game first.");
            req.getRequestDispatcher("/_view/insertShot.jsp").forward(req, resp);
            return;
        }
        
        ArrayList<Shot> shots = gameShotsController.findAllShotsInGame(String.valueOf(gameID));
        
        if(shots == null) {
        	currentFrame = 1;
        	currentShot = 1;
        } else {
        	//currentFrame = Math.ceil(double)((shots.size() + 1)/2);
        	currentFrame = (int) Math.ceil((double) (shots.size() + 1) / 2);
        	currentShot = shots.size() + 1;
        }
        
        // We'll track frame and shot number via session
        //Integer currentFrame = (Integer) req.getSession().getAttribute("currentFrame");
        //String currentShot = (String) req.getSession().getAttribute("currentShot");
        
        // Initialize if first shot
        /*if (currentFrame == null) {
            currentFrame = 1;
            currentShot = 1;
        }*/
        
        // Don't allow more than 12 frames
        if (currentFrame > 12 || currentShot > 23) {
            req.setAttribute("errorMessage", "Game already has 12 frames completed. Cannot add more shots.");
            req.getRequestDispatcher("/_view/insertShot.jsp").forward(req, resp);
            return;
        }
        
        /*ArrayList<Event> events = null;
        eventsController = new AllEventsController();
        events = eventsController.getEvents();
        req.setAttribute("events", events);*/
        
        // Set values
        req.setAttribute("gameID", gameID);
        req.setAttribute("frameNumber", currentFrame);
        req.setAttribute("shotNumber", currentShot);
        req.setAttribute("shots", shots);
        
        req.getRequestDispatcher("/_view/insertShot.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        System.out.println("\nInsertShotServlet: doPost");    
        
        String errorMessage = null;
        String successMessage = null;
        String shotNumber = null;
        int gameID = 0;
        int frameNumber = 0;
        String count = null;
        String leave = null;
        String score = null;
        String type = null;
        String board = null;
        String lane = null;
        String ball = null;
        
        //int gameID = 0;
        //int frameNumber = 0;

        // Get form parameters
        shotNumber = req.getParameter("shotNumber");
        gameID = Integer.parseInt(req.getParameter("gameID"));
        frameNumber = Integer.parseInt(req.getParameter("frameNumber"));
        count = req.getParameter("count");
        leave = req.getParameter("leave");
        score = req.getParameter("score");
        type = req.getParameter("type");
        board = req.getParameter("board");
        lane = req.getParameter("lane");
        ball = req.getParameter("ball");
        
        
        int strikeShotID = -1;
        int emptyShotID = -1;
        int spareShotID = -1;
        int gutterShotID = -1;
        int foulShotID = -1;
        int normalShotID = -1;
        

        // Validate parameters
        if (shotNumber == null || shotNumber.trim().isEmpty()) {
            errorMessage = "Shot number is required";
        /*} else if (gameIDStr == null || gameIDStr.trim().isEmpty()) {
            errorMessage = "Game ID is required";
        } else if (frameNumberStr == null || frameNumberStr.trim().isEmpty()) {
            errorMessage = "Frame number is required";*/
        } else {
            try {
                //gameID = Integer.parseInt(gameIDStr);
                // frameNumber = Integer.parseInt(frameNumberStr);
                
                // Validate ranges
                if (frameNumber < 1 || frameNumber > 12) {
                    errorMessage = "Frame number must be between 1 and 12";
                /*} else if (!shotNumber.equals("1") && !shotNumber.equals("2")) {
                    errorMessage = "Shot number must be either 1 or 2";*/
                } else {
                	
                	System.out.println(leave);
                	
                    // Handle strike scenario - second shot can be null/empty
                    /*if (shotNumber.equals("2") && (count == null || count.trim().isEmpty())) {
                        count = "0"; // or null if preferred
                    }*/
                	if (leave.contains("X")) {
                		strikeShotID = shotController.insertShot(shotNumber, gameID, frameNumber, 
                                "X", "", "", "", "", "", "");
                		
                		if(!(Integer.valueOf(shotNumber)%2 == 0)) {
                			shotNumber = String.valueOf(Integer.valueOf(shotNumber) + 1);
                    		emptyShotID = shotController.insertShot(shotNumber, gameID, frameNumber, 
                                    "", "", "", "", "", "", "");
                		}
                        
                        if (strikeShotID > 0) {
                            successMessage = "Shot successfully added to Frame " + frameNumber + 
                                            " (Shot " + shotNumber + ")";
                        }
                        
                	} else if (leave.contains("/")) {
                		spareShotID = shotController.insertShot(shotNumber, gameID, frameNumber, 
                                "/", "", "", "", "", "", "");
                		
                	} else if (leave.contains("-")) {
                		gutterShotID = shotController.insertShot(shotNumber, gameID, frameNumber, 
                				"-", "", "", "", "", "", "");
                		
                	} else if (leave.contains("F")) {
                		foulShotID = shotController.insertShot(shotNumber, gameID, frameNumber, 
                				"F", "", "", "", "", "", "");
                		
                	} else {
                		
                		String[] parts = leave.split(",");
                		
                		leave = "";
                		int sum = 0;
                		for (String part : parts) {
                			leave += part.trim();
                		    sum += Integer.parseInt(part.trim());
                		}
                		
                		normalShotID = shotController.insertShot(shotNumber, gameID, frameNumber, 
                				String.valueOf(count), leave , "", "", "", "", "");
                		
                	}
          
                	
                    
                    // Insert the shot using your existing method
                    /*Integer shotID = shotController.insertShot(shotNumber, gameID, frameNumber, 
                            count, leave, "", "", "", "", "");*/
                    
                    /*if (normalShotID > 0) {
                        successMessage = "Shot successfully added to Frame " + frameNumber + 
                                        " (Shot " + shotNumber + ")";
                        
                        // Calculate next shot and frame
                        //String nextShot = shotNumber.equals("1") ? "2" : "1";
                        //int nextFrame = shotNumber.equals("1") ? frameNumber : frameNumber + 1;
                        
                        // Store in session
                        //req.getSession().setAttribute("currentFrame", nextFrame);
                        //req.getSession().setAttribute("currentShot", nextShot);
                        
                        // Set attributes for form
                        //req.setAttribute("nextFrame", nextFrame);
                        //req.setAttribute("nextShotNumber", nextShot);
                    } else {
                        errorMessage = "Failed to insert shot";
                    }*/
                }
            } catch (NumberFormatException e) {
                errorMessage = "Game ID and Frame Number must be valid numbers";
            }
        }
        
        // Get events for navigation
        /*ArrayList<Event> events = null;
        eventsController = new AllEventsController();
        events = eventsController.getEvents();
        req.setAttribute("events", events);*/
        
        ArrayList<Shot> shots = gameShotsController.findAllShotsInGame(String.valueOf(gameID));
        
        
        	//currentFrame = Math.ceil(double)((shots.size() + 1)/2);
    	frameNumber = (int) Math.ceil((double) (shots.size() + 1) / 2);
    	shotNumber = String.valueOf(shots.size() + 1);
        
        // Set request attributes
        req.setAttribute("gameID", gameID);
        req.setAttribute("frameNumber", frameNumber);
        req.setAttribute("shotNumber", shotNumber);
        req.setAttribute("count", count);
        req.setAttribute("leave", leave);
        req.setAttribute("score", score);
        req.setAttribute("type", type);
        req.setAttribute("board", board);
        req.setAttribute("lane", lane);
        req.setAttribute("ball", ball);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        
        req.getRequestDispatcher("/_view/insertShot.jsp").forward(req, resp);
    }    
}