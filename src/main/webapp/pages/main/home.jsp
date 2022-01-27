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
        <p><fmt:message key="home.title" /></p>
        <p><fmt:message key="home.subtitle" /></p>
        <input type="button" value="<fmt:message key="home.button" />"
               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_activities'">
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
