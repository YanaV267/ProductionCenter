<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="custom_tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_show.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css" type="text/css"/>
</head>

<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=search_users">
        <p id="title"><fmt:message key="users.title"/></p>
        <div id="search">
            <input type="text" name="surname" placeholder="<fmt:message key="sign_up.surname.placeholder"/>"
                   autocomplete="off" value="<c:out value="${requestScope.user.surname}"/>"><br/>
            <input type="text" name="name" disabled
                   placeholder="<fmt:message key="sign_up.name.placeholder"/>" autocomplete="off"
                   value="<c:out value="${requestScope.user.name}"/>"><br/>
            <div id="status">
                <label>
                    <input type="checkbox" name="status" value="active"
                           <c:if test="${requestScope.user.status eq 'active'}">checked</c:if>>
                    <fmt:message key="users.status.active"/>
                </label>
                <label>
                    <input type="checkbox" name="status" value="blocked"
                           <c:if test="${requestScope.user.status eq 'blocked'}">checked</c:if>>
                    <fmt:message key="users.status.blocked"/>
                </label>
            </div>
            <input type="button" name="clear" value="<fmt:message key="courses.clear"/>"
                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_users'">
            <input type="submit" value="<fmt:message key="courses.search"/>">
        </div>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=change_user_status">
        <c:if test="${fn:length(users) > 0}">
            <div id="all">
                <div><fmt:message key="users.login"/></div>
                <input type="hidden">
                <input type="hidden">
                <div><fmt:message key="users.name"/></div>
                <div><fmt:message key="users.email"/></div>
                <div><fmt:message key="users.phone_number"/></div>
                <div><fmt:message key="users.status"/></div>
                <c:forEach var="user" items="${users}">
                    <div><c:out value="${user.key.login}"/></div>
                    <input type="hidden" name="login" value="<c:out value="${user.key.login}"/>">
                    <input type="hidden" name="current_status" value="<c:out value="${user.key.userStatus.status}"/>">
                    <div><c:out value="${user.key.surname}"/> <c:out value="${user.key.name}"/></div>
                    <div><c:out value="${user.key.email}"/></div>
                    <div><c:out value="${user.value}"/></div>
                    <div>
                        <label class="switch">
                            <input type="checkbox"
                                   <c:if test="${user.key.userStatus.status eq 'blocked'}">checked </c:if>>
                            <span class="slider round"></span>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${fn:length(users) == 0}">
            <div id="none"><fmt:message key="users.message"/></div>
        </c:if>
        <c:choose>
            <c:when test="${not empty user}">
                <ctg:pages page="${page}" last="${last}" command="search_users"/>
            </c:when>
            <c:otherwise>
                <ctg:pages page="${page}" last="${last}" command="go_to_users"/>
            </c:otherwise>
        </c:choose>
        <input type="submit" value="<fmt:message key="users.save"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/users.js"></script>
</body>
<jsp:include page="main/footer.jsp"/>
</html>