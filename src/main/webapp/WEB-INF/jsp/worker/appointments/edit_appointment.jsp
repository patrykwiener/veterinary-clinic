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
    <link rel="stylesheet" type="text/css" href="style.css">
    <style media="screen">
        .submit {
            width: 100%
        }

        .center {
            margin: auto;
            width: 60%;

            padding: 10px;
        }

        .pointer {
            cursor: pointer;
        }
        .input {
            width: 50%
        }
        .error{
          text-align: center;
          color: red;
        }
    </style>
    <script type="text/javascript">
      function activateNumberInput(id){
        var checkbox = document.getElementById(id);
        var input = document.getElementById("input" + id);
        if (checkbox.checked == true){
          input.disabled = false;
        } else{
          input.disabled = true;
        }
      }
    </script>
</head>
<body>
${banner}
<br>
<div class="w3-container center">
    <div class="w3-cell-row">
        <div class="w3-container w3-blue w3-cell">
            <h3>${owner} - ${patient}</h3>
        </div>
        <div class="w3-container w3-blue w3-cell">
            <h3 class="w3-right-align">${appointmentDate}</h3>
        </div>
    </div>
    <br>
    <form:form action="/worker/appointments/edit_appointment" method="post" modelAttribute="paymentFilter">
        <table class="w3-table-all">
            <tr>
                <th></th>
                <th>Usługa</th>
                <th>Koszt</th>
            </tr>
            <c:forEach var="service" items="${serviceList}">
                <tr>
                    <td><form:checkbox path="services" value="${service.serviceId}" class="pointer" /></td>
                    <td>${service.serviceType}</td>
                    <td>${service.serviceCost}</td>
                </tr>
            </c:forEach>
            <form:errors path="services" cssClass="error"/>
        </table>
        <br>
        <table class="w3-table-all">
            <tr>
                <th></th>
                <th>Lek</th>
                <th class="w3-center">Koszt</th>
                <th class="w3-center">Liczba</th>
                <th class="w3-center">Limit</th>
            </tr>
            <c:forEach var="medicine" items="${medicineCounterList}">
                <tr>
                    <td><form:checkbox path="medicineNames" value="${medicine.medicineName}" id="${medicine.medicineName}" class="pointer" onclick="activateNumberInput(id)"/></td>
                    <td>${medicine.medicineName}</td>
                    <td class="w3-center">${medicine.soldItemsPrice}</td>
                    <td class="w3-center"><form:input path="requestedItems" type="number" min="0" max="${medicine.medicineCount}" value="0" id="input${medicine.medicineName}" disabled="true" /></td>
                    <td class="w3-center">${medicine.medicineCount}</td>
                </tr>
            </c:forEach>
        </table>
        <br>


        <form:button type="submit" class="w3-button w3-blue w3-hover-gray submit">Dodaj</form:button>
    </form:form>
    <br>


</div>
</body>

</html>
