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
    <button type="submit">Create driver</button>
</form>
<p>
    <a href="${pageContext.request.contextPath}/">
        <button type="submit">Main page</button>
    </a>
</p>
</body>
</html>
