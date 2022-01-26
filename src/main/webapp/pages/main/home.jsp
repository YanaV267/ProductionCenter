<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div id="home">
        <p>Раскрой талант с нами!</p>
        <p>Благодаря нашему продюсерскому центру Вы сможете преодолеть страхи и реализовать свои мечты</p>
        <input type="button" value="Узнать подробнее"
               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_activities'">
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
