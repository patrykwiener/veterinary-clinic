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

Edytujesz teraz pracownika: ${editedWorker.lastName}

<div class="container">
	<form:form action="/admin/admin_workers_edit" method="post" modelAttribute="newEditedWorker">
		<form:hidden path="userId" value="${editedWorker.userId}" />
    	<form:label path="firstName">Imię: </form:label>
        <form:input type="text" maxlength="20" value = "${editedWorker.firstName}" placeholder="Imię" path="firstName"/>
        <form:errors path="firstName"/>
        <br>
        <form:label path="lastName">Nazwisko: </form:label>
        <form:input type="text" maxlength="20" value = "${editedWorker.lastName}" placeholder="Nazwisko" path="lastName"/>
        <form:errors path="lastName"/>
        <br>
        <form:label path="login">Login: </form:label>
        <form:input type="text" maxlength="20" value = "${editedWorker.login}" placeholder="Login" path="login"/>
        <form:errors path="login"/>
        <br>
        <form:label path="password">Hasło: </form:label>
        <form:input type="password" maxlength="20" value = "${editedWorker.password}" placeholder="Hasło" path="password"/>
        <form:errors path="password"/>
        <br>
        <form:label path="confirmPassword">Powtórz hasło: </form:label>
        <form:input type="password" maxlength="20" value = "${editedWorker.password}" placeholder="Hasło" path="confirmPassword"/>
        <form:errors path="confirmPassword"/>
        <br>
        <form:label path="phoneNumber">Numer telefonu: </form:label>
        <form:input type="tel" maxlength="20" value = "${editedWorker.phoneNumber}" placeholder="Numer telefonu" path="phoneNumber"/>
        <form:errors path="phoneNumber"/>
        <br>
        <form:button type="submit" name="action" value="save">Zapisz zmiany</form:button>
        <form:button type="submit" name="action" value="cancel">Odrzuć zmiany</form:button>
	</form:form>
</div>


</body>
</html>