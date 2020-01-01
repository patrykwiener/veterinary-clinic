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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script type="text/javascript">

      function validate_select(){
        var select = $("#select_client");
        if ($("#select_client").val() != ""){
          $("#edit").attr("disabled", false);
        }
        else {
          $("#edit").attr("disabled", true);
        }
      }

      function load_page() {
        $("#edit").attr("disabled", true);
      }

    </script>
</head>
<body onload="load_page()">
${banner}
<div class="w3-container center">

      <h1>Wybierz klienta</h1>
    <form:form action="/worker/client/choose_client" method="post" modelAttribute="chosenClient">
      <form:select path="userId" class="w3-select w3-border" onchange="validate_select()" id="select_client">
        <form:option value="" label="..." />
        <form:options items="${clientList}" itemLabel="fullName"/>"
      </form:select>
        <form:errors path="userId" cssClass="error"/>
        <br>
        <br>
        <form:button type="submit" class="w3-button w3-blue w3-hover-gray submit" id="edit">Edytuj</form:button>
    </form:form>
</div>
</body>
</html>
