<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>AddPage</title>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
</head>
<body>
    <form action="../addItemsOperator.action" method="post">
        ItemsName : &nbsp;<input type="text" name="itemsName"/><br>
        ItemsPrice: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="itemsPrice"/><br>
        CreateTime: &nbsp;<input type="text" name="itemsTime"/><br>
        DetailInfo: &nbsp;&nbsp;&nbsp;&nbsp;<input name="itemsInfo" /><br>
        <input type="submit" value="AddOperator">
        <input type="reset" value="Reset">
    </form>
</body>
</html>
