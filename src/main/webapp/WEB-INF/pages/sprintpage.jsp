<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="Sprints">
  <h1 class="text-uppercase text-break">SPRINTS FOR ${team.subject}</h1>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-6 offset-md-3">
        <c:if test="${not empty currentSprint}">
          <div class="card mt-4 mb-5">
            <div class="card-body">
              <h3 class="card-title">Current Sprint: ${currentSprint.number}</h3>
              <p class="card-text">End Date: ${currentSprint.endDate}</p>
              <p class="card-text">Tasks:</p>
              <ul class="list-group">
                <c:forEach var="task" items="${currentSprint.tasks}">
                  <li class="list-group-item">${task.description} - Assigned to: ${task.assignedUsername}</li>
                </c:forEach>
              </ul>
              <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
              <form id="createTaskForm" action="${pageContext.request.contextPath}/TaskCreate" method="post">
                <div class="form-group">
                  <label for="taskDescription">Task Description:</label>
                  <input type="text" id="taskDescription" name="description" class="form-control" required>
                </div>

                <div class="form-group">
                  <label for="assignedUser">Assign User:</label>
                  <select id="assignedUser" name="userId" class="form-control" required>
                    <c:forEach var="member" items="${team.members}">
                      <option value="${member.id}">${member.username}</option>
                    </c:forEach>
                    <option value="${team.master.id}">${team.master.username}</option>
                  </select>
                </div>

                <!-- Hidden inputs for teamId and sprintId -->
                <input type="hidden" name="teamId" value="${team.id}">
                <input type="hidden" name="sprintId" value="${currentSprint.id}">

                <button type="submit" class="btn btn-primary mt-2">Create Task</button>
              </form>
              </c:if>
            </div>
          </div>
        </c:if>

        <c:if test="${empty sprints or empty currentSprint}">
          <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
            <form id="createSprintForm" action="${pageContext.request.contextPath}/SprintPage" method="post">
              <div class="form-group">
                <label for="endDate">End Date:</label>
                <input type="datetime-local" id="endDate" name="endDate" class="form-control" required>
              </div>
              <input type="hidden" name="id" value="${team.id}">
              <button type="submit" class="btn btn-primary mt-2">Create Sprint</button>
            </form>

            <script>
              document.getElementById('createSprintForm').addEventListener('submit', function (event) {
                var currentDate = new Date();
                var selectedDate = new Date(this.elements.endDate.value);

                if (selectedDate < currentDate) {
                  event.preventDefault();
                  alert('Please select a future date and time.');
                }
              });
            </script>
          </c:if>
        </c:if>

        <c:if test="${not empty sprints}">
          <h3 class="mb-1">Past Sprints</h3>
          <ul class="list-group">
            <c:forEach var="sprint" items="${sprints}">
              <c:if test="${sprint != currentSprint}">
              <li class="list-group-item">
                <h3 class="mb-1">Sprint ${sprint.number}</h3>
                <p class="mb-1">End Date: ${sprint.endDate}</p>
                <p class="card-text">Tasks:</p>
                <ul class="list-group">
                  <c:forEach var="task" items="${sprint.tasks}">
                    <li class="list-group-item">${task.description} - Assigned to: ${task.assignedUsername}</li>
                  </c:forEach>
                </ul>
                <c:if test="${not empty sprint.review}">
                  <p class="mb-1">Review: ${sprint.review}</p>
                </c:if>
                <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
                  <form id="reviewForm_${sprint.id}" action="${pageContext.request.contextPath}/SprintReview" method="post">
                    <input type="text" name="review" id="review" class="form-control">
                    <input type="hidden" name="sprintId" value="${sprint.id}">
                    <input type="hidden" name="id" value="${team.id}">
                    <button type="submit" class="btn btn-primary mt-2">Write Review</button>
                  </form>
                </c:if>
              </li>
            </c:if>
            </c:forEach>
          </ul>
        </c:if>
      </div>
    </div>
  </div>
</t:pageTemplate>