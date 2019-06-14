<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <form:form action="/client/appointments/future_form" method="post" modelAttribute="appointmentFilter">
        <form:label path="startingAppointmentDate">Data początkowa: </form:label> <br>
        <form:input type="date" placeholder="Data początkowa" path="startingAppointmentDate"/> <br>
        <form:errors path="startingAppointmentDate"/>

        <form:label path="endingAppointmentDate">Data końcowa: </form:label> <br>
        <form:input type="date" placeholder="Data końcowa" path="endingAppointmentDate"/> <br>
        <form:errors path="endingAppointmentDate"/>
        <br>
        Lekarze:
        <br>
        <form:checkboxes path="doctors" items="${doctorList}" itemValue="reference" itemLabel="fullName" delimiter=" "/> <br>
        <form:errors path="doctors" />
        <br>
        Pacjenci:
        <br>
        <form:checkboxes path="patients" items="${patientList}" itemValue="reference" itemLabel="patientName" delimiter=" "/> <br>
        <form:errors path="patients" />
        <br>
        <form:button type="submit">Filtruj</form:button>

    </form:form>
</div>

</body>
</html>
