<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/general.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/add.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${rb}"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=add_course">
        <p id="title"><fmt:message key="courses.add.title" bundle="${rb}"/></p>
        <div id="courseProps">
            <div>
<%--                TODO:переключение чтобы не было сброса данных--%>
                <select name="category" onchange="location.href=
                        '${pageContext.request.contextPath}/controller?command=go_to_add_course&category='
                        + this.options[this.selectedIndex].value">
                    <option disabled <c:if test="${empty selected_category}">selected</c:if>>
                        <fmt:message key="activities.category" bundle="${rb}"/> --
                    </option>
                    <c:forEach var="category" items="${categories}">
                        <option <c:if test="${category eq selected_category}">selected</c:if>>
                                ${category}</option>
                    </c:forEach>
                </select>
                <select name="type">
                    <option disabled selected><fmt:message key="activities.type" bundle="${rb}"/> --</option>
                    <c:forEach var="activity" items="${activities}">
                        <option>${activity.type}</option>
                    </c:forEach>
                </select>
                <select name="teacher">
                    <option disabled selected><fmt:message key="courses.teacher" bundle="${rb}"/> --</option>
                    <c:forEach var="teacher" items="${teachers}">
                        <option>${teacher.surname} ${teacher.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <p><fmt:message key="courses.weekdays" bundle="${rb}"/>:</p>
                <div id="weekdays">
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.monday" bundle="${rb}"/>">
                        <fmt:message key="courses.monday" bundle="${rb}"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.tuesday" bundle="${rb}"/>">
                        <fmt:message key="courses.tuesday" bundle="${rb}"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.wednesday" bundle="${rb}"/>">
                        <fmt:message key="courses.wednesday" bundle="${rb}"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.thursday" bundle="${rb}"/>">
                        <fmt:message key="courses.thursday" bundle="${rb}"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.friday" bundle="${rb}"/>">
                        <fmt:message key="courses.friday" bundle="${rb}"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.saturday" bundle="${rb}"/>">
                        <fmt:message key="courses.saturday" bundle="${rb}"/></label>
                </div>
                <div id="time">
                    <div>
                        <p><fmt:message key="courses.start_time" bundle="${rb}"/>:</p>
                        <input type="time" name="time">
                    </div>
                    <div>
                        <p><fmt:message key="courses.duration" bundle="${rb}"/>:</p>
                        <input type="number" name="duration" min="30" max="120" step="15">
                    </div>
                </div>
            </div>
            <div>
                <p><fmt:message key="courses.age" bundle="${rb}"/>:</p>
                <div id="age">
                    <input type="number" name="min_age" min=3 max=60 placeholder="min">
                    <input type="number" name="max_age" min=3 max=60 placeholder="max">
                </div>
                <p><fmt:message key="courses.lesson_price" bundle="${rb}"/>:</p>
                <input type="text" name="lesson_price" min="1" max="30">
                <p><fmt:message key="courses.student_amount" bundle="${rb}"/>:</p>
                <input type="number" name="student_amount" min="1" max="30" step="1">
            </div>
        </div>
        <textarea name="description" placeholder="<fmt:message key="courses.description" bundle="${rb}"/>"></textarea>
        <input type="submit" value="<fmt:message key="courses.submit" bundle="${rb}"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/courses.js"></script>
<c:if test="${not empty message}">
    <jsp:include page="../modal.jsp"/>
</c:if>
</body>
</html>