<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="Teams">
    <h1>TEAMS</h1>
    <div class="row">
        <c:forEach var="team" items="${teamsList}">
            <div class="col-sm-6 col-md-4 col-lg-3 mb-4">
                <div class="card h-100 w-auto">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${team.subject}</h5>
                        <p class="card-text">Master: ${team.master.username}</p>
                        <a href="${pageContext.request.contextPath}/TeamPage?id=${team.id}" class="btn btn-primary mt-auto">View Team</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</t:pageTemplate>