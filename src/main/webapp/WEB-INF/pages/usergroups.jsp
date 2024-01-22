<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Usergroups">
    <h1>CURRENT USERS</h1>

    <!-- Checking if the current user has the 'ADMIN' role to display user management features -->
    <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">

        <!-- Form for updating user roles (visible to administrators only) -->
        <form method="post" action="${pageContext.request.contextPath}/Usergroups">
    </c:if>

        <!-- Table to display user information -->
        <table class="table">
            <thead>
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
            </tr>
            </thead>
            <tbody>

            <!-- Loop through the list of users and display their information -->
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>

                        <!-- Dropdown to change user roles (visible to administrators only) -->
                        <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
                            <select name="userRole=-=${user.email}">

                                <!-- Loop through rolesList and create options in the dropdown -->
                                <c:forEach var="role" items="${rolesList}">
                                    <option value="${role}" ${role eq user.usergroup ? 'selected' : ''}>${role}</option>
                                </c:forEach>
                            </select>
                        </c:if>

                        <!-- Display user role (visible to users with 'RESEARCH' role) -->
                        <c:if test = "${pageContext.request.isUserInRole('RESEARCH')}">
                            ${user.usergroup}
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Checking if the current user has the 'ADMIN' role to display the submit button -->
        <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
        <button type="submit" class="btn btn-primary">Submit</button>
        </c:if>

    <!-- Closing the form (visible to administrators only) -->
    <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
    </form>
    </c:if>
</t:pageTemplate>