<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Klinika weterynaryjna</title>
    <meta charset=UTF-8/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>

<body>
${banner}

<div class="w3-display-container">
    <div class="w3-display-topmiddle">


        <form class="contact_data_form">
            <h1>DANE KONTAKTOWE</h1>
            <p>tel. ${companyData.phoneNumber}</p>

            <p>
                ${companyData.companyName} <br>
                ul. ${companyData.street} <br>
                 ${companyData.postcode}  ${companyData.city} <br>
                 woj. ${companyData.voivodeship} <br>
                 ${companyData.country} <br>
            </p>

            <p>e-mail: ${companyData.email}</p>

            <p>
                godziny otwarcia: <br>
                Poniedziałek: 08:00 - 16:00 <br>
                Wtorek: 08:00 - 16:00 <br>
                Środa: 08:00 - 16:00 <br>
                Czwartek: 08:00 - 16:00 <br>
                Piątek: 08:00 - 16:00 <br>
                Sobota: 08:00 - 16:00 <br>
                Niedziela: 08:00 - 16:00 <br>
            </p>


        </form>
    </div>
</div>


</body>
</html>
