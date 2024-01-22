<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Login">
    <!-- Display a warning message if the 'message' attribute is not null -->
    <c:if test="${message != null}">
        <div class="alert alert-warning" role="alert">
                ${message}
        </div>
    </c:if>

    <!-- Login Form -->
    <form class="form-signin" method="POST" action="j_security_check">
        <h1 class="h3 mb-3 font-weight-normal">Sign in</h1>

        <!-- Email Input -->
        <label for="email" class="sr-only">Email</label>
        <input type="email" id="email" name="j_username" class="form-control" placeholder="Email" required autofocus />

        <!-- Password Input -->
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="j_password" class="form-control" placeholder="Password" required/>

        <!-- Sign-in Button -->
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

    <!-- Link to Register Page -->
    <a class="btn btn-lg btn-secondary btn-block" href="${pageContext.request.contextPath}/Register">Register</a>
</t:pageTemplate>