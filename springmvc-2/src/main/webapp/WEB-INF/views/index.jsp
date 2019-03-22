<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>

<br>
<a href="user/login" >login</a><br><br>


<br><br>

<fmt:message key="i18n.language"></fmt:message>
<a href="index?locale=zh_CN" >中文</a> &nbsp;
<a href="index?locale=en_US" >English</a>

</body>
</html>
