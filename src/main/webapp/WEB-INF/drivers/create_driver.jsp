<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver</title>
</head>
<body>
<h1>Please provide driver's credentials.</h1>

<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    Driver's name:<input type="text" name="name" pattern="^([A-Z])[a-z]*">
    Driver's license number:<input type="text" name="license">

</form>
</body>
</html>
