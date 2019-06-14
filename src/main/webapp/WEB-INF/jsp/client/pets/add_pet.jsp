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

<div class="container">
<form:form action="/client/pets/add_pet" method="post" modelAttribute="patient">
    <form:label path="patientName">Imię: </form:label> <br>
    <form:input path="patientName" type="text" maxlength="20"/> <br>
    <form:errors path="patientName"/> <br>
    <form:button type="submit" name="action" value="save">Dodaj zwierzaka</form:button>
    <form:button type="submit" name="action" value="cancel">Wróć</form:button>
</form:form>
</div>

</body>
</html>
