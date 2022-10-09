<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Import Users</title>
</head>
<body>
<form action = "ImportUsersServlet" method = "post"
      enctype = "multipart/form-data">
  <input type = "text" name = "description"/>
  <input type = "file" name = "file"/>
  <br />
  <input type = "submit" value = "Upload File" />
</form>
</body>
</html>
