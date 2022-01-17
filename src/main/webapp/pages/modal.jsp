<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css" type="text/css"/>
</head>
<body>
<main>
    <div id="modal-container">
        <div id="modal-content">
            <div id="modal-close" class="close"></div>
            <p><fmt:message key="${message}" bundle="${rb}"/></p>
        </div>
    </div>
</main>
<script src="${pageContext.request.contextPath}/script/modal.js"></script>
</body>
</html>
