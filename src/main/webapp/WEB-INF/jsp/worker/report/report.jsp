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
      <h1>Usługi</h1>
      <table class="w3-table-all">
          <tr>
              <th>Data</th>
              <th>Klient</th>
              <th>Pacjent</th>
              <th>Usługa</th>
              <th>Cena</th>
          </tr>
          <c:forEach var="paymentForService" items="${paymentForServiceList}">
            <tr>
              <td><fmt:formatDate value="${paymentForService.appointment.appointmentDate}" type="both" dateStyle="medium"
                                      timeStyle="short" pattern="dd/MM/YY HH:mm"/></td>
              <td>${paymentForService.client.firstName} ${paymentForService.client.lastName}</td>
              <td>${paymentForService.appointment.patient.patientName}</td>
              <td>${paymentForService.service.serviceType}</td>
              <td>${paymentForService.cost}</td>
            </tr>
          </c:forEach>
          <tr class="w3-blue">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>${paymentForServicesBetweenDates}</td>
          </tr>
        </table>
        <h3>Przychód: ${paymentForServicesBetweenDates}zł </h3>
        <br>
          <h1>Leki</h1>
        <table class="w3-table-all">
          <tr>
            <th>Data</th>
            <th>Klient</th>
            <th>Pacjent</th>
            <th>Lek</th>
            <th>Liczba</th>
            <th>Cena kupna</th>
            <th>Cena sprzedaży</th>
          </tr>
          <c:forEach var="paymentForMedicine" items="${paymentForMedicineList}">
            <tr>
              <td><fmt:formatDate value="${paymentForMedicine.appointment.appointmentDate}" type="both" dateStyle="medium"
                                      timeStyle="short" pattern="dd/MM/YY HH:mm"/></td>
              <td>${paymentForMedicine.client.firstName} ${paymentForMedicine.client.lastName}</td>
              <td>${paymentForMedicine.appointment.patient.patientName}</td>
              <td>${paymentForMedicine.medicine.medicineName}</td>
              <td>${paymentForMedicine.quantity}</td>
              <td>${paymentForMedicine.medicine.purchasePrice}</td>
              <td>${paymentForMedicine.medicine.soldItemsPrice}</td>
            </tr>
          </c:forEach>
          <tr class="w3-blue">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>${purchasePriceSum}</td>
            <td>${soldPriceSum}</td>
          </tr>
        </table>
        <h3>Przychód: ${profitSum}zł</h3>
        <br>
          <br>
    </div>
  </body>
</html>
