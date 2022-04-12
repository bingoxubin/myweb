<%--
  Created by IntelliJ IDEA.
  User: xubin03
  Date: 2021/3/22
  Time: 3:11 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录界面</title>
</head>
<body>
<form action="<%=request.getContextPath() %>/LoginServlet" method="post" onsubmit="return login(this);">
    <input type="text" name="username"><br>
    <input type="password" name="password"><br>
    <input type="submit" value="登录">
    <input type="reset" value="重置">
</form>
</body>
</html>
