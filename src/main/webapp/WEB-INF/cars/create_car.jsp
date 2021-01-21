<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Please write new Car.</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/create_car">
    Car's model:<input type="text" value="${carModel}" name="model" required>
    Car's manufacturer's id:<input type="number" min="1" name="manufacturersId" required>
    <button type="submit">Create</button>
</form>
<p>
    <a href="${pageContext.request.contextPath}/">
        <button type="submit">Main page</button>
    </a>
</p>
</body>
</html>
