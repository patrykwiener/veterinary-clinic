/*
*********************************************************************
Name: MySQL-owa baza danych dla przychodni weterynaryjnej - cz.4 - select
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
/* SELECT statements */

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

/*---------------------------------------------------------------------------------------------------------------------*/

/* SELECT statement - a list of patients belonging to a given client */

SELECT id_pacjenta, imie_pacjenta, data_zgonu
FROM pacjenci
WHERE id_wlasciciela = 1;


/* SELECT statement - a list of patients who passed away*/

SELECT id_pacjenta, imie_pacjenta, data_zgonu
FROM pacjenci
WHERE data_zgonu IS NOT NULL;

/*---------------------------------------------------------------------------------------------------------------------*/

/* SELECT statement - a list of clients */

SELECT id_uzytkownika, imie, nazwisko, login, numer_telefonu
FROM uzytkownicy
WHERE rodzaj_uzytkownika = 'K';


/* SELECT statement - a list of doctors */

SELECT id_uzytkownika, imie, nazwisko, login, numer_telefonu
FROM uzytkownicy
WHERE rodzaj_uzytkownika = 'L';

/* SELECT statement - a list of doctors available on a given date at a given time */ 

SELECT id_uzytkownika, imie, nazwisko, login, numer_telefonu
FROM uzytkownicy
WHERE rodzaj_uzytkownika = 'L' 
  AND id_uzytkownika NOT IN (SELECT id_lekarza FROM wizyty WHERE data_wizyty = '2018-01-01 9:00');

/* SELECT statement - one randomly chosen doctor available on a given date at a given time */ 

SELECT id_uzytkownika, imie, nazwisko, login, numer_telefonu
FROM uzytkownicy
WHERE rodzaj_uzytkownika = 'L' 
  AND id_uzytkownika NOT IN (SELECT id_lekarza FROM wizyty WHERE data_wizyty = '2018-01-01 9:00')
ORDER BY RAND()
LIMIT 1;

/*---------------------------------------------------------------------------------------------------------------------*/

/* SELECT statements - a list of all past appointments */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE data_wizyty < curdate();

/* SELECT statements - a list of all past appointments that actually took place */

SELECT id_wizyty, id_lekarza, id_pacjenta, IF(czy_juz_sie_odbyla = 1, 'TAK', 'NIE') AS czy_juz_sie_odbyla, data_wizyty
FROM wizyty
WHERE data_wizyty < curdate() AND czy_juz_sie_odbyla = 1;

/* SELECT statements - a list of all future appointments */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE data_wizyty >= curdate();

/* SELECT statement - a list of appointments for given date */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE DATE(data_wizyty) = '2018-01-01';

/* SELECT statement - a list of appointments for given date and given time*/

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE data_wizyty = '2018-01-01 09:00';

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE data_wizyty = '2018-01-01 12:00'; -- no such appointment in base

/* SELECT statement - a list of appointments for given date for given doctor */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE DATE(data_wizyty) = '2018-01-01' AND id_lekarza = 7;

/* SELECT statement - a list of appointments for given MONTH for given doctor */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE MONTH(data_wizyty) = 1 AND id_lekarza = 7; -- a list of appointments for January for dr Dolittle

/* SELECT statement - a list of all appointments for given MONTH */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE MONTH(data_wizyty) = 1; -- a list of appointments for January

/* SELECT statement - a list of all appointments for given MONTH and YEAR */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE MONTH(data_wizyty) = 1 AND YEAR(data_wizyty) = 2019; -- a list of appointments for January 2019

/* SELECT statement - a list of appointments for given client */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE id_pacjenta IN (SELECT id_pacjenta FROM pacjenci WHERE id_wlasciciela = 1); 

/* SELECT statement - a list of future appointments for given client */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE id_pacjenta IN (SELECT id_pacjenta FROM pacjenci WHERE id_wlasciciela = 1)
  AND data_wizyty >= curdate(); 

/* SELECT statement - a list of appointments for given patient */

SELECT id_wizyty, id_lekarza, id_pacjenta, data_wizyty
FROM wizyty
WHERE id_pacjenta = 1;

/* SELECT statement - an income report for all appointments for a given period of time */

SELECT SUM(kwota_calkowita) AS suma_oplat /* ,  YEAR(data_wizyty) AS za_rok, MONTH(data_wizyty) AS za_miesiac */
FROM wizyty
WHERE data_wizyty BETWEEN '2019-01-01' AND '2019-12-31';

/* SELECT statement - a report for all appointments for a given year */

SELECT COUNT(w.id_wizyty) AS liczba_wizyt,  
    SUM(w.kwota_calkowita) AS suma_oplat, 
    COUNT(w.id_lekarza) AS liczba_pracujacych_lekarzy,
    COUNT(DISTINCT w.id_pacjenta) AS liczba_pacjentow,
    COUNT(DISTINCT p.id_wlasciciela) AS liczba_klientow
FROM wizyty w LEFT JOIN (SELECT id_pacjenta, id_wlasciciela FROM pacjenci) AS p ON w.id_pacjenta = p.id_pacjenta 
WHERE DATE(data_wizyty) BETWEEN '2019-01-01' AND '2019-12-31';

/* SELECT statement - a report for all appointments for a given period of time */

SELECT COUNT(w.id_wizyty) AS liczba_wizyt,  
    SUM(w.kwota_calkowita) AS suma_oplat, 
    COUNT(w.id_lekarza) AS liczba_pracujacych_lekarzy,
    COUNT(DISTINCT w.id_pacjenta) AS liczba_pacjentow,
    COUNT(DISTINCT p.id_wlasciciela) AS liczba_klientow,
    @okres_od := '2019-01-01' AS okres_od,
    @okres_do := '2019-12-31' AS okres_do
FROM wizyty w LEFT JOIN (SELECT id_pacjenta, id_wlasciciela FROM pacjenci) AS p ON w.id_pacjenta = p.id_pacjenta 
WHERE DATE(data_wizyty) BETWEEN @okres_od AND @okres_do;

/*---------------------------------------------------------------------------------------------------------------------*/

/* SELECT statement - a list of all medical services */

SELECT id_uslugi, cena_uslugi, rodzaj_uslugi
FROM cennik;

/* SELECT statement - a list of medical services below a given price point */

SELECT id_uslugi, cena_uslugi, rodzaj_uslugi
FROM cennik
WHERE cena_uslugi <= 100.00;

/* SELECT statement - a list of medical services performed on a given patient */

SELECT c.id_uslugi, c.cena_uslugi, c.rodzaj_uslugi, p.id_pacjenta, p.imie_pacjenta, o.id_klienta
FROM pacjenci p
  LEFT JOIN wizyty w ON p.id_pacjenta = w.id_pacjenta
  LEFT JOIN oplaty_za_uslugi o ON w.id_wizyty = o.id_wizyty
  LEFT JOIN cennik c ON o.id_uslugi = c.id_uslugi
WHERE p.id_pacjenta = 1 AND c.id_uslugi IS NOT NULL;

/* SELECT statement - a list of medical services performed on a given patient on a given date*/

SELECT c.id_uslugi, c.cena_uslugi, c.rodzaj_uslugi, p.id_pacjenta, p.imie_pacjenta, o.id_klienta
FROM pacjenci p
  LEFT JOIN wizyty w ON p.id_pacjenta = w.id_pacjenta
  LEFT JOIN oplaty_za_uslugi o ON w.id_wizyty = o.id_wizyty
  LEFT JOIN cennik c ON o.id_uslugi = c.id_uslugi
WHERE p.id_pacjenta = 1
  AND DATE(w.data_wizyty) = '2018-01-01';


/*---------------------------------------------------------------------------------------------------------------------*/

/* SELECT statement - a list of medical service fees for a given patient for a given visit */

SELECT o.id_op_uslugi, 
        o.id_klienta, 
        o.id_wizyty, 
        o.cena_uslugi, 
        o.id_uslugi, 
        c.rodzaj_uslugi, 
        p.id_pacjenta, 
        p.imie_pacjenta, 
        w.data_wizyty
FROM pacjenci p,
  LEFT JOIN wizyty w ON p.id_pacjenta = w.id_pacjenta
  LEFT JOIN oplaty_za_uslugi o ON w.id_wizyty = o.id_wizyty
  LEFT JOIN cennik c ON o.id_uslugi = c.id_uslugi
WHERE p.id_pacjenta = 1
  AND w.data_wizyty = '2018-01-01';

/* SELECT statement - total cost of medical service fees for a given patient for a given visit */

SELECT COUNT(cena_uslugi) AS koszt_calkowity_wizyty,
      w.data_wizyty,
      w.id_wizyty,
      p.id_pacjenta,
      p.imie_pacjenta,
      w.id_klienta
FROM pacjenci p,
  LEFT JOIN wizyty w ON p.id_pacjenta = w.id_pacjenta
  LEFT JOIN oplaty_za_uslugi o ON w.id_wizyty = o.id_wizyty
  LEFT JOIN cennik c ON o.id_uslugi = c.id_uslugi
WHERE p.id_pacjenta = 1
  AND w.data_wizyty = '2018-01-01';

/*---------------------------------------------------------------------------------------------------------------------*/

/* SELECT statement - a list of all medications (different batches separately) */

SELECT id_leku, 
      nazwa_leku, 
      liczba_zakupionych_sztuk, 
      liczba_pozostalych_sztuk,
      jednostka,
      cena_kupna, 
      data_kupna, 
      cena_sp_szt
FROM leki;

/* SELECT statement - a list of all medications (different batches grouped together) */

SELECT id_leku, 
      nazwa_leku, 
      liczba_pozostalych_sztuk,
      cena_sp_szt
FROM leki
GROUP BY nazwa_leku;

/* SELECT statement - a list of medications in stock and available for purchase */

SELECT id_leku, 
      nazwa_leku, 
      liczba_zakupionych_sztuk, 
      liczba_pozostalych_sztuk,
      jednostka,
      cena_kupna, 
      data_kupna, 
      cena_sp_szt
FROM leki
WHERE liczba_pozostalych_sztuk > 0
  AND data_kupna > DATE_SUB(curdate(), INTERVAL 1 YEAR);
  -- subtract one year from current date and compare it with the day of purchase 
  -- for the given medication to determine whether it's past the "sell by" date

/* SELECT statement - a list of medications that need replenishment */

SELECT id_leku, 
      nazwa_leku, 
      liczba_zakupionych_sztuk, 
      liczba_pozostalych_sztuk,
      jednostka,
      cena_kupna, 
      data_kupna, 
      cena_sp_szt
FROM leki
WHERE liczba_pozostalych_sztuk < 10 
    OR data_kupna < DATE_SUB(curdate(), INTERVAL 1 YEAR); 
    -- subtract one year from current date and compare it with the day of purchase 
    -- for the given medication to determine whether it's past the "sell by" date

/* SELECT statement - a list of all batches of a given medication */

SELECT id_leku, 
      nazwa_leku, 
      liczba_zakupionych_sztuk, 
      liczba_pozostalych_sztuk,
      jednostka,
      cena_kupna, 
      data_kupna, 
      cena_sp_szt
FROM leki
WHERE nazwa_leku = 'Ditrivet 120';

/* SELECT statement - a list of medications whose names contain given word/phrase*/

SET @word = 'pieluchy'; -- assign the desired keyword to search for to a variable
SELECT id_leku, 
      nazwa_leku, 
      liczba_zakupionych_sztuk, 
      liczba_pozostalych_sztuk,
      jednostka,
      cena_kupna, 
      data_kupna, 
      cena_sp_szt
FROM leki
WHERE nazwa_leku LIKE CONCAT('%', @word,'%');

/*---------------------------------------------------------------------------------------------------------------------*/

/* SELECT statement - a list of medication cost for a given patient for a given visit (date only)*/

SET
  @data_wiz = '2018-01-01',   -- initialise variables with values to search for
    @id_pacj = 1;
SELECT o.id_op_leku, 
        o.id_klienta, 
        p.id_pacjenta,
        p.imie_pacjenta,
        o.id_wizyty, 
        w.data_wizyty,
        o.id_leku, 
        l.nazwa_leku,
        o.cena,
        o.ilosc
FROM pacjenci p
  LEFT JOIN wizyty w ON p.id_pacjenta = w.id_pacjenta
  LEFT JOIN oplaty_za_leki o ON w.id_wizyty = o.id_wizyty
  LEFT JOIN leki l ON o.id_leku = l.id_leku
WHERE p.id_pacjenta = @id_pacj
  AND DATE(w.data_wizyty) = @data_wiz;\

/* SELECT statement - a list of medication cost for a given patient for a given visit (date and time)*/

SET
  @data_wiz = '2018-01-01 9:00',   -- initialise variables with values to search for
    @id_pacj = 1;
SELECT o.id_op_leku, 
        o.id_klienta, 
        p.id_pacjenta,
        p.imie_pacjenta,
        o.id_wizyty, 
        w.data_wizyty,
        o.id_leku, 
        l.nazwa_leku,
        o.cena,
        o.ilosc
FROM pacjenci p
  LEFT JOIN wizyty w ON p.id_pacjenta = w.id_pacjenta
  LEFT JOIN oplaty_za_leki o ON w.id_wizyty = o.id_wizyty
  LEFT JOIN leki l ON o.id_leku = l.id_leku
WHERE p.id_pacjenta = @id_pacj
  AND w.data_wizyty = @data_wiz;

/* SELECT statement - total cost of medication cost for a given patient for a given visit */

SET
  @data_wiz = '2018-01-01 9:00',   -- initialise variables with values to search for
  @id_pacj = 1;
SELECT SUM(cena) AS koszt_calkowity_lekow,
      w.data_wizyty,
      w.id_wizyty,
      p.id_pacjenta,
      p.imie_pacjenta,
      o.id_klienta
FROM pacjenci p
  LEFT JOIN wizyty w ON p.id_pacjenta = w.id_pacjenta
  LEFT JOIN oplaty_za_leki o ON w.id_wizyty = o.id_wizyty
  LEFT JOIN leki l ON o.id_leku = l.id_leku
WHERE p.id_pacjenta = @id_pacj
  AND w.data_wizyty = @data_wiz;

/*---------------------------------------------------------------------------------------------------------------------*/