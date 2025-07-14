<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CS320 Add Ball</title>
    
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

        td input[type="text"]:focus {
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
        <h1>Add Ball to Arsenal</h1>

        <c:if test="${! empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <c:if test="${! empty successMessage}">
            <div class="success">Successfully added <span class="success_title">${successMessage}</span> to Arsenal</div>
        </c:if>

        <form action="${pageContext.servletContext.contextPath}/insertBall" method="post">
            <table>
                <tr>
                    <td class="label">Long Name:</td>
                    <td><input type="text" name="longname" size="20" value="${longname}" /></td>
                </tr>
                <tr>
                    <td class="label">Short Name:</td>
                    <td><input type="text" name="shortname" size="20" value="${shortname}" /></td>
                </tr>
                <tr>
                    <td class="label">Brand:</td>
                    <td><input type="text" name="brand" size="20" value="${brand}" /></td>
                </tr>
                <tr>
                    <td class="label">Type:</td>
                    <td><input type="text" name="type" size="20" value="${type}" /></td>
                </tr>
                <tr>
                    <td class="label">Core:</td>
                    <td><input type="text" name="core" size="20" value="${core}" /></td>
                </tr>
                <tr>
                    <td class="label">Cover:</td>
                    <td><input type="text" name="cover" size="20" value="${cover}" /></td>
                </tr>
                <tr>
                    <td class="label">Color:</td>
                    <td><input type="text" name="color" size="20" value="${color}" /></td>
                </tr>
                <tr>
                    <td class="label">Surface:</td>
                    <td><input type="text" name="surface" size="20" value="${surface}" /></td>
                </tr>
                <tr>
                    <td class="label">Year:</td>
                    <td><input type="text" name="year" size="20" value="${year}" /></td>
                </tr>
                <tr>
                    <td class="label">Serial Number:</td>
                    <td><input type="text" name="serialNumber" size="20" value="${serialNumber}" /></td>
                </tr>
                <tr>
                    <td class="label">Weight:</td>
                    <td><input type="text" name="weight" size="20" value="${weight}" /></td>
                </tr>
                <tr>
                    <td class="label">Mapping:</td>
                    <td><input type="text" name="mapping" size="20" value="${mapping}" /></td>
                </tr>
            </table>

            <input type="submit" name="submitinsertball" value="Add Ball to Arsenal">
        </form>

        <form action="${pageContext.servletContext.contextPath}/arsenal" method="post">
            <input type="submit" name="submitarsenal" value="View Arsenal">
        </form>
        
           <br></br>
            <form action="${pageContext.servletContext.contextPath}/viewIndex" method="post">
                <button type="submit" name="submitviewIndex">View/Adding Page</button>
            </form>
        
    </div>

</body>
</html>
