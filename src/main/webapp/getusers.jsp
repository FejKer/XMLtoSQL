<%@ page import="java.util.*" %>
<%@ page import="javax.xml.bind.DatatypeConverter" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Get Users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body>

<input type="text" id="input" onkeyup="search()" placeholder="Wyszukaj...">

<table id="userTable" class="table table-striped table-hover">
    <thead>
        <tr>
            <th onclick="sortTable(0)" scope="col">#</th>
            <th onclick="sortTable(1)" scope="col">Name</th>
            <th onclick="sortTable(2)" scope="col">Surname</th>
            <th onclick="sortTable(3)" scope="col">Login</th>
        </tr>
    </thead>
    <tbody>
<%
    HashMap<Long, ArrayList<String>> data = (HashMap<Long, ArrayList<String>>) request.getAttribute("users");
    ArrayList<String> users;

    for(int i = 1; i <= data.size(); i++) {
        users = new ArrayList<>(data.get((long) i));
        String name = users.get(0);

        MessageDigest md = MessageDigest.getInstance("MD5");        //md5 hashing
        md.update(name.getBytes());
        byte[] digest = md.digest();
        String nameHashed = DatatypeConverter.printHexBinary(digest).toUpperCase();

        String surname = users.get(1) + "_" + nameHashed;
        String login = users.get(2);

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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script src="scripts.js"></script>
</body>
</html>
