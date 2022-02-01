<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cmt" uri="custom_tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/timetable.css" type="text/css"/>
</head>

<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="timetable.title"/></p>
        <c:forEach var="weekday" items="${requestScope.weekdays}">
            <div>
                <div><fmt:message key="timetable.${weekday}"/></div>
                <div>
                    <cmt:timetable weekday="${weekday}" courses="${requestScope.courses}" enrollments="${requestScope.enrollments}">
                        <fmt:message key="timetable.no_lessons"/>
                    </cmt:timetable>
                </div>
            </div>
        </c:forEach>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
<jsp:include page="main/footer.jsp"/>
</html>