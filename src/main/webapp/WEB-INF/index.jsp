<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Main page</title>
  </head>
  <body>
  <form action="${pageContext.request.contextPath}/cars/create_car">
    <button type="submit">Create a car</button>
  </form>
  <form action="${pageContext.request.contextPath}/drivers/all_drivers">
    <button type="submit">Display all drivers</button>
  </form>
  <form action="${pageContext.request.contextPath}/manufacturer/create_manufacturer">
    <button type="submit">Create a manufacturer</button>
  </form>
  <form action="${pageContext.request.contextPath}/drivers/create_driver">
    <button type="submit">Create driver</button>
  </form>
  <form action="${pageContext.request.contextPath}/cars/add_driver_to_car">
    <button type="submit">Add driver to car</button>
  </form>
  </body>
</html>
