<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Event</title>
    
    		 <link href="https://fonts.googleapis.com/css2?family=Orbitron&display=swap" rel="stylesheet">

    <style>
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
            width: 70%;
            max-width: 900px;
            margin: 30px auto;
            padding: 30px;
            background: #1a0033;
            border-left: 10px solid #ff6600;
            border-right: 10px solid #ff6600;
            box-shadow: 0 0 15px rgba(255, 102, 0, 0.8);
            border-radius: 12px;
        }

        h1 {
            color: #ff00ff;
            text-shadow: 1px 1px 5px #ff6600;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            margin: 0 auto 30px;
        }

        td.label {
            text-align: right;
            font-weight: bold;
            color: #ff00ff;
            padding-right: 20px;
            width: 40%;
            font-size: 18px;
        }

        td input[type="text"] {
            width: 90%;
            padding: 8px;
            border: 2px solid #ff00ff;
            border-radius: 5px;
            background-color: #0a0a2a;
            color: #00ffcc;
            font-size: 16px;
            transition: all 0.3s ease;
        }

        td input[items="${events}"]:focus {
            border-color: #00ffcc;
            outline: none;
            box-shadow: 0 0 8px #00ffcc;
        }
        
        
select {
    width: 95%;
    padding: 8px;
    border: 2px solid #ff00ff;
    border-radius: 5px;
    background-color: #0a0a2a;
    color: #00ffcc;
    font-size: 16px;
    font-family: 'Orbitron', sans-serif;
    transition: all 0.3s ease;
}

select:focus {
    border-color: #00ffcc;
    outline: none;
    box-shadow: 0 0 8px #00ffcc;
}

        .error {
            color: red;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .success {
            color: #00ffcc;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .success_title {
            color: #ff00ff;
            font-style: italic;
            font-weight: bold;
        }

        input[type="submit"] {
            font-family: 'Orbitron', sans-serif; 
            background: #ff6600;
            color: white;
            border: none;
            padding: 12px 25px;
            margin-top: 15px;
            margin-right: 10px;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s, box-shadow 0.3s;
            box-shadow: 0 0 10px #ff6600;
        }

        input[type="submit"]:hover {
            background: #ff3300;
            box-shadow: 0 0 15px #ff00ff;
        }
        
        input[type="date"] {
    		width: 90%;
    		padding: 8px;
    		border: 2px solid #ff00ff;
    		border-radius: 5px;
    		background-color: #0a0a2a;
    		color: #00ffcc;
    		font-size: 16px;
    		font-family: 'Orbitron', sans-serif;
    		transition: all 0.3s ease;
    		appearance: none; /* For better cross-browser consistency */
		}

		input[type="date"]:focus {
    		border-color: #00ffcc;
    		outline: none;
    		box-shadow: 0 0 8px #00ffcc;
		}
		
		input[type="date"]::-webkit-calendar-picker-indicator {
    		filter: invert(1); /* Makes the icon light-colored */
    		cursor: pointer;
		}
		
		button[type="submit"] {
    font-family: 'Orbitron', sans-serif; 
    background: #ff6600;
    color: white;
    border: none;
    padding: 12px 25px;
    margin-top: 15px;
    margin-right: 10px;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
    transition: background 0.3s, box-shadow 0.3s;
    box-shadow: 0 0 10px #ff6600;
}

button[type="submit"]:hover {
    background: #ff3300;
    box-shadow: 0 0 15px #ff00ff;
}
		
        
    </style>
</head>

<body>

    <div class="wrapper">
        <h1>Add Event</h1>

        <c:if test="${! empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <c:if test="${! empty successMessage}">
            <div class="success">Successfully added <span class="success_longName">${successMessage}</span> to Table</div>
        </c:if>

        <form action="${pageContext.servletContext.contextPath}/insertEvent" method="post">
            <table>
                <tr>
                    <td class="label">Long Name:</td>
                    <td><input type="text" name="event_longname" size="20" value="${event_longname}" /></td>
                </tr>
                <tr>
                    <td class="label">Short Name:</td>
                    <td><input type="text" name="event_shortname" size="20" value="${event_shortname}" /></td>
                </tr>
                <tr>
                    <td class="label">Type:</td>
                    <td><input type="text" name="event_type" size="20" value="${event_type}" /></td>
                </tr>
                <tr>
				    <td class="label">Establishment:</td>
				    <td>
				        <select name="establishment">
				            <c:forEach items="${establishments}" var="establishment">
				                <option value="${establishment.longname}">
				                    ${establishment.longname}
				                </option>
				            </c:forEach>
				        </select>
				    </td>
				</tr>
                <tr>
                    <td class="label">Season:</td>
                    <td><input type="text" name="event_season" size="20" value="${event_season}" /></td>
                </tr>
                <tr>
                    <td class="label">Team:</td>
                    <td><input type="text" name="event_team" size="20" value="${event_team}" /></td>
                </tr>
                <tr>
                    <td class="label">Composition:</td>
                    <td><input type="text" name="event_composition" size="20" value="${event_composition}" /></td>
                </tr>
                <tr>
                    <td class="label">Day:</td>
                    <td><input type="text" name="event_day" size="20" value="${event_day}" /></td>
                </tr>
                <tr>
                    <td class="label">Time:</td>
                    <td><input type="text" name="event_time" size="20" value="${event_time}" /></td>
                </tr>
                <tr>
                    <td class="label">Start Date:</td>
                    <td><input type="date" name="event_start" size="20" value="${event_start}" /></td>
                </tr>
                <tr>
                    <td class="label">End Date:</td>
                    <td><input type="date" name="event_end" size="20" value="${event_end}" /></td>
                </tr>
                <tr>
                    <td class="label">Games Per Session:</td>
                    <td><input type="text" name="event_gamesPerSession" size="20" value="${event_gamesPerSession}" /></td>
                </tr>
                <tr>
                    <td class="label">Weeks:</td>
                    <td><input type="text" name="event_weeks" size="20" value="${event_weeks}" /></td>
                </tr>
                <tr>
                    <td class="label">Playoffs:</td>
                    <td><input type="text" name="event_playoffs" size="20" value="${event_playoffs}" /></td>
                </tr>
            </table>

            <input type="submit" name="submitinsertevent" value="Add Event">
        </form>

        <br>
		
		<form action="${pageContext.servletContext.contextPath}/allEvents" method="post">
            <input type="submit" name="submithome" value="View All Events">
        </form>
        <br>
                      <br></br>
            <form action="${pageContext.servletContext.contextPath}/viewIndex" method="post">
                <button type="submit" name="submitviewIndex">View/Adding Page</button>
            </form>

    </div>

</body>
</html>
