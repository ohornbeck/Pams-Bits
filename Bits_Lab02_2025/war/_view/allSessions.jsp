<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>CS320 All Sessions</title>
		
				 <link href="https://fonts.googleapis.com/css2?family=Orbitron&display=swap" rel="stylesheet">
		<style type="text/css">
       body {
            font-family: 'Orbitron', sans-serif;
            background-color: #0a0a2a;
            color: #00ffcc;
            margin: 0;
            padding: 0;
            text-align: center;
            min-height: 100vh;
        }

        .wrapper {
            width: 80%;
            max-width: 1000px;
            margin: 30px auto;
            padding: 20px;
            background: #1a0033;
            border-left: 10px solid #ff6600;
            border-right: 10px solid #ff6600;
            box-shadow: 0 0 15px rgba(255, 102, 0, 0.8);
            border-radius: 10px;
        }

        h1, h2 {
            color: #ff00ff;
            text-shadow: 1px 1px 5px #ff6600;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #1a0033;
            border-radius: 10px;
            box-shadow: 0 0 15px #00ffcc;
            margin-top: 20px;
        }

        td.LeagueColHeading,
        td.SeasonColHeading,
        td.WeekColHeading,
        td.DateBowledColHeading,
        td.RegSubColHeading,
        td.OpponentColHeading,
        td.StartLaneColHeading, 
        td.BallColHeading,
        td.GameOneColHeading,
        td.GameTwoColHeading,
        td.GameThreeColHeading,
        td.SeriesColHeading	{
            text-align: center;
            font-weight: bold;
            color: #ff00ff;
            background: #1a0033;
            padding: 10px;
            border-bottom: 2px solid #ff6600;
            text-shadow: none;
        }

        td.leagueCol,
        td.seasonCol,
        td.weekCol,
        td.dateBowledCol,
        td.regSubCol,
        td.opponentCol,
        td.startLaneCol, 
        td.ballCol,
        td.gameOneCol,
        td.gameTwoCol,
        td.gameThreeCol,
        td.seriesCol	 {
            text-align: left;
            color: #00ffcc;
            background: #1a0033;
            font-weight: bold;
            padding: 10px 20px;
            border-bottom: 1px solid #00ffcc;
        }

        tr.eventRow {
            transition: background 0.3s ease;
        }

        tr.eventRow:hover {
            background: #220066;
        }

        button {
            font-family: 'Orbitron', sans-serif; 
            background: #ff6600;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: background 0.3s ease, box-shadow 0.3s ease;
            text-shadow: 1px 1px 5px #000;
            box-shadow: 0 0 10px #ff6600;
            font-size: 16px;
            margin: 20px 10px 0;
        }

        button:hover {
            background: #ff3300;
            box-shadow: 0 0 15px #ff00ff;
        }

        .error {
            color: red;
            font-weight: bold;
            margin-bottom: 20px;
        }
        
        .table-container {
    		overflow-x: auto;
   	 		max-width: 100%;
		}

				
</style>
</head>

<body>

    <div class="wrapper">

        <!-- Error message (show only if needed) -->
        <c:if test="${! empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <h1>Sessions</h1>

        <form action="${pageContext.servletContext.contextPath}/index" method="post">
          <div class="table-container">
            <table>
                <tr>
                    <td class="LeagueColHeading">League type</td>
                    <td class="SeasonColHeading">Season</td>
                    <td class="WeekColHeading">Week Number</td>
                    <td class="DateBowledColHeading">Date Bowled</td>
                    <td class="RegSubColHeading">Reg/Sub</td>
                    <td class="OpponentColHeading">Opponent</td>
                    <td class="StartLaneColHeading">Starting Lane</td>
                    <td class="BallColHeading">Ball(s)</td>
                    <td class="GameOneColHeading">Game One Score</td>
                    <td class="GameTwoColHeading">Game Two Score</td>
                    <td class="GameThreeColHeading">Game Three Score</td>
                    <td class="SeriesColHeading">Series</td>
                </tr>

                <c:forEach items="${sessions}" var="session">
                    <tr class="sessionRow">
                        <td class="leagueCol">${session.league}</td>
                        <td class="seasonCol">${session.season}</td>
                        <td class="weekCol">${session.week}</td>
                        <td class="dateBowledCol">${session.bowled}</td>
                        <td class="regSubCol">${session.regSub}</td>
                        <td class="opponentCol">${session.opponent}</td>
                        <td class="startLaneCol">${session.start}</td>
                        <td class="ballCol">${session.ball}</td>
                        <td class="gameOneCol">${session.gameOneScore}</td>
                        <td class="gameTwoCol">${session.gameTwoScore}</td>
                        <td class="gameThreeCol">${session.gameThreeScore}</td>
                        <td class="seriesCol">${session.series}</td>
                    </tr>
                </c:forEach>
            </table>
            </div>
        </form>
        <div class="button-container">
        	<br>
			<form action="${pageContext.servletContext.contextPath}/insertSession" method="get">
                <button type="submit" name="submitinsertnewsession">Add New Session</button>
            </form>
            
                      <br></br>
            <form action="${pageContext.servletContext.contextPath}/viewIndex" method="post">
                <button type="submit" name="submitviewIndex">View/Adding Page</button>
            </form>
        </div>
    </div>
</body>
</html>
