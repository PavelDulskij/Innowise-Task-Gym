<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<h1>Log in to your profile</h1>
<br/>
<form action="dispatcher-servlet" method="post">
    <input type="hidden" name="command" value="log_in" />
    Username: <br/>

    <input type="text" name="login"> <br/>
    Password: <br/>

    <input type="password" name="password"> <br/>
    <br/>
    <input type="submit" value="Submit">
</form>
<br>
    Don't have an account? <a href="register.jsp">Register</a>
</body>
</html>