<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/activity/show.css" type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="activities.title"/></p>
        <c:if test="${sessionScope.role == 'admin'}">
            <input type="button" value="<fmt:message key="activities.add"/>"
                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_add_activity'">
        </c:if>
        <c:forEach var="category" items="${categories}">
            <div>
                <p class="subtitle"><c:out value="${category}"/></p>
                <div id="header">
                    <p></p>
                    <p><fmt:message key="activities.teacher"/></p>
                    <p><fmt:message key="activities.type"/></p>
                </div>
                <c:forEach var="activity" items="${activities}">
                    <c:if test="${activity.category eq category}">
                        <c:set var="teacher_appointed" value="false"/>
                        <c:forEach var="course" items="${courses}">
                            <c:if test="${course.key.activity.category eq category && course.key.activity.type eq activity.type}">
                                <div>
                                    <c:choose>
                                        <c:when test="${not empty course.value}">
                                            <img src="<c:out value="${course.value}"/>" alt="account">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/pics/account.png"
                                                 alt="account">
                                        </c:otherwise>
                                    </c:choose>
                                    <p>
                                        <c:out value="${course.key.teacher.surname}"/> <c:out
                                            value="${course.key.teacher.name}"/>
                                    </p>
                                    <p><c:out value="${activity.type}"/></p>
                                </div>
                                <c:set var="teacher_appointed" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${teacher_appointed eq 'false'}">
                            <div>
                                <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
                                <p><fmt:message key="activities.no_teacher"/></p>
                                <p><c:out value="${activity.type}"/></p>
                            </div>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
        <c:if test="${fn:length(categories) == 0}">
            <div><fmt:message key="activities.message"/></div>
        </c:if>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>