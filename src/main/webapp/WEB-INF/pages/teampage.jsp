<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="${team.subject}">
    <h1 style="word-wrap: break-word;">${team.subject}</h1>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <div class="card">
                    <div class="card-body">
                        <p class="card-text">Master: ${team.master.username}</p>

                        <h3 class="mt-4">Members</h3>
                        <ul class="list-group">
                            <c:forEach var="member" items="${team.members}">
                                <li class="list-group-item">${member.username}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:pageTemplate>