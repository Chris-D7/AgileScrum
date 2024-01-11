<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="Teams">
    <h1>TEAMS</h1>
    <button id="toggleFormBtn" class="btn btn-primary mb-3">Create Team</button>
    <form action="${pageContext.request.contextPath}/Teams" method="post" id="createTeamForm" style="display: none;">
        <div class="form-group">
            <label for="subject">Subject:</label>
            <input type="text" class="form-control" id="subject" name="subject" required>
        </div>

        <input type="hidden" name="master" value="${pageContext.request.getRemoteUser()}">

        <button type="submit" class="btn btn-success mt-3">Create Team</button>
    </form>
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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#toggleFormBtn").click(function (e) {
                e.preventDefault();
                $("#createTeamForm").toggle();

                if ($("#createTeamForm").is(":visible")) {
                    $("#toggleFormBtn").text("Cancel").removeClass("btn-primary").addClass("btn-danger");
                } else {
                    $("#toggleFormBtn").text("Create Team").removeClass("btn-danger").addClass("btn-primary");
                }
            });
        });
    </script>
</t:pageTemplate>