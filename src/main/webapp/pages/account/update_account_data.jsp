<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/account/update_account.css" type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=update_account_data">
        <p id="title"><fmt:message key="account.update.title"/></p>
        <div>
            <div id="nav">
                <p><fmt:message key="account.update.navigation.authentication"/></p>
                <p><fmt:message key="account.update.navigation.personal"/></p>
            </div>
            <div id="authent">
                <input type="text" name="login"
                       placeholder="<fmt:message key="sign_in.login.placeholder"/>" required
                       pattern="[a-zA-Z][A-Za-z0-9]{4,29}" autocomplete="off" value="<c:out value="${user.login}"/>"
                       title="<fmt:message key="sign_in.login.title"/>"><br/>
                <input type="password" name="password" pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                       placeholder="<fmt:message key="account.update.old_password.placeholder"/>"
                       title="<fmt:message key="sign_in.password.title"/>"><br/>
                <input type="password" name="new_password" pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                       placeholder="<fmt:message key="account.update.new_password.placeholder"/>"
                       title="<fmt:message key="sign_in.password.title"/>"><br/>
                <input type="password" name="repeated_password" pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                       placeholder="<fmt:message key="sign_up.repeat_password.placeholder"/>"
                       title="<fmt:message key="sign_in.password.title"/>"><br/>
                <input type="submit" value="<fmt:message key="account.update.submit"/>">
            </div>
            <div id="personal">
                <input type="text" name="surname" required
                       placeholder="<fmt:message key="sign_up.surname.placeholder"/>"
                       pattern="[??-??A-Z][??-??a-z]{1,20}" autocomplete="off" value="<c:out value="${user.surname}"/>"
                       title="<fmt:message key="sign_up.surname.title"/>"><br/>
                <input type="text" name="name" required
                       placeholder="<fmt:message key="sign_up.name.placeholder"/>"
                       pattern="[??-??A-Z][??-????a-z]{1,15}" autocomplete="off" value="<c:out value="${user.name}"/>"
                       title="<fmt:message key="sign_up.name.title"/>"><br/>
                <input type="email" name="email" placeholder="Email" value="<c:out value="${user.email}"/>" required
                       pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})" autocomplete="off"
                       title="<fmt:message key="sign_up.email.title"/>: *****@***.**"><br/>
                <input type="tel" name="number" required
                       placeholder="+375(xx)xxx-xx-xx" autocomplete="off"
                       value="<c:out value="${number}"/>" pattern="\+375\(\d{2}\)\d{3}-\d{2}-\d{2}"
                       title="<fmt:message key="sign_up.number.placeholder"/>: +375(xx)xxx-xx-xx"><br/>
                <input type="submit" value="<fmt:message key="account.update.submit"/>">
            </div>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/account/update.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>

</body>
<jsp:include page="../main/footer.jsp"/>
</html>