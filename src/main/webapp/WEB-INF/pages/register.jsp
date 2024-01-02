<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Register">
    <c:if test="${message != null}">
        <div class="alert alert-warning" role="alert">
                ${message}
        </div>
    </c:if>

    <form class="form-signin" method="POST" action="${pageContext.request.contextPath}/Register">
        <h1 class="h3 mb-3 font-weight-normal">Register</h1>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus />

        <label for="email" class="sr-only">Email</label>
        <input type="email" id="email" name="email" class="form-control" placeholder="Email" required />

        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required />

        <label for="confirmPassword" class="sr-only">Confirm Password</label>
        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Confirm Password" required />

        <button class="btn btn-lg btn-primary btn-block" type="submit">Create Account</button>
    </form>

    <a class="btn btn-lg btn-secondary btn-block" href="${pageContext.request.contextPath}/Login">Back to Login</a>
</t:pageTemplate>
