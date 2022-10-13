<%@ page import="javax.xml.bind.DatatypeConverter" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="ovh.fejker.filetosql.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Get Users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body>
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
<form method="post" class="form-inline">
    <input id="searchBar" class="form-control mr-sm-2" type="search" placeholder="Search..." aria-label="Search" name="searchInput">
</form>
<table id="userTable" class="table table-striped table-hover">
    <thead>
        <tr>
            <th scope="col">
                <form action="SortedUsersServlet" method="post">
                    <input type="hidden" name="column" value="id">
                    <button class="sort" type="submit">id</button>
                </form>
            </th>
            <th scope="col">
                <form action="SortedUsersServlet" method="post">
                    <input type="hidden" name="column" value="login">
                    <button class="sort" type="submit">login</button>
                </form>
            </th>
            <th scope="col">
                <form action="SortedUsersServlet" method="post">
                    <input type="hidden" name="column" value="name">
                    <button class="sort" type="submit">name</button>
                </form>
            </th>
            <th scope="col">
                <form action="SortedUsersServlet" method="post">
                    <input type="hidden" name="column" value="surname">
                    <button class="sort" type="submit">surname</button>
                </form>
            </th>
        </tr>
    </thead>
    <tbody>
<%
    ArrayList<User> users = new ArrayList<User>((ArrayList<User>) request.getAttribute("users"));

    for(User user : users) {
        String name = user.getName();

        MessageDigest md = MessageDigest.getInstance("MD5");        //md5 hashing
        md.update(name.getBytes());
        byte[] digest = md.digest();
        String nameHashed = DatatypeConverter.printHexBinary(digest).toUpperCase();

        String surname = user.getSurname() + "_" + nameHashed;
        String login = user.getLogin();
        long i = user.getId();

%>
    <tr>
        <td><%=i%></td>
        <td><%=name%></td>
        <td><%=surname%></td>
        <td><%=login%></td>
    </tr>
<%}%>
    </tbody>
</table>

<nav aria-label="Pagination">
    <p><%=request.getAttribute("numberOfUsers")%> users</p>
    <ul>
        <c:choose>
            <c:when test="${currentPage == 1}">
               <li aria-hidden="true">&laquo;</li>
               <li aria-hidden="true">&lsaquo;</li>
            </c:when>
            <c:otherwise>
            <li>
                <a href="?page=1" aria-label="First Page">&laquo;</a>
            </li>
            <li>
                <a href="?page=${currentPage - 1}" aria-label="Previous Page">&lsaquo;</a>
            </li>
        </c:otherwise>
        </c:choose>

        <li aria-current="page">page ${currentPage}/${numberOfPages}</li>
        <c:choose>
            <c:when test="${currentPage == numberOfPages}">
                <li aria-hidden="true">&rsaquo;</li>
                <li aria-hidden="true">&raquo;</li>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="?page=${currentPage + 1}" aria-label="Next Page">&rsaquo;</a>
                </li>
                <li>
                    <a href="?page=${numberOfPages}" aria-label="Last Page">&raquo;</a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script src="scripts.js"></script>
</body>
</html>
