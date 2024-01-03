<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="About">
    <h1>ABOUT</h1>
    <c:choose>
        <c:when test = "${pageContext.request.isUserInRole('ADMIN')}">
        <form method="POST" action="${pageContext.request.contextPath}/About">
            <div class="form-group">
                <textarea class="form-control" id="editedAboutText" name="editedAboutText">${aboutText}</textarea>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
        </c:when>
        <c:otherwise>
            <div class="container-fluid text-wrap text-break">
                <p>${aboutText}</p>
            </div>
        </c:otherwise>
    </c:choose>
</t:pageTemplate>