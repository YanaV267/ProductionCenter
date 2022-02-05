<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contacts.css" type="text/css"/>
</head>

<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="contacts.title"/></p>
        <div>
            <p class="subtitle"><fmt:message key="contacts.employers"/></p>
            <c:forEach var="employer" items="${requestScope.employers}">
                <div id="data">
                    <div><c:out value="${employer.key.surname}"/> <c:out value="${employer.key.name}"/></div>
                    <div>
                        <div class="icons">
                            <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                            <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                        </div>
                        <div>
                            <div><c:out value="${employer.key.email}"/></div>
                            <div><c:out value="${employer.value}"/></div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${fn:length(requestScope.teachers) != 0}">
            <div>
                <p class="subtitle"><fmt:message key="header.teachers"/></p>
                <c:forEach var="teacher" items="${requestScope.teachers}">
                    <div id="data">
                        <div>
                            <div><c:out value="${teacher.key.surname}"/> <c:out value="${teacher.key.name}"/></div>
                            <div>
                                <c:forEach var="course" items="${requestScope.courses}">
                                    <c:if test="${course.teacher.surname eq teacher.key.surname
                                && course.teacher.name eq teacher.key.name}">
                                        <div>- <c:out value="${course.activity.type}"/></div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                        <div>
                            <div class="icons">
                                <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                                <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                            </div>
                            <div>
                                <div><c:out value="${teacher.key.email}"/></div>
                                <div><c:out value="${teacher.value}"/></div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/contacts.js"></script>
</body>
<jsp:include page="main/footer.jsp"/>
</html>