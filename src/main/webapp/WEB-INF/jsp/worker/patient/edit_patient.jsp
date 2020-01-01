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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
      <style media="screen">
          .submit {
              width: 100%
          }

          .error {
              text-align: center;
              color: red;
          }

          .center {
              margin: auto;
              width: 60%;

              padding: 10px;
          }
          .input_data {
              margin: auto;
              width: 50%;
          }
      </style>
        <script type="text/javascript">

        function validate_checkbox(){
          var checkbox = $("#deathIndicator")
          var editButton = $("#edit")
          if (checkbox.is(":checked")){
            $("#dateInput").attr("disabled", false);
          }
          else {
            $("#dateInput").attr("disabled", true);
          }
        }

        function load_page() {
            validate_checkbox()
            document.getElementById("dateInput").value = new Date().toISOString().split("T")[0];
            document.getElementById("dateInput").max = new Date().toISOString().split("T")[0];
        }

        </script>
  </head>
  <body onload="load_page()">
${banner}
<div class="w3-container center">
    <h1>Edytuj dane clienta</h1>
    <form:form action="/worker/patient/edit_patient" method="post" modelAttribute="editedPatient">
      <form:hidden path="patientId" value="${editedPatient.patientId}" />
      <h3>Imię: </h3>
      <form:input type="text" placeholder="Imię" path="patientName" maxlength="20" class="input_data"/>
      <form:errors path="patientName" class="error"/>
      <br>
      <h3>Data śmierci</h3>
      <input type="checkbox" id="deathIndicator" onchange="validate_checkbox()"/>
      <form:input type="date" path="dateOfDeath" id="dateInput" />
      <br><br>
      <form:button type="submit" class="w3-button w3-blue w3-hover-gray submit">Edytuj</form:button>

    </form:form>
</div>
  </body>
</html>
