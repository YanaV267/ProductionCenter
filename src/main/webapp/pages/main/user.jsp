<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<div id="menu">
    <div id="menu1"><a href="${pageContext.request.contextPath}/controller?command=go_to_home">
        <fmt:message key="header.home"/></a></div>
    <div id="menu2"><fmt:message key="header.courses"/>
        <div>
            <p><a href="${pageContext.request.contextPath}/controller?command=go_to_courses">
                <fmt:message key="header.all_courses"/></a></p>
            <p><a href="${pageContext.request.contextPath}/controller?command=go_to_activities">
                <fmt:message key="header.activities"/></a></p>
        </div>
    </div>
    <div id="menu3"><a href="${pageContext.request.contextPath}/controller?command=go_to_timetable">
        <fmt:message key="header.timetable"/></a></div>
    <div id="menu4"><a href="${pageContext.request.contextPath}/controller?command=go_to_contacts">
        <fmt:message key="header.contacts"/></a></div>
</div>
<div id="account">
    <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
    <div>
        <p><a href="${pageContext.request.contextPath}/controller?command=go_to_account">
            <fmt:message key="header.profile"/></a></p>
        <p><a href="${pageContext.request.contextPath}/controller?command=go_to_user_enrollments">
            <fmt:message key="header.enrollments"/></a></p>
        <p><a href="${pageContext.request.contextPath}/controller?command=sign_out">
            <fmt:message key="header.sign_out"/></a></p>
    </div>
</div>