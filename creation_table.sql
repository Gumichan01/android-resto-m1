
-- Suppression des tables
drop table if exists gps;
drop table if exists Periode;
drop table if exists Note;

.print "\n======== Création des tables ========\n"

-- Creation de la table Note
.print "Création de Note"
create table if not exists Note (

	idnote integer primary key autoincrement,
	valeur integer not null, check(valeur between 0 and 5)
);

-- Creation de la table Periode
.print "Création de Periode"
create table if not exists Periode (

	idperiode integer primary key autoincrement,
	jour text not null,
	heure_ouverture integer not null,
	heure_fermeture integer not null,
	check(jour in ("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi",
		"Dimanche")),
check(heure_ouverture between 0 and 23),
check(heure_fermeture between 0 and 23)
);

-- Création de la table gps
.print "Création de gps"
create table if not exists gps (

	idgps integer primary key autoincrement,
	latitude double not null,
	longitude double not null
);


-- Tests
.print "\n======== Tests ========\n"

.print "Test Note negatif"
insert into Note(valeur) values(null);
.print "Test Note positif"
insert into Note(valeur) values(3);
insert into Note(valeur) values(1);
insert into Note(valeur) values(4);
select * from Note;

.print "Test Note negatif"
insert into Periode(jour,heure_ouverture,heure_fermeture) values(null,9,19);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,22);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",8,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values(null,null,0);
insert into Periode(jour,heure_ouverture,heure_fermeture) values(null,8,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values(null,null,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("STOP",8,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",64,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,64);
.print "Test Note positif"
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Vendredi",10,23);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Samedi",15,2);
.print ""
.print "SELECT"
select * from Periode;
.print ""

.print "Test GPS negatif"
insert into gps(latitude,longitude) values(null,0.256);
insert into gps(latitude,longitude) values(0.256,null);
insert into gps(latitude,longitude) values(null,null);
.print "Test GPS positif"
insert into gps(latitude,longitude) values(48.949685,72.256147);
insert into gps(latitude,longitude) values(48.829372,2.381068);
insert into gps(latitude,longitude) values(48.862537,2.463994);
.print ""
.print "SELECT"
select * from gps;


