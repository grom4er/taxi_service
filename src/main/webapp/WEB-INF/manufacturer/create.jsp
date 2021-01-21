<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacture</title>
</head>
<body>
<h1>Please write a new manufacturer.</h1>
<form method="post" action="${pageContext.request.contextPath}/manufacturer/create">
    Driver name:<input type="text" name="name" pattern="^([A-Z])[a-z]*">
    Driver license number:<input type="text" name="license" pattern="^([A-Z])[a-z]*">
    <button type="submit">Create manufacture</button>
</form>
<p>
    <a href="${pageContext.request.contextPath}/">
        <button type="submit">Main page</button>
    </a>
</p>
</body>
</html>
