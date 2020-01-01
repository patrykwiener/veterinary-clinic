DROP TABLE IF EXISTS `dane_firmy`;

CREATE TABLE `dane_firmy` (
  `id_dane` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nazwa_firmy` VARCHAR(200) NOT NULL,
  `wlasciciel_firmy` VARCHAR(50),
  `adres_kraj` VARCHAR(20) NOT NULL,
  `adres_woj` VARCHAR(20),
  `adres_powiat` VARCHAR(20),
  `adres_gmina` VARCHAR(20),
  `adres_kod` VARCHAR(10), 
  `adres_miejsc` VARCHAR(30) NOT NULL,
  `adres_ulica_nr` VARCHAR(100) NOT NULL,
  `adres_koresp` VARCHAR(300),
  `nr_tel` VARCHAR(30) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `nip` VARCHAR(13)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dane_firmy` */

/* inserting company details: */
insert  into `dane_firmy`(`nazwa_firmy`, 
							`wlasciciel_firmy`, 
                            `adres_kraj`, 
                            `adres_woj`, 
                            `adres_powiat`, 
							`adres_kod`, 
                            `adres_miejsc`, 
                            `adres_ulica_nr`, 
							`nr_tel`,
                            `email`,
                            `nip`) values 

('Klinika Weterynaryjna Polsl', 
'Pan Wlasciciel', 
'Polska', 
'śląskie', 
'gliwicki', 
'44-100',
'Gliwice', 
'Akademicka 16/404', 
'+48 32 0000000',
'klinika.kontakt@polsl.pl',
'012-34-56-789');
                            
/* Update mailing address */
SET
	@adr = (SELECT CONCAT(nazwa_firmy, ', ', adres_ulica_nr, ', ', adres_kod, ' ', adres_miejsc, ', ', adres_kraj) AS adr FROM dane_firmy WHERE id_dane = 1); 
UPDATE dane_firmy
SET
  adres_koresp = @adr
WHERE
   id_dane = 1;
   
/* selecting company details */ 
SELECT * FROM dane_firmy;

DELETE FROM dane_firmy
WHERE id_dane = 2;

