<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:pageTemplate pageTitle="News">
  <h1>NEWS</h1>

  <c:if test="${pageContext.request.isUserInRole('RESEARCH') || pageContext.request.isUserInRole('ADMIN')}">
    <button id="toggleFormButton" class="btn btn-primary">Create</button>

    <form id="newsForm" method="POST" action="${pageContext.request.contextPath}/News" enctype="multipart/form-data" style="display: none;">
      <div class="form-group">
        <label for="newsTitle">Title</label>
        <input type="text" class="form-control" id="newsTitle" name="newsTitle" required>
      </div>

      <div class="form-group">
        <label for="newsBody">Body</label>
        <textarea class="form-control" id="newsBody" name="newsBody" rows="5" required></textarea>
      </div>

      <div class="form-group">
        <label for="file">Image</label>
        <input type="file" class="form-control" id="file" name="file">
      </div>

      <input type="hidden" name="newsAuthor" value="${pageContext.request.getRemoteUser()}">

      <button type="submit" class="btn btn-primary">Post</button>
    </form>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script>
      $(document).ready(function () {
        $("#toggleFormButton").click(function (e) {
          e.preventDefault(); // Prevent the default form submission
          $("#newsForm").toggle();

          // Update button text and class based on form visibility
          if ($("#newsForm").is(":visible")) {
            $("#toggleFormButton").text("Cancel").removeClass("btn-primary").addClass("btn-danger");
          } else {
            $("#toggleFormButton").text("Create").removeClass("btn-danger").addClass("btn-primary");
          }
        });
      });
    </script>
  </c:if>

  <c:forEach var="news" items="${newsList}">
    <div class="card mb-4">
      <div class="card-body">
        <h3 class="card-title">${news.title}</h3>
        <p class="card-subtitle mb-2 text-muted">Author: ${news.author}</p>
        <p class="card-subtitle mb-2 text-muted">Date Posted: ${news.getDatePostedFormatted()}</p>
        <p class="card-text">${news.body}</p>
        <img src="${pageContext.request.contextPath}/NewsPhotos?newsid=${news.id}" class="img-fluid" alt="">
        <c:if test="${pageContext.request.isUserInRole('ADMIN') || (pageContext.request.isUserInRole('RESEARCH') && (pageContext.request.getRemoteUser() eq news.email))}">
          <form method="POST" action="${pageContext.request.contextPath}/NewsDelete">
            <input type="hidden" name="newsId" value="${news.id}">
            <button type="submit" class="btn btn-danger">Delete</button>
          </form>
        </c:if>
      </div>
    </div>
  </c:forEach>
</t:pageTemplate>
