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
    <script type="text/javascript">

        function get_patients() {
            return $.ajax({
                type: "GET",
                url: "/worker/appointments/history/get_patients",
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
                url: "/worker/appointments/history/get_clients",
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

        function select_client() {
            $.ajax({
                type: "GET",
                url: "/worker/appointments/history/select_client",
                data: {
                    "id": $("#select_client").val()
                },
                success: function () {
                    $.when(get_patients(), get_appointments()).then();
                }
            })
        }

        function select_patient() {
            $.ajax({
                type: "GET",
                url: "/worker/appointments/history/select_patient",
                data: {
                    "id": $("#select_patient").val()
                },
                success: function () {
                    $.when(get_clients(), get_appointments()).then();
                }
            })
        }

        function get_appointments() {
            return $.ajax({
                type: "GET",
                url: "/worker/appointments/history/get_appointments",
                success: function (data) {
                    var table = $("#appointment_table");
                    table.empty();
                    table.append("<tr><th>Data</th><th>Klient</th><th>Imię pacjenta</th></tr>");
                    $.each(data, function (index, appointment) {
                        var dateOptions = {day: '2-digit', month: '2-digit', year: '2-digit'};
                        var timeOptions = {hour12: false, hour: '2-digit', minute: '2-digit'};
                        var date = new Date(appointment.appointmentDate);
                        var dateToDisplay = date.toLocaleDateString('en-GB', dateOptions) + ' ' + date.toLocaleTimeString('en-GB', timeOptions);
                        var client = appointment.patient.owner.firstName + ' ' + appointment.patient.owner.lastName;
                        var patient = appointment.patient.patientName;
                        table.append("<tr><td>" + dateToDisplay + "</td><td>" + client + "</td><td>" + patient + "</td></tr>");
                    });
                }
            });
        }

        function load_page() {
            $.when(get_patients(), get_clients(), get_appointments()).then();
        }

    </script>
</head>
<body onload="load_page()">

${banner}

<div class="w3-container">
    <h1>Historia wizyt</h1>
    <div class="w3-row-padding">
        <div class="w3-half">
            <label>Klient</label>
            <select class="w3-select w3-border" onchange="select_client()" id="select_client">

            </select>
        </div>
        <div class="w3-half" >
            <label>Pacjent</label>
            <select class="w3-select w3-border" onchange="select_patient()" id="select_patient">

            </select>
        </div>
    </div>
    <br>

    <table class="w3-table-all w3-centered" id="appointment_table">
    </table>

</div>


</body>
</html>
