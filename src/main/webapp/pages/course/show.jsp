<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/general.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/show.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${rb}"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="get" action="${pageContext.request.contextPath}/controller?command=search_courses">
        <p id="title"><fmt:message key="courses.title" bundle="${rb}"/></p>
        <div id="layout">
            <div id="search">
                <select name="category"
                        onchange="location.href='${pageContext.request.contextPath}/controller?command=go_to_show_course'">
                    <option disabled selected><fmt:message key="activities.category" bundle="${rb}"/> --</option>
                    <c:forEach var="category" items="${categories}">
                        <option>${category}</option>
                    </c:forEach>
                </select>
                <select name="type">
                    <option disabled selected><fmt:message key="activities.type" bundle="${rb}"/> --</option>
                    <c:forEach var="activity" items="${activities}">
                        <option>${activity.type}</option>
                    </c:forEach>
                </select>
                <p><fmt:message key="courses.weekdays" bundle="${rb}"/>:</p>
                <div id="weekdays">
                    <label><input type="checkbox" value="<fmt:message key="timetable.monday" bundle="${rb}"/>">
                        <fmt:message key="courses.monday" bundle="${rb}"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.tuesday" bundle="${rb}"/>">
                        <fmt:message key="courses.tuesday" bundle="${rb}"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.wednesday" bundle="${rb}"/>">
                        <fmt:message key="courses.wednesday" bundle="${rb}"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.thursday" bundle="${rb}"/>">
                        <fmt:message key="courses.thursday" bundle="${rb}"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.friday" bundle="${rb}"/>">
                        <fmt:message key="courses.friday" bundle="${rb}"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.saturday" bundle="${rb}"/>">
                        <fmt:message key="courses.saturday" bundle="${rb}"/></label>
                </div>
                <div id="buttons">
                    <input type="submit" value="<fmt:message key="courses.search" bundle="${rb}"/>">
                    <input type="submit" value="<fmt:message key="courses.info" bundle="${rb}"/>" disabled>
                    <input type="hidden" name="command" value="go_to_course_info">
                    <input type="hidden" name="chosen_type">
                    <input type="hidden" name="chosen_teacher">
                    <input type="hidden" name="chosen_age_group">
                    <c:choose>
                        <c:when test="${sessionScope.role == 'admin'}">
                            <input type="button" value="<fmt:message key="courses.add" bundle="${rb}"/>"
                                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_add_course'">
                            <input type="button" value="<fmt:message key="courses.update" bundle="${rb}"/>" disabled
                                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_update_course'">
                        </c:when>
                        <c:otherwise>
                            <input type="button" value="<fmt:message key="courses.enroll" bundle="${rb}"/>" disabled
                                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_enroll_on_course'">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${courses.size() > 0}">
                <div id="all">
                    <div><fmt:message key="activities.type" bundle="${rb}"/></div>
                    <div><fmt:message key="courses.teacher" bundle="${rb}"/></div>
                    <div><fmt:message key="courses.age_group" bundle="${rb}"/></div>
                    <div><fmt:message key="courses.lesson_price" bundle="${rb}"/></div>
                    <c:forEach var="course" items="${courses}">
                        <div><c:out value="${course.activity.type}"/></div>
                        <div><c:out value="${course.teacher.surname}"/> ${course.teacher.name}</div>
                        <div><c:out value="${course.ageGroup.minAge}"/>-<c:out value="${course.ageGroup.maxAge}"/> <fmt:message key="courses.age.title" bundle="${rb}"/></div>
                        <div><c:out value="${course.lessonPrice}"/>р</div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${courses.size() == 0}">
                <div id="none"><fmt:message key="courses.message" bundle="${rb}"/></div>
            </c:if>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/courses.js"></script>
<c:if test="${not empty message}">
    <jsp:include page="../modal.jsp"/>
</c:if>
</body>
</html>