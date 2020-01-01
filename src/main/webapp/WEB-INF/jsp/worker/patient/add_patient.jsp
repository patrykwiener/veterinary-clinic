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
            width: 60%;

            padding: 10px;
        }

        .submit {
            width: 100%
        }

        input {
            margin: auto;
            width: 50%;
        }

        .error {
            text-align: center;
            color: red;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script type="text/javascript">

        function validate_parameters() {
            var select = $("#select_client");
            var input = $("#patient_name_input");
            if (select.val() != "" && input.val() != "") {
                $("#add").attr("disabled", false);
            } else {
                $("#add").attr("disabled", true);
            }
        }

        function load_page() {
            $("#add").attr("disabled", true);
        }

    </script>
</head>
<body onload="load_page()">
${banner}
<div class="w3-container center">

    <h1>Dodaj pacjenta</h1>
    <form:form action="/worker/patient/add_patient" method="post" modelAttribute="newPatient">
        <h3>Klient</h3>
        <form:select path="owner" class="w3-select w3-border" onchange="validate_parameters()" id="select_client">
            <form:option value="" label="..."/>
            <form:options items="${ownerList}" itemLabel="fullName"/>"
        </form:select>
        <br><br>
        <h3>Nowy pacjent</h3>
        <form:input path="patientName" type="text" placeholder="Imię" maxlength="20" oninput="validate_parameters()" onpaste="validate_parameters()" id="patient_name_input"/>
        <br>
        <form:errors path="patientName" cssClass="error"/>
        <br><br><br>
        <form:button type="submit" class="w3-button w3-blue w3-hover-gray submit" id="add">Dodaj</form:button>
    </form:form>
</div>
</body>
</html>
