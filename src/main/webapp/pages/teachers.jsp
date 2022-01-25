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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/teachers.css" type="text/css"/>
</head>

<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=search_teachers">
        <p id="title"><fmt:message key="teachers.title"/></p>
        <div id="search">
            <input type="text" name="surname" placeholder="<fmt:message key="sign_up.surname.placeholder"/>"
                   autocomplete="off" pattern="[А-ЯA-Z][а-яa-z]{1,20}"
                   value="<c:out value="${requestScope.teacher.surname}"/>"
                   title="<fmt:message key="sign_up.surname.title"/>"><br/>
            <input type="text" name="name" disabled
                   placeholder="<fmt:message key="sign_up.name.placeholder"/>" autocomplete="off"
                   pattern="[А-ЯA-Z][а-яёa-z]{1,15}" value="<c:out value="${requestScope.teacher.name}"/>"
                   title="<fmt:message key="sign_up.name.title"/>"><br/>
            <div id="status">
                <label>
                    <input type="checkbox" name="status" value="assigned"
                           <c:if test="${not empty requestScope.teacher.status}">checked</c:if>>
                    <fmt:message key="teachers.assigned_on_course"/>
                </label>
            </div>
            <input type="button" name="clear" value="<fmt:message key="courses.clear"/>"
                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_teachers'">
            <input type="submit" value="<fmt:message key="courses.search"/>">
        </div>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=change_user_role">
        <c:if test="${fn:length(users) == 0}">
            <div id="none"><fmt:message key="teachers.message"/></div>
        </c:if>
        <c:if test="${fn:length(users) > 0}">
            <div id="all">
                <div><fmt:message key="teachers.login"/></div>
                <input type="hidden">
                <input type="hidden">
                <div><fmt:message key="teachers.name"/></div>
                <div><fmt:message key="teachers.email"/></div>
                <div><fmt:message key="teachers.phone_number"/></div>
                <div><fmt:message key="teachers.assignment"/></div>
                <c:forEach begin="${page * 15 - 15}" end="${page * 15}" var="user" items="${users}">
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
            <c:choose>
                <c:when test="${not empty user}">
                    <ctg:pages page="${page}" size="${fn:length(users)}" command="go_to_teachers"/>
                </c:when>
                <c:otherwise>
                    <ctg:pages page="${page}" size="${fn:length(users)}" command="search_teachers"/>
                </c:otherwise>
            </c:choose>
        </c:if>
        <input type="submit" value="<fmt:message key="teachers.save"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/teachers.js"></script>
</body>
<jsp:include page="main/footer.jsp"/>
</html>