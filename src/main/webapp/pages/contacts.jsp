<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contacts.css" type="text/css"/>
    <title><fmt:message key="header.title"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="contacts.title"/></p>
        <div>
            <p class="subtitle"><fmt:message key="contacts.employers"/></p>
            <c:forEach var="employer" items="${employers}">
                <div id="data">
                    <div>${employer.key.surname} ${employer.key.name}</div>
                    <div>
                        <div class="icons">
                            <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                            <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                        </div>
                        <div>
                            <div>${employer.key.email}</div>
                            <div>${employer.value}</div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${teachers.size() != 0}">
            <div>
                <p class="subtitle"><fmt:message key="header.teachers"/></p>
                <c:forEach var="teacher" items="${teachers}">
                    <div id="data">
                        <div>${teacher.key.surname} ${teacher.key.name}
                            <c:forEach var="course" items="${courses}">
                                <c:if test="${course.teacher.surname eq teacher.key.surname && course.teacher.name eq teacher.key.name}">
                                    - ${course.activity.type}
                                </c:if>
                            </c:forEach>
                        </div>
                        <div>
                            <div class="icons">
                                <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                                <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                            </div>
                            <div>
                                <div>${teacher.key.email}</div>
                                <div>${teacher.value}</div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/contacts_height.js"></script>
</body>
</html>