<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1> Login page</h1>
<h4 style ="color:red">${error}</h4>

<form method="post" action="${pageContext.request.contextPath}/drivers/login">
    Driver login:<input type="text" name="login" pattern="^[a-z]*">
    Driver password :<input type="text" name="pwd">
    <button type="submit">Login</button>
</form>
<p>
    <a href="${pageContext.request.contextPath}/index">
        <button type="submit">Main page</button>
    </a>
</p>
</body>
</html>
