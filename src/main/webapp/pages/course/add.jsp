<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/general.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/add.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=add_course">
        <p id="title"><fmt:message key="courses.add.title"/></p>
        <div id="courseProps">
            <div>
<%--                TODO:переключение чтобы не было сброса данных--%>
                <select name="category" onchange="location.href=
                        '${pageContext.request.contextPath}/controller?command=go_to_add_course&category='
                        + this.options[this.selectedIndex].value">
                    <option disabled <c:if test="${empty selected_category}">selected</c:if>>
                        <fmt:message key="activities.category"/> --
                    </option>
                    <c:forEach var="category" items="${categories}">
                        <option <c:if test="${category eq selected_category}">selected</c:if>>
                                ${category}</option>
                    </c:forEach>
                </select>
                <select name="type">
                    <option disabled selected><fmt:message key="activities.type"/> --</option>
                    <c:forEach var="activity" items="${activities}">
                        <option>${activity.type}</option>
                    </c:forEach>
                </select>
                <select name="teacher">
                    <option disabled selected><fmt:message key="courses.teacher"/> --</option>
                    <c:forEach var="teacher" items="${teachers}">
                        <option>${teacher.surname} ${teacher.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <p><fmt:message key="courses.weekdays"/>:</p>
                <div id="weekdays">
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.monday"/>">
                        <fmt:message key="courses.monday"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.tuesday"/>">
                        <fmt:message key="courses.tuesday"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.wednesday"/>">
                        <fmt:message key="courses.wednesday"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.thursday"/>">
                        <fmt:message key="courses.thursday"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.friday"/>">
                        <fmt:message key="courses.friday"/></label>
                    <label><input type="checkbox" name="weekdays"
                                  value="<fmt:message key="timetable.saturday"/>">
                        <fmt:message key="courses.saturday"/></label>
                </div>
                <div id="time">
                    <div>
                        <p><fmt:message key="courses.start_time"/>:</p>
                        <input type="time" name="time">
                    </div>
                    <div>
                        <p><fmt:message key="courses.duration"/>:</p>
                        <input type="number" name="duration" min="30" max="120" step="15">
                    </div>
                </div>
            </div>
            <div>
                <p><fmt:message key="courses.age"/>:</p>
                <div id="age">
                    <input type="number" name="min_age" min=3 max=60 placeholder="min">
                    <input type="number" name="max_age" min=3 max=60 placeholder="max">
                </div>
                <p><fmt:message key="courses.lesson_price"/>:</p>
                <input type="text" name="lesson_price" min="1" max="30" pattern="^((\d\d\.)?\d{1,2})$">
                <p><fmt:message key="courses.student_amount"/>:</p>
                <input type="number" name="student_amount" min="1" max="30" step="1">
            </div>
        </div>
        <textarea name="description" placeholder="<fmt:message key="courses.description"/>"></textarea>
        <input type="submit" value="<fmt:message key="courses.submit"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/courses.js"></script>
<c:if test="${not empty message}">
    <jsp:include page="../modal.jsp"/>
</c:if>
</body>
</html>