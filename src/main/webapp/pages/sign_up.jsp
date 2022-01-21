<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign_up.css" type="text/css"/>
</head>

<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form id="reg" method="post" action="${pageContext.request.contextPath}/controller?command=sign_up">
        <div id="page1">
            <p class="title"><fmt:message key="sign_up.title"/></p>
            <input type="text" name="login" required
                   placeholder="<fmt:message key="sign_in.login.placeholder"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{4,29}" autocomplete="off" value="<c:out value="${user.login}"/>"
                   title="<fmt:message key="sign_in.login.title"/>"><br/>
            <input type="password" name="password" required
                   placeholder="<fmt:message key="sign_in.password.placeholder"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}" value="<c:out value="${user.password}"/>"
                   title="<fmt:message key="sign_in.password.title"/>"><br/>
            <input type="password" name="repeated_password" required
                   placeholder="<fmt:message key="sign_up.repeat_password.placeholder"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}" value="<c:out value="${user.repeated_password}"/>"
                   title="<fmt:message key="sign_in.password.title"/>"><br/>
            <div id="next">
                <input type="button" value="<fmt:message key="sign_up.next"/>">
            </div>
        </div>
        <div id="page2">
            <p class="title"><fmt:message key="sign_up.title"/></p>
            <input type="text" name="surname" required
                   placeholder="<fmt:message key="sign_up.surname.placeholder"/>"
                   pattern="[А-ЯA-Z][а-яa-z]{1,20}" autocomplete="off" value="<c:out value="${user.surname}"/>"
                   title="<fmt:message key="sign_up.surname.title"/>"><br/>
            <input type="text" name="name" required
                   placeholder="<fmt:message key="sign_up.name.placeholder"/>"
                   pattern="[А-ЯA-Z][а-яёa-z]{1,15}" autocomplete="off" value="<c:out value="${user.name}"/>"
                   title="<fmt:message key="sign_up.name.title"/>"><br/>
            <input type="email" name="email" placeholder="Email" autocomplete="off" required
                   pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"
                   value="<c:out value="${user.email}"/>"
                   title="<fmt:message key="sign_up.email.title"/>: *****@***.**"><br/>
            <input type="tel" name="number" required placeholder="+375(xx)xxx-xx-xx" autocomplete="off"
                   value="<c:out value="${user.number}"/>" pattern="\+375\(\d{2}\)\d{3}-\d{2}-\d{2}"
                   title="<fmt:message key="sign_up.number.placeholder"/>: +375(xx)xxx-xx-xx"><br/>
            <div id="subm">
                <img src="${pageContext.request.contextPath}/pics/back_arrow.png" alt="arrow">
                <input type="submit" value="<fmt:message key="sign_up.submit"/>">
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
<jsp:include page="main/footer.jsp"/>
</html>