<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-3.3.1.js"></script>
</head>
<body>
<%--
    xiaohong-123456-xiaohong@163.com-18-liaoning-shenyang
--%>
<form action="test/convertor" method="post">
    User: <input type="text" name="user">
    <input type="submit" value="提交">
</form>

<br><br>

<form:form action="${pageContext.request.contextPath}/test/dataBinding" method="post" modelAttribute="user">
    name:<form:input path="username"></form:input>
    <form:errors path="username"></form:errors><br>
    password: <form:password path="password"></form:password><br>
    email: <form:input path="email"></form:input>
    <form:errors path="email"></form:errors><br>
    age:<form:input path="age"></form:input><br>
    birth: <form:input path="birth"></form:input>
    <form:errors path="birth"></form:errors><br>
    salary:<form:input path="salary"></form:input>
    <form:errors path="salary"></form:errors><br>
    <input type="submit" value="提交">
</form:form>

<form action="test/dataBinding" method="post">

    name:<input type="text" name="username"><br/>
    password:<input type="password" name="password"><br/>
    email:<input type="text" name="email"><br/>
    age:<input type="text" name="age"><br/>
    birth:<input type="text" name="birth"><br/>
    salary:<input type="text" name="salary"><br/>
    <input type="submit" value="提交">
</form>

</body>
</html>
