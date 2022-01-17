<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/timetable.css" type="text/css"/>
    <title><fmt:message key="header.title" bundle="${rb}"/></title>
</head>

<body>
<jsp:include page="main/home.jsp"/>
<main>
    <div id="rect"></div>
    <form>
        <p id="title"><fmt:message key="timetable.title" bundle="${rb}"/></p>
        <div>
            <div><fmt:message key="timetable.monday" bundle="${rb}"/></div>
            <div>
                <div>
                    <div>
                        18:00
                    </div>
                    <div>
                        Эстрадный вокал
                    </div>
                </div>
            </div>
        </div>
        <div>
            <div><fmt:message key="timetable.tuesday" bundle="${rb}"/></div>
            <div>
                <div>
                    <div>
                        16:00
                    </div>
                    <div>
                        Эстрадный вокал
                    </div>
                </div>
                <div>
                    <div>
                        18:00
                    </div>
                    <div>
                        Хореография
                    </div>
                </div>
                <div>
                    <div>
                        18:00
                    </div>
                    <div>
                        Эстрадный вокал
                    </div>
                </div>
            </div>
        </div>
        <div>
            <div><fmt:message key="timetable.wednesday" bundle="${rb}"/></div>
            <div>
                В этот день занятий нет
            </div>
        </div>
        <div>
            <div><fmt:message key="timetable.thursday" bundle="${rb}"/></div>
            <div>
                В этот день занятий нет
            </div>
        </div>
        <div>
            <div><fmt:message key="timetable.friday" bundle="${rb}"/></div>
            <div>
                В этот день занятий нет
            </div>
        </div>
        <div>
            <div><fmt:message key="timetable.saturday" bundle="${rb}"/></div>
            <div>
                В этот день занятий нет
            </div>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/height.js"></script>
</body>
</html>