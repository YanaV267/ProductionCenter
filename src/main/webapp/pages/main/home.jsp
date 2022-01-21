<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<jsp:include page="header.jsp"/>
<main>
</main>
<c:if test="${not empty message}">
    <jsp:include page="../modal.jsp"/>
</c:if>
<jsp:include page="footer.jsp"/>
</body>
</html>
