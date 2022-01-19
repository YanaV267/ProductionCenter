<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/activity/add.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="../main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=add_activity">
        <p id="title"><fmt:message key="activities.add.title"/></p>
        <select name="category">
            <option disabled selected><fmt:message key="activities.category"/> --</option>
            <c:forEach var="category" items="${categories}">
                <option>${category}</option>
            </c:forEach>
        </select>
        <input type="text" name="new_category" autocomplete="off" value="${activity.category}"
               placeholder="<fmt:message key="activities.new_category.placeholder"/>"
               pattern="[а-яА-Я]{3,20}" title="<fmt:message key="activities.new_category.title"/>">
        <input type="text" name="type" autocomplete="off" value="${activity.type}"
               placeholder="<fmt:message key="activities.type"/>"
               pattern="[а-яА-Я -]{3,30}" title="<fmt:message key="activities.type.title"/>">
        <div id="buttons">
            <input type="button" value="<fmt:message key="activities.reset"/>">
            <input type="submit" value="<fmt:message key="activities.add"/>">
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/activity.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>

</body>
</html>