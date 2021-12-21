<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<header>
    <div id="logo">
        <div class="stage" id="stage1"></div>
        <div class="stage" id="stage2"></div>
        <div class="stage" id="stage3"></div>
        <div class="stage" id="stage4"></div>
        <div class="stage" id="stage5"></div>
    </div>
    <div id="headerTitle">Продюсерский центр "Ступени"</div>
    <div id="menu">
        <div id="menu1"><a href="${pageContext.request.contextPath}/pages/home.jsp">Главная</a></div>
        <div id="menu2">Занятия</div>
        <div id="menu3">Расписание</div>
        <div id="menu4">Контакты</div>
    </div>
    <div id="account">
        <img src="${pageContext.request.contextPath}/pics/account.png" alt="account">
        <div>
            <p><a href="${pageContext.request.contextPath}/pages/sign_in.jsp">Вход</a></p>
            <p><a href="${pageContext.request.contextPath}/pages/sign_up.jsp">Регистрация</a></p>
        </div>
    </div>
</header>
</body>
</html>
