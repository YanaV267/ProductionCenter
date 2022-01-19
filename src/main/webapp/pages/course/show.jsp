<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/general.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/show.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="get" action="${pageContext.request.contextPath}/controller?command=search_courses">
        <p id="title"><fmt:message key="courses.title"/></p>
        <div id="layout">
            <div id="search">
                <select name="category"
                        onchange="location.href='${pageContext.request.contextPath}/controller?command=go_to_show_course'">
                    <option disabled selected><fmt:message key="activities.category"/> --</option>
                    <c:forEach var="category" items="${categories}">
                        <option>${category}</option>
                    </c:forEach>
                </select>
                <select name="type">
                    <option disabled selected><fmt:message key="activities.type"/> --</option>
                    <c:forEach var="activity" items="${activities}">
                        <option>${activity.type}</option>
                    </c:forEach>
                </select>
                <p><fmt:message key="courses.weekdays"/>:</p>
                <div id="weekdays">
                    <label><input type="checkbox" value="<fmt:message key="timetable.monday"/>">
                        <fmt:message key="courses.monday"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.tuesday"/>">
                        <fmt:message key="courses.tuesday"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.wednesday"/>">
                        <fmt:message key="courses.wednesday"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.thursday"/>">
                        <fmt:message key="courses.thursday"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.friday"/>">
                        <fmt:message key="courses.friday"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.saturday"/>">
                        <fmt:message key="courses.saturday"/></label>
                </div>
                <div id="buttons">
                    <input type="submit" value="<fmt:message key="courses.search"/>">
                    <input type="submit" value="<fmt:message key="courses.info"/>" disabled>
                    <input type="hidden" name="command" value="go_to_course_info">
                    <c:choose>
                        <c:when test="${sessionScope.role == 'admin'}">
                            <input type="button" value="<fmt:message key="courses.add"/>"
                                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_add_course'">
                            <input type="button" value="<fmt:message key="courses.update"/>" disabled
                                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_update_course'">
                        </c:when>
                        <c:otherwise>
                            <input type="button" value="<fmt:message key="courses.enroll"/>" disabled
                                   onclick="location.href=`${pageContext.request.contextPath}/controller?command=go_to_enroll_on_course&&chosen_course_id=`+document.querySelector('[name=chosen_course_id]').value">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${courses.size() > 0}">
                <div id="all">
                    <div><fmt:message key="activities.type"/></div>
                    <input type="hidden" name="chosen_course_id">
                    <div><fmt:message key="courses.teacher"/></div>
                    <div><fmt:message key="courses.age_group"/></div>
                    <div><fmt:message key="courses.lesson_price"/></div>
                    <c:forEach var="course" items="${courses}">
                        <div><c:out value="${course.activity.type}"/></div>
                        <input type="hidden" name="course_id" value="<c:out value="${course.id}"/>">
                        <div><c:out value="${course.teacher.surname}"/> <c:out value="${course.teacher.name}"/></div>
                        <div><c:out value="${course.ageGroup.minAge}"/>-<c:out value="${course.ageGroup.maxAge}"/>
                            <fmt:message key="courses.age.title"/></div>
                        <div><c:out value="${course.lessonPrice}"/>р</div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${courses.size() == 0}">
                <div id="none"><fmt:message key="courses.message"/></div>
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