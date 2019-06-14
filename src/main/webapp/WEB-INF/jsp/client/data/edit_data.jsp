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

<div class="w3-cell-row">
    <div class="w3-container w3-cell">
        <div class="container">
            <form:form action="/client/data/edit_data" method="post" modelAttribute="newEditedUser">
                <form:hidden path="userId" value="${loggedUser.userId}" />

                <form:label path="firstName">Imię: </form:label>
                <form:input type="text" placeholder="Imię" path="firstName" maxlength="20"/>
                <form:errors path="firstName"/>
                <br>
                <form:label path="lastName">Nazwisko: </form:label>
                <form:input type="text" placeholder="Nazwisko" path="lastName" maxlength="20"/>
                <form:errors path="lastName"/>
                <br>
                <form:label path="login">Login: </form:label>
                <form:input type="text" placeholder="Login" path="login" maxlength="20"/>
                <form:errors path="login"/>
                <br>
                <form:label path="password">Hasło: </form:label>
                <form:input type="password" placeholder="Hasło" path="password" maxlength="20"/>
                <form:errors path="password"/>
                <br>
                <form:label path="confirmPassword">Powtórz hasło: </form:label>
                <form:input type="password" placeholder="Hasło" path="confirmPassword" maxlength="20"/>
                <form:errors path="confirmPassword"/>
                <br>
                <form:label path="phoneNumber">Numer telefonu: </form:label>
                <form:input type="tel" placeholder="Numer telefonu" path="phoneNumber" maxlength="9"/>
                <form:errors path="phoneNumber"/>
                <br>
                <form:button name="action" value="save" type="submit">Zapisz zmiany</form:button>
                <form:button name="action" value="cancel" type="submit">Odrzuć zmiany</form:button>
            </form:form>
        </div>
    </div>
</div>


</body>
</html>
