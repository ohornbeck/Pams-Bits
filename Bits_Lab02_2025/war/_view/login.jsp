<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>CS320 Library Login</title>
    
    		 <link href="https://fonts.googleapis.com/css2?family=Orbitron&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <style>
        body {
            font-family: 'Orbitron', sans-serif;
            background-color: #0a0a2a;
            color: #00ffcc;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        /* Container for form */
        .container {
            width: 50%;
            margin: 100px auto;
            background: #1a0033;
            padding: 20px;
            box-shadow: 0 0 15px #ff6600;
            border-radius: 8px;
        }

        /* Title and messages */
        h1 {
            color: #ff00ff;
            text-shadow: 2px 2px 10px #ff6600;
            font-size: 2.5em;
        }

        .error {
            color: red;
            font-weight: bold;
            margin-bottom: 15px;
        }

        /* Labels and inputs */
        .label {
            font-weight: bold;
            color: #ff00ff;
            text-align: right;
            padding-right: 10px;
        }

        input[type="text"], input[type="password"] {
            padding: 10px;
            margin: 10px 0;
            width: 60%;  /* Reduced width */
            border: 1px solid #00ffcc;
            border-radius: 5px;
            background: #220066;
            color: #00ffcc;
            font-size: 16px;
        }

        input[type="submit"] {
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
            margin-top: 20px;
        }

        input[type="submit"]:hover {
            background: #ff3300;
            box-shadow: 0 0 15px #ff00ff;
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>Login to RevMetrix</h1>
        
        <c:if test="${! empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
    
        <form action="${pageContext.servletContext.contextPath}/login" method="post">
            <div>
                <label for="username" class="label">User Name:</label>
                <input type="text" name="username" id="username" size="12" value="${username}" />
            </div>
            <div>
                <label for="password" class="label">Password:</label>
                <input type="password" name="password" id="password" size="12" value="${password}" />
            </div>
            <div>
                <input type="submit" name="submit" value="Login">
            </div>
        </form>
    </div>
</body>

</html>
