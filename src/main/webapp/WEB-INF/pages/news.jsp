<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:pageTemplate pageTitle="About">
  <h1>NEWS</h1>

  <c:if test="${pageContext.request.isUserInRole('RESEARCH') || pageContext.request.isUserInRole('ADMIN')}">
    <form method="POST" action="${pageContext.request.contextPath}/News">
      <div class="form-group">
        <label for="newsTitle">Title</label>
        <input type="text" class="form-control" id="newsTitle" name="newsTitle" required>
      </div>

      <div class="form-group">
        <label for="newsBody">Body</label>
        <textarea class="form-control" id="newsBody" name="newsBody" rows="5" required></textarea>
      </div>

      <input type="hidden" name="newsAuthor" value="${pageContext.request.getRemoteUser()}">

      <button type="submit" class="btn btn-primary">Post</button>
    </form>
  </c:if>

  <c:forEach var="news" items="${newsList}">
    <div class="card mb-4">
      <div class="card-body">
        <h3 class="card-title">${news.title}</h3>
        <p class="card-subtitle mb-2 text-muted">Author: ${news.author}</p>
        <p class="card-subtitle mb-2 text-muted">Date Posted: ${news.getDatePostedFormatted()}</p>
        <p class="card-text">${news.body}</p>
        <img src="${news.image}" class="img-fluid">
      </div>
    </div>
  </c:forEach>
</t:pageTemplate>
