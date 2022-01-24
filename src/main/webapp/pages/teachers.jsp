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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/teachers.css" type="text/css"/>
</head>

<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=change_user_role">
        <p id="title"><fmt:message key="teachers.title"/></p>
        <c:if test="${fn:length(requestScope.users) == 0}">
            <div id="none"><fmt:message key="teachers.message"/></div>
        </c:if>
        <c:if test="${fn:length(requestScope.users) > 0}">
            <div id="all">
                <div><fmt:message key="teachers.login"/></div>
                <input type="hidden">
                <input type="hidden">
                <div><fmt:message key="teachers.name"/></div>
                <div><fmt:message key="teachers.email"/></div>
                <div><fmt:message key="teachers.phone_number"/></div>
                <div><fmt:message key="teachers.assignment"/></div>
                <c:forEach begin="${requestScope.page * 15 - 15}" end="${requestScope.page * 15}" var="user"
                           items="${requestScope.users}">
                    <div><c:out value="${user.key.login}"/></div>
                    <input type="hidden" name="login" value="<c:out value="${user.key.login}"/>">
                    <input type="hidden" name="role" value="<c:out value="${user.key.userRole.role}"/>">
                    <div><c:out value="${user.key.surname}"/> <c:out value="${user.key.name}"/></div>
                    <div><c:out value="${user.key.email}"/></div>
                    <div><c:out value="${user.value}"/></div>
                    <div>
                        <label class="switch">
                            <input type="checkbox"
                                   <c:if test="${user.key.userRole.role eq 'teacher'}">checked </c:if>>
                            <span class="slider round"></span>
                        </label>
                    </div>
                </c:forEach>
            </div>
            <ctg:pages page="${requestScope.page}" size="${fn:length(requestScope.users)}" command="go_to_teachers"/>
        </c:if>
        <input type="submit" value="<fmt:message key="teachers.save"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/teachers.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
<jsp:include page="main/footer.jsp"/>
</html>