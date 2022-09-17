<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="com.example.assignment.*"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Grades</title>
</head>
<body>
<div align="center">
    <h1> Grades: </h1>
    <% Map<String,Integer> grades = (HashMap<String, Integer>)request.getAttribute("grades");%>

    <%for(Map.Entry<String, Integer> entry : grades.entrySet()){%>
        <a href="/Assignment_war_exploded/course?courseName=<%=entry.getKey()%>"> <%= entry.getKey() %> <a/> grade: <%=entry.getValue()%> <br/>
    <%}%>
</div>
</body>
</html>
