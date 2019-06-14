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

Edytujesz teraz zwierzaka: ${editedPatient.patientName}

<div class="container">
    <form:form action="/client/pets/edit_pet" method="post" modelAttribute="newEditedPatient">
        <form:hidden path="patientId" value="${editedPatient.patientId}" />
        <form:label path="patientName">Nowe imię zwierzaka: </form:label> <br>
        <form:input type="text" placeholder="Nowe imię zwierzaka" path="patientName" maxlength="20"/> <br>
        <form:errors path="patientName"/>
        <br>
        <form:button type="submit" name="action" value="save">Zapisz zmiany</form:button>
        <form:button type="submit" name="action" value="cancel">Odrzuć zmiany</form:button>
    </form:form>
</div>

</body>
</html>
