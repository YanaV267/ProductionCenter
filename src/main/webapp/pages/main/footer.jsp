<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:if test="${not empty message}">
    <jsp:include page="../modal.jsp"/>
</c:if>
<footer>
    <div><fmt:message key="title"/></div>
    <div>©2003-2022 <fmt:message key="footer.copyright"/></div>
</footer>