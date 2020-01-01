<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Klinika weterynaryjna</title>
    <meta charset=UTF-8/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>

<body>
${banner}

<div class="w3-container">
    <br>
    <table class="w3-table-all w3-centered">
        <tr>
            <th>Imię</th>
            <th>Nazwisko</th>
            <th>Numer Telefonu</th>
        </tr>
        <c:forEach var="doctor" items="${allDoctors}">
            <tr>
                <td>${doctor.firstName}</td>
                <td>${doctor.lastName}</td>
                <td>${doctor.phoneNumber}</td>
            </tr>
        </c:forEach>
    </table>

</div>


</body>
</html>
