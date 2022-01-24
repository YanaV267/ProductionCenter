<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cmt" uri="custom_tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/general.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/add.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/update.css" type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post"
          action="${pageContext.request.contextPath}/controller?command=update_course&chosen_course_id=${course.id}">
        <p id="title"><fmt:message key="courses.update.title"/> "<c:out value="${course.activity.type}"/>"</p>
        <div id="courseProps">
            <div>
                <select name="teacher">
                    <option disabled selected><fmt:message key="courses.teacher"/> --</option>
                    <c:forEach var="teacher" items="${sessionScope.teachers}">
                        <option
                                <c:if test="${teacher.key.surname eq course.teacher.surname && teacher.key.name
                                                                                eq course.teacher.name}">selected</c:if>>
                            <c:out value="${teacher.key.surname}"/> <c:out value="${teacher.key.name}"/></option>
                    </c:forEach>
                </select>
                <p><fmt:message key="courses.weekdays"/>:</p>
                <div id="weekdays">
                    <c:forEach var="weekday" items="${sessionScope.weekdays}">
                        <label><input type="checkbox" value="<fmt:message key="timetable.${weekday}"/>"
                        <c:forEach var="lesson" items="${course.lessons}">
                                      <c:if test="${lesson.weekDay eq weekday}">checked </c:if>
                        </c:forEach>>
                            <fmt:message key="courses.${weekday}"/></label>
                    </c:forEach>
                </div>
            </div>
            <div id="timetable">
                <c:forEach var="lesson" items="${course.lessons}">
                    <div class="time">
                        <p><fmt:message key="timetable.${lesson.weekDay}"/>:</p>
                        <input type="hidden" name="weekdays" value="<fmt:message key="timetable.${lesson.weekDay}"/>">
                        <div>
                            <p><fmt:message key="courses.start_time"/>:</p>
                            <input type="time" name="time" value="<c:out value="${lesson.startTime}"/>">
                        </div>
                        <div>
                            <p><fmt:message key="courses.duration"/>:</p>
                            <input type="number" name="duration" min="30" max="120" step="15"
                                   value="<c:out value="${lesson.duration}"/>">
                        </div>
                    </div>
                </c:forEach>
                <c:forEach begin="1" end="3" varStatus="loop">
                    <div class="time" style="display: none">
                        <p></p>
                        <input type="hidden" name="weekdays">
                        <div>
                            <p><fmt:message key="courses.start_time"/>:</p>
                            <input type="time" name="time">
                        </div>
                        <div>
                            <p><fmt:message key="courses.duration"/>:</p>
                            <input type="number" name="duration" min="30" max="120" step="15">
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div id="last">
                <p><fmt:message key="courses.age"/>:</p>
                <div id="age">
                    <input type="number" name="min_age" min=3 max=60 placeholder="min"
                           value="<c:out value="${course.ageGroup.minAge}"/>">
                    <input type="number" name="max_age" min=3 max=60 placeholder="max"
                           value="<c:out value="${course.ageGroup.maxAge}"/>">
                </div>
                <p><fmt:message key="courses.lesson_price"/>:</p>
                <input type="text" name="lesson_price" min="1" max="30" pattern="^((\d\d?\.)?\d{1,2})$"
                       value="<c:out value="${course.lessonPrice}"/>">
                <p><fmt:message key="courses.student_amount"/>:</p>
                <input type="number" name="student_amount" min="1" max="30" step="1"
                       value="<c:out value="${course.studentAmount}"/>">
            </div>
        </div>
        <div>
        <textarea name="description" placeholder="<fmt:message key="courses.description"/>"><c:out
                value="${course.description}"/></textarea>
            <div id="status">
                <p><fmt:message key="courses.status"/>:</p>
                <label>
                    <input type="radio" name="status" value="<fmt:message key="courses.status.upcoming"/>"
                           <c:if test="${course.status eq 'UPCOMING'}">checked</c:if>><fmt:message
                        key="courses.status.upcoming"/>
                </label>
                <label>
                    <input type="radio" name="status" value="<fmt:message key="courses.status.running"/>"
                           <c:if test="${course.status eq 'RUNNING'}">checked</c:if>><fmt:message
                        key="courses.status.running"/>
                </label>
                <label>
                    <input type="radio" name="status" value="<fmt:message key="courses.status.paused"/>"
                           <c:if test="${course.status eq 'PAUSED'}">checked</c:if>><fmt:message
                        key="courses.status.paused"/>
                </label>
            </div>
        </div>
        <div id="buttons">
            <input type="hidden" name="chosen_course_id" value="<c:out value="${course.id}"/>">
            <input type="submit" value="<fmt:message key="courses.update"/>">
            <input type="button" value="<fmt:message key="courses.back"/>"
                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_courses'">
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/checkbox.js"></script>
<script src="${pageContext.request.contextPath}/script/course/update.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>