
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


-- Creation de la table Periode
.print "CREATE TABLE Periode"
create table Periode (

	jour text not null,
	heure_ouverture_matinale integer not null,
	heure_fermeture_matinale integer not null,
	
	heure_ouverture_aprem integer not null,
	heure_fermeture_aprem integer not null,

	check(jour in ("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi",
		"Dimanche")),

	check(heure_ouverture_matinale between 8 and 12),
	check(heure_fermeture_matinale between 9 and 12),
	check(heure_ouverture_aprem between 12 and 18),
	check(heure_fermeture_aprem between 13 and 23)
);


-- Création de la table Restaurant
.print "CREATE TABLE Restaurant"

create table Restaurant (

	nom text,
	adresse text,
	tel text,
	web text,
	photo text,
	cout integer,
	note integer,
	type_cuisine text,
	latitude double,
	longitude double,
	check(type_cuisine in ("Classique","Végétarien","Italien","Chinois","Japonais","Fast food")),
	check(note between 0 and 5)
);


/*create table Restaurant(

	nom text,
	adresse text,
	tel text,
	web text,
	photo text,
	cout integer,
	note integer,
	type_cuisine text,
	latitude double,
	longitude double,
	check(type_cuisine in ("Classique","Végétarien","Italien","Chinois","Japonais","Fast food")),
	check(note between 0 and 5)
);*/



-- Création de la table Ouvrir
.print "CREATE TABLE Ouvrir"
create table if not exists Ouvrir (

	idresto integer,
	idperiode integer,
	primary key(idresto,idperiode),
	foreign key(idresto) references Restaurant(idresto),
	foreign key(idperiode) references Periode(idperiode)
);

