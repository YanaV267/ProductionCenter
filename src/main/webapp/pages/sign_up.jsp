<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/guest.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign_up.css" type="text/css"/>
    <title>Production Center</title>
</head>

<body>
<jsp:include page="guest_page.jsp"/>
<main>
    <form id="reg" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="sign_up">
        <div id="page1">
            <p class="title">Регистрация</p>
            <input type="text" name="login" placeholder="Логин" pattern="[a-zA-Z][A-Za-z0-9]{4,29}" autocomplete="off"
                   title="Логин может иметь длину от 5 до 30 символов и должен состоять только из букв латинского алфавита и цифр"><br/>
            <input type="password" name="password" placeholder="Пароль" pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                   title="Пароль может иметь длину от 8 до 30 символов и должен состоять только из букв латинского алфавита и цифр"><br/>
            <input type="password" name="repeated_password" placeholder="Повторите пароль" pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                   title="Пароль может иметь длину от 8 до 30 символов и должен состоять только из букв латинского алфавита и цифр"><br/>
            <div id="next">
                <input type="button" value="Далее">
            </div>
        </div>
        <div id="page2">
            <p class="title">Регистрация</p>
            <input type="text" name="surname" placeholder="Фамилия" pattern="[А-ЯA-Z][а-яa-z]{1,20}" autocomplete="off"
                   title="Фамилия должна содержать только буквы (начинается с заглавной)"><br/>
            <input type="text" name="name" placeholder="Имя" pattern="[А-ЯA-Z][а-яёa-z]{1,15}" autocomplete="off"
                   title="Имя должно содержать только буквы (начинается с заглавной)"><br/>
            <input type="email" name="email" placeholder="Email" autocomplete="off"
                   pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"
                   title="Формат ввода почты: *****@***.**"><br/>
            <input type="tel" name="number" placeholder="Номер телефона" autocomplete="off"
                   pattern="\+375\(\d{2}\)\d{3}-\d{2}-\d{2}" title="Формат ввода номера телефона: +375(xx)xxx-xx-xx"><br/>
            <div id="subm">
                <input type="submit" value="Зарегистрироваться">
            </div>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/sign_up.js"></script>
</body>
<footer>

</footer>
</html>