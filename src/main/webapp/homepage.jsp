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

<c:if test="${not empty sessionScope.user}">
    <h1>Hello, ${sessionScope.user.login}!</h1>
    <h3>Your password is ${sessionScope.user.password}</h3>
    </c:if>
    <form action="dispatcher-servlet" method="post">
        <input type="hidden" name="command", value="log_out">
        <input type="submit" value="Log out">
    </form>
</body>
</html>
