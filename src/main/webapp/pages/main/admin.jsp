<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<body>
<div id="menu">
    <div id="menu1"><a href="${pageContext.request.contextPath}/controller?command=go_to_home">
        <fmt:message key="header.home" bundle="${ rb }"/></a></div>
    <div id="menu2"><fmt:message key="header.courses" bundle="${ rb }"/>
        <div>
            <p><a href="${pageContext.request.contextPath}/controller?command=go_to_courses">
                <fmt:message key="header.all_courses" bundle="${ rb }"/></a></p>
            <p><a href="${pageContext.request.contextPath}/controller?command=go_to_activities">
                <fmt:message key="header.activities" bundle="${ rb }"/></a></p>
        </div>
    </div>
    <div id="menu3"><a href="${pageContext.request.contextPath}/controller?command=go_to_enrollments">
        <fmt:message key="header.enrollments" bundle="${ rb }"/></a></div>
    <div id="menu4"><a href="${pageContext.request.contextPath}/controller?command=go_to_teachers">
        <fmt:message key="header.teachers" bundle="${ rb }"/></a></div>
</div>
<div id="account">
    <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
    <div>
        <p><a href="${pageContext.request.contextPath}/controller?command=go_to_account">
            <fmt:message key="header.profile" bundle="${ rb }"/></a></p>
        <p><a href="${pageContext.request.contextPath}/controller?command=go_to_users">
            <fmt:message key="header.users" bundle="${ rb }"/></a></p>
        <p><a href="${pageContext.request.contextPath}/controller?command=sign_out">
            <fmt:message key="header.sign_out" bundle="${ rb }"/></a></p>
    </div>
</div>
</body>
</html>
