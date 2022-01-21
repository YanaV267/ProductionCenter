<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<footer>
    <div><fmt:message key="title"/></div>
    <div>©2003-2022<fmt:message key="footer.copyright"/></div>
</footer>