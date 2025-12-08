<%--
  Created by IntelliJ IDEA.
  User: pasha
  Date: 01.12.2025
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Home page</title>
</head>
<body>

<h1>Hello, ${login}! Your password is ${password}</h1>
<form action="dispatcher-servlet" method="post">
    <input type="hidden" name="command" , value="log_out">
    <input type="submit" value="Log out">
</form>
</body>
</html>
