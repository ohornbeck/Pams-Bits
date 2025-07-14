<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
	<head>
		<title>Carry Percentages</title>
		
		
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

        td.resultCol {
            text-align: left;
            font-size: 18px;
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
	<h1>Carry Percentages:</h1>
	
			<c:if test="${requestScope.formSubmitted and not empty requestScope.errorMessage}">
    			<div class="error">${requestScope.errorMessage}</div>
			</c:if>


		<form action="${pageContext.servletContext.contextPath}/carryPercentage" method="post">
			<table>
				<tr>
    				<td class="label">Frame Number:</td>
    				<td>
        				<select name="frameNum">
            				<option value="" <c:if test="${empty frameNum}">selected</c:if>> </option>
            			<c:forEach begin="1" end="12" var="i">
                			<option value="${i}" <c:if test="${frameNum == i}">selected</c:if>>${i}</option>
            			</c:forEach>
        				</select>
    			</td>
				</tr>
				<tr>
   	 				<td class="label">Event:</td>
    				<td>
        				<select name="event">
            			<!-- Blank option -->
            			<option value="" <c:if test="${empty selectedEvent}">selected</c:if>> </option>
            
            			<!-- Actual event options -->
            			<c:forEach items="${events}" var="event">
                			<option value="${event.shortname}" <c:if test="${event.shortname == selectedEvent}">selected</c:if>>
                    			${event.shortname}
                			</option>
            			</c:forEach>
        				</select>
    				</td>
				</tr>
				<tr>
    				<td class="label">Season:</td>
    				<td>
        				<select name="season">
            				<option value="Fa-24" <c:if test="${season == 'Fa-24'}">selected</c:if>>Fa-24</option>
            				<option value="Su-25" <c:if test="${season == 'Su-25'}">selected</c:if>>Su-25</option>
            				<option value="" <c:if test="${empty season}">selected</c:if>> </option>
        				</select>
    				</td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
			      <td class="label">Carry Percentage:</td>
			         <td class="resultCol">
    					<fmt:formatNumber value="${percentResult}" type="number" maxFractionDigits="2" />%
					</td>   
			    </tr>
			</table>
			
			<input type="Submit" name="submitStrikePercentage" value="Find Carry Percentage">
		</form>
		<br>
		<form action="${pageContext.servletContext.contextPath}/statIndex" method="post">
                <button type="submit" name="submitstatIndex">Statistics Page</button>
        </form>
		</div>	
	</body>
</html>