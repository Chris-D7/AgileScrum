<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="${team.subject}">
    <h1 style="word-wrap: break-word;">${team.subject}</h1>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
                <button id="toggleFormButton" class="btn btn-primary mb-3">Edit</button>
                </c:if>
                <div class="card">
                    <div class="card-body">
                        <p class="card-text">Master: ${team.master.username}</p>

                        <h3 class="mt-4">Members</h3>
                        <ul class="list-group">
                            <c:forEach var="member" items="${team.members}">
                                <li class="list-group-item">
                                        ${member.username}
                                            <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
                                                <form class="deleteMemberForm" id="deleteMemberForm" action="${pageContext.request.contextPath}/TeamDeleteMember" method="post" style="display: none;">
                                                    <input type="hidden" name="teamId" value="${team.id}">
                                                    <input type="hidden" name="memberId" value="${member.id}">
                                                    <button type="submit" class="btn btn-danger btn-sm ml-2" onclick="return confirm('Are you sure you want to delete this member?')">Delete</button>
                                                </form>
                                            </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
                        <form style="display: none;" class="m-3" id="addMemberForm" action="${pageContext.request.contextPath}/TeamAddMember" method="post">
                            <div class="form-group">
                                <input type="hidden" name="teamId" value="${team.id}">
                                <input type="email" name="email" id="email" placeholder="Add Member by Email" required>
                            </div>
                            <button type="submit" class="btn btn-primary ml-2 mt-2">Add</button>
                        </form>
                    </c:if>
                </div>
                <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
                    <form style="display: none;" id="deleteForm" action="${pageContext.request.contextPath}/TeamDelete" method="post">
                        <input type="hidden" name="teamId" value="${team.id}">
                        <button type="submit" class="btn btn-danger ml-2 mt-2" onclick="return confirm('Are you sure you want to delete this team?')">Delete Team</button>
                    </form>
                </c:if>
                <c:forEach var="member" items="${team.members}">
                    <c:if test="${pageContext.request.getRemoteUser() == member.email}">
                        <form id="leaveTeam" action="${pageContext.request.contextPath}/TeamDeleteMember" method="post">
                            <input type="hidden" name="teamId" value="${team.id}">
                            <input type="hidden" name="memberId" value="${member.id}">
                            <button type="submit" class="btn btn-danger ml-2 mt-2" onclick="return confirm('Are you sure you want to leave this team?')">Leave Team</button>
                        </form>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>

    <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script>
            $(document).ready(function () {
                $("#toggleFormButton").click(function (e) {
                    e.preventDefault();

                    $(".deleteMemberForm").toggle();
                    $("#addMemberForm").toggle();
                    $("#deleteForm").toggle();

                    if ($("#deleteMemberForm").is(":visible")) {
                        $("#toggleFormButton").text("Cancel").removeClass("btn-primary").addClass("btn-danger");
                    } else {
                        $("#toggleFormButton").text("Edit").removeClass("btn-danger").addClass("btn-primary");
                    }
                });
            });
        </script>
    </c:if>
</t:pageTemplate>