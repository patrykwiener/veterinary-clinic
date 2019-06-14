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

INSERT INTO oplaty_za_uslugi(id_klienta, id_wizyty, id_uslugi)
VALUES
  (1, (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00'), (SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'podanie leku')),
  (1, 1, (SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'test'));

INSERT INTO leki(nazwa_leku)
VALUES
  ('test');

INSERT INTO oplaty_za_leki(id_klienta, id_wizyty, id_leku)
VALUES
  (1, (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00'), 1),
  (1, 1, (SELECT id_leku FROM leki WHERE nazwa_leku = 'test'));
  /* test appointment date:  '2020-01-01 10:00:00' */

/*---------------------------------------------------------------------------------------------------------------------*/
/* UPDATE statements */

/* Update statements for table `pacjenci` */

/* update patient's name: */
UPDATE pacjenci AS p,
  (SELECT * FROM pacjenci WHERE imie_pacjenta = 'Hedwig') AS pacjent
SET
  p.imie_pacjenta = 'Hedwiga'
WHERE
   p.id_pacjenta = pacjent.id_pacjenta;

/* update patient's date of death: */
UPDATE pacjenci AS p,
  (SELECT * FROM pacjenci WHERE imie_pacjenta = 'Hedwiga') AS pacjent
SET
  p.data_zgonu = '1997-07-27'
WHERE
  p.id_pacjenta = pacjent.id_pacjenta;

/* update patient's owner: */
UPDATE pacjenci AS p,
  (SELECT * FROM pacjenci WHERE imie_pacjenta = 'Hedwiga') AS pacjent
SET
  p.id_wlasciciela = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Harry' AND nazwisko = 'Potter')
WHERE
  p.id_pacjenta = pacjent.id_pacjenta;

UPDATE pacjenci AS p,
  (SELECT * FROM pacjenci WHERE imie_pacjenta = 'Hedwiga') AS pacjent
SET
  p.id_wlasciciela = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Arya' AND nazwisko = 'Stark')
WHERE
  p.id_pacjenta = pacjent.id_pacjenta;

/*---------------------------------------------------------------------------------------------------------------------*/

/*Update statements for table `uzytkownicy` */

/* update user's name and surname */
UPDATE uzytkownicy AS u, 
	(SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Alcest' AND nazwisko = 'mikołajkowy') AS uzytk
SET
  u.imie = 'Alcest',
  u.nazwisko = 'Mikołajkowy'
WHERE
  u.id_uzytkownika = uzytk.id_uzytkownika;

/* update user's password */
UPDATE uzytkownicy AS u, 
	(SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Alcest' AND nazwisko = 'Mikołajkowy') AS uzytk
SET
  u.haslo = 'FoodIsLife'
WHERE
  u.id_uzytkownika = uzytk.id_uzytkownika;

/* update user's phone number */
UPDATE uzytkownicy AS u, 
	(SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Gregory' AND nazwisko = 'House') AS uzytk
SET
  u.numer_telefonu = '987654321'
WHERE 
  u.id_uzytkownika = uzytk.id_uzytkownika;

/*---------------------------------------------------------------------------------------------------------------------*/

/* Update statements for table `wizyty` */

/* update doctor's id for future appointment - assign a doctor randomly: */
UPDATE wizyty AS w,
	(SELECT id_wizyty FROM wizyty WHERE id_pacjenta = 
    (SELECT id_pacjenta FROM pacjenci WHERE imie_pacjenta = 'Idefix') AND 
    data_wizyty = '2019-07-12 12:00:00') AS wizyta
SET
  w.id_lekarza = (SELECT id_uzytkownika FROM uzytkownicy WHERE rodzaj_uzytkownika = 'L' ORDER BY RAND() LIMIT 1)
WHERE 
  w.id_wizyty = wizyta.id_wizyty;

/* update total cost and whether the appointment has taken place for the appointment */
UPDATE wizyty AS w,
	(SELECT id_wizyty FROM wizyty WHERE 
    id_pacjenta = (SELECT id_pacjenta FROM pacjenci WHERE imie_pacjenta = 'Idefix') AND 
    data_wizyty = '2019-01-18 12:00:00') AS wizyta
SET
  w.kwota_calkowita = (SELECT cena_uslugi FROM cennik WHERE rodzaj_uslugi = 'sterylizacja') +
                    (SELECT cena_uslugi FROM cennik WHERE rodzaj_uslugi = 'usuwanie kleszcza'),
  w.czy_juz_sie_odbyla = 1
WHERE 
  w.id_wizyty = wizyta.id_wizyty;

  /* update appointment's time */ 
  /* update total cost and whether the appointment has taken place for the appointment */
UPDATE wizyty AS w,
	(SELECT id_wizyty FROM wizyty WHERE id_pacjenta = 
    (SELECT id_pacjenta FROM pacjenci WHERE imie_pacjenta = 'Idefix') AND 
    data_wizyty = '2019-07-12 12:00:00') AS wizyta
SET
  w.data_wizyty = '2019-07-11 12:00:00'
WHERE 
  w.id_wizyty = wizyta.id_wizyty;

/*---------------------------------------------------------------------------------------------------------------------*/

/* Update statements for table `cennik` */

/* update service's name */
UPDATE cennik AS c,
	(SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'amptuacja lapki') AS usluga
SET 
  c.rodzaj_uslugi = 'amputacja łapy'
WHERE 
  c.id_uslugi = usluga.id_uslugi;

/* update service's price */
UPDATE cennik AS c,
	(SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'nastawienie zwichniętego skrzydła') AS usluga
SET
  c.cena_uslugi = 59.99
WHERE 
  c.id_uslugi = usluga.id_uslugi;

/*---------------------------------------------------------------------------------------------------------------------*/

/* Update statements for table `oplaty_za_uslugi` */

/* update client id for given payment */
UPDATE oplaty_za_uslugi AS op,
	(SELECT id_op_uslugi 
    FROM oplaty_za_uslugi 
    WHERE id_wizyty = 
		(SELECT id_wizyty 
		FROM wizyty 
        WHERE data_wizyty = '2019-07-12 10:00:00')) AS oplata
SET 
  op.id_klienta = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'Obelix' AND nazwisko = 'Gal')
WHERE 
  op.id_op_uslugi = oplata.id_op_uslugi;

/* update appointment id for given payment */
UPDATE oplaty_za_uslugi AS op,
	(SELECT id_op_uslugi 
    FROM oplaty_za_uslugi 
    WHERE id_wizyty = NULL AND
		id_uslugi = (SELECT id_uslugi 
					FROM cennik 
                    WHERE rodzaj_uslugi = 'test')) AS oplata
SET 
  op.id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2020-01-01 10:00:00')
WHERE 
  op.id_op_uslugi = oplata.id_op_uslugi;

  /* update service cost and service id for given appointment */
UPDATE oplaty_za_uslugi AS op,
	(SELECT id_op_uslugi FROM oplaty_za_uslugi WHERE  
    id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2020-01-01 10:00:00')) AS oplata
SET 
  op.cena_uslugi = (SELECT cena_uslugi FROM cennik WHERE rodzaj_uslugi = 'test'),
  op.id_uslugi = (SELECT id_uslugi FROM cennik WHERE rodzaj_uslugi = 'test')
WHERE
  op.id_op_uslugi = oplata.id_op_uslugi;

/*---------------------------------------------------------------------------------------------------------------------*/
/* Update statements for table `leki` */

/* update med's name */
UPDATE leki AS l,
	(SELECT id_leku FROM leki WHERE nazwa_leku = 'test') AS lek
SET 
  l.nazwa_leku = 'TEST'
WHERE
  l.id_leku = lek.id_leku;

/* update amount bought and amount left for given med */
UPDATE leki AS l,
	(SELECT id_leku FROM leki WHERE nazwa_leku = 'test' OR nazwa_leku = 'TEST') AS lek
SET
  l.liczba_zakupionych_sztuk = 15,
  l.liczba_pozostalych_sztuk = 9
WHERE
  l.id_leku = lek.id_leku;

/* update unit for given med */
UPDATE leki AS l,
	(SELECT id_leku FROM leki WHERE nazwa_leku = 'test' OR nazwa_leku = 'TEST') AS lek
SET
  l.jednostka = 'test_jednostki'
WHERE
  l.id_leku = lek.id_leku;

/* update purchase price and date and selling price for given med */
UPDATE leki AS l,
	(SELECT id_leku FROM leki WHERE nazwa_leku = 'test' OR nazwa_leku = 'TEST') AS lek
SET
  l.cena_kupna = 12.99,
  l.data_kupna = CURRENT_DATE,
  l.cena_sp_szt = 18.99
WHERE
  l.id_leku = lek.id_leku;

/*---------------------------------------------------------------------------------------------------------------------*/
/* Update statements for table `oplaty_za_leki` */
/* update client id for given payment */
UPDATE oplaty_za_leki AS op,
	(SELECT id_op_leku FROM oplaty_za_leki WHERE
    id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00')) AS oplata
SET
  op.id_klienta = (SELECT id_uzytkownika FROM uzytkownicy WHERE imie = 'klient' AND nazwisko = 'test')
WHERE
  op.id_op_leku = oplata.id_op_leku;

/* update appointment id for given payment */
UPDATE oplaty_za_leki AS op,
	(SELECT id_op_leku FROM oplaty_za_leki WHERE
    id_wizyty = NULL AND 
    id_leku = (SELECT id_leku FROM leki WHERE nazwa_leku = 'test')) AS oplata
SET
  op.id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2020-01-01 10:00:00')
WHERE
  op.id_op_leku = oplata.id_op_leku;

/* update med id, price and amount for given payment */
UPDATE oplaty_za_leki AS op,
	(SELECT id_op_leku FROM oplaty_za_leki WHERE
    id_wizyty = (SELECT id_wizyty FROM wizyty WHERE data_wizyty = '2019-07-12 10:00:00')) AS oplata
SET 
  op.id_leku = (SELECT id_leku FROM leki WHERE nazwa_leku = 'test'),
  op.cena = (SELECT cena_sp_szt FROM leki WHERE nazwa_leku = 'test'),
  op.ilosc = 2
WHERE
  op.id_op_leku = oplata.id_op_leku;

/*---------------------------------------------------------------------------------------------------------------------*/
