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

Edytujesz teraz usługę: ${editedService.serviceType}

<div class="container">
	<form:form action="/admin/admin_services_edit" method="post" modelAttribute="newEditedService">
		<form:hidden path="serviceId" value="${editedService.serviceId}" />
    	<form:label path="serviceType">Rodzaj usługi: </form:label>
        <form:input type="text" maxlength="50" value = "${editedService.serviceType}" placeholder="Rodzaj usługi" path="serviceType"/>
        <form:errors path="serviceType"/>
		<br>
        <form:label path="serviceCost">Cena usługi: </form:label>
        <form:input type="number" value = "${editedService.serviceCost}" min="0" max="999.99" step="0.01" placeholder="Cena usługi" path="serviceCost"/>
        <form:errors path="serviceCost"/>
        <br>
        <form:button type="submit" name="action" value="save">Zapisz zmiany</form:button>
        <form:button type="submit" onclick="location.href='/admin/admin_services'" name="action" value="cancel">Odrzuć zmiany</form:button>
	</form:form>
</div>


</body>
</html>