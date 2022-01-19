<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign_in.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=sign_in">
        <p id="title"><fmt:message key="sign_in.title"/></p>
        <input type="text" name="login" placeholder="<fmt:message key="sign_in.login.placeholder"/>"
               pattern="[a-zA-Z][A-Za-z0-9]{4,29}" autocomplete="off" value="${user_login}" required
               title="<fmt:message key="sign_in.login.title"/>"><br/>
        <input type="password" name="password"
               placeholder="<fmt:message key="sign_in.password.placeholder"/>"
               pattern="[a-zA-Z][A-Za-z0-9]{7,29}" value="${user_password}" required
               title="<fmt:message key="sign_in.password.title"/>"><br/>
        <div id="subm">
            <div><input type="submit" value="<fmt:message key="sign_in.submit"/>"></div>
            <div id="reg" onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_sign_up'">
                <p><fmt:message key="sign_in.registration"/></p>
                <img src="${pageContext.request.contextPath}/pics/arrow.png" alt="arrow">
            </div>
        </div>
    </form>
</main>
<c:if test="${sign_in_error}">
    <jsp:include page="modal.jsp"/>
</c:if>
</body>
</html>