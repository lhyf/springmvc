<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>input</title>
</head>
<body>
<form:form action="user/save" method="post" modelAttribute="user">
    <fmt:message key="i18n.username" />
    <form:input path="username" /><br>

    <fmt:message key="i18n.password"/>
    <form:password path="password" /><br>

    <fmt:message key="i18n.birth"/>
    <form:input path="email"/><br>

    <fmt:message key="i18n.email"/>
    <form:input path="birth"/><br>
    <input type="submit" value="提交">
</form:form>
</body>
</html>
