<%--
  Created by IntelliJ IDEA.
  User: pasha
  Date: 28.11.2025
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <h1>Create a new profile</h1>
    <form action="dispatcher-servlet" method="post">
        <input type="hidden" name="command" value="register" />
        Username: <br/>
        <input type="text" name="login"> <br/>
        Password: <br/>
        <input type="password" name="password"> <br/>
        Confirm password: <br/>
        <input type="password" name="confirmPass"> <br/>
        <br />
        <input type="submit" value="Submit">
    </form>
<p>
    Already have an account? <a href="index.jsp">Log in!</a>
</p>
</body>
</html>
