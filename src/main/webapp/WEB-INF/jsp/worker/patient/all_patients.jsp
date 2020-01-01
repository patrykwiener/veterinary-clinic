<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <title>Klinika weterynaryjna</title>
    <meta charset=UTF-8/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="style.css">
    <style media="screen">
    .center {
        margin: auto;
        width: 80%;

        padding: 10px;
    }
    </style>
  </head>
  <body>
    ${banner}
    <div class="w3-container center">
      <h1>Żywi pacjenci</h1>
      <table class="w3-table-all">
        <tr>
          <th>Klient</th>
          <th>Pacjent</th>
        </tr>
        <c:forEach var="patient" items="${livingPatientList}">
          <tr>
            <td>${patient.owner.fullName}</td>
            <td>${patient.patientName}</td>
          </tr>
          </c:forEach>
      </table>
      <br>
      <h1>Nieżywi pacjenci</h1>
      <table class="w3-table-all">
        <tr>
          <th>Klient</th>
          <th>Pacjent</th>
          <th>Data zgonu</th>
        </tr>
        <c:forEach var="patient" items="${deadPatientList}">
          <tr>
            <td>${patient.owner.fullName}</td>
            <td>${patient.patientName}</td>
            <td><fmt:formatDate value="${patient.dateOfDeath}" type="both" dateStyle="medium"
                                    timeStyle="short" pattern="dd/MM/YYYY"/></td>
          </tr>
          </c:forEach>
      </table>
      <br><br>
    </div>
  </body>
</html>
