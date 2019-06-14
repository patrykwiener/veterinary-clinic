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
    Wybierz godzinę:
    <form:form action="/client/appointments/new" method="post" modelAttribute="newAppointment">
        <form:select path="appointmentDate" items="${availableDates}" size="16"/> <br>
        <form:button name="action" value="save">Zapisz wizytę</form:button>
        <form:button name="action" value="cancel">Cofnij</form:button>
    </form:form>

</div>
</body>
</html>
