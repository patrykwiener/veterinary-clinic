<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello World!</title>
    <meta charset=UTF-8/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel='stylesheet' href='style.css'>
</head>
<body>

<div class="w3-bar w3-black">
    <a href="#" class="w3-bar-item w3-button">Kontakt</a>
    <a href="#" class="w3-bar-item w3-button">Nasi lekarze</a>
</div>

<div class="w3-cell-row">
    <div class="w3-container w3-cell">
        <div class="container">
            <form action="#" method="post">
                Nie masz konta?
                <br>
                Zarejestruj się!
                <br>
                <label for="first_name">Imię: </label>
                <input type="text" placeholder="Imię" id="first_name" name="first_name" required>
                <br>
                <label for="last_name">Nazwisko: </label>
                <input type="text" placeholder="Nazwisko" id="last_name" name="last_name" required>
                <br>
                <label for="rlogin">Login: </label>
                <input type="text" placeholder="Login" id="rlogin" name="login" required>
                <br>
                <label for="rpassword">Hasło: </label>
                <input type="password" placeholder="Hasło" id="rpassword" name="password" required>
                <br>
                <label for="repeat_password">Powtórz hasło: </label>
                <input type="password" placeholder="Hasło" id="repeat_password" name="password" required>
                <br>
                <label for="tel_number">Numer telefonu: </label>
                <input type="text" placeholder="Numer telefonu" id="tel_number" name="password" required>
                <br>
                <button type="submit">Zarejestruj</button>
            </form>
        </div>
    </div>


    <div class="w3-container w3-cell">
        <div class="container">
            <form action="redirect_login" method="post">
                Zaloguj
                <br>
                <label for="llogin">Login: </label>
                <input type="text" placeholder="Login" id="llogin" name="login" required>
                <br>
                <label for="lpassword">Hasło: </label>
                <input type="password" placeholder="Hasło" id="lpassword" name="password" required>
                <br>
                <button type="submit">Zaloguj</button>
            </form>
        </div>

    </div>
</div>


</body>
</html>
