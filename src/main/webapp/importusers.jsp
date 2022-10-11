<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Import Users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body>
<div class="mb-3">
    <label for="formFile" class="form-label">Load file</label>
    <form action = "ImportUsersServlet" method = "post"
          enctype = "multipart/form-data">
        <input type = "text" name = "description"/>
        <input class="form-control" type="file" id="formFile" accept=".xml" name = "file">
        <br />
        <input type = "submit" value = "Upload File" />
    </form>
</div>
<form action = "ImportUsersServlet" method = "post"
      enctype = "multipart/form-data">
  <input type = "text" name = "description"/>
  <input type = "file" accept=".xml" name = "file"/>
  <br />
  <input type = "submit" value = "Upload File" />
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>
