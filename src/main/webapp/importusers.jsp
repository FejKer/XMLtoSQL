<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Import Users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="jquery-3.6.1.js"></script>
    <script src="bootstrap.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link href="style.css" rel="stylesheet">
</head>
<body class="text-center">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="index.jsp">Index Page</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="GetUsersServlet">Get Users</a>
            <a class="nav-item nav-link" href="importusers.jsp">Import Users</a>
        </div>
    </div>
</nav>
<div id="main" class="cover-container d-flex h-100 p-3 mx-auto flex-column">
    <main role="main" class="inner cover">
        <label for="formFile" class="form-label">Upload .xml file with user data</label>
        <form action = "ImportUsersServlet" method = "post"
              enctype = "multipart/form-data">
            <input type = "hidden" name = "description"/>
            <input class="form-control" type="file" id="formFile" accept=".xml" name = "file">
            <br />
            <input type = "submit" class="btn btn-lg btn-secondary" value = "Upload File" />
        </form>
    </main>
</div>
<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Info</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ${result} records were uploaded successfully. Do you wish to visit Get Users site?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <a href="GetUsersServlet">
                    <button type="button" class="btn btn-primary" >Get Users</button>
                </a>
            </div>
        </div>
    </div>
</div>
<c:choose>
    <c:when test="${success == true}">
        <script>
            document.addEventListener('DOMContentLoaded', () => {
                $('.modal').modal('show');
            });
        </script>
    </c:when>
</c:choose>
</body>
</html>
