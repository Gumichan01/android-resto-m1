
/*

	Script de création de table

	Utile our le projet

*/


-- Suppression des tables
drop table if exists Ouvrir;
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


-- Création de la table Ouvrir
.print "CREATE TABLE Ouvrir"
create table if not exists Ouvrir (

	idresto integer,
	idperiode integer,
	primary key(idresto,idperiode),
	foreign key(idresto) references Restaurant(idresto),
	foreign key(idperiode) references Periode(idperiode)
);

