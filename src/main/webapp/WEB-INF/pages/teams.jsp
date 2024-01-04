<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Teams">
    <h1>TEAMS</h1>
    <div class="card-deck">
        <c:forEach var="team" items="${teamsList}">
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">${team.subject}</h5>
                    <p class="card-text">Master: ${team.master.username}</p>
                    <a href="${pageContext.request.contextPath}/TeamPage?teamId=${team.id}" class="btn btn-primary">View Team</a>
                </div>
            </div>
        </c:forEach>
    </div>
</t:pageTemplate>