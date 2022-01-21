<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/enrollment/show_for_user.css"
          type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=cancel_enrollment">
        <p id="title"><fmt:message key="enrollment.all.title"/></p>
        <c:if test="${enrollments.size() > 0}">
            <div id="all">
                <div><fmt:message key="enrollment.teacher"/></div>
                <input type="hidden" name="chosen_enrollment_id">
                <div><fmt:message key="enrollment.activity_type"/></div>
                <div><fmt:message key="enrollment.all.lesson_amount"/></div>
                <div><fmt:message key="enrollment.total_price"/></div>
                <div><fmt:message key="enrollment.reservation_date"/></div>
                <div><fmt:message key="enrollment.status"/></div>
                <c:forEach var="enrollment" items="${enrollments}">
                    <div><c:out value="${enrollment.key.course.teacher.surname}"/> <c:out
                            value="${enrollment.key.course.teacher.name}"/></div>
                    <input type="hidden" name="enrollment_id" value="<c:out value="${enrollment.key.id}"/>">
                    <div><c:out value="${enrollment.key.course.activity.type}"/></div>
                    <div><c:out value="${enrollment.key.lessonAmount}"/></div>
                    <div><c:out value="${enrollment.key.lessonAmount * enrollment.key.course.lessonPrice}"/></div>
                    <div><c:out value="${enrollment.value}"/></div>
                    <div id="status"><c:out value="${enrollment.key.enrollmentStatus.status}"/></div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${enrollments.size() == 0}">
            <div id="none"><fmt:message key="enrollment.message"/></div>
        </c:if>
        <div id="buttons">
            <input type="button" value="<fmt:message key="enrollment.pay"/>" disabled
                   onclick="location.href=`${pageContext.request.contextPath}/controller?command=go_to_pay_for_enrollment&chosen_enrollment_id=`+document.querySelector('[name=chosen_enrollment_id]').value">
            <input type="submit" value="<fmt:message key="enrollment.cancel"/>" disabled>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/enrollments_for_user.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>