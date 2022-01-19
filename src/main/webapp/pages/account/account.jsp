<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/account/account.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/uploadController" enctype="multipart/form-data">
        <p id="title">${user.login}</p>
        <div>
            <div>
                <c:choose>
                    <c:when test="${not empty picture}">
                        <img src="${picture}" alt="account">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
                    </c:otherwise>
                </c:choose>
            </div>
            <div>
                <div id="icons">
                    <img src="${pageContext.request.contextPath}/pics/account_name.png" alt="name">
                    <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                    <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                </div>
                <div>
                    <div>${user.surname} ${user.name}</div>
                    <div>${user.email}</div>
                    <div>${number}</div>
                </div>
            </div>
            <div id="wrapper">
                <input name="uploaded_picture" type="file" id="upload_file_input"
                       accept="image/jpg">
                <label for="upload_file_input">
                    <div id="upload_file"><fmt:message key="account.photo"/></div>
                </label>
            </div>
            <input type="button" value="<fmt:message key="account.balance"/>"
                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_replenish_balance'">
            <input type="button" value="<fmt:message key="account.update"/>"
                   onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_update_account_data'">
            <input type="submit" value="<fmt:message key="account.upload_photo"/>" disabled>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/account.js"></script>
<c:if test="${not empty message}">
    <jsp:include page="../modal.jsp"/>
</c:if>
</body>
</html>