<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Home page</title>
</head>
<body>

<c:if test="${not empty sessionScope.user}">
    <h1>
        Hello, ${sessionScope.user.login}!
    </h1>

    <p>
        (test) Your password hash is: ${sessionScope.user.password}
    </p>
</c:if>

<c:if test="${empty sessionScope.user}">
    <p>User is not logged in</p>
</c:if>

<form action="dispatcher-servlet" method="post">
    <input type="hidden" name="command" value="logout">
    <input type="submit" value="Log out">
</form>

</body>
</html>
