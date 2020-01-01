<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Klinika weterynaryjna</title>
    <meta charset=UTF-8/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="style.css">
</head>
<body>
${banner}

<div class="w3-cell-row">

    <form:form action="/admin/admin_clients" method="post" modelAttribute="user">
        <div class="w3-container w3-cell">
            <form:button name="action" value="add">Dodaj klienta</form:button> <br>
            <!-- FOR FUTURE DEVELOPEMENT: removing clients -->
<!--        <form:button name="action" value="remove">Usuń klienta</form:button> <br>   --> 
            <form:button name="action" value="edit">Edytuj dane klienta</form:button> <br>
        </div>
        <div class="w3-container w3-cell">
            <form:select path="userId" items="${userList}" size="10"/>
        </div>
    </form:form>
</div>

</body>
</html>