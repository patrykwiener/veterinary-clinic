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

    <form:form action="/admin/admin_medicines" method="post" modelAttribute="medicine">
        <div class="w3-container w3-cell">
            <form:button name="action" value="add">Dodaj lek</form:button> <br>
            <!-- FOR FUTURE DEVELOPEMENT: removing medicines -->
<!--        <form:button name="action" value="remove">Usuń lek</form:button> <br>   -->
            <form:button name="action" value="edit">Edytuj dane leku</form:button> <br>
        </div>
        <div class="w3-container w3-cell">
            <form:select path="medicineId" items="${medicineList}" size="10"/>
        </div>
    </form:form>
</div>

</body>
</html>