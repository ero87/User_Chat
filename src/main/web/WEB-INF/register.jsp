<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%!
    public void printError(HttpServletRequest request, HttpServletResponse response, String paramName) throws IOException {
        if (request.getParameter("errorName") != null) {
            response.getWriter().write("<span style=\"color: red\">");
            response.getWriter().write(request.getParameter(paramName));
            response.getWriter().write("</span>");
        }
    }
%>
<%!
    private String getParamValue(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value != null ? value : "";
    }
%>


<form action="/register" method="post">
    <h3>Информация о пассажире</h3>
    <input id="name" name="name" type="text" value="<%= getParamValue(request, "name")%>"/>
    <%--    <%printError(request, response, "errorName");%>--%>
    <c:choose>
        <c:when test="${requestScope.errorName != null}">
            <span style="color: red">
                <c:out value="${requestScope.errorName}"/>
            </span>
        </c:when>
        <c:otherwise>
            <label for="name"> Name </label>
        </c:otherwise>
    </c:choose>
    <br>
    <%--    <% if(request.getAttribute("errorName") != null) {%>--%>
    <%--        <span style="color: red">--%>
    <%--            <%=request.getAttribute("errorName")%>--%>
    <%--        </span>--%>
    <%--    <%} else {%>--%>
    <%--        <label for="name"> Name </label>--%>
    <%--    <%}%>--%>

    <input id="surname" name="surname" type="text" value="<%= getParamValue(request, "surname")%>"/>
    <c:choose>
        <c:when test="${requestScope.errorSurname != null}">
            <span style="color: red">
                <c:out value="${requestScope.errorSurname}"/>
            </span>
        </c:when>
        <c:otherwise>
            <label for="surname"> Surname </label>
        </c:otherwise>
    </c:choose>
    <br>
    <%--    <% if(request.getAttribute("errorSurname") != null) {%>--%>
    <%--        <span style="color: red">--%>
    <%--            <%=request.getAttribute("errorSurname")%>--%>
    <%--        </span>--%>
    <%--    <%} else {%>--%>
    <%--        <label for="surname"> Surname </label>--%>
    <%--    <%}%>--%>
    <%--    <br/>--%>
    <input id="email" name="email" type="text" value="<%= getParamValue(request, "email")%>"/>
    <c:choose>
        <c:when test="${requestScope.errorEmail != null}">
            <span style="color: red">
                <c:out value="${requestScope.errorEmail}"/>
            </span>
        </c:when>
        <c:otherwise>
            <label for="email"> Email </label>
        </c:otherwise>
    </c:choose>
    <br>
    <%--    <% if(request.getAttribute("errorEmail") != null) {%>--%>
    <%--        <span style="color: red">--%>
    <%--            <%=request.getAttribute("errorEmail")%>--%>
    <%--        </span>--%>
    <%--    <%} else {%>--%>
    <%--        <label for="email"> Email </label>--%>
    <%--    <%}%>--%>
    <%--    <br/>--%>
    <input id="password" name="password" type="password"/>
    <c:choose>
        <c:when test="${requestScope.errorPassword != null}">
            <span style="color: red">
                <c:out value="${requestScope.errorPassword}"/>
            </span>
        </c:when>
        <c:otherwise>
            <label for="password"> Password </label>
        </c:otherwise>
    </c:choose>
    <br>
<%--    <% if (request.getAttribute("errorPassword") != null) {%>--%>
<%--    <span style="color: red">--%>
<%--            <%=request.getAttribute("errorPassword")%>--%>
<%--        </span>--%>
<%--    <%} else {%>--%>
<%--    <label for="password"> Password </label>--%>
<%--    <%}%>--%>
<%--    <br/>--%>
    <input id="confirmPassword" name="confirmPassword" type="password"/>
    <c:choose>
        <c:when test="${requestScope.errorConfirmPassword != null}">
            <span style="color: red">
                <c:out value="${requestScope.errorConfirmPassword}"/>
            </span>
        </c:when>
        <c:otherwise>
            <label for="confirmPassword"> Confirm Password </label>
        </c:otherwise>
    </c:choose>
    <br>
<%--    <% if (request.getAttribute("errorConfirmPassword") != null) {%>--%>
<%--    <span style="color: red">--%>
<%--            <%=request.getAttribute("errorConfirmPassword")%>--%>
<%--        </span>--%>
<%--    <%} else {%>--%>
<%--    <label for="confirmPassword"> Confirm Password </label>--%>
<%--    <%}%>--%>
<%--    <br/>--%>
    <input type="submit" value="Register"/>
    <br/>
    <a href="/login">Login</a>
</form>
</body>
</html>
