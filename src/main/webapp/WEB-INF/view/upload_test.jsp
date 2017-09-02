<%--
  Created by IntelliJ IDEA.
  User: qixiang
  Date: 9/2/17
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" enctype="multipart/form-data" action="/vehicle/info/">
    name：<input type="text" name="name"/><br/>
    displacement: <input type="text" name="displacement"/> <br/>
    gearbox: <input type="text" name="gearbox"/><br>
    boxes: <input type="text" name="boxes"/> <br>
    manned: <input type="text" name="manned"/> <br>
    description: <input type="text" name="description"/> <br>
    picture：<input type="file" name="attachment" accept="image/jpeg,image/png"/><br />
    <input type="submit" value="Upload">
</form>
</body>
</html>
