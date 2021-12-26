<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/home.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/${sessionScope.role}.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${ rb }"/></title>
</head>

<body>
<header>
    <div id="logo">
        <div class="stage" id="stage1"></div>
        <div class="stage" id="stage2"></div>
        <div class="stage" id="stage3"></div>
        <div class="stage" id="stage4"></div>
        <div class="stage" id="stage5"></div>
    </div>
    <div id="headerTitle"><fmt:message key="header.title" bundle="${ rb }"/></div>
    <jsp:include page="${sessionScope.role}.jsp"/>
    <div id="language">
        <p>${sessionScope.language}</p>
        <div>
            <p><a href="${pageContext.request.contextPath}/controller?command=change_locale&language=EN">EN</a></p>
            <p><a href="${pageContext.request.contextPath}/controller?command=change_locale&language=RU">RU</a></p>
        </div>
    </div>
</header>
</body>
</html>
