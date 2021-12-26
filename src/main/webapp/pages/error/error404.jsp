<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error 404</title>
</head>
<body>
Request from ${pageContext.errorData.requestURI} is failed<br/>
Status code : ${pageContext.errorData.statusCode}<br/>
Servlet name : ${pageContext.errorData.servletName}<br/>
Exception: ${pageContext.exception}<br/>
Message from exception: ${pageContext.exception.message}<br/>
<a href="${pageContext.request.contextPath}/index.jsp">Back to main</a>
</body>
</html>