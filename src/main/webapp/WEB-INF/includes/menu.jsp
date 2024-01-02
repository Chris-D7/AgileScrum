<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark p-1 mb-2">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">AS</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
      <ul class="navbar-nav me-auto mb-2 mb-md-0">
        <li class="nav-item">
          <a class="nav-link ${activePage eq 'About' ? ' active ' : ''}" aria-current="page" href="${pageContext.request.contextPath}/About">About</a>
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item">
          <c:choose>
            <c:when test="${pageContext.request.getRemoteUser() == null}">
              <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/Register">Register</a>
            </li>
            </c:when>
            <c:otherwise>
              <c:if test = "${pageContext.request.isUserInRole('RESEARCH') || pageContext.request.isUserInRole('ADMIN')}">
                <a class="nav-link" href="${pageContext.request.contextPath}/Usergroups">Roles</a>
                </li>
                <li class="nav-item">
              </c:if>
              <a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a>
            </c:otherwise>
          </c:choose>
        </li>
      </ul>
    </div>
  </div>
</nav>
</header>