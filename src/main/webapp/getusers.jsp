<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Get Users</title>
</head>
<body>
<c:forEach items="${data}" var="d">
    <tr>
        <td>${d}</td>
    </tr>
</c:forEach>
</body>
</html>
