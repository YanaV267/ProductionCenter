<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/enrollment/enrollment.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=enroll_on_course">
        <p id="title"><fmt:message key="enrollment.title"/></p>
        <div>
            <div>
                <div>
                    <div><fmt:message key="enrollment.activity_type"/>:</div>
                    <div><fmt:message key="courses.teacher"/>:</div>
                    <div><fmt:message key="courses.age_group"/>:</div>
                </div>
                <div>
                    <div>${course.activity.type}</div>
                    <div>${course.teacher.surname} ${course.teacher.name}</div>
                    <div>${course.ageGroup.minAge}-${course.ageGroup.maxAge} <fmt:message
                            key="courses.age.title"/></div>
                </div>
            </div>
            <div>
                <div>
                    <div><fmt:message key="courses.lesson_price"/>:</div>
                    <div><fmt:message key="enrollment.total_price"/>:</div>
                </div>
                <div>
                    <div id="price">${course.lessonPrice}р</div>
                    <div id="total"></div>
                </div>
            </div>
            <div id="timetable">
                <div><fmt:message key="courses.timetable"/>:</div>
                <c:forEach var="lesson" items="${lessons}">
                    <div>${lesson.weekDay}: ${lesson.startTime} (${lesson.duration}мин)</div>
                </c:forEach>
            </div>
            <div id="amount">
                <div><fmt:message key="enrollment.lesson_amount"/>:</div>
                <input type="number" name="lesson_amount" min="1" max="20">
            </div>
            <div>
                <input type="submit" value="<fmt:message key="courses.enroll"/>">
                <input type="button" value="<fmt:message key="courses.back"/>"
                       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_courses'">
                <input type="hidden" name="chosen_course_id" value="${course.id}">
            </div>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/enrollment.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>
</body>
</html>
