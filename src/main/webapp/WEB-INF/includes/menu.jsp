<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
  <!-- Bootstrap Navigation Bar -->
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark p-1 mb-2">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">AS</a>

    <!-- Navbar Toggler for Responsive Design -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Navbar Items Container -->
    <div class="collapse navbar-collapse" id="navbarCollapse">
      <!-- Navbar Left (First) Section -->
      <ul class="navbar-nav me-auto mb-2 mb-md-0">
        <!-- News Link -->
        <li class="nav-item">
          <a class="nav-link ${activePage eq 'News' ? ' active ' : ''}" aria-current="page" href="${pageContext.request.contextPath}/News">News</a>
        </li>

        <c:if test="${pageContext.request.getRemoteUser() != null && (pageContext.request.isUserInRole('RESEARCH') || pageContext.request.isUserInRole('ADMIN') || pageContext.request.isUserInRole('COMMON'))}">
          <!-- Teams Link (visible only for certain roles) -->
          <li class="nav-item">
            <a class="nav-link ${activePage eq 'Teams' ? ' active ' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Teams">Teams</a>
          </li>
        </c:if>

        <!-- About Link -->
        <li class="nav-item">
          <a class="nav-link ${activePage eq 'About' ? ' active ' : ''}" aria-current="page" href="${pageContext.request.contextPath}/About">About</a>
        </li>
      </ul>

      <!-- Navbar Right (Second) Section -->
      <ul class="navbar-nav">

        <!-- Login and Register Links (visible if user is not authenticated) -->
        <li class="nav-item">
          <c:choose>
            <c:when test="${pageContext.request.getRemoteUser() == null}">
              <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/Register">Register</a>
            </li>
            </c:when>
            <!-- Usergroups and Logout Links (visible if user is authenticated) -->
            <c:otherwise>
              <c:if test = "${pageContext.request.isUserInRole('RESEARCH') || pageContext.request.isUserInRole('ADMIN')}">
                <a class="nav-link ${activePage eq 'Usergroups' ? ' active ' : ''}" href="${pageContext.request.contextPath}/Usergroups">Roles</a>
                </li>
                <li class="nav-item">
              </c:if>
                <!-- Logout Link -->
              <a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a>
            </c:otherwise>
          </c:choose>
        </li>
      </ul>
    </div>
  </div>
</nav>
</header>