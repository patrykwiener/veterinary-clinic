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

        input {
            margin: auto;
            width: 100%;
        }

        .submit {
            width: 100%
        }
        .error {
            text-align: center;
            color: red;
        }
    </style>
</head>
<body>

${banner}
<div class="w3-container center">
    <h1>Edytuj dane clienta</h1>
    <form:form action="/worker/client/edit_client" method="post" modelAttribute="newEditedUser">
        <form:hidden path="userId" value="${clientToEdit.userId}"/>
        <h3>Imię: </h3>
        <form:input type="text" placeholder="Imię" path="firstName" maxlength="20"/>
        <form:errors path="firstName" class="error"/>
        <br>
        <h3>Nazwisko: </h3>
        <form:input type="text" placeholder="Nazwisko" path="lastName" maxlength="20"/>
        <form:errors path="lastName" class="error"/>
        <br>
        <h3>Login: </h3>
        <form:input type="text" placeholder="Login" path="login" maxlength="20"/>
        <form:errors path="login" class="error"/>
        <br>
        <h3>Hasło: </h3>
        <form:input type="password" placeholder="Hasło" path="password"  maxlength="20"/>
        <form:errors path="password" class="error"/>
        <br>
        <h3>Powtórz hasło: </h3>
        <form:input type="password" placeholder="Hasło" path="confirmPassword"  maxlength="20"/>
        <form:errors path="confirmPassword" class="error"/>
        <br>
        <h3>Numer telefonu:</h3>
        <form:input type="tel" placeholder="Numer telefonu" path="phoneNumber" maxlength="9"/>
        <form:errors path="phoneNumber" class="error"/>
        <br><br><br>
        <form:button type="submit" class="w3-button w3-blue w3-hover-gray submit">Edytuj</form:button>
        <br>
    </form:form>
</div>
</body>
</html>
