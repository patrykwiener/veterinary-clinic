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

Usuwasz teraz zwierzaka: ${editedPatient.patientName}

<div class="container">
    <form:form action="/client/pets/delete_pet" method="post" modelAttribute="newEditedPatient">
        <form:label path="dateOfDeath">Data śmierci (po wypełnieniu tego pola wszystkie przyszłe wizyty zostaną
            usunięte): </form:label> <br>
        <form:input type="date" placeholder="Data śmierci" path="dateOfDeath"/> <br>
        <form:errors path="dateOfDeath"/>
        <br>
        <form:button type="submit" name="action" value="save">Zapisz zmiany</form:button>
        <form:button type="submit" name="action" value="cancel">Odrzuć zmiany</form:button>
    </form:form>
</div>

</body>
</html>
