<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="custom_tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/general.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/course/show.css" type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=search_courses">
        <p id="title"><fmt:message key="courses.title"/></p>
        <div id="layout">
            <div id="search">
                <select name="category" onchange="location.href=
                        '${pageContext.request.contextPath}/controller?command=go_to_courses&category='
                        + this.options[this.selectedIndex].value">
                    <option disabled selected><fmt:message key="activities.category"/> --</option>
                    <c:forEach var="category" items="${categories}">
                        <option <c:if test="${category eq requestScope.selected_category}">selected</c:if>>
                            <c:out value="${category}"/></option>
                    </c:forEach>
                </select>
                <select name="type">
                    <option disabled selected><fmt:message key="activities.type"/> --</option>
                    <c:forEach var="activity" items="${activities}">
                        <option <c:if test="${activity.type eq requestScope.selected_type}">selected</c:if>>
                            <c:out value="${activity.type}"/></option>
                    </c:forEach>
                </select>
                <p><fmt:message key="courses.weekdays"/>:</p>
                <div id="weekdays">
                    <c:forEach var="weekday" items="${sessionScope.weekdays}">
                        <label><input type="checkbox" name="weekdays" value="<c:out value="${weekday}"/>"
                        <c:forEach var="selected_weekday" items="${requestScope.selected_weekdays}">
                                      <c:if test="${selected_weekday eq weekday}">checked</c:if>
                        </c:forEach>>
                            <fmt:message key="courses.${weekday}"/></label>
                    </c:forEach>
                </div>
                <div id="buttons">
                    <input type="submit" value="<fmt:message key="courses.search"/>">
                    <input type="button" name="clear" value="<fmt:message key="courses.clear"/>"
                           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_courses'">
                    <input type="button" value="<fmt:message key="courses.info"/>" disabled
                           onclick="location.href=
                                   '${pageContext.request.contextPath}/controller?command=go_to_course_info&chosen_course_id='
                                   + document.getElementsByName('chosen_course_id')[0].value">
                    <c:choose>
                        <c:when test="${sessionScope.role == 'admin'}">
                            <div></div>
                            <input type="button" value="<fmt:message key="courses.add"/>"
                                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_add_course'">
                            <input type="button" value="<fmt:message key="courses.update"/>" disabled
                                   onclick="location.href=`${pageContext.request.contextPath}/controller?command=go_to_update_course&chosen_course_id=`
                                           + document.querySelector('[name=chosen_course_id]').value">
                        </c:when>
                        <c:otherwise>
                            <input type="button" value="<fmt:message key="courses.enroll"/>" disabled
                                   onclick="location.href=`${pageContext.request.contextPath}/controller?command=go_to_enroll_on_course&chosen_course_id=`
                                           + document.querySelector('[name=chosen_course_id]').value">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div>
                <c:if test="${fn:length(courses) > 0}">
                    <div id="all">
                        <div><fmt:message key="activities.type"/></div>
                        <input type="hidden" name="chosen_course_id">
                        <div><fmt:message key="courses.teacher"/></div>
                        <div><fmt:message key="courses.age_group"/></div>
                        <div><fmt:message key="courses.lesson_price"/></div>
                        <c:forEach var="course" items="${courses}">
                            <div><c:out value="${course.activity.type}"/></div>
                            <input type="hidden" name="course_id" value="<c:out value="${course.id}"/>">
                            <div><c:out value="${course.teacher.surname}"/> <c:out
                                    value="${course.teacher.name}"/></div>
                            <div><c:out value="${course.ageGroup.minAge}"/>-<c:out value="${course.ageGroup.maxAge}"/>
                                <fmt:message key="courses.age.title"/></div>
                            <div><c:out value="${course.lessonPrice}"/>р</div>
                        </c:forEach>
                    </div>
                    <c:choose>
                        <c:when test="${not empty requestScope.selected_category or not empty requestScope.selected_category
                        or not empty requestScope.selected_category}">
                            <ctg:pages page="${page}" last="${last}" command="search_courses"/>
                        </c:when>
                        <c:otherwise>
                            <ctg:pages page="${page}" last="${last}" command="go_to_courses"/>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${fn:length(courses) == 0}">
                    <div id="none"><fmt:message key="courses.message"/></div>
                </c:if>
            </div>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/checkbox.js"></script>
<script src="${pageContext.request.contextPath}/script/course/show.js"></script>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>