<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bowling Shot Entry</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            font-family: Arial, sans-serif;
        }
        .header {
            text-align: center;
            margin-bottom: 20px;
        }
        .game-info {
            display: flex;
            justify-content: space-between;
            width: 100%;
            max-width: 600px;
            margin-bottom: 10px;
        }
        .frame-info {
            text-align: center;
            margin-bottom: 20px;
            font-size: 1.2em;
            font-weight: bold;
        }
        .content {
            display: flex;
            justify-content: center;
            align-items: flex-start;
            width: 100%;
            max-width: 800px;
        }
        .pin-layout {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 0 20px;
        }
        .pin-row {
            display: flex;
            margin-bottom: 10px;
            justify-content: center;
        }
        .pin {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            border: 1px solid #333;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 3px;
            cursor: pointer;
            background-color: white;
            font-size: 0.7em;
        }
        /* First shot - pins start white, turn black when selected */
        .pin.first-shot {
            background-color: white;
            color: black;
            border: 1px solid #333;
        }
        .pin.first-shot.selected {
            background-color: black;
            color: white;
        }
        
        /* Second shot - pins standing after first shot (black) */
        .pin.second-shot.standing {
            background-color: black;
            color: white;
            cursor: pointer;
        }
        
        /* Second shot - pins selected in current shot (turn red) */
        .pin.second-shot.selected {
            background-color: #ff4444; /* Red color */
            color: white;
        }
        
        /* Second shot - pins already knocked down (grayed out) */
        .pin.second-shot.knocked-down {
            background-color: #cccccc;
            color: #666666;
            pointer-events: none;
        }
        .options {
            display: flex;
            flex-direction: column;
            margin-left: 30px;
        }
        .option-btn {
            margin: 5px;
            padding: 10px 15px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .strike { background: #4CAF50; color: white; }
        .spare { background: #2196F3; color: white; }
        .foul { background: #f44336; color: white; }
        .gutter { background: #9E9E9E; color: white; }
        .disabled-btn {
            opacity: 0.5;
            cursor: not-allowed;
        }
        .submit-btn {
            margin-top: 15px;
            padding: 8px 20px;
            background-color: #333;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button {
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
            margin: 10px;
        }
        #indexbutton {
            font-family: 'Orbitron', sans-serif;
        }
        .error-message {
            color: red;
            margin: 10px 0;
        }
        .success-message {
            color: green;
            margin: 10px 0;
        }
    </style>
</head>

<body>
    <div class="header">
        <h1>Enter Shot Details</h1>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        
        <c:if test="${not empty successMessage}">
            <div class="success-message">${successMessage}</div>
        </c:if>
        
        <div class="game-info">
            <div>Game: ${gameID} - Frame: ${frameNumber} - Shot: ${shotNumber}</div>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/insertShot" method="post">
        <input type="hidden" name="gameID" value="${gameID}">
        <input type="hidden" name="shotNumber" value="${shotNumber}">
        <input type="hidden" name="frameNumber" value="${frameNumber}">
        
        <div class="content">
            <div class="pin-layout">
                <!-- Row 1 (Pins 7-8-9-10) -->
                <div class="pin-row">
                    <c:forEach var="pin" items="7,8,9,10">
                        <div class="pin ${shotNumber == 1 ? 'first-shot' : standingPins.contains(pin) ? 'second-shot standing' : 'second-shot knocked-down'}"
                             data-pin="${pin}">
                             ${pin}
                        </div>
                    </c:forEach>
                </div>
                <!-- Row 2 (Pins 4-5-6) -->
                <div class="pin-row">
                    <c:forEach var="pin" items="4,5,6">
                        <div class="pin ${shotNumber == 1 ? 'first-shot' : standingPins.contains(pin) ? 'second-shot standing' : 'second-shot knocked-down'}"
                             data-pin="${pin}">
                             ${pin}
                        </div>
                    </c:forEach>
                </div>
                <!-- Row 3 (Pins 2-3) -->
                <div class="pin-row">
                    <c:forEach var="pin" items="2,3">
                        <div class="pin ${shotNumber == 1 ? 'first-shot' : standingPins.contains(pin) ? 'second-shot standing' : 'second-shot knocked-down'}"
                             data-pin="${pin}">
                             ${pin}
                        </div>
                    </c:forEach>
                </div>
                <!-- Row 4 (Pin 1) -->
                <div class="pin-row">
                    <div class="pin ${shotNumber == 1 ? 'first-shot' : standingPins.contains('1') ? 'second-shot standing' : 'second-shot knocked-down'}"
                         data-pin="1">1
                    </div>
                </div>
                
                <button type="submit" class="submit-btn">Submit Shot</button>
            </div>

            <div class="options">
                <button type="submit" name="leave" value="X" 
                    class="option-btn strike ${shotNumber == 2 ? 'disabled-btn' : ''}">X (Strike)</button>
                <button type="submit" name="leave" value="/" 
                    class="option-btn spare ${shotNumber == 1 ? 'disabled-btn' : ''}">/ (Spare)</button>
                <button type="submit" name="leave" value="-" 
                    class="option-btn gutter">- (Gutter)</button>
                <button type="submit" name="leave" value="F" 
                    class="option-btn foul">F (Foul)</button>
            </div>
        </div>

        <input type="hidden" name="count" id="count" value="">
        <input type="hidden" name="leave" id="leave" value="${standingPinsString}">
        <input type="hidden" name="score" id="score" value="">
        <input type="hidden" name="board" id="board" value="">
        <input type="hidden" name="lane" id="lane" value="">
    </form>

    <button id="indexButton" onclick="location.href='${pageContext.request.contextPath}/index'">Index</button>
    <button onclick="location.href='${pageContext.request.contextPath}/insertGame'">Game</button>
    
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Initialize from server data
        var standingPinsInput = document.getElementById('standingPins');
        var currentShotPins = new Set();
        if (standingPinsInput.value) {
            standingPinsInput.value.split(',').forEach(function(pin) {
                if (pin) currentShotPins.add(pin);
            });
        }
        
        // Set up pin click handlers
        document.querySelectorAll('.pin').forEach(function(pin) {
            // Only make standing pins clickable
             
                pin.addEventListener('click', function() {
                    var pinNum = this.getAttribute('data-pin');
                    
                    if (currentShotPins.has(pinNum)) {
                        currentShotPins.delete(pinNum);
                        this.classList.remove('selected');
                    } else {
                        currentShotPins.add(pinNum);
                        this.classList.add('selected');
                    }
                    
                    // Update hidden inputs
                    standingPinsInput.value = Array.from(currentShotPins).join(',');
                    document.getElementById('count').value = calculateCount();
                    document.getElementById('leave').value = calculateLeave();
                });
                
                // Initialize selected state if needed
                if (currentShotPins.has(pin.getAttribute('data-pin'))) {
                    pin.classList.add('selected');
                }
            }
        });

        function calculateCount() {
            // First shot: count is 10 minus number of selected pins
            // Second shot: count is number of selected pins (since they're standing pins)
            if (${shotNumber == 1}) {
                return 10 - currentShotPins.size;
            } else {
                return currentShotPins.size;
            }
        }

        function calculateLeave() {
            // For first shot: show standing pins
            // For second shot: show remaining pins after this shot
            return Array.from(currentShotPins).join(',');
        }
    });
    </script>
</body>
</html>