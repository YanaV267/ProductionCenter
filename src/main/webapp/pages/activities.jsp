<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/activities.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${ rb }"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="activities.title" bundle="${ rb }"/></p>
        <div>
            <p class="subtitle">Вокальное</p>
            <div id="header">
                <p></p>
                <p>Преподаватель</p>
                <p>Специальность</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
                <p>Красикова Н.</p>
                <p>Эстрадный вокал</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
                <p>Лещенко И.</p>
                <p>Эстрадный вокал</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
                <p>Красикова Н.</p>
                <p>Эстрадный вокал</p>
            </div>
        </div>
        <div>
            <p class="subtitle">Хореографическое</p>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
<footer>

</footer>
</html>