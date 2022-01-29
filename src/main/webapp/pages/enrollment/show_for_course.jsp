<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="custom_tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_show.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/enrollment/show_for_user.css"
          type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/enrollment/show_for_course.css"
          type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="enrollment.course.title"/></p>
        <div><fmt:message key="enrollment.teacher"/>: <c:out value="${requestScope.course.teacher.surname}"/>
            <c:out value="${requestScope.course.teacher.name}"/></div>
        <div><fmt:message key="enrollment.activity_type"/>: <c:out value="${requestScope.course.activity.type}"/></div>
        <c:if test="${fn:length(requestScope.enrollments) > 0}">
            <input type="hidden" name="course_id" value="${requestScope.course.id}">
            <div id="all">
                <div><fmt:message key="enrollment.name"/></div>
                <div><fmt:message key="enrollment.all.lesson_amount"/></div>
                <div><fmt:message key="enrollment.reservation_date"/></div>
                <div><fmt:message key="enrollment.status"/></div>
                <c:forEach var="enrollment" items="${requestScope.enrollments}">
                    <div><c:out value="${enrollment.key.user.surname}"/> <c:out
                            value="${enrollment.key.user.name}"/></div>
                    <div><c:out value="${enrollment.key.lessonAmount}"/></div>
                    <div><c:out value="${enrollment.value}"/></div>
                    <div><c:out value="${enrollment.key.enrollmentStatus.status}"/></div>
                </c:forEach>
            </div>
            <ctg:pages page="${requestScope.page}" last="${requestScope.last}"
                       command="go_to_enrolled_on_course"/>
        </c:if>
        <c:if test="${fn:length(requestScope.enrollments) == 0}">
            <div id="none"><fmt:message key="enrollment.course.message"/></div>
        </c:if>
        <input type="button" value="<fmt:message key="courses.back"/>"
               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_course_info&chosen_course_id='
                       + document.getElementsByName('course_id')[0].value">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/enrollment/for_course.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>