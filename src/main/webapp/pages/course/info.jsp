<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/account/account.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/info.css" type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=go_to_enroll_on_course">
        <p id="title"><fmt:message key="courses.info.title"/> "<c:out value="${course.activity.type}"/>"</p>
        <div>
            <div>
                <c:choose>
                    <c:when test="${not empty picture}">
                        <img src="<c:out value="${picture}"/>" alt="account">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="data">
                <div><fmt:message key="courses.teacher"/>: <c:out value="${course.teacher.surname}"/>
                    <c:out value="${course.teacher.name}"/></div>
                <div>
                    <div id="icons">
                        <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                        <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                    </div>
                    <div>
                        <div><c:out value="${course.teacher.email}"/></div>
                        <div><c:out value="${number}"/></div>
                    </div>
                </div>
            </div>
            <div>
                <div><fmt:message key="courses.age_group"/>: <c:out value="${course.ageGroup.minAge}"/>-
                    <c:out value="${course.ageGroup.maxAge}"/>
                    <fmt:message key="courses.age.title"/></div>
                <div><fmt:message key="courses.lesson_price"/>: <c:out value="${course.lessonPrice}"/>р</div>
            </div>
            <div>
                <div><fmt:message key="courses.description"/>:</div>
                <c:choose>
                    <c:when test="${not empty course.description}">
                        <div><c:out value="${description}"/></div>
                    </c:when>
                    <c:otherwise>
                        <div><fmt:message key="courses.no_description"/></div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="timetable">
                <div><fmt:message key="courses.timetable"/>:</div>
                <c:forEach var="lesson" items="${lessons}">
                    <div><fmt:message key="timetable.${lesson.weekDay}"/>: <c:out value="${lesson.startTime}"/>
                        (<c:out value="${lesson.duration}"/>мин)
                    </div>
                </c:forEach>
            </div>
            <div>
                <input type="hidden" name="chosen_course_id" value="<c:out value="${course.id}"/>">
                <input type="submit" value="<fmt:message key="courses.enroll"/>">
                <input type="button" value="<fmt:message key="courses.back"/>"
                       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_courses'">
            </div>
        </div>
    </form>
</main>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>
