<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/courses/general.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/courses/show.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${ rb }"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="courses.title" bundle="${ rb }"/></p>
        <div id="layout">
            <div id="search">
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
                <input type="submit" value="<fmt:message key="courses.enroll" bundle="${ rb }"/>">
            </div>
            <table>
                <tr>
                    <td><fmt:message key="courses.type" bundle="${ rb }"/></td>
                    <th><fmt:message key="courses.age_group" bundle="${ rb }"/></th>
                    <th><fmt:message key="courses.timetable" bundle="${ rb }"/></th>
                </tr>
                <tr>
                    <td>Специальность</td>
                    <td>Возрастная группа</td>
                    <td>Расписание занятий</td>
                </tr>
            </table>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/courses.js"></script>
</body>
<footer>

</footer>
</html>