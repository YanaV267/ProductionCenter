<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign_in.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${ rb }"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=sign_in">
        <p id="title"><fmt:message key="sign_in.title" bundle="${ rb }"/></p>
        <input type="text" name="login" placeholder="<fmt:message key="sign_in.login.placeholder" bundle="${ rb }"/>"
               pattern="[a-zA-Z][A-Za-z0-9]{4,29}" autocomplete="off" value="${user_login}"
               title="<fmt:message key="sign_in.login.title" bundle="${ rb }"/>"><br/>
        <input type="password" name="password"
               placeholder="<fmt:message key="sign_in.password.placeholder" bundle="${ rb }"/>"
               pattern="[a-zA-Z][A-Za-z0-9]{7,29}" value="${user_password}"
               title="<fmt:message key="sign_in.password.title" bundle="${ rb }"/>"><br/>
        <div id="subm">
            <div><input type="submit" value="<fmt:message key="sign_in.submit" bundle="${ rb }"/>"></div>
            <div id="reg">
                <p><fmt:message key="sign_in.registration" bundle="${ rb }"/></p>
                <img src="${pageContext.request.contextPath}/pics/arrow.png" alt="arrow">
            </div>
        </div>
    </form>
</main>
<c:if test="${sign_in_error}">
    <jsp:include page="modal.jsp"/>
</c:if>>
</body>
<footer>

</footer>
</html>