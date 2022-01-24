<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/account/update_account.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/account/replenish_balance.css" type="text/css"/>
</head>

<body>
<jsp:include page="../main/header.jsp"/>
<main>
    <div id="rect"></div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=replenish_balance">
        <p id="title"><fmt:message key="account.balance.title"/></p>
        <div>
            <div id="authent">
                <input type="text" name="card_number" placeholder="xxxx xxxx xxxx xxxx"
                       pattern="\d{4} \d{4} \d{4} \d{4}" autocomplete="off" value="<c:out value="${requestScope.card.cardNumber}"/>"
                       title="<fmt:message key="account.balance.card_number"/>: xxxx xxxx xxxx xxxx"><br/>
                <input type="text" name="owner_name" autocomplete="off"
                       placeholder="<fmt:message key="account.balance.owner_name.placeholder"/>"
                       pattern="[A-Z ]{3,30}" value="<c:out value="${requestScope.card.ownerName}"/>"
                       title="<fmt:message key="account.balance.owner_name"/>"><br/>
                <input type="text" name="expiration_date" placeholder="xx/xx" autocomplete="off"
                       pattern="(0[1-9]|1[0-2])\/(2[2-6])" value="<c:out value="${requestScope.card.expirationDate}"/>"
                       title="<fmt:message key="account.balance.expiration_date"/>: xx/xx"><br/>
                <input type="tel" name="cvv_number" placeholder="CVV" autocomplete="off"
                       value="<c:out value="${requestScope.card.cvvNumber}"/>" pattern="\d{3}"
                       title="<fmt:message key="account.balance.cvv_number"/>"><br/>
                <input type="text" name="balance" pattern="^((\d{2,4}\.\d{2})|(\d{2,4}))$" autocomplete="off"
                       placeholder="<fmt:message key="account.balance.summary"/>"
                       value="<c:out value="${requestScope.card.balance}"/>"
                       title="<fmt:message key="account.balance.summary.placeholder"/>: xxxx.xx"><br/>
                <input type="submit" value="<fmt:message key="account.balance.replenish"/>">
            </div>
        </div>
    </form>
</main>
<script src="${pageContext.request.contextPath}/script/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/script/balance.js"></script>
<script src="${pageContext.request.contextPath}/script/incorrect_input.js"></script>

</body>
<jsp:include page="../main/footer.jsp"/>
</html>