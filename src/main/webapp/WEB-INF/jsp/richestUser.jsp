
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="richestResult">
    <h1 align="center">The richest users</h1>
    <p></p>
    <table border="1" align="center" valign="center">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${richestUsers}" var="user">
            <tr>
                <td align="center">${user.userId}/></td>
                <td align="center">${user.name}</td>
                <td align="center">${user.surname}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
