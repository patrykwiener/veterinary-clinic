/*
*********************************************************************
Name: MySQL-owa baza danych dla przychodni weterynaryjnej - cz.2 - update
Link: --
Version 1.0
Author: Maja Kuricus, majakur297@student.polsl.pl
*********************************************************************
*/

USE `przychodnia`;

/*---------------------------------------------------------------------------------------------------------------------*/

/* INSERT statements for testing: */

INSERT INTO pacjenci(id_wlasciciela, imie_pacjenta)
VALUES 
  (1, 'Hedwig'),
  (1, 'Nymeria');

INSERT INTO uzytkownicy(imie, nazwisko, rodzaj_uzytkownika, login, haslo, numer_telefonu)
VALUES
  ('Harry', 	'Potter', 		'K', 	'harryp', 	'alohomora', 		0),
  ('Arya', 		'Stark', 		'K', 	'aryas', 	'valarmorghulis', 	0),
  ('Alceste', 	'mikołajkowy', 	'K', 	'alcest', 	'123456', 			'123456'),
  ('klient', 	'test', 		'K', 	'klient', 	'klient', 			'123457'),
  ('lekarz', 	'test', 		'L', 	'lekarz', 	'lekarz', 			'123458');

INSERT INTO uzytkownicy(imie, nazwisko, rodzaj_uzytkownika, login, haslo, numer_telefonu)
VALUES
  ('Gregory', 'House', 'L', 'house', 'everybodylies', 911);

INSERT INTO wizyty(id_lekarza, id_pacjenta, data_wizyty)
VALUES
  ((SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Gregory' AND nazwisko = 'House'), 
		(SELECT id_pacjenta FROM pacjenci WHERE imie_pacjenta = 'Idefix'), '2019-07-12 10:00:00'),
  ((SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Gregory' AND nazwisko = 'House'), 1, '2020-01-01 10:00:00'),
  (1, (SELECT id_pacjenta FROM pacjenci WHERE imie_pacjenta = 'Idefix'), '2019-01-18 12:00:00');

INSERT INTO cennik(cena_uslugi, rodzaj_uslugi)
VALUES
  (300.00, 'operacja serca'),
  (200.00, 'amputacja łapki'),
  (150.00, 'wyrwanie zęba'),
  (50.00, 'nastawienie zwichniętego skrzydła'),
  (150.00, 'konsultacja'),
  (200.00, 'prosty zabieg'),
  (300.00, 'skomplikowany zabieg'),
  (500.00, 'operacja'),
  (50.00, 'zastrzyk'),
  (20.00, 'podanie leku'), 
  (150.00, 'nadzór całodobowy'),
  (15.00, 'test');

INSERT INTO oplaty_za_uslugi(id_wizyty, id_uslugi)
VALUES
  ((SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00'), (SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'podanie leku')),
  (NULL, (SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'test'));

INSERT INTO leki(nazwa_leku)
VALUES
  ('test');

INSERT INTO oplaty_za_leki(id_wizyty, id_leku)
VALUES
  ((SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00'), 1),
  (NULL, (SELECT id_leku FROM leki WHERE nazwa_leku = 'test'));
  /* test appointment date:  '2020-01-01 10:00:00' */

/*---------------------------------------------------------------------------------------------------------------------*/
/* UPDATE statements */

/* Update statements for table `pacjenci` */

/* update patient's name: */
UPDATE pacjenci
SET
  imie_pacjenta = 'Hedwiga'
WHERE
   imie_pacjenta = 'Hedwig';

/* update patient's date of death: */
UPDATE pacjenci
SET
  data_zgonu = '1997-07-27'
WHERE
  imie_pacjenta = 'Hedwiga';

/* update patient's owner: */
UPDATE pacjenci
SET
  id_wlasciciela = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Harry' AND nazwisko = 'Potter')
WHERE
  imie_pacjenta = 'Hedwiga'; 

UPDATE pacjenci
SET
  id_wlasciciela = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Arya' AND nazwisko = 'Stark')
WHERE
  imie_pacjenta = 'Nymeria'; 

/*---------------------------------------------------------------------------------------------------------------------*/

/*Update statements for table `uzytkownicy` */

/* update user's name and surname */
UPDATE uzytkownicy
SET
  imie = 'Alcest',
  nazwisko = 'Mikołajkowy'
WHERE
  imie = 'Alcest' AND 
  nazwisko = 'mikołajkowy';

/* update user's password */
UPDATE uzytkownicy
SET
  haslo = 'FoodIsLife'
WHERE
  imie = 'Alcest' AND 
  nazwisko = 'Mikołajkowy';
  /*id_uzytkownika = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Alcest' AND nazwisko = 'Mikołajkowy');*/

/* update user's phone number */
UPDATE uzytkownicy
SET
  numer_telefonu = '987654321'
WHERE 
  imie = 'Gregory' AND
  nazwisko = 'House';
  /* id_uzytkownika = ... */

/*---------------------------------------------------------------------------------------------------------------------*/

/* Update statements for table `wizyty` */

/* update doctor's id for future appointment - assign a doctor randomly: */
UPDATE wizyty
SET
  id_lekarza = (SELECT id_uzytkownika FROM uzytkownicy WHERE rodzaj_uzytkownika = 'L' ORDER BY RAND() LIMIT 1)
WHERE 
  id_pacjenta = (SELECT id_pacjenta FROM pacjenci WHERE imie_pacjenta = 'Idefix') AND 
  data_wizyty = '2019-07-12';

/* update total cost and whether the appointment has taken place for the appointment */
UPDATE wizyty
SET
  kwota_calkowita = (SELECT cena_uslugi FROM cennik WHERE rodzaj_uslugi = 'sterylizacja') +
                    (SELECT cena_uslugi FROM cennik WHERE rodzaj_uslugi = 'usuwanie kleszcza'),
  czy_juz_sie_odbyla = 1
WHERE 
  id_pacjenta = (SELECT id_pacjenta FROM pacjenci WHERE imie_pacjenta = 'Idefix') AND 
  data_wizyty = '2019-01-18 12:00:00';

  /* update appointment's time */ 
  /* update total cost and whether the appointment has taken place for the appointment */
UPDATE wizyty
SET
  data_wizyty = '2019-07-11 12:00:00'
WHERE 
  id_pacjenta = (SELECT id_pacjenta FROM pacjenci WHERE imie_pacjenta = 'Idefix') AND 
  data_wizyty = '2019-07-12 12:00:00';

/*---------------------------------------------------------------------------------------------------------------------*/

/* Update statements for table `cennik` */

/* update service's name */
UPDATE cennik
SET 
  rodzaj_uslugi = 'amputacja łapy'
WHERE 
  rodzaj_uslugi = 'amptuacja lapki';

/* update service's price */
UPDATE cennik
SET
  cena_uslugi = 59.99
WHERE 
  rodzaj_uslugi = 'nastawienie zwichniętego skrzydła';

/*---------------------------------------------------------------------------------------------------------------------*/

/* Update statements for table `oplaty_za_uslugi` */

/* update client id for given payment */
UPDATE oplaty_za_uslugi
SET 
  id_klienta = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Obelix' AND nazwisko = 'Gal')
WHERE 
  id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00');

/* update appointment id for given payment */
UPDATE oplaty_za_uslugi
SET 
  id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2020-01-01 10:00:00')
WHERE 
  id_wizyty = NULL AND
  id_uslugi = (SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'test');

  /* update service cost and service id for given appointment */
UPDATE oplaty_za_uslugi
SET 
  cena_uslugi = (SELECT cena_uslugi FROM cennik WHERE rodzaj_uslugi = 'test'),
  id_uslugi = (SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'test')
WHERE 
  id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2020-01-01 10:00:00');

/*---------------------------------------------------------------------------------------------------------------------*/
/* Update statements for table `leki` */

/* update med's name */
UPDATE leki
SET 
  nazwa_leku = 'TEST'
WHERE
  nazwa_leku = 'test';

/* update amount bought and amount left for given med */
UPDATE leki
SET
  liczba_zakupionych_sztuk = 15,
  liczba_pozostalych_sztuk = 9
WHERE
  nazwa_leku = 'test' OR nazwa_leku = 'TEST';

/* update unit for given med */
UPDATE leki
SET
  jednostka = 'test_jednostki'
WHERE
  nazwa_leku = 'test' OR nazwa_leku = 'TEST';

/* update purchase price and date and selling price for given med */
UPDATE leki
SET
  cena_kupna = 12.99,
  data_kupna = CURRENT_DATE,
  cena_sp_szt = 18.99
WHERE
  nazwa_leku = 'TEST' OR nazwa_leku = 'TEST';

/*---------------------------------------------------------------------------------------------------------------------*/
/* Update statements for table `oplaty_za_leki` */
/* update client id for given payment */
UPDATE oplaty_za_leki
SET
  id_klienta = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'klient' AND nazwisko = 'test')
WHERE
  id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00');

/* update appointment id for given payment */
UPDATE oplaty_za_leki
SET
  id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2020-01-01 10:00:00')
WHERE
  id_wizyty = NULL AND 
  id_leku = (SELECT id_leku FROM leki WHERE nazwa_leku = 'test');

/* update med id, price and amount for given payment */
UPDATE oplaty_za_leki
SET 
  id_leku = (SELECT id_leku FROM leki WHERE nazwa_leku = 'test'),
  cena = (SELECT cena_sp_szt FROM leki WHERE nazwa_leku = 'test'),
  ilosc = 2
WHERE
  id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00');

/*---------------------------------------------------------------------------------------------------------------------*/
