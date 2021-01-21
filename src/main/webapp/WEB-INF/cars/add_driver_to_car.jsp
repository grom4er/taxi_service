<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Driver to car</title>
</head>
<body>
<h1>Write driver and car id to add it.</h1>

<h4 style="color: crimson">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/cars/add_driver_to_car">
    Car's id:<input type="number" name="carID" required>
    Driver's id:<input type="number" name="driverID" required>
    <button type="submit">Add</button>
</form>

<p>
    <a href="${pageContext.request.contextPath}/">
        <button type="submit">Main page</button>
    </a>
</p>
</body>
</html>
