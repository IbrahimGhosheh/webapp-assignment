<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Student Login</title>
</head>
<body>
<div align="center">
    <h1>Student Login</h1>
    <h3 style="color:red;">Invalid Credentials, Kindly login Again.</h3>
    <form action="login" method="get">
        <table style="with: 100%">
            <tr>
                <td>ID</td>
                <td><input type="number" name="ID" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="number" name="password" /></td>
            </tr>

        </table>
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>
