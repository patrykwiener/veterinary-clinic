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
    <table border="1">
        <tr>
            <th>Data</th>
            <th>Imię lekarza</th>
            <th>Nazwisko lekarza</th>
            <th>Imię pacjenta</th>
        </tr>
        <c:forEach var="appointment" items="${futureAppointments}">
            <tr>
                <td><fmt:formatDate value="${appointment.appointmentDate}" type="both" dateStyle="medium"
                                    timeStyle="short"/></td>
                <td>${appointment.doctor.firstName}</td>
                <td>${appointment.doctor.lastName}</td>
                <td>${appointment.patient.patientName}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
