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

Edytujesz teraz lek: ${editedMedicine.medicineName}

<div class="container">
	<form:form action="/admin/admin_medicines_edit" method="post" modelAttribute="newEditedMedicine">
		<form:hidden path="medicineId" value="${editedMedicine.medicineId}" />
    	<form:label path="medicineName">Nazwa leku: </form:label>
        <form:input type="text" maxlength="50" value = "${editedMedicine.medicineName}" placeholder="Nazwa leku" path="medicineName"/>
        <form:errors path="medicineName"/>
        <br>
        <form:label path="purchasedItems">Liczba zakupionych sztuk: </form:label>
        <form:input type="number" value = "${editedMedicine.purchasedItems}" min="0" max="99999999999" placeholder="Liczba zakupionych sztuk" path="purchasedItems"/>
        <form:errors path="purchasedItems"/>
        <br>
        <form:label path="remainingItems">Liczba pozostałych sztuk: </form:label>
        <form:input type="number" value = "${editedMedicine.remainingItems}" min="0" max="99999999999" placeholder="Liczba pozostałych sztuk" path="remainingItems"/>
        <form:errors path="remainingItems"/>
        <br>
        <form:label path="unit">Jednostka: </form:label>
        <form:input type="text" maxlength="10" value = "${editedMedicine.unit}" placeholder="Jednostka" path="unit"/>
        <form:errors path="unit"/>
        <br>
        <form:label path="purchasePrice">Cena kupna: </form:label>
        <form:input type="number" value = "${editedMedicine.purchasePrice}" min="0" step="0.01" max="999.99" placeholder="Cena kupna" path="purchasePrice"/>
        <form:errors path="purchasePrice"/>
        <br>
        <form:label path="purchaseDate">Data kupna: </form:label>
        <form:input type="date" value = "${editedMedicine.purchaseDate}" placeholder="Data kupna" path="purchaseDate"/>
        <form:errors path="purchaseDate"/>
        <br>
        <form:label path="soldItemsPrice">Cena sprzedaży sztuki: </form:label>
        <form:input type="number" value = "${editedMedicine.soldItemsPrice}" min="0" step="0.01" max="999.99" placeholder="Cena sprzedaży sztuki" path="soldItemsPrice"/>
        <form:errors path="soldItemsPrice"/>
        <br>
        <form:button type="submit" name="action" value="save">Zapisz zmiany</form:button>
        <form:button type="submit" onclick="location.href='/admin/admin_medicines'" name="action" value="cancel">Odrzuć zmiany</form:button>
	</form:form>
</div>


</body>
</html>