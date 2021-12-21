<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/guest.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign_in.css" type="text/css"/>
    <title>Production Center</title>
</head>

<body>
<jsp:include page="guest_page.jsp"/>
<jsp:include page="modal.jsp"/>
<main>
    <form method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="sign_in">
        <p id="title">Вход в аккаунт</p>
        <input type="text" name="login" placeholder="Логин" pattern="[a-zA-Z][A-Za-z0-9]{4,29}" autocomplete="off"
               title="Логин может иметь длину от 5 до 30 символов и должен состоять только из букв латинского алфавита и цифр"><br/>
        <input type="password" name="password" placeholder="Пароль" pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
               title="Пароль может иметь длину от 8 до 30 символов и должен состоять только из букв латинского алфавита и цифр"><br/>
        <div id="subm">
            <div><input type="submit" value="Подтвердить"></div>
            <div id="reg">
                <p>Регистрация</p>
                <img src="${pageContext.request.contextPath}/pics/arrow.png" alt="arrow">
            </div>
        </div>
    </form>
</main>
</body>
<footer>

</footer>
</html>