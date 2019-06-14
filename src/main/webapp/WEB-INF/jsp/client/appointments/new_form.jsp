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
    <div class="container">
        <form:form action="/client/appointments/new_form" method="post" modelAttribute="newAppointment">
            <div class="w3-container w3-cell">
                <form:label path="appointmentDate">Data wizyty: </form:label> <br>
                <form:input type="date" placeholder="Data wizyty" path="appointmentDate"/> <br>
                ${errorMessage}
            </div>
            <div class="w3-container w3-cell">
                <form:label path="doctor">Lekarz: </form:label> <br>
                <form:select path="doctor" items="${doctorList}"/>
            </div>
            <div class="w3-container w3-cell">
                <form:label path="patient">Pacjent: </form:label> <br>
                <form:select path="patient" items="${patientList}"/>
            </div>
            <form:button type="submit">Filtruj</form:button>
        </form:form>
    </div>
</div>
</body>
</html>
