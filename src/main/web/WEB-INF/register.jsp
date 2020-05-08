<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

    <!--Custom styles-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/register.css"/>">
    <script src="<c:url value="/static/js/jquery.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/js/main.js"/>" type="text/javascript"></script>
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

<div class="container">
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h3>Sign Up</h3>
                <div class="d-flex justify-content-end social_icon">
                    <span><i class="fab fa-facebook-square"></i></span>
                    <span><i class="fab fa-google-plus-square"></i></span>
                    <span><i class="fab fa-twitter-square"></i></span>
                </div>
            </div>
            <div class="card-body">
                <form action="register" method="post" enctype="multipart/form-data">
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input id="name-input" name="name" type="text"
                               placeholder="<c:out value="${requestScope.errorName != null ? requestScope.errorName : 'Name'}"/>"
                               class="<c:out value="${requestScope.errorName != null ? 'danger-placeholder' : ''}"/>"
                               value="<%= getParamValue(request, "name")%>"
                               required/>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input id="surname-input" name="surname" type="text" value="<%= getParamValue(request, "surname")%>"
                               placeholder="<c:out value="${requestScope.errorSurname != null ? requestScope.errorSurname : 'Surname'}"/>"
                               class="<c:out value="${requestScope.errorName != null ? 'danger-placeholder' : ''}"/>"
                               required/>
                    </div>
                    <p id="wrong-email" hidden="hidden" style="color: red">Wrong Email Format!</p>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input id="email-input" name="email" type="text" value="<%= getParamValue(request, "email")%>"
                               placeholder="<c:out value="${requestScope.errorEmail != null ? requestScope.errorEmail : 'Email'}"/>"
                               class="<c:out value="${requestScope.errorEmail != null ? 'danger-placeholder' : ''}"/>"
                               required/>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>
                        <input id="password-input" name="password" type="password"
                               placeholder="<c:out value="${requestScope.errorPassword != null ? requestScope.errorPassword : 'Password'}"/>"
                               class="<c:out value="${requestScope.errorPassword != null ? 'danger-placeholder' : ''}"/>"
                               required/>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>
                        <input id="confirm-password-input" name="confirmPassword" type="password"
                               placeholder="<c:out value="${requestScope.errorConfirmPassword != null ? requestScope.errorConfirmPassword : 'Confirm Password'}"/>"
                               class="<c:out value="${requestScope.errorConfirmPassword != null ? 'danger-placeholder' : ''}"/>"
                               required/>
                    </div>
                    <span class="remember">Select Profile Image</span>
                    <input type="file" name="file" accept="image/*">
                    <div class="form-group">
                        <button onclick="doRegister()" class="btn float-left login_btn">Register</button>
                    </div>
                    <c:if test="${requestScope.userExist != null}">
                        <p class="danger">
                            <c:out value="${requestScope.userExist}"/>
                        </p>
                    </c:if>
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                    Already registered? <a href="/login">Login</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
