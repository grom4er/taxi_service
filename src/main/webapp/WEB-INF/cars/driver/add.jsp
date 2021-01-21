<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Driver to car</title>
</head>
<body>
<h1>Write driver and car id to add it.</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/driver/add">
    Car's id:<input type="number" name="car_id" required>
    Driver's id:<input type="number" name="driver_id" required>
    <button type="submit">Add</button>
</form>
<p>
    <a href="${pageContext.request.contextPath}/index">
        <button type="submit">Main page</button>
    </a>
</p>
</body>
</html>
