<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>result</title>
</head>
<body>
<div id="richestForm">
<form method="GET"  action="banks">
    <input type="hidden" name="command" value="richest" required/>
    <p>Richest user</p>
    <input type="submit" value="Get">
</form>
</div>

<div id="accountsSum">
<form method="GET"  action="banks">
    <input type="hidden" name="command" value="sum" required/>
    <p>Accounts sum</p>
    <input type="submit" value="Get">
</form>
</div>
<div id="sumAccountResult">

</div>
</body>
</html>
