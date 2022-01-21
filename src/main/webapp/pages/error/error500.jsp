<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" type="text/css"/>
    <title>Error 500</title>
</head>
<body>
<main>
    <form method="get" action="${pageContext.request.contextPath}/index.jsp">
        <div id="error">
            <img src="${pageContext.request.contextPath}/pics/error.png" alt="error">
            <div>
                <div id="number">
                    <div>500</div>
                    <div>Internal Server Error</div>
                </div>
                <div><fmt:message key="error.500.message"/></div>
                Exception: ${pageContext.exception}<br/>
                <c:forEach var="trace" items="${pageContext.exception.stackTrace}">
                    ${trace}<br/>
                </c:forEach>
                <input type="submit" value="<fmt:message key="error.back"/>"/>
            </div>
        </div>
    </form>
</main>
</body>
</html>