<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cources.css" type="text/css"/>
    <title>Production Center</title>
</head>

<body>
<jsp:include page="user_page.jsp"/>
<main>
    <form>
        <p id="title">Доступные занятия</p>
        <div id="layout">
            <div id="search">
                <select>
                    <option disabled selected>Выберите направление --</option>
                    <option>Вокальное</option>
                    <option>Хореографическое</option>
                    <option>Инструментальное</option>
                </select>
                <select>
                    <option disabled selected>Выберите --</option>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                </select>
                <p>Дни недели:</p>
                <div id="weekdays">
                    <label><input type="checkbox" value="Понедельник">пн</label>
                    <label><input type="checkbox" value="Вторник">вт</label>
                    <label><input type="checkbox" value="Среда">ср</label>
                    <label><input type="checkbox" value="Четверг">чт</label>
                    <label><input type="checkbox" value="Пятница">пт</label>
                    <label><input type="checkbox" value="Суббота">сб</label>
                </div>
                <input type="submit" value="Записаться" disabled>
            </div>
            <table>
                <tr>
                    <th>Направление</th>
                    <th>Вид</th>
                    <th>Возрастная группа</th>
                    <th>Расписание занятий</th>
                </tr>
                <tr>
                    <td>Направление</td>
                    <td>Вид</td>
                    <td>Возрастная группа</td>
                    <td>Расписание занятий</td>
                </tr>
            </table>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/courses.js"></script>
</body>
<footer>

</footer>
</html>