<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver</title>
</head>
<body>
<h1>Please write a new driver.</h1>

<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    Driver name:<input type="text" name="name" pattern="^([A-Z])[a-z]*">
    Driver license number:<input type="text" name="license">
    Driver login:<input type="text" name="login" pattern="^a-z0-9]*">
    Driver password number:<input type="password" name="pwd">
    <button type="submit">Create driver</button>
</form>
<p>
    <a href="${pageContext.request.contextPath}/index">
        <button type="submit">Main page</button>
    </a>
</p>
</body>
</html>
