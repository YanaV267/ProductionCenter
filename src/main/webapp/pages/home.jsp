<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css"/>
    <c:choose>
        <c:when test="${sessionScope.role == null}">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/guest.css" type="text/css"/>
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/${sessionScope.role}.css" type="text/css"/>
        </c:otherwise>
    </c:choose>
    <title>Продюсерский центр</title>
</head>

<body>
<c:choose>
    <c:when test="${sessionScope.role == null}">
        <jsp:include page="guest_page.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="${sessionScope.role}_page.jsp"/>
    </c:otherwise>
</c:choose>
</body>
</html>
