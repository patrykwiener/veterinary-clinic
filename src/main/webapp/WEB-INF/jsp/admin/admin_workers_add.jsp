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
	<form:form action="/admin/admin_workers_add" method="post" modelAttribute="user">
    	<form:label path="firstName">Imię: </form:label>
        <form:input type="text" maxlength="20" placeholder="Imię" path="firstName"/>
        <form:errors path="firstName"/>
        <br>
        <form:label path="lastName">Nazwisko: </form:label>
        <form:input type="text" maxlength="20" placeholder="Nazwisko" path="lastName"/>
        <form:errors path="lastName"/>
        <br>
        <form:label path="login">Login: </form:label>
        <form:input type="text" maxlength="20" placeholder="Login" path="login"/>
        <form:errors path="login"/>
        <br>
        <form:label path="password">Hasło: </form:label>
        <form:input type="password" maxlength="20" placeholder="Hasło" path="password"/>
        <form:errors path="password"/>
        <br>
        <form:label path="confirmPassword">Powtórz hasło: </form:label>
        <form:input type="password" maxlength="20" placeholder="Hasło" path="confirmPassword"/>
        <form:errors path="confirmPassword"/>
        <br>
        <form:label path="phoneNumber">Numer telefonu: </form:label>
        <form:input type="tel" maxlength="20" placeholder="Numer telefonu" path="phoneNumber"/>
        <form:errors path="phoneNumber"/>
        <br>
        <form:button type="submit">Dodaj pracownika</form:button>
	</form:form>
</div>


</body>
</html>