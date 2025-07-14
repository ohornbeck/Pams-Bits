<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>CS320 All Events</title>
		
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

 td.longnameColHeading,
td.ShortnameColHeading,
td.TypeColHeading,
td.EstablishmentColHeading,
td.SeasonColHeading,
td.TeamColHeading,
td.CompositionColHeading,
td.DayColHeading,
td.TimeColHeading,
td.StartColHeading,
td.EndColHeading,
td.GamesPerSessionColHeading,
td.WeeksColHeading,
td.PlayoffsHeading {
    text-align: center;
    font-weight: bold;
    color: #ff00ff;
    background: #1a0033;
    padding: 10px;
    border-bottom: 2px solid #ff6600;
    text-shadow: none;
}

td.eventLongnameCol,
td.eventShortnameCol,
td.eventTypeCol,
td.eventEstablishmentCol,
td.eventSeasonCol,
td.eventTeamCol,
td.eventCompositionCol,
td.eventDayCol,
td.eventTimeCol,
td.eventStartCol,
td.eventEndCol,
td.eventGamesPerSessionCol,
td.eventWeeksCol,
td.eventPlayoffsCol {
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

        <h1>Events</h1>

        <form action="${pageContext.servletContext.contextPath}/index" method="post">
        <div class="table-container">
            <table>
                <tr>
                    <td class="longnameColHeading">Long Name</td>
                    <td class="ShortnameColHeading">Short Name</td>
                    <td class="TypeColHeading">Type</td>
                    <td class="EstablishmentColHeading">Establishment</td>
                    <td class="SeasonColHeading">Season</td>
                    <td class="TeamColHeading">Team</td>
                    <td class="CompositionColHeading">Composition</td>
                    <td class="DayColHeading">Day</td>
                    <td class="TimeColHeading">Time</td>
                    <td class="StartColHeading">Start</td>
                    <td class="EndColHeading">End</td>
                    <td class="GamesPerSessionColHeading">Games Per Session</td>
                    <td class="WeeksColHeading">Weeks</td>
                    <td class="PlayoffsHeading">Playoffs</td>
                </tr>

                <c:forEach items="${events}" var="event">
                    <tr class="eventRow">
                        <td class="eventLongnameCol">${event.longname}</td>
                        <td class="eventShortnameCol">${event.shortname}</td>
                        <td class="eventTypeCol">${event.type}</td>
                        <td class="eventEstablishmentCol">${event.establishment}</td>
                        <td class="eventSeasonCol">${event.season}</td>
                        <td class="eventTeamCol">${event.team}</td>
                        <td class="eventCompositionCol">${event.composition}</td>
                        <td class="eventDayCol">${event.day}</td>
                        <td class="eventTimeCol">${event.time}</td>
                        <td class="eventStartCol">${event.start}</td>
                        <td class="eventEndCol">${event.end}</td>
                        <td class="eventGamesPerSessionCol">${event.gamesPerSession}</td>
                        <td class="eventWeeksCol">${event.weeks}</td>
                        <td class="eventPlayoffsCol">${event.playoffs}</td>
                    </tr>
                </c:forEach>
            </table>
            </div>

        </form>
        <br>

			<form action="${pageContext.servletContext.contextPath}/insertEvent" method="get">
                <button type="submit" name="submitinsertnewestablishment">Add New Event</button>
            </form>
           <br></br>
            <form action="${pageContext.servletContext.contextPath}/viewIndex" method="post">
                <button type="submit" name="submitviewIndex">View/Adding Page</button>
            </form>
            
            
    </div>

</body>
</html>
