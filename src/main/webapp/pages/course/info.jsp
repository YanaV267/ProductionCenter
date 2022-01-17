<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/account/account.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/info.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${rb}"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="courses.info.title" bundle="${rb}"/> "${course.activity.type}"</p>
        <div>
            <div>
                <c:choose>
                    <c:when test="${not empty picture}">
                        <img src="${picture}" alt="account">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="data">
                <div><fmt:message key="courses.teacher" bundle="${rb}"/>: ${teacher.surname} ${teacher.name}</div>
                <div>
                    <div id="icons">
                        <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                        <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                    </div>
                    <div>
                        <div>${teacher.email}</div>
                        <div>${number}</div>
                    </div>
                </div>
            </div>
            <div>
                <div><fmt:message key="courses.age_group" bundle="${rb}"/>: ${course.ageGroup.minAge}-
                    ${course.ageGroup.maxAge} <fmt:message key="courses.age.title" bundle="${rb}"/></div>
                <div><fmt:message key="courses.lesson_price" bundle="${rb}"/>: ${course.lessonPrice}р</div>
            </div>
            <div>
                <div><fmt:message key="courses.description" bundle="${rb}"/>:</div>
                <c:choose>
                    <c:when test="${not empty course.description}">
                        <div>${description}</div>
                    </c:when>
                    <c:otherwise>
                        <div><fmt:message key="courses.description" bundle="${rb}"/></div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="timetable">
                <div><fmt:message key="courses.timetable" bundle="${rb}"/>:</div>
                <c:forEach var="lesson" items="${lessons}">
                    <div>${lesson.weekDay}: ${lesson.startTime} (${lesson.duration}мин)</div>
                </c:forEach>
            </div>
            <div>
                <input type="submit" value="<fmt:message key="courses.enroll" bundle="${rb}"/>">
                <input type="button" value="<fmt:message key="courses.back" bundle="${rb}"/>">
            </div>
        </div>
    </form>
</main>
</body>
</html>
