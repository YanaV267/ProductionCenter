<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/account.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${ rb }"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title">${sessionScope.login}</p>
        <div>
            <div>
                <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
            </div>
            <div>
                <div>
                    <div><fmt:message key="account.surname" bundle="${ rb }"/>:</div>
                    <div><fmt:message key="account.name" bundle="${ rb }"/>:</div>
                    <div><fmt:message key="account.email" bundle="${ rb }"/>:</div>
                    <div><fmt:message key="account.number" bundle="${ rb }"/>:</div>
                </div>
                <div>
                    <div>
                        Волкова
                    </div>
                    <div>
                        Яна
                    </div>
                    <div>
                        yv2002@gmail.com
                    </div>
                    <div>
                        +375(44)793-20-07
                    </div>
                </div>
            </div>
        </div>
    </form>
</main>
</body>
<footer>

</footer>
</html>