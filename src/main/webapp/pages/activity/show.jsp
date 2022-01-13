<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/activity/show.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${ rb }"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="activities.title" bundle="${ rb }"/></p>
        <c:if test="${sessionScope.role == 'admin'}">
            <input type="button" value="<fmt:message key="activities.add" bundle="${ rb }"/>"
                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_add_activity'">
        </c:if>
        <c:forEach var="category" items="${categories}">
            <div>
                <p class="subtitle">${category}</p>
                <div id="header">
                    <p></p>
                    <p><fmt:message key="activities.teacher" bundle="${ rb }"/></p>
                    <p><fmt:message key="activities.type" bundle="${ rb }"/></p>
                </div>
                <c:forEach var="activity" items="${activities}">
                    <c:if test="${activity.category eq category}">
                        <div>
                                <%--                            TODO:конкретное фото--%>
                            <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
                            <c:set var="teacher_appointed" value="false"/>
                            <c:forEach var="course" items="${courses}">
                                <c:choose>
                                    <c:when test="${course.activity.category eq category && course.activity.type eq activity.type}">
                                        <p>${course.teacher.surname} ${course.teacher.name}</p>
                                        <c:set var="teacher_appointed" value="true"/>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${teacher_appointed eq 'false'}">
                                <p><fmt:message key="activities.no_teacher" bundle="${ rb }"/></p>
                            </c:if>
                            <p>${activity.type}</p>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
        <c:if test="${categories.size() == 0}">
            <div><fmt:message key="activities.message" bundle="${ rb }"/></div>
        </c:if>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
<c:if test="${not empty message}">
    <jsp:include page="../modal.jsp"/>
</c:if>
</body>
</html>