<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/timetable.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="timetable.title"/></p>
        <c:forEach var="weekday" items="${weekdays}">
            <div>
                <div><fmt:message key="timetable.${weekday}"/></div>
                <div>
                    <c:set var="lessons_today" value="false"/>
                    <c:forEach var="enrollment" items="${enrollments}">
                        <c:forEach var="lesson" items="${enrollment.course.lessons}">
                            <c:if test="${lesson.weekDay eq weekday}">
                                <div>
                                    <div>${lesson.startTime}</div>
                                    <div>${enrollment.course.activity.type}</div>
                                    <c:set var="lessons_today" value="true"/>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    <c:if test="${lessons_today eq 'false'}">
                        <fmt:message key="timetable.no_lessons"/>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
</html>