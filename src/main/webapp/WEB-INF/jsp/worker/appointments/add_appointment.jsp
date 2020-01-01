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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <style media="screen">
        .submit {
            width: 100%
        }
        .error{
          text-align: center;
          color: red;
        }
        .center {
    margin: auto;
    width: 60%;

    padding: 10px;
  }
    </style>
    <script type="text/javascript">
        function get_patients() {
            return $.ajax({
                type: "GET",
                url: "/worker/appointments/add_appointment/get_patients",
                success: function (data) {
                    var select = $("#select_patient");
                    select.empty();
                    select.append("<option value=\"\">...</option>");
                    $.each(data, function (index, patient) {
                        var option = "<option value=" + patient.patientId + ">" + patient.patientName + "</option>";
                        select.append(option);
                    });
                }
            });
        }

        function get_clients() {

            return $.ajax({
                type: "GET",
                url: "/worker/appointments/add_appointment/get_clients",
                success: function (data) {
                    var select = $("#select_client");
                    var selected_client = select.val();

                    select.empty();
                    select.append("<option value=\"\">...</option>");
                    $.each(data, function (index, client) {
                        var option = "<option value=" + client.userId + ">" + client.firstName + " " + client.lastName + "</option>";
                        select.append(option);
                    });
                    select.val(selected_client);
                }
            });
        }

        function set_client() {

            $.ajax({
                type: "GET",
                url: "/worker/appointments/add_appointment/select_client",
                data: {
                    "id": $("#select_client").val()
                },
                success: function () {
                    get_patients();
                }
            })
        }

        function set_patient() {
            $.ajax({
                type: "GET",
                url: "/worker/appointments/add_appointment/select_patient",
                data: {
                    "id": $("#select_patient").val()
                },
                success: function () {

                }
            })
        }

        function load_page() {
            $.when(get_patients(), get_clients()).then();

            document.getElementById("datePickerId").value = new Date().toISOString().split("T")[0];
            setTimeout(update, 100);
        }

        function update() {
            $("#select_client").val("${chosenClient}")
            $("#select_patient").val("${chosenPatient}")
        }

    </script>

</head>
<body onload="load_page()">
${banner}



<div class="w3-container center">
            <form:form action="/worker/appointments/add_appointment" method="post" modelAttribute="appointmentAddition">

                <h1>Klient</h1>
                <form:select path="clientId" class="w3-select w3-border" onchange="set_client()" name="select_client"
                             id="select_client">
                </form:select>
                <form:errors path="clientId" cssClass="error"/>
                <br><br>
                <h1>Pacjent</h1>
                <form:select path="patientId" class="w3-select w3-border" onchange="set_patient()" name="select_patient"
                             id="select_patient">
                </form:select>
                <form:errors path="patientId" cssClass="error"/>

                <br><br><br><br>
                <div class="w3-cell-row">
                    <div class="w3-container w3-cell">


                        <h1>Data</h1>
                        <form:input path="appointmentDate" type="date" onkeydown="return false" id="datePickerId"/>
                    </div>
                    <div class="w3-container w3-cell">
                        <h1>Czas</h1>
                        <form:input path="appointmentDate" type="time" step="1800" min="08:00" max="16:00"
                                    id="timePickerId" value="08:00"/>
                    </div>
                </div>
                <form:errors path="appointmentDate" cssClass="error"/>
                <br><br>

                <form:button type="submit" class="w3-button w3-blue w3-hover-gray submit">Dodaj</form:button>
            </form:form>

</div>
</body>

</html>
