<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Klinika weterynaryjna</title>
    <meta charset=UTF-8/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="style.css">
</head>
<body>
${banner}

<div class="container">
    Imię: ${loggedUser.firstName} <br>
    Nazwisko: ${loggedUser.lastName} <br>
    Login: ${loggedUser.login} <br>
    Numer telefonu: ${loggedUser.phoneNumber} <br>

    <a href="/client/data/edit_data">Zmień dane</a>
</div>

</body>
</html>
