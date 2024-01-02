<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Usergroups">
    <h1>CURRENT USERS</h1>
    <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
    <form method="post" action="${pageContext.request.contextPath}/Usergroups">
    </c:if>
        <table class="table">
            <thead>
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>
                        <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
                            <select name="userRole">
                                <c:forEach var="role" items="${rolesList}">
                                    <option value="${role}=-=${user.email}" ${role eq user.usergroup ? 'selected' : ''}>${role}</option>
                                </c:forEach>
                            </select>
                        </c:if>
                        <c:if test = "${pageContext.request.isUserInRole('RESEARCH')}">
                            ${user.usergroup}
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
        <button type="submit" class="btn btn-primary">Submit</button>
        </c:if>
    <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
    </form>
    </c:if>
</t:pageTemplate>