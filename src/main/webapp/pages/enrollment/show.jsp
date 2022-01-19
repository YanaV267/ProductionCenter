<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/enrollment/show.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=change_enrollment_status">
        <p id="title"><fmt:message key="enrollment.all.title"/></p>
        <c:if test="${enrollments.size() > 0}">
            <div id="all">
                <div><fmt:message key="enrollment.name"/></div>
                <input type="hidden">
                <input type="hidden">
                <div><fmt:message key="enrollment.activity_type"/></div>
                <div><fmt:message key="enrollment.all.lesson_amount"/></div>
                <div><fmt:message key="enrollment.reservation_date"/></div>
                <div><fmt:message key="enrollment.status"/></div>
                <div><fmt:message key="enrollment.approval"/></div>
                <c:forEach var="enrollment" items="${enrollments}">
                    <div><c:out value="${enrollment.key.user.surname}"/> <c:out
                            value="${enrollment.key.user.name}"/></div>
                    <input type="hidden" name="enrollment_id" value="${enrollment.key.id}">
                    <input type="hidden" name="status" value="${enrollment.key.enrollmentStatus.status}">
                    <div><c:out value="${enrollment.key.course.activity.type}"/></div>
                    <div><c:out value="${enrollment.key.lessonAmount}"/></div>
                    <div><c:out value="${enrollment.value}"/></div>
                    <div id="status"><c:out value="${enrollment.key.enrollmentStatus.status}"/></div>
                    <div>
                        <label class="switch">
                            <input type="checkbox"
                                   <c:if test="${enrollment.key.enrollmentStatus.status eq 'approved'}">checked </c:if>
                            <c:choose>
                                   <c:when test="${enrollment.key.enrollmentStatus.status eq 'reserved'}">disabled </c:when>
                                   <c:when test="${enrollment.key.enrollmentStatus.status eq 'expired'}">disabled </c:when>
                            </c:choose>>
                            <span class="slider round"></span>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${enrollments.size() == 0}">
            <div id="none"><fmt:message key="enrollment.message"/></div>
        </c:if>
        <input type="submit" value="<fmt:message key="enrollment.save"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/users.js"></script>
<script src="${pageContext.request.contextPath}/script/enrollment.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
</html>