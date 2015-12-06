
-- Suppression des tables
drop table if exists Avoir;
drop table if exists Restaurant;
drop table if exists gps;
drop table if exists Periode;
drop table if exists Note;

.print "\n======== Creation des tables ========\n"

-- Creation de la table Note
.print "CREATE TABLE Note"
create table if not exists Note (

	idnote integer primary key autoincrement,
	valeur integer not null, check(valeur between 0 and 5)
);

-- Creation de la table Periode
.print "CREATE TABLE Periode"
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
.print "CREATE TABLE GPS"
create table if not exists gps (

	idgps integer primary key autoincrement,
	latitude double not null,
	longitude double not null
);


-- Création de la table Restaurant
.print "CREATE TABLE Restaurant"

create table if not exists Restaurant (

	idresto integer primary key autoincrement,
	nom text not null unique,
	adresse text not null,
	ville text not null,
	tel text not null,
	web text not null,
	photo text not null,
	cout integer not null,
	type_cuisine text not null,
	idgps not null unique,
	check(type_cuisine in ("Classique","Végétarien","Italien","Chinois","Japonais","Fast food"))
	foreign key(idgps) references gps(idgps)
);

-- Création de la table Avoir
.print "CREATE TABLE Avoir"
create table if not exists Avoir (

	idresto integer,
	idnote integer,
	primary key(idresto,idnote),
	foreign key(idresto) references Restaurant(idresto),
	foreign key(idnote) references Note(idnote)
);


-- Tests
.print "\n======== Tests ========\n"

insert into Note(valeur) values(3);
insert into Note(valeur) values(1);
insert into Note(valeur) values(4);
select * from Note;

/*.print "Test Note negatif"
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
.print "Test Note positif"*/
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Vendredi",10,23);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Samedi",15,2);
.print ""
.print "SELECT"
select * from Periode;
.print ""

/*.print "Test GPS negatif"
insert into gps(latitude,longitude) values(null,0.256);
insert into gps(latitude,longitude) values(0.256,null);
insert into gps(latitude,longitude) values(null,null);
.print "Test GPS positif"*/
insert into gps(latitude,longitude) values(48.949685,72.256147);
insert into gps(latitude,longitude) values(48.902229, 2.302833);
insert into gps(latitude,longitude) values(48.829372,2.381068);
.print ""
.print "SELECT"
select * from gps;

insert into Restaurant(nom,adresse,ville,tel,web,photo,cout,type_cuisine,idgps) 
values("Lunch time","Rue de Neuilly","Clichy","01xxxxxxxx","www.toto.dump","photo",6,"Fast food",2);
insert into Restaurant(nom,adresse,ville,tel,web,photo,cout,type_cuisine,idgps) 
values("Pomme de Pain","9-13, Rue Marie André Lagroua Weill Halle","Paris","01xxxxxxxx",
		"www.pommedepain.fr","photo",9,"Classique",3);
.print ""
.print "SELECT"
select * from Restaurant;





