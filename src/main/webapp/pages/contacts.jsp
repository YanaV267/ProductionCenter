<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contacts.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${rb}"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="contacts.title" bundle="${rb}"/></p>
        <div>
            <p class="subtitle"><fmt:message key="contacts.employers" bundle="${rb}"/></p>
            <c:forEach var="employer" items="${employers}">
                <div id="data">
                    <div>${employer.surname} ${employer.name}</div>
                    <div>
                        <div class="icons">
                            <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                            <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                        </div>
                        <div>
                            <div>${employer.email}</div>
                            <c:forEach var="employer_number" items="${employers_numbers}">
                                <c:if test="${employer_number.key eq employer.id}">
                                    <div>${employer_number.value}</div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${teachers.size() != 0}">
            <div>
                <p class="subtitle"><fmt:message key="header.teachers" bundle="${rb}"/></p>
                <c:forEach var="teacher" items="${teachers}">
                    <div id="data">
                        <div>${teacher.surname} ${teacher.name} -
                            <c:forEach var="course" items="${courses}">
                                <c:if test="${course.teacher.surname eq teacher.surname && course.teacher.name eq teacher.name}">
                                    ${course.activity.type}
                                </c:if>
                            </c:forEach>
                        </div>
                        <div>
                            <div class="icons">
                                <img src="${pageContext.request.contextPath}/pics/mail.png" alt="mail">
                                <img src="${pageContext.request.contextPath}/pics/phone.png" alt="phone">
                            </div>
                            <div>
                                <div>${teacher.email}</div>
                                <c:forEach var="teacher_number" items="${teachers_numbers}">
                                    <c:if test="${teacher_number.key eq teacher.id}">
                                        <div>${teacher_number.value}</div>
                                    </c:if>
                                </c:forEach>
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