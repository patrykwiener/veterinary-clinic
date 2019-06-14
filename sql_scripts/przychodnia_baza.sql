/*
*********************************************************************
Based on the script from http://www.mysqltutorial.org
*********************************************************************
Name: MySQL-owa baza danych dla przychodni weterynaryjnej 
Link: --
Version 1.0
Author: Maja Kuricus, majakur297@student.polsl.pl
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=``*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=`NO_AUTO_VALUE_ON_ZERO` */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`przychodnia` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci`*/;

USE `przychodnia`;

/*---------------------------------------------------------------------------------------------------------------------*/

/*Table structure for table `pacjenci` */

DROP TABLE IF EXISTS `pacjenci`;

CREATE TABLE `pacjenci` (
  `id_pacjenta` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `id_wlasciciela` INT NOT NULL,
  `imie_pacjenta` VARCHAR(20) NOT NULL,
  /* `data_ur` DATE NOT NULL, */
  `data_zgonu` DATE DEFAULT NULL,
  FOREIGN KEY fk_wlasciciel(id_wlasciciela)
  REFERENCES uzytkownicy(id_uzytkownika)
  ON UPDATE CASCADE
  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pacjenci` */

/* inserting with indices: */
insert  into `pacjenci`(`id_wlasciciela`,`imie_pacjenta`,`data_zgonu`) values 

(1,'Szarik', '1970-01-01'),   -- Jan Kos
(2,'Saba', '1885-01-01' ),     -- Nel Rawlison
(3,'Moby Dick', '1851-01-01'),     -- Ahab Kapitan
(4,'Idefix', NULL),     -- Obelix Gal
(5, 'Tytus de Zoo', '2008-01-01'), -- Papcio Chmiel
(6, 'Bucefał', '326-03-26') -- Aleksander Wielki
;

/* inserting without an index: */
insert  into `pacjenci`(`id_wlasciciela`, `imie_pacjenta`,`data_zgonu`) values 

(7, 'Polinezja', '1952-01-01');   -- Dr. Dolittle

/*---------------------------------------------------------------------------------------------------------------------*/

/*Table structure for table `uzytkownicy` */

DROP TABLE IF EXISTS `uzytkownicy`;

CREATE TABLE `uzytkownicy` (
  `id_uzytkownika` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `imie` VARCHAR(20) NOT NULL,
  `nazwisko` VARCHAR(20) NOT NULL,
  `rodzaj_uzytkownika` char(1) NOT NULL,
  `login` VARCHAR(20) NOT NULL,
  `haslo` VARCHAR(20) NOT NULL,
  `numer_telefonu` VARCHAR(20) NOT NULL,
  /* `email` VARCHAR(50), 
  -- UNIQUE KEY unique_email(email) */
  UNIQUE KEY unique_login(login)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `uzytkownicy` */
/* inserting clients with indices: */
insert  into `uzytkownicy`(`id_uzytkownika`,`imie`,`nazwisko`,`rodzaj_uzytkownika`,`login`,`haslo`,`numer_telefonu`) values 

(1,'Jan','Kos','K','janek','janek','111111111'),
(2,'Nel','Rawlison','K','nel','nel','222222222'),
(3,'Ahab','Kapitan','K','ahab','ahab','333333333'),
(4,'Obelix','Gal','K','obelix','obelix','444444444'),
(5,'Papcio','Chmiel','K','papcio','papcio','555555555'),
(6,'Aleksander','Wielki','K','aleksander','aleksander','666666666')
;

/* inserting doctors with indices: */
insert  into `uzytkownicy`(`id_uzytkownika`,`imie`,`nazwisko`,`rodzaj_uzytkownika`,`login`,`haslo`,`numer_telefonu`) values 

(7,'John','Dolittle','L','dolittle','dolittle','777777777');

/* inserting (doctors) without indices: */
insert  into `uzytkownicy`(`imie`,`nazwisko`,`rodzaj_uzytkownika`,`login`,`haslo`,`numer_telefonu`) values 

('Fred','Vet','L','fred','fred','123456789');


/*---------------------------------------------------------------------------------------------------------------------*/

/*Table structure for table `wizyty` */

DROP TABLE IF EXISTS `wizyty`;

CREATE TABLE `wizyty` (
  `id_wizyty` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `id_lekarza` INT NOT NULL,
  `id_pacjenta` INT NOT NULL,
  `kwota_calkowita` DECIMAL(5,2) DEFAULT 0.00,
  `czy_juz_sie_odbyla` TINYINT(1) NOT NULL DEFAULT 0,
  `data_wizyty` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY fk_lekarz(id_lekarza)
  REFERENCES uzytkownicy(id_uzytkownika)
  ON UPDATE CASCADE
  ON DELETE RESTRICT,
  UNIQUE KEY unique_date(data_wizyty)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wizyty` */

/* inserting past appointments with indices and total cost: */
insert  into `wizyty`(`id_wizyty`,`id_lekarza`,`id_pacjenta`,`kwota_calkowita`,`czy_juz_sie_odbyla`,`data_wizyty`) values 

(1, 7, 1, 100.99, 1, '2018-01-01 09:00:00'); 

/* inserting future appointments without indices, total cost and "took place" flag: */
insert  into `wizyty`(`id_lekarza`,`id_pacjenta`, `data_wizyty`) values 

(7, 1, '2019-11-01 09:30:00');

/*---------------------------------------------------------------------------------------------------------------------*/

/*Table structure for table `cennik` */

DROP TABLE IF EXISTS `cennik`;

CREATE TABLE `cennik` (
  `id_uslugi` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `cena_uslugi` DECIMAL(5,2) DEFAULT 0.00,
  `rodzaj_uslugi` VARCHAR(50) NOT NULL,
  UNIQUE KEY unique_rodzaj(rodzaj_uslugi)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cennik` */

/* inserting medical services with indices: */
insert  into `cennik`(`id_uslugi`,`cena_uslugi`,`rodzaj_uslugi`) values 

(1, 50.99, 'usuwanie kleszcza'),
(2, 200.00, 'sterylizacja'); 

/* inserting medical parameters without indices: */
insert  into `cennik`(`cena_uslugi`,`rodzaj_uslugi`) values 

(150.00, 'szczepienie przeciw wściekliźnie'),
(50.00, 'konsultacja weterynaryjna');

/*---------------------------------------------------------------------------------------------------------------------*/

/*Table structure for table `oplaty_za_uslugi` */

DROP TABLE IF EXISTS `oplaty_za_uslugi`;

CREATE TABLE `oplaty_za_uslugi` (
  `id_op_uslugi` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `id_klienta` INT NOT NULL,
  `id_wizyty` INT NOT NULL,
  `cena_uslugi` DECIMAL(5,2) DEFAULT 0.00,
  `id_uslugi` INT NOT NULL,
  FOREIGN KEY fk_klient(id_klienta)
  REFERENCES uzytkownicy(id_uzytkownika)
  ON UPDATE CASCADE
  ON DELETE RESTRICT,
  FOREIGN KEY fk_wizyta(id_wizyty)
  REFERENCES wizyty(id_wizyty)
  ON UPDATE CASCADE
  ON DELETE RESTRICT,
  FOREIGN KEY fk_usluga(id_uslugi)
  REFERENCES cennik(id_uslugi)
  ON UPDATE CASCADE
  ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oplaty_za_uslugi` */

/* inserting billings for appointments with indices and total cost: */
insert  into `oplaty_za_uslugi`(`id_op_uslugi`,`id_klienta`,`id_wizyty`,`cena_uslugi`,`id_uslugi`) values 

(1, 1, 1, 50.99, 2); 

/* inserting billings for appointments without indices: */
insert  into `oplaty_za_uslugi`(`id_klienta`,`id_wizyty`,`cena_uslugi`,`id_uslugi`) values 

(1, 1, 200, 1); 

/*---------------------------------------------------------------------------------------------------------------------*/

/*Table structure for table `leki` */

DROP TABLE IF EXISTS `leki`;

CREATE TABLE `leki` (
  `id_leku` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nazwa_leku` VARCHAR(50) NOT NULL,
  `liczba_zakupionych_sztuk` INT NOT NULL DEFAULT 0,
  `liczba_pozostalych_sztuk` INT NOT NULL DEFAULT 0,
  `jednostka` VARCHAR(10) NOT NULL,
  `cena_kupna` DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  `data_kupna` DATE NOT NULL,
  `cena_sp_szt` DECIMAL(5,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `leki` */

/* inserting medicines with indices: */
insert  into `leki`(`id_leku`,`nazwa_leku`,`liczba_zakupionych_sztuk`,`liczba_pozostalych_sztuk`,`jednostka`,`cena_kupna`,`data_kupna`,`cena_sp_szt`) values 

(1,'Vermicon spray przeciw pchłom 250ml',10,10,'butelka',40.00,'2018-12-12',58.10),
(2,'Pieluchy dla psów - samców - 12 szt. SM',10,10,'opak.',33.50,'2018-12-13',43.60),
(3,'Pieluchy dla psów - samców - 12 szt. ML',10,10,'opak.',38.50,'2018-12-13',50.10),
(4,'Pieluchy dla psów - samców - 12 szt. LXL',10,10,'opak.',41.30,'2018-12-13',53.70)
; 

/* inserting medicines without indices, amounts, units, dates and prices: */
insert  into `leki`(`nazwa_leku`, `jednostka`, `data_kupna`) values 

('Ditrivet 120', 'opak.', CURRENT_DATE);

/*---------------------------------------------------------------------------------------------------------------------*/

/*Table structure for table `oplaty_za_leki` */

DROP TABLE IF EXISTS `oplaty_za_leki`;

CREATE TABLE `oplaty_za_leki` (
  `id_op_leku` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `id_klienta` INT NOT NULL,
  `id_wizyty` INT NOT NULL,
  `id_leku` INT NOT NULL,
  `cena` DECIMAL(5,2) DEFAULT 0.00,
  `ilosc` INT NOT NULL,
  FOREIGN KEY fk_klient_op(id_klienta)
  REFERENCES uzytkownicy(id_uzytkownika)
  ON UPDATE CASCADE
  ON DELETE RESTRICT,
  FOREIGN KEY fk_wizyta_op(id_wizyty)
  REFERENCES wizyty(id_wizyty)
  ON UPDATE CASCADE
  ON DELETE RESTRICT,
  FOREIGN KEY fk_lek_opcennik(id_leku)
  REFERENCES leki(id_leku)
  ON UPDATE CASCADE
  ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oplaty_za_leki` */

/* inserting billings for appointments with indices and total cost: */
insert  into `oplaty_za_leki`(`id_op_leku`,`id_klienta`,`id_wizyty`, `id_leku` ,`cena`,`ilosc`) values 

(1, 1, 1, 1, 58.10, 1); 

/* inserting billings for appointments without indices: */
insert  into `oplaty_za_leki`(`id_klienta`,`id_wizyty`, `id_leku` ,`cena`,`ilosc`) values  

(1, 2, 2, 43.60, 1); 

/*---------------------------------------------------------------------------------------------------------------------*/

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
