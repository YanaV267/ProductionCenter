<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="custom_tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css" type="text/css"/>
</head>

<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=change_user_status">
        <p id="title"><fmt:message key="users.title"/></p>
        <c:if test="${fn:length(users) > 0}">
            <div id="all">
                <div><fmt:message key="users.login"/></div>
                <input type="hidden">
                <input type="hidden">
                <div><fmt:message key="users.name"/></div>
                <div><fmt:message key="users.email"/></div>
                <div><fmt:message key="users.phone_number"/></div>
                <div><fmt:message key="users.status"/></div>
                <c:forEach begin="${page * 15 - 15}" end="${page * 15}" var="user" items="${users}">
                    <div><c:out value="${user.key.login}"/></div>
                    <input type="hidden" name="login" value="<c:out value="${user.key.login}"/>">
                    <input type="hidden" name="status" value="<c:out value="${user.key.userStatus.status}"/>">
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
        <ctg:pages page="${page}" size="${fn:length(users)}" command="go_to_users"/>
        <input type="submit" value="<fmt:message key="users.save"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/users.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
<jsp:include page="main/footer.jsp"/>
</html>