<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<c:out value="${sessionScope.user}"/>
<br>
<img src="<c:out value="${sessionScope.user.imageUrl}"/>">
<%--<%= request.getSession().getAttribute("user")%>--%>
<br>
<a href="/logout">Logout</a>
</body>
</html>
