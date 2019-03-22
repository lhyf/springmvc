<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>

<br>
<a href="user/login" >login</a><br><br>

<form action="fileupload/file" method="post" enctype="multipart/form-data">
    描述:<input type="text" name="desc"><br>
    文件:<input type="file" name="file"><<br>
    <input type="submit" value="提交">
</form>

<br><br>

<fmt:message key="i18n.language"></fmt:message>
<a href="index?loacl=zh_CN" >中文</a> &nbsp;
<a href="index?loacl=en_US" >English</a>

</body>
</html>
