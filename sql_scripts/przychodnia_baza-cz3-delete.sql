/*
*********************************************************************
Name: MySQL-owa baza danych dla przychodni weterynaryjnej - cz.3 - delete
Link: --
Version 1.0
Author: Maja Kuricus, majakur297@student.polsl.pl
*********************************************************************
*/

USE `przychodnia`;

/*---------------------------------- INSERT statements for testing: --------------------------------------------------



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

---------------------------------------------------------------------------------------------------------------------*/
/* DELETE statements */

/* Records in table `pacjenci`:

id_pacjenta,id_wlasciciela,imie_pacjenta,data_zgonu
1,1,Szarik,1970-01-01
2,2,Saba,1885-01-01
3,3,"Moby Dick",1851-01-01
4,4,Idefix,NULL
5,5,"Tytus de Zoo",2008-01-01
6,6,Bucefał,0326-03-26
7,7,Polinezja,1952-01-01
8,10,Hedwiga,1997-07-27
9,1,Nymeria,NULL

/* Delete statement for table `pacjenci` */

DELETE FROM pacjenci
WHERE
   id_pacjenta = 10;

/*---------------------------------------------------------------------------------------------------------------------*/

/*Delete statement for table `uzytkownicy` */

/* update user's name and surname */
DELETE FROM uzytkownicy 
WHERE
  id_uzytkownika = 15;

/*---------------------------------------------------------------------------------------------------------------------*/

/* Delete statement for table `wizyty` */

/* update doctor's id for future appointment - assign a doctor randomly: */
DELETE FROM wizyty
WHERE 
  id_wizyty = 6;

/*---------------------------------------------------------------------------------------------------------------------*/

/* Delete statement for table `cennik` */

DELETE FROM cennik
WHERE
  id_uslugi = 17;

/*---------------------------------------------------------------------------------------------------------------------*/

/* Delete statement for table `oplaty_za_uslugi` */

DELETE FROM oplaty_za_uslugi
WHERE
  id_op_uslugi = 5;

/*---------------------------------------------------------------------------------------------------------------------*/
/* Delete statement for table `leki` */

DELETE FROM leki
WHERE
  id_leku = 7;

/*---------------------------------------------------------------------------------------------------------------------*/
/* Delete statement for table `oplaty_za_leki` */

DELETE FROM oplaty_za_leki
WHERE
  id_op_leku = 5;

/*---------------------------------------------------------------------------------------------------------------------*/
