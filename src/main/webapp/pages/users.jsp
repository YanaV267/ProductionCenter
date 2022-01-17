<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${rb}"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=change_user_status">
        <p id="title"><fmt:message key="users.title" bundle="${rb}"/></p>
        <c:if test="${users.size() > 0}">
            <div id="all">
                <div><fmt:message key="users.login" bundle="${rb}"/></div>
                <input type="hidden" name="login" value="${login}">
                <input type="hidden" name="status">
                <div><fmt:message key="users.name" bundle="${rb}"/></div>
                <div><fmt:message key="users.email" bundle="${rb}"/></div>
                <div><fmt:message key="users.phone_number" bundle="${rb}"/></div>
                <div><fmt:message key="users.status" bundle="${rb}"/></div>
                <c:forEach var="user" items="${users}">
                    <div><c:out value="${user.login}"/></div>
                    <input type="hidden" name="login" value="${user.login}">
                    <input type="hidden" name="status" value="${user.userStatus.status}">
                    <div><c:out value="${user.surname}"/> <c:out value="${user.name}"/></div>
                    <div><c:out value="${user.email}"/></div>
                    <c:forEach var="user_number" items="${users_numbers}">
                        <c:if test="${user_number.key eq user.login}">
                            <div><c:out value="${user_number.value}"/></div>
                        </c:if>
                    </c:forEach>
                    <div>
                        <label class="switch">
                            <input type="checkbox" <c:if test="${user.userStatus.status eq 'blocked'}">checked </c:if>>
                            <span class="slider round"></span>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${users.size() == 0}">
            <div id="none"><fmt:message key="users.message" bundle="${rb}"/></div>
        </c:if>
        <input type="submit" value="<fmt:message key="users.save" bundle="${rb}"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/users.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
</html>