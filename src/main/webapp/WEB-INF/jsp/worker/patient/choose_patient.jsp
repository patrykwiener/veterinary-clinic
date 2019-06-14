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

        .error {
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
                url: "/worker/patient/choose_patient/get_patients",
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
                url: "/worker/patient/choose_patient/get_clients",
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
                url: "/worker/patient/choose_patient/select_client",
                data: {
                    "id": $("#select_client").val()
                },
                success: function () {
                    get_patients();
                    validate_parameters();
                }
            })
        }

        function validate_parameters() {
            var select_client = $("#select_client");
            var select_patient = $("#select_patient");
            if (select_client.val() != "" && select_patient.val() != "") {
                $("#edit").attr("disabled", false);
            } else {
                $("#edit").attr("disabled", true);
            }
        }

        function load_page() {
            $.when(get_patients(), get_clients()).then();
            $("#edit").attr("disabled", true);
        }

    </script>

</head>
<body onload="load_page()">
${banner}
<div class="w3-container center">
    <form:form action="/worker/patient/choose_patient" method="post" modelAttribute="chosenPatient">
        <h1>Wybierz pacjenta</h1>
        <h3>Klient</h3>
        <select class="w3-select w3-border" onchange="set_client()" name="select_client"
                     id="select_client">
        </select>
        <br><br>
        <h3>Pacjent</h3>
        <form:select path="patientId" class="w3-select w3-border" onchange=" validate_parameters()" name="select_patient"
                     id="select_patient">
        </form:select>
        <form:errors path="patientId" cssClass="error"/>
        <br><br><br>
        <form:button type="submit" class="w3-button w3-blue w3-hover-gray submit" id="edit">Edytuj</form:button>
    </form:form>

</div>
</body>

</html>
