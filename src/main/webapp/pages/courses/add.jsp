<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/courses/general.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/courses/add.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${ rb }"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title">Добавление занятий</p>
        <div id="courseProps">
            <div>
                <select>
                    <option disabled selected><fmt:message key="courses.category" bundle="${ rb }"/> --</option>
                    <option>Вокальное</option>
                    <option>Хореографическое</option>
                    <option>Инструментальное</option>
                </select>
                <select>
                    <option disabled selected><fmt:message key="courses.type" bundle="${ rb }"/> --</option>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                </select>
                <select>
                    <option disabled selected><fmt:message key="courses.teacher" bundle="${ rb }"/> --</option>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                </select>
            </div>
            <div>
                <p><fmt:message key="courses.weekdays" bundle="${ rb }"/>:</p>
                <div id="weekdays">
                    <label><input type="checkbox" value="<fmt:message key="timetable.monday" bundle="${ rb }"/>">
                        <fmt:message key="courses.monday" bundle="${ rb }"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.tuesday" bundle="${ rb }"/>">
                        <fmt:message key="courses.tuesday" bundle="${ rb }"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.wednesday" bundle="${ rb }"/>">
                        <fmt:message key="courses.wednesday" bundle="${ rb }"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.thursday" bundle="${ rb }"/>">
                        <fmt:message key="courses.thursday" bundle="${ rb }"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.friday" bundle="${ rb }"/>">
                        <fmt:message key="courses.friday" bundle="${ rb }"/></label>
                    <label><input type="checkbox" value="<fmt:message key="timetable.saturday" bundle="${ rb }"/>">
                        <fmt:message key="courses.saturday" bundle="${ rb }"/></label>
                </div>
                <div id="time">
                    <div>
                        <p><fmt:message key="courses.start_time" bundle="${ rb }"/>:</p>
                        <input type="time">
                    </div>
                    <div>
                        <p><fmt:message key="courses.duration" bundle="${ rb }"/>:</p>
                        <input type="number" min="30" max="120" step="15">
                    </div>
                </div>
            </div>
            <div>
                <p><fmt:message key="courses.age" bundle="${ rb }"/>:</p>
                <div id="age">
                    <input type="number" min=3 max=60 placeholder="min">
                    <input type="number" min=3 max=60 placeholder="max">
                </div>
                <p><fmt:message key="courses.cost" bundle="${ rb }"/>:</p>
                <input type="text" min="1" max="30">
                <p><fmt:message key="courses.people_amount" bundle="${ rb }"/>:</p>
                <input type="number" min="1" max="30" step="1">
            </div>
        </div>
        <textarea placeholder="<fmt:message key="courses.description" bundle="${ rb }"/>"></textarea>
        <input type="submit" value="<fmt:message key="courses.submit" bundle="${ rb }"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/courses.js"></script>
</body>
<footer>

</footer>
</html>