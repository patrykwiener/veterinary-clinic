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

        .error {
            text-align: center;
            color: red;
        }
    </style>
    <script type="text/javascript">
        function load_page() {
            document.getElementById("startDate").value = new Date().toISOString().split("T")[0];
            var tomorrow = new Date();
            tomorrow.setDate(tomorrow.getDate() + 1);
            document.getElementById("endDate").value = tomorrow.toISOString().split("T")[0];
        }

    </script>
</head>
<body onload="load_page()">
${banner}
<div class="w3-container center">
    <br>
    <form:form action="/worker/report/report_parameters" method="post" modelAttribute="workerReport">
        <div class="w3-cell-row">
            <div class="w3-container w3-cell">
                <h1>Data początkowa</h1>
                <form:input path="startDate" type="date" id="startDate" class="w3-border"/>
            </div>
            <div class="w3-container w3-cell">
                <h1>Data końcowa</h1>
                <form:input path="endDate" type="date" id="endDate" class="w3-border"/>
            </div>
        </div>
        <form:errors path="endDate" cssClass="error"/>
        <br>
        <br>
        <form:button type="submit" class="w3-button w3-blue w3-hover-gray submit">Generuj</form:button>
    </form:form>
</div>
</body>
</html>
