<%@ page import="java.util.*" %>
<%@ page import="javax.xml.bind.DatatypeConverter" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Get Users</title>
</head>
<body>
<table>
<%
    HashMap<Long, ArrayList<String>> data = (HashMap<Long, ArrayList<String>>) request.getAttribute("users");
    ArrayList<ArrayList<String>> users = new ArrayList<>(data.values());
    for(ArrayList<String> userData : users) {
        String name = userData.get(0);

        MessageDigest md = MessageDigest.getInstance("MD5");        //md5 hashing
        md.update(name.getBytes());
        byte[] digest = md.digest();
        String nameHashed = DatatypeConverter.printHexBinary(digest).toUpperCase();

        String surname = userData.get(1) + "_" + nameHashed;
        String login = userData.get(2);
%>
    <tr>
        <td><%=name%></td>
        <td><%=surname%></td>
        <td><%=login%></td>
    </tr>
<%}%>
</table>
</body>
</html>
