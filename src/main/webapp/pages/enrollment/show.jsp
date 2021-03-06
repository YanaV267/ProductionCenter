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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/enrollment/show.css" type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=search_enrollments">
        <p id="title"><fmt:message key="enrollment.all.title"/></p>
        <div id="search">
            <div id="enr_status">
                <label>
                    <input type="radio" name="status" value="reserved"
                           <c:if test="${requestScope.selected_status eq 'reserved'}">checked</c:if>>
                    <fmt:message key="enrollment.status.reserved"/>
                </label>
                <label>
                    <input type="radio" name="status" value="expired"
                           <c:if test="${requestScope.selected_status eq 'expired'}">checked</c:if>>
                    <fmt:message key="enrollment.status.expired"/>
                </label>
                <label>
                    <input type="radio" name="status" value="renewed"
                           <c:if test="${requestScope.selected_status eq 'renewed'}">checked</c:if>>
                    <fmt:message key="enrollment.status.renewed"/>
                </label>
                <label>
                    <input type="radio" name="status" value="paid"
                           <c:if test="${requestScope.selected_status eq 'paid'}">checked</c:if>>
                    <fmt:message key="enrollment.status.paid"/>
                </label>
                <label>
                    <input type="radio" name="status" value="approved"
                           <c:if test="${requestScope.selected_status eq 'approved'}">checked</c:if>>
                    <fmt:message key="enrollment.status.approved"/>
                </label>
            </div>
            <input type="button" name="clear" value="<fmt:message key="courses.clear"/>"
                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_enrollments'">
            <input type="submit" value="<fmt:message key="courses.search"/>">
        </div>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=change_enrollment_status">
        <c:if test="${fn:length(enrollments) > 0}">
            <div id="all">
                <div><fmt:message key="enrollment.name"/></div>
                <input type="hidden">
                <input type="hidden">
                <div><fmt:message key="enrollment.activity_type"/></div>
                <div><fmt:message key="enrollment.all.lesson_amount"/></div>
                <div><fmt:message key="enrollment.reservation_date"/></div>
                <div><fmt:message key="enrollment.status"/></div>
                <div><fmt:message key="enrollment.approval"/></div>
                <c:forEach var="enrollment" items="${enrollments}">
                    <div><c:out value="${enrollment.key.user.surname}"/> <c:out
                            value="${enrollment.key.user.name}"/></div>
                    <input type="hidden" name="enrollment_id" value="<c:out value="${enrollment.key.id}"/>">
                    <input type="hidden" name="status"
                           value="<c:out value="${enrollment.key.enrollmentStatus.status}"/>">
                    <div><c:out value="${enrollment.key.course.activity.type}"/></div>
                    <div><c:out value="${enrollment.key.lessonAmount}"/></div>
                    <div><c:out value="${enrollment.value}"/></div>
                    <div id="status"><c:out value="${enrollment.key.enrollmentStatus.status}"/></div>
                    <div>
                        <label class="switch">
                            <input type="checkbox"
                                   <c:if test="${enrollment.key.enrollmentStatus.status eq 'approved'}">checked </c:if>
                            <c:choose>
                                   <c:when test="${enrollment.key.enrollmentStatus.status eq 'reserved'}">disabled </c:when>
                                   <c:when test="${enrollment.key.enrollmentStatus.status eq 'expired'}">disabled </c:when>
                            </c:choose>>
                            <span class="slider round"></span>
                        </label>
                    </div>
                </c:forEach>
            </div>
            <c:choose>
                <c:when test="${not empty requestScope.selected_status}">
                    <ctg:pages page="${page}" last="${last}" command="search_enrollments"/>
                </c:when>
                <c:otherwise>
                    <ctg:pages page="${page}" last="${last}" command="go_to_enrollments"/>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${fn:length(enrollments) == 0}">
            <div id="none"><fmt:message key="enrollment.message"/></div>
        </c:if>
        <input type="submit" value="<fmt:message key="enrollment.save"/>">
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/enrollment/show.js"></script>
<script src="${pageContext.request.contextPath}/script/enrollment/height.js"></script>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>