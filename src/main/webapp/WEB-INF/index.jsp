<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Main page</title>
  </head>
  <body>
  <form action="${pageContext.request.contextPath}/cars/create">
    <button type="submit">Create a car</button>
  </form>
  <form action="${pageContext.request.contextPath}/drivers/">
    <button type="submit">Display all drivers</button>
  </form>
  <form action="${pageContext.request.contextPath}/manufacturer/create">
    <button type="submit">Create a manufacturer</button>
  </form>
  <form action="${pageContext.request.contextPath}/drivers/create">
    <button type="submit">Create driver</button>
  </form>
  <form action="${pageContext.request.contextPath}/cars/driver/add">
    <button type="submit">Add driver to car</button>
  </form>
  </body>
</html>
