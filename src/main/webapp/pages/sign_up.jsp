<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign_up.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${ rb }"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form id="reg" method="post" action="${pageContext.request.contextPath}/controller?command=sign_up">
        <div id="page1">
            <p class="title"><fmt:message key="sign_up.title" bundle="${ rb }"/></p>
            <input type="text" name="login" required
                   placeholder="<fmt:message key="sign_in.login.placeholder" bundle="${ rb }"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{4,29}" autocomplete="off" value="${user_data.login}"
                   title="<fmt:message key="sign_in.login.title" bundle="${ rb }"/>"><br/>
            <input type="password" name="password" required
                   placeholder="<fmt:message key="sign_in.password.placeholder" bundle="${ rb }"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}" value="${user_data.password}"
                   title="<fmt:message key="sign_in.password.title" bundle="${ rb }"/>"><br/>
            <input type="password" name="repeated_password" required
                   placeholder="<fmt:message key="sign_up.repeat_password.placeholder" bundle="${ rb }"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}" value="${user_data.repeated_password}"
                   title="<fmt:message key="sign_in.password.title" bundle="${ rb }"/>"><br/>
            <div id="next">
                <input type="button" value="<fmt:message key="sign_up.next" bundle="${ rb }"/>">
            </div>
        </div>
        <div id="page2">
            <p class="title"><fmt:message key="sign_up.title" bundle="${ rb }"/></p>
            <input type="text" name="surname" required
                   placeholder="<fmt:message key="sign_up.surname.placeholder" bundle="${ rb }"/>"
                   pattern="[А-ЯA-Z][а-яa-z]{1,20}" autocomplete="off" value="${user_data.surname}"
                   title="<fmt:message key="sign_up.surname.title" bundle="${ rb }"/>"><br/>
            <input type="text" name="name" required
                   placeholder="<fmt:message key="sign_up.name.placeholder" bundle="${ rb }"/>"
                   pattern="[А-ЯA-Z][а-яёa-z]{1,15}" autocomplete="off" value="${user_data.name}"
                   title="<fmt:message key="sign_up.name.title" bundle="${ rb }"/>"><br/>
            <input type="email" name="email" placeholder="Email" autocomplete="off" required
                   pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})" value="${user_data.email}"
                   title="<fmt:message key="sign_up.email.title" bundle="${ rb }"/>: *****@***.**"><br/>
            <input type="tel" name="number" required
                   placeholder="+375(xx)xxx-xx-xx" autocomplete="off"
                   value="${user_data.number}" pattern="\+375\(\d{2}\)\d{3}-\d{2}-\d{2}"
                   title="<fmt:message key="sign_up.number.placeholder" bundle="${ rb }"/>: +375(xx)xxx-xx-xx"><br/>
            <div id="subm">
                    <img src="${pageContext.request.contextPath}/pics/back_arrow.png" alt="arrow">
                   <input type="submit" value="<fmt:message key="sign_up.submit" bundle="${ rb }"/>">
            </div>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/sign_up.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>
<c:if test="${not empty message}">
    <jsp:include page="modal.jsp"/>
</c:if>
</body>
</html>