<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<div class="w3-cell-row">
    <div class="w3-container w3-cell">
        <div class="container">
            <form:form action="/" method="post" modelAttribute="user">
                Zaloguj
                <br>
                <form:label path="login">Login: </form:label>
                <form:input type="text" placeholder="Login" path="login" maxlength="20"/>
                <form:errors path="login"/>
                <br>
                <form:label path="password">Hasło: </form:label>
                <form:input type="password" placeholder="Hasło" path="password" maxlength="20"/>
                <form:errors path="password"/>
                <br>
                <form:button>Zaloguj</form:button>
            </form:form>
        </div>
    </div>
    Nie masz konta?
    <br>
    <a href="/register_client">Zarejestruj się!</a>
    <br>
</div>


</body>
</html>
