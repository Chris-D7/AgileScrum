<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="Sprints">
  <h1 class="text-uppercase text-break">SPRINTS FOR ${team.subject}</h1>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-6 offset-md-3">

        <!-- Checking if there is a current sprint -->
        <c:if test="${not empty currentSprint}">

          <!-- Displaying information about the current sprint -->
          <div class="card mt-4">
            <div class="card-body">
              <h3 class="card-title">Current Sprint: ${currentSprint.number}</h3>
              <p class="card-text mb-1">End Date: ${currentSprint.endDate}</p>

              <!-- Displaying completion progress bar -->
              <p class="card-text mb-1 mt-2">Completion:</p>
              <div class="progress" role="progressbar" aria-valuenow="${currentSprint.doneTasks}" aria-valuemin="0" aria-valuemax="${currentSprint.totalTasks}">
                <div class="progress-bar bg-success" style="width: ${currentSprint.totalTasks > 0 ? Math.floor((currentSprint.doneTasks / currentSprint.totalTasks) * 100) : 0}%">
                    ${currentSprint.totalTasks > 0 ? Math.floor((currentSprint.doneTasks / currentSprint.totalTasks) * 100) : 0}%
                </div>
              </div>

              <!-- Displaying tasks in a list -->
              <p class="card-text mb-1 mt-2">Tasks:</p>
              <ul class="list-group mb-4">
                <c:forEach var="task" items="${currentSprint.tasks}">
                  <li class="list-group-item ${task.status ? 'bg-success' : 'bg-danger'} p-2 text-white">
                    <div class="d-flex justify-content-between align-items-center">
                      <div>
                        <p class="mb-0">${task.description} - Assigned to: ${task.assignedUsername}</p>
                      </div>

                      <!-- Checking if the current user can complete the task -->
                      <c:if test="${pageContext.request.getRemoteUser() eq task.assignedUserEmail}">

                        <!-- Form for completing the task -->
                        <form id="completeTaskForm_${task.id}" action="${pageContext.request.contextPath}/TaskStatus" method="post" class="form-inline">
                          <input type="hidden" name="taskId" value="${task.id}">
                          <input type="hidden" name="teamId" value="${team.id}">
                          <div class="form-check">
                            <button type="submit" class="btn btn-outline-light">
                                Toggle
                            </button>
                          </div>
                        </form>
                      </c:if>
                    </div>
                  </li>
                </c:forEach>
              </ul>

              <!-- Form for creating a new task (visible to the team master) -->
              <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
              <form id="createTaskForm" action="${pageContext.request.contextPath}/TaskCreate" method="post">
                <div class="form-group">
                  <label for="taskDescription">Task Description:</label>
                  <input type="text" id="taskDescription" name="description" class="form-control" required>
                </div>

                <!-- Assigned user selection dropdown -->
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

        <!-- Checking if there are no sprints or no current sprint -->
        <c:if test="${empty sprints or empty currentSprint}">

          <!-- Checking if the current user is the team master -->
          <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">

            <!-- Form for creating a new sprint -->
            <form id="createSprintForm" action="${pageContext.request.contextPath}/SprintPage" method="post">
              <div class="form-group">
                <label for="endDate">End Date:</label>
                <input type="datetime-local" id="endDate" name="endDate" class="form-control" required>
              </div>
              <input type="hidden" name="id" value="${team.id}">
              <button type="submit" class="btn btn-primary mt-2">Create Sprint</button>
            </form>


            <!-- JavaScript to validate that the selected date is in the future -->
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

        <!-- Displaying information about past sprints -->
        <c:if test="${not empty sprints and sprints[0].id ne currentSprint.id}">
          <h3 class="mb-1 mt-4">Past Sprints</h3>
          <ul class="list-group">
            <c:forEach var="sprint" items="${sprints}">
              <c:if test="${sprint != currentSprint}">
              <li class="list-group-item">
                <h3 class="mb-1">Sprint ${sprint.number}</h3>
                <p class="mb-1 mt-2">End Date: ${sprint.endDate}</p>
                <p class="card-text mb-1 mt-2">Completion:</p>
                <div class="progress" role="progressbar" aria-valuenow="${sprint.doneTasks}" aria-valuemin="0" aria-valuemax="${sprint.totalTasks}">
                  <div class="progress-bar bg-success" style="width: ${sprint.totalTasks > 0 ? Math.floor((sprint.doneTasks / sprint.totalTasks) * 100) : 0}%">
                      ${sprint.totalTasks > 0 ? Math.floor((sprint.doneTasks / sprint.totalTasks) * 100) : 0}%
                  </div>
                </div>
                <p class="card-text mb-1 mt-2">Tasks:</p>
                <ul class="list-group mb-4">
                  <c:forEach var="task" items="${sprint.tasks}">
                    <li class="list-group-item ${task.status ? 'bg-success' : 'bg-danger'} p-2 text-white">
                      <div class="d-flex justify-content-between align-items-center">
                        <div>
                          <p class="mb-0">${task.description} - Assigned to: ${task.assignedUsername}</p>
                        </div>
                      </div>
                    </li>
                  </c:forEach>
                </ul>
                <c:if test="${not empty sprint.review}">
                  <p class="mb-1 fw-bold">Review: ${sprint.review}</p>
                </c:if>
                <c:if test="${pageContext.request.getRemoteUser() == team.master.email}">
                  <form id="reviewForm_${sprint.id}" action="${pageContext.request.contextPath}/SprintReview" method="post">
                    <input type="text" name="review" id="review" class="form-control" placeholder="Write a Review...">
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