<%-- 
    Document   : Index
    Created on : Apr 24, 2020, 2:04:39 PM
    Author     : Odisseas KD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <% 
        if(session.getAttribute("user1") != null)
        out.print("First name: " + ((models.User)(session.getAttribute("user1"))).getFirst_name() + "<br>");
        out.print("Last name: " +((models.User)(session.getAttribute("user1"))).getLast_name() + "<br>");
        out.print("Telephone: " +((models.User)(session.getAttribute("user1"))).getTel() + "<br>");
        out.print("Email: " +((models.User)(session.getAttribute("user1"))).getEmail() + "<br>");
%>
    </body>
</html>
